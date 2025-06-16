package com.docdock.group09.user_service.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponse {
    private String email;
    private String password;
    private String name;
}
