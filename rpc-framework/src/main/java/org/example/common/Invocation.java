package org.example.common;

import java.io.Serializable;
import java.util.Arrays;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/3 21:14
 ***/
public class Invocation implements Serializable {
    private String interfaceName;
    private String version;
    private String methodName;
    private Class[] parameterTypes;
    private Object[] parameter;

    public Invocation() {

    }

    public Invocation(String interfaceName, String version, String methodName, Class[] parameterTypes, Object[] parameter) {
        this.interfaceName = interfaceName;
        this.version = version;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.parameter = parameter;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameter() {
        return parameter;
    }

    public void setParameter(Object[] parameter) {
        this.parameter = parameter;
    }


    @Override
    public String toString() {
        return "Invocation{" +
                "interfaceName='" + interfaceName + '\'' +
                ", version='" + version + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", parameter=" + Arrays.toString(parameter) +
                '}';
    }
}
