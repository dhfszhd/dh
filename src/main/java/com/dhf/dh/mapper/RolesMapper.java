package com.dhf.dh.mapper;

import com.dhf.dh.entity.Roles;
import com.dhf.dh.entity.Users;
import org.apache.ibatis.annotations.Param;

public interface RolesMapper {
    Roles selRole(@Param("username") String username);
}
