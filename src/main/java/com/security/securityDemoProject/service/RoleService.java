package com.security.securityDemoProject.service;

import com.security.securityDemoProject.entity.RoleEntity;

import java.util.Set;

public interface RoleService {
    public Set<RoleEntity> getRoleUserRegister(String strRole);
}
