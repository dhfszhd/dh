package com.dhf.dh.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.dhf.dh.config.shiro.ShiroRealms;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean (name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
    //创建shiro的过滤器 负责拦截所有请求
    //创建shiro的工厂
    @Bean
    @Qualifier("ShiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //配置需要认证和权限的路径
        Map<String, String> map =new HashMap<>();
        map.put("/js/**","anon");
        map.put("/login.html","anon");
        map.put("/register.html","anon");
        map.put("/insuser","anon");
        map.put("/login","anon");
        map.put("/selall","anon");
        map.put("/selfoods","anon");
        map.put("/**","authc");
        shiroFilterFactoryBean.setLoginUrl("login.html");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean ;
    }
    //创建shiro的安全管理器

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("realms") Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager ;

    }
    //创建realm
    @Bean
    @Qualifier("realms")
    public Realm getRealm(){
        ShiroRealms realms = new ShiroRealms();
//        realms.setCacheManager(new EhCacheManager());
//        realms.setCachingEnabled(true);//开启全局缓存
//        realms.setAuthorizationCachingEnabled(true);//开启认证缓存
//        realms.setAuthenticationCacheName("authenticationCacheName");//缓存名称
//        realms.setAuthorizationCachingEnabled(true);//开启权限缓存
//        realms.setAuthorizationCacheName("authorizationCacheName");//缓存名称


        return realms;
    }
}
