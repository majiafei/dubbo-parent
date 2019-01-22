package cn.e3mall.dubbo.provider;

import cn.e3mall.dubbo.service.UserService;

/**
 * @ProjectName: house
 * @Package: cn.e3mall.dubbo.provider
 * @ClassName: UserServiceImpl
 * @Author: majiafei
 * @Description:
 * @Date: 2019/1/21 19:44
 */
public class UserServiceImpl implements UserService {

    public String addUser(String name) {
        return "hello" + name;
    }
}
