package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.UserRequest;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.service.UserService;
import com.itschool.study_pod.service.base.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "사용자", description = "사용자 API")
@RequestMapping("/api/users")
public class UserController extends CrudController<UserRequest, UserResponse, User> {

    private final UserService userService;

    @Override
    protected CrudService<UserRequest, UserResponse, User> getBaseService() {
        return userService;
    }
}
