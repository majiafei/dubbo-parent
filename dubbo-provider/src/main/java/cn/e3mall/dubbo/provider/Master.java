package cn.e3mall.dubbo.provider;

import cn.e3mall.dubbo.model.RequestData;
import cn.e3mall.dubbo.service.UserService;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @ProjectName: house
 * @Package: cn.e3mall.dubbo.provider
 * @ClassName: Master
 * @Author: majiafei
 * @Description:
 * @Date: 2019/1/21 19:21
 */
public class Master {

    private static final ConcurrentHashMap CONCURRENT_HASH_MAP = new ConcurrentHashMap();
    private static ExecutorService executorService = new ThreadPoolExecutor(100, 100, 1000, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(100));

    private static void registerService() {
        CONCURRENT_HASH_MAP.put(UserService.class.getName(), new UserServiceImpl());
    }

    private static void listenClient() {
        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            for (int i = 0; i < 100; i++) {
                executorService.execute(new ServerTask(serverSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        registerService();
        listenClient();
    }

    private static class ServerTask implements Runnable {

        private ServerSocket serverSocket;
        private Socket socket = null;

        public ServerTask(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        public void run() {
            try {
                while (true) {
                    System.out.println(Thread.currentThread() + "正在准备处理请求");
                    socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    Object object = objectInputStream.readObject();
                    RequestData requestData = (RequestData) object;
                    Object instance = CONCURRENT_HASH_MAP.get(requestData.getInterfaceName());
                    Method declaredMethod = instance.getClass().getDeclaredMethod(requestData.getMethodName(), requestData.getParameterTypes());
                    Object result = declaredMethod.invoke(instance, requestData.getParameters());
                    // 将结果写入输出流
                    OutputStream outputStream = socket.getOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(result);
                    System.out.println(Thread.currentThread().getName() + "处理完请求");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
