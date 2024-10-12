package com.security.securityDemoProject.entity;

import com.security.securityDemoProject.util.Constant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    public UserEntity(){}
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    //@Enumerated(EnumType.STRING)
    //private Constant.Provider provider;  // Nhà cung cấp OAuth (GOOGLE, FACEBOOK, GITHUB...)

    //private String providerId;  // ID từ nhà cung cấp OAuth (nếu cần lưu trữ)

    //private boolean enabled;
}

