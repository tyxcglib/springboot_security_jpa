package com.tyx.security.service;

import com.tyx.security.dao.RoleDao;
import com.tyx.security.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create By C  2019-09-08 22:52
 */
@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role findRoleByRID(int rid){
        return roleDao.findById(rid).get();
    }

    public void saveRole(Role role){
        roleDao.save(role);
    }
}
