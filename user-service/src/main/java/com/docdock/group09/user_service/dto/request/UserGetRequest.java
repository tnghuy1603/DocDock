package com.docdock.group09.user_service.dto.request;

import com.docdock.group09.user_service.constant.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserGetRequest {
    private String specialty;
    private List<UserRole> roles;
    private String searchKeyword;
    private int limit = 8;
    private int offset = 0;
}
