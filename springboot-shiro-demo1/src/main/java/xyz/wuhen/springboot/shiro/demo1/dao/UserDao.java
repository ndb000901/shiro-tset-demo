package xyz.wuhen.springboot.shiro.demo1.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.wuhen.springboot.shiro.demo1.entity.Perms;
import xyz.wuhen.springboot.shiro.demo1.entity.Role;
import xyz.wuhen.springboot.shiro.demo1.entity.User;

import java.util.List;

@Mapper
public interface UserDao {
    void save(User user);

    User selectUserByUserName(String username);
    //根据用户名查询所有角色
    User findRolesByUserName(String username);

    //根据角色id查询权限信息方法
    List<Perms> findPermsByRoleId(String id);
}
