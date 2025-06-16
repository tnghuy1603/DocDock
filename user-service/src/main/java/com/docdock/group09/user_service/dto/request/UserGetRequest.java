package com.docdock.group09.user_service.dto.request;

import com.docdock.group09.user_service.constant.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGetRequest {
    private String action;
    private String specialty;
    private UserRole role;
    private String name;
    private String phoneNumber;
    private String userId;
    private String email;
    private int limit = 8;
    private int offset = 0;
}
