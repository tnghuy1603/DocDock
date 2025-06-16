package com.docdock.group09.user_service.service;

import com.docdock.group09.user_service.dto.response.UserResponse;
import com.docdock.group09.user_service.dto.request.UserGetRequest;

import java.util.List;
import java.util.Set;

public interface UserService {
    UserResponse getUserDetailsById(String id);
    List<UserResponse> getUserDetailsList(UserGetRequest request);
    Set<String> getUserIds(UserGetRequest request);
}
