package com.docdock.group09.medical_record_service.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String id;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
}
