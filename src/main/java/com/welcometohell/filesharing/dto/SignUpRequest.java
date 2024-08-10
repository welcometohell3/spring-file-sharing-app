package com.welcometohell.filesharing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String username;
    private String password;
    private String name;
    private String email;
}
