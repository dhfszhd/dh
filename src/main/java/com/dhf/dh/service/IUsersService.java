package com.dhf.dh.service;

import com.dhf.dh.entity.Users;

public interface IUsersService {
    //用过账号密码查询用户
    Users selByusps(Users users);
    //选择性查找用户
    Users selByAll (Users users);
    //添加新的用户
    Integer insUser(Users users);
    //通过ID修改用户
    Integer upUser(Users users);
    //通过ID 选择性修改用户
    Integer upUserAll(Users users);
    //余额充值
    Integer upUserBalance(Users users);

}
