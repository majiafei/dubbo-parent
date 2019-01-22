package cn.e3mall.client;

import cn.e3mall.dubbo.model.RequestData;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @ProjectName: house
 * @Package: cn.e3mall.client
 * @ClassName: ProxyFactory
 * @Author: majiafei
 * @Description:
 * @Date: 2019/1/21 19:38
 */
public class ProxyFactory {

    public static <T> T getProxy(final Class<T> clazz, final String host, final int port) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket(host, port);
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                RequestData requestData = new RequestData();
                requestData.setParameters(args);
                requestData.setParameterTypes(method.getParameterTypes());
                requestData.setMethodName(method.getName());
                requestData.setInterfaceName(clazz.getName());
                objectOutputStream.writeObject(requestData);
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Object result = objectInputStream.readObject();
                return result;
            }
        });
    }

}
