package com.dhf.dh.config.shiro;

import com.dhf.dh.entity.Roles;
import com.dhf.dh.entity.Users;
import com.dhf.dh.mapper.RolesMapper;
import com.dhf.dh.mapper.UsersMapper;
import org.apache.catalina.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroRealms extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(ShiroRealms.class);
    @Autowired
    private RolesMapper rolesMapper ;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        Roles selRole = rolesMapper.selRole(username);
        log.debug("角色----------->{}",selRole.getRole() );
        SimpleAuthorizationInfo simpleAuthorizationInfo =  new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(selRole.getRole());
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.debug("token-------账号->{}",authenticationToken.getPrincipal());
        log.debug("token-------密码->{}",authenticationToken.getCredentials());
        String username = (String) authenticationToken.getPrincipal();

        return new SimpleAuthenticationInfo(username,authenticationToken.getCredentials(),this.getName());


    }
}
