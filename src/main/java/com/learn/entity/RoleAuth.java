package com.learn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAuth {
    private Integer roleAuthId;
    private Integer roleId;
    private Integer authId;

    public RoleAuth(Integer roleId, Integer authId) {
        this.roleId = roleId;
        this.authId = authId;
    }
}
