package com.docdock.group09.user_service.entity;

import com.docdock.group09.user_service.constant.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "`user`")
public class UserEntity implements UserDetails{
    @Id
    @UuidGenerator
    private String id;
    private String name;
    private String address;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String password;
    private String phoneNumber;
    private LocalDate dob;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.toString()));
    }

    @Override
    public String getUsername() {
        return this.name;
    }
}
