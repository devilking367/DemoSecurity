package com.security.securityDemoProject.service.serviceImpl;

import com.security.securityDemoProject.entity.RoleEntity;
import com.security.securityDemoProject.entity.RoleName;
import com.security.securityDemoProject.reponsitory.RoleRepository;
import com.security.securityDemoProject.service.RoleService;
import com.security.securityDemoProject.util.Constant;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Set<RoleEntity> getRoleUserRegister(String strRole){
        Set<RoleEntity> roles = new HashSet<>();

        // Nếu có vai trò, kiểm tra và gán vai trò tương ứng
        switch (strRole) {
            case "admin":
                RoleEntity adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException(Constant.ErrorStatus.ROLE_NOTFOUND.getMsg()));
                roles.add(adminRole);
                break;
            default:
                RoleEntity userRole = roleRepository.findByName(RoleName.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException(Constant.ErrorStatus.ROLE_NOTFOUND.getMsg()));
                roles.add(userRole);
        }
        return roles;
    }
}
