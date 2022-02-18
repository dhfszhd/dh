package com.dhf.dh.service;

import com.dhf.dh.entity.Roles;
import org.apache.ibatis.annotations.Param;

public interface IRolesService {
    //根据用户名查ID
    Roles selRole(String username);
}
