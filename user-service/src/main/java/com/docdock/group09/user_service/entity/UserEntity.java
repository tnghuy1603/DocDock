package com.docdock.group09.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`user`")
public class UserEntity {
    @Id
    @GeneratedValue
    private String id;
    private String name;
    private String address;
    private String email;
    private String password;
    private LocalDate dob;
}
