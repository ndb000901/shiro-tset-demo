package xyz.wuhen.springboot.shiro.demo2.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import xyz.wuhen.springboot.shiro.demo2.entity.Perms;
import xyz.wuhen.springboot.shiro.demo2.entity.Role;
import xyz.wuhen.springboot.shiro.demo2.entity.User;
import xyz.wuhen.springboot.shiro.demo2.service.UserService;
import xyz.wuhen.springboot.shiro.demo2.shiro.salt.MyByteSource;
import xyz.wuhen.springboot.shiro.demo2.utils.ApplicationContextUtils;

import java.util.List;

public class CustomerRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        System.out.println("调用授权验证： " + primaryPrincipal);
        UserService userService = (UserService) ApplicationContextUtils.getBean("userService");
        List<Role> roles = userService.findRolesByUserName(primaryPrincipal).getRoles();

        if (!CollectionUtils.isEmpty(roles)) {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            System.out.println("=============================");
            roles.forEach(role -> {
                simpleAuthorizationInfo.addRole(role.getName());
                System.out.println(role.getName());
                List<Perms> perms = userService.findPermsByRoleId(role.getId());
                if(!CollectionUtils.isEmpty(perms)) {
                    perms.forEach(perm-> {
                        simpleAuthorizationInfo.addStringPermission(perm.getName());
                        System.out.println(perm.getName());
                    });
                }
            });


            System.out.println("=============================");
            return simpleAuthorizationInfo;
        }


        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("======================");
        String principal = (String) token.getPrincipal();
        UserService userService = (UserService) ApplicationContextUtils.getBean("userService");
        User user = userService.selectUserByUserName(principal);
        System.out.println("---------------");
        System.out.println(user.toString());
        if (!ObjectUtils.isEmpty(user)) {
            return new SimpleAuthenticationInfo(user.getUsername(),
                    user.getPasswd(),
                    new MyByteSource(user.getSalt()),
                    this.getName());
        }
        return null;
    }
}
