package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.UserRequest;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse create(UserRequest request) {
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
