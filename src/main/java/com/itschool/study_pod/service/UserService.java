package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.admin.AdminPasswordUpdateRequest;
import com.itschool.study_pod.dto.request.user.UserPasswordUpdateRequest;
import com.itschool.study_pod.dto.request.user.UserRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.Admin;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.repository.UserRepository;
import com.itschool.study_pod.service.base.CrudService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService extends CrudService<UserRequest, UserResponse, User> {

    private final UserRepository userRepository;

    @Override
    protected JpaRepository<User, Long> getBaseRepository() {
        return userRepository;
    }

    @Override
    protected User toEntity(UserRequest requestEntity) {
        return User.of(requestEntity);
    }

    /*
    * 비밀번호 수정하기
    * */
    @Transactional
    public Header<UserResponse> updatePassword(Long id, Header<UserPasswordUpdateRequest> request) {

        User entity = getBaseRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(this.getClass().getSimpleName() + " : 해당 id " + id + "에 해당하는 객체가 없습니다."));

        entity.updatePassword(request.getData().getPassword());

        return apiResponse(entity);
    }

}
