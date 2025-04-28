package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.UserRequest;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserApiController extends CrudController<UserRequest, UserResponse, User> {

    public UserApiController(UserService baseService) {
        super(baseService);
    }
}
