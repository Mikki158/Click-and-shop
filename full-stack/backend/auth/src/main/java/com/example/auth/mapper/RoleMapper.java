package com.example.auth.mapper;

import com.example.auth.dto.RoleDto;
import com.example.auth.entity.Role;

public class RoleMapper {
    public static RoleDto mapToRoleDto(Role role) {
        return new RoleDto(
                role.getRoleName()
        );
    }

    public static Role mapToRole(RoleDto roleDto) {
        return new Role(
                roleDto.getRoleName()
        );
    }
}
