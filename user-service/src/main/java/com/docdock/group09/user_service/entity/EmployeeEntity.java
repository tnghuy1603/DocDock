package com.docdock.group09.user_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employee")
public class EmployeeEntity extends UserEntity{
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String specialty;
    private String experience;
    private String education;


}
