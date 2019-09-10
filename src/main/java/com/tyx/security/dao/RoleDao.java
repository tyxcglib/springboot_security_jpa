package com.tyx.security.dao;

import com.tyx.security.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleDao extends JpaRepository<Role,Integer>, JpaSpecificationExecutor<Role> {

//    Set<Role> findRolesById(Integer uid);

}
