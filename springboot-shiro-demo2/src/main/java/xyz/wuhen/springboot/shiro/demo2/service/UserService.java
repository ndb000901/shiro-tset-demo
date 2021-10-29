package xyz.wuhen.springboot.shiro.demo2.service;

import xyz.wuhen.springboot.shiro.demo2.entity.Perms;
import xyz.wuhen.springboot.shiro.demo2.entity.User;

import java.util.List;

public interface UserService {
    void register(User user);
    User selectUserByUserName(String username);
    User findRolesByUserName(String username);

    List<Perms> findPermsByRoleId(String id);
}
