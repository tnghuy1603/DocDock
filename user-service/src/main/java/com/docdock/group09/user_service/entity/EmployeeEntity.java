package com.docdock.group09.user_service.entity;

import com.docdock.group09.user_service.constant.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "employee")
public class EmployeeEntity extends UserEntity{
    private String specialty;
    private String experience;
    private String education;
}
