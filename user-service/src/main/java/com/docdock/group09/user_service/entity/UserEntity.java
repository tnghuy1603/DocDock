package com.docdock.group09.user_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
