package xyz.wuhen.springboot.shiro.demo2.config;



import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.wuhen.springboot.shiro.demo2.shiro.cache.RedisCacheManager;
import xyz.wuhen.springboot.shiro.demo2.shiro.realms.CustomerRealm;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //1.创建shiroFilter 拦截所有请求

    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //配置系统受限资源
        //配置系统公共资源
        Map<String,String> map = new HashMap<>();
        map.put("/**","authc");
        map.put("/user/login","anon");
        map.put("/login.html","anon");
        map.put("/user/registerview","anon");
        map.put("/user/register","anon");
        map.put("/user/getImage","anon");
        map.put("/register","anon");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        //默认认证界面路径---当认证不通过时跳转
        shiroFilterFactoryBean.setLoginUrl("/user/loginview");
        return shiroFilterFactoryBean;
    }

    //2.创建安全管理器

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        //设置realm
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }
    //3.创建自定义realm

    @Bean
    public Realm getRealm() {
        CustomerRealm customerRealm = new CustomerRealm();
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //算法设置
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //散列次数
        hashedCredentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        //开启缓存
//        customerRealm.setCacheManager(new EhCacheManager());
        customerRealm.setCacheManager(new RedisCacheManager());
        //开启全局缓存

        customerRealm.setCachingEnabled(true);
//        //开启认证缓存
//        customerRealm.setAuthenticationCachingEnabled(true);
//        customerRealm.setAuthenticationCacheName("authenticationCache");
        //开启授权缓存
        customerRealm.setAuthorizationCachingEnabled(true);
        customerRealm.setAuthorizationCacheName("authorizationCache");
        return customerRealm;
    }
}
