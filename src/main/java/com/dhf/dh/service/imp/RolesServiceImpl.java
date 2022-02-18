package com.dhf.dh.service.imp;

import com.dhf.dh.entity.Roles;
import com.dhf.dh.mapper.RolesMapper;
import com.dhf.dh.service.IRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesServiceImpl implements IRolesService {
    @Autowired
    private RolesMapper rolesMapper ;
    @Override
    public Roles selRole(String username) {
        Roles selRole = rolesMapper.selRole(username);
        return selRole;
    }
}
