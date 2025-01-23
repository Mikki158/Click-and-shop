package com.example.AuthTG.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDto{

    private Long userId;
    private String username;
    private String firstName;
    private String photoUrl;
    private Set<String> role;
}
