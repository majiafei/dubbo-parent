package cn.e3mall.dubbo.model;

import java.io.Serializable;

/**
 * @ProjectName: house
 * @Package: cn.e3mall.dubbo.model
 * @ClassName: RequestData
 * @Author: majiafei
 * @Description:
 * @Date: 2019/1/21 19:12
 */
public class RequestData implements Serializable {

    private String interfaceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

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

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
