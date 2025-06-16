package com.docdock.group09.user_service.controller;

import com.docdock.group09.user_service.dto.request.UserGetRequest;
import com.docdock.group09.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable String id){
        return ResponseEntity.ok(userService.getUserDetailsById(id));
    }

    @GetMapping
    public ResponseEntity<?> listUsers(UserGetRequest request){
        return ResponseEntity.ok(userService.getUserDetailsList(request));
    }

    @GetMapping("/ids")
    public ResponseEntity<?> getUserIds(UserGetRequest request){
        return ResponseEntity.ok(userService.getUserIds(request));
    }

}
