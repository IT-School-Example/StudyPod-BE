package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.User.UserCreateRequest;
import com.itschool.study_pod.dto.request.User.UserRequest;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse create(UserCreateRequest request) {
        return userRepository.save(User.of(request)).response();
    }

    public UserResponse read(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException())
                .response();
    }

    @Transactional
    public UserResponse update(Long id, UserRequest userRequest) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        entity.update(userRequest);

        return entity.response();
    }

    public void delete(Long id) {
        User findEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        userRepository.delete(findEntity);
    }

}
