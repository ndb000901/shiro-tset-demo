package xyz.wuhen.shiro.demo4;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;


public class TestCustomerMd5RealmAuthenicator {
    public static void main(String[] args) {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        CustomerMd5Realm customerMd5Realm = new CustomerMd5Realm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();

        //设置算法
        matcher.setHashAlgorithmName("md5");
        //设置散列次数
        matcher.setHashIterations(1024);
        //设置hash凭证匹配器
        customerMd5Realm.setCredentialsMatcher(matcher);
        securityManager.setRealm(customerMd5Realm);

        SecurityUtils.setSecurityManager(securityManager);


        Subject subject = SecurityUtils.getSubject();

        //认证
        UsernamePasswordToken token = new UsernamePasswordToken("haha","123456");
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
        }

        //授权
        if (subject.isAuthenticated()) {
            //基于角色权限控制
            System.out.println("super: " + subject.hasRole("super"));
            System.out.println("admin: " + subject.hasRole("admin"));

            //基于多角色权限控制
            System.out.println(subject.hasAllRoles(Arrays.asList("admin","super")));

            //是否具有其中的一个角色
            boolean[] booleans = subject.hasRoles(Arrays.asList("admin","super","user"));
            for (boolean v : booleans) {
                System.out.println(v);
            }
            //基于权限字符串的访问控制
            System.out.println(subject.isPermitted("user:*:01"));

            boolean[] permitted = subject.isPermitted("user:*:01","order:*:10");
            for (boolean v : permitted) {
                System.out.println(v);
            }

            //同时具有哪些权限
            boolean permittedAll = subject.isPermittedAll("user:*:01","product:*:*");
            System.out.println(permittedAll);
        }
    }
}
