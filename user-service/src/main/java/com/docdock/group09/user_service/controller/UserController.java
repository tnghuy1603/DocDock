package com.docdock.group09.user_service.controller;

import com.docdock.group09.user_service.constant.UserRole;
import com.docdock.group09.user_service.dto.request.UpdateUserRequest;
import com.docdock.group09.user_service.dto.request.UserGetRequest;
import com.docdock.group09.user_service.dto.response.DocDockResponse;
import com.docdock.group09.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable String id, @RequestParam(value = "role", required = false) UserRole role) {
        return DocDockResponse.returnSuccess(userService.getUserDetailsById(id, role));
    }

    @GetMapping
    public ResponseEntity<?> listUsers(UserGetRequest request){
        return DocDockResponse.returnSuccess(userService.getUserDetailsList(request));
    }

    @GetMapping("/ids")
    public ResponseEntity<?> getUserIds(UserGetRequest request){
        return DocDockResponse.returnSuccess(userService.getUserIds(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest request, @PathVariable String id){
        return DocDockResponse.returnSuccess(userService.updateUser(id, request));
    }

}
