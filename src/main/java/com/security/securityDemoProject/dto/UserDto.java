package com.security.securityDemoProject.dto;

import com.security.securityDemoProject.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
public class UserDto {
    private String username;
    private String email;
    private Set<RoleEntity> roles;
}
