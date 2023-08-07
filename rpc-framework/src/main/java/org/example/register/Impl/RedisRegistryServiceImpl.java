package org.example.register.Impl;

import org.example.common.ServiceMeta;
import org.example.config.RpcProperties;
import org.example.register.RegistryService;
import org.example.utils.JsonUtils;
import org.example.utils.RpcServiceNameBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/***
 * @Description 使用redis来作为服务注册中心
 * @Author zhucui
 * @DateTime 2023/8/7 21:28
 ***/
public class RedisRegistryServiceImpl implements RegistryService {
    private JedisPool jedisPool;

    private String UUID;

    private static final int ttl = 10 * 1000;
    /**
     * 服务名称的集合
     */
    private Set<String> serviceKeySet = new HashSet<>();
    /**
     * 单线程定时任务执行心跳机制
     */
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public RedisRegistryServiceImpl() {
        RpcProperties properties = RpcProperties.getInstance();
        String[] address = properties.getRegisterAddr().split(":");
        JedisPoolConfig config = new JedisPoolConfig();
        // redis最大连接数
        config.setMaxTotal(10);
        // redis最大空闲连接数
        config.setMaxIdle(5);
        jedisPool = new JedisPool(config, address[0], Integer.parseInt(address[1]));
        UUID = java.util.UUID.randomUUID().toString();
        // 心跳检测
        heartbeat();

    }

    @Override
    public void registry(ServiceMeta service) {
        String serviceKey = RpcServiceNameBuilder.buildServiceKey(
                service.getServiceGroup(),
                service.getServiceName(),
                service.getServiceVersion());
        if (!serviceKeySet.contains(serviceKey)) {
            serviceKeySet.add(serviceKey);
        }
        service.setUUID(UUID);
        service.setEndTime(System.currentTimeMillis() + ttl);
        Jedis jedis = getJedis();
        // values中：第一个是节点，第二个是过期时间
        String script = "redis.call('RPUSH', KEYS[1], ARGV[1])\n" +
                "redis.call('EXPIRE', KEYS[1], ARGV[2])";
        List<String> values = new ArrayList<>();
        values.add(JsonUtils.toJson(service));
        values.add(String.valueOf(10));
        // keys中只有一个参数，即group, serviceName, version组成的字符串
        jedis.eval(script, Collections.singletonList(serviceKey), values);
        jedis.close();
    }

    @Override
    public void unRegistry(ServiceMeta service) {
        String serviceKey = RpcServiceNameBuilder.buildServiceKey(
                service.getServiceGroup(),
                service.getServiceName(),
                service.getServiceVersion());
        // redis中可以不用管，到期会自动清除
        serviceKeySet.remove(serviceKey);
    }

    @Override
    public void destroy() {

    }

    @Override
    public List<ServiceMeta> getService(String serviceName) {
        return listServices(serviceName);
    }

    /**
     * 心跳检测机制：用一个线程执行定时任务，每5秒钟检测每一个服务对应的节点是否都存活
     */
    private void heartbeat() {
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            for (String key : serviceKeySet) {
                // 获取所有的服务节点，查询服务节点是否过期
                List<ServiceMeta> serviceNodes = listServices(key);
                Iterator<ServiceMeta> iterator = serviceNodes.iterator();
                while (iterator.hasNext()) {
                    ServiceMeta node = iterator.next();
                    // 删除过期节点
                    if (node.getEndTime() < System.currentTimeMillis()) {
                        iterator.remove();
                    }
                    // 如果是自身，即redis注册中心的服务，则续签，每次续签5秒
                    if (node.getUUID().equals(UUID)) {
                        node.setEndTime(node.getEndTime() + ttl >> 1);
                    }
                }
                // 重新加载服务
                if (serviceNodes.size() != 0) {
                    reload(key, serviceNodes);
                }
            }
        }, 5, 5, TimeUnit.SECONDS);

    }

    /**
     * 获取一个redis连接
     *
     * @return
     */
    private Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        RpcProperties properties = RpcProperties.getInstance();
        jedis.auth(properties.getRegisterPsw());
        return jedis;
    }

    /**
     * 获取所有的服务
     *
     * @param serviceKey
     * @return
     */
    private List<ServiceMeta> listServices(String serviceKey) {
        // 本地都没有注册，直接不用访问redis注册中心了
        if (!serviceKeySet.contains(serviceKey)) {
            return null;
        }
        Jedis jedis = getJedis();
        List<String> list = jedis.lrange(serviceKey, 0, -1);
        jedis.close();
        return list.stream().map(
                o -> JsonUtils.fromJson(o, ServiceMeta.class)
        ).collect(Collectors.toList());
    }

    /**
     * 重新加载服务
     *
     * @param serviceKey
     * @param serviceList
     */
    private void reload(String serviceKey, List<ServiceMeta> serviceList) {
        // Lua脚本，redis原子操作；
        // 先删除key, 然后重新push存活的节点
        String script = "redis.call('DEL', KEYS[1])\n" +
                "for i = 1, #ARGV do\n" +
                "   redis.call('RPUSH', KEYS[1], ARGV[i])\n" +
                "end \n" +
                "redis.call('EXPIRE', KEYS[1],KEYS[2])";
        List<String> keys = new ArrayList<>();
        keys.add(serviceKey);
        // key的存活时间
        keys.add(String.valueOf(10));
        List<String> values = serviceList.stream().map(o -> JsonUtils.toJson(o)).collect(Collectors.toList());
        Jedis jedis = getJedis();
        jedis.eval(script, keys, values);
        jedis.close();
    }
}
