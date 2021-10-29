package xyz.wuhen.springboot.shiro.demo1.service;


import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.wuhen.springboot.shiro.demo1.dao.UserDao;
import xyz.wuhen.springboot.shiro.demo1.entity.Perms;
import xyz.wuhen.springboot.shiro.demo1.entity.Role;
import xyz.wuhen.springboot.shiro.demo1.entity.User;
import xyz.wuhen.springboot.shiro.demo1.utils.SaltUtils;

import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public void register(User user) {
        String salt = SaltUtils.getSalt(10);
        user.setSalt(salt);
        Md5Hash md5Hash = new Md5Hash(user.getPasswd(),salt,1024);
        user.setPasswd(md5Hash.toHex());
        userDao.save(user);
    }

    @Override
    public User selectUserByUserName(String username) {
        return userDao.selectUserByUserName(username);
    }

    @Override
    public User findRolesByUserName(String username) {
        return userDao.findRolesByUserName(username);
    }

    @Override
    public List<Perms> findPermsByRoleId(String id) {
        return userDao.findPermsByRoleId(id);
    }
}
