package cn.e3mall.client;

import cn.e3mall.dubbo.model.RequestData;
import cn.e3mall.dubbo.service.UserService;

/**
 * @ProjectName: house
 * @Package: cn.e3mall.client
 * @ClassName: Master
 * @Author: majiafei
 * @Description:
 * @Date: 2019/1/21 19:37
 */
public class Master {

    public static void main(String[] args) {
        UserService userService = ProxyFactory.getProxy(UserService.class, "localhost", 6666);
        for (int i = 0; i < 100; i++) {
            String result = userService.addUser("nihao");
            System.out.println(result);
        }
    }

}
