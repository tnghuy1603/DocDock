package com.docdock.group09.user_service.dto.response;

import com.docdock.group09.user_service.constant.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class UserResponse {
    private String id;
    private String name;
    private String address;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate dob;
    private UserRole role;
    private String bloodType;
    private Integer height;
    private String allergies;
    private String pastIllness;
    private String specialty;
    private String experience;
    private String education;
}
