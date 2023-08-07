package org.example.annotation;

import java.lang.annotation.*;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/7 10:37
 ***/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface RpcService {
    /**
     * service group
     * @return
     */
    String group() default "";

    /**
     * service version
     *
     * @return
     */
    String version() default "";

    /**
     * 实现的接口类， 用来进行特殊指定
     *
     * @return
     */
    Class interfaceClass() default void.class;
}
