package xyz.wuhen.springboot.shiro.demo1.service;

import xyz.wuhen.springboot.shiro.demo1.entity.Perms;
import xyz.wuhen.springboot.shiro.demo1.entity.Role;
import xyz.wuhen.springboot.shiro.demo1.entity.User;

import java.util.List;

public interface UserService {
    void register(User user);
    User selectUserByUserName(String username);
    User findRolesByUserName(String username);

    List<Perms> findPermsByRoleId(String id);
}
