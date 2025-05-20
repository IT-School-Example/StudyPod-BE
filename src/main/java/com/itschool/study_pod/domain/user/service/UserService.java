package com.itschool.study_pod.domain.user.service;

import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.domain.user.dto.request.UserEmailUpdateRequest;
import com.itschool.study_pod.domain.user.dto.request.UserNicknameUpdateRequest;
import com.itschool.study_pod.domain.user.dto.request.UserPasswordUpdateRequest;
import com.itschool.study_pod.domain.user.dto.request.UserRequest;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.enumclass.AccountRole;
import com.itschool.study_pod.global.base.crud.CrudService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService extends CrudService<UserRequest, UserResponse, User> {

    private final PasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    @Override
    protected JpaRepository<User, Long> getBaseRepository() {
        return userRepository;
    }

    @Override
    protected User toEntity(UserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .role(AccountRole.ROLE_USER)
                .name(request.getName())
                .nickname(request.getNickname())
                .createdBy(request.getEmail())
                .build();
    }

    /*
    * 비밀번호 수정하기
    * */
    @Transactional
    public Header<Void> updatePassword(Long id, Header<UserPasswordUpdateRequest> request) {

        User entity = getBaseRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(this.getClass().getSimpleName() + " : 해당 id " + id + "에 해당하는 객체가 없습니다."));

        String encryptedPassword = bCryptPasswordEncoder.encode(request.getData().getPassword());

        entity.updatePassword(encryptedPassword);

        return Header.OK();
    }

    @Transactional
    public Header<Void> updateNickname(Long id, Header<UserNicknameUpdateRequest> request) {

        User entity = getBaseRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(this.getClass().getSimpleName() + " : 해당 id " + id + "에 해당하는 객체가 없습니다."));

        entity.updatePassword(request.getData().getNickname());

        return Header.OK();
    }

    @Transactional
    public Header<Void> updateEmail(Long id, Header<UserEmailUpdateRequest> request) {

        User entity = getBaseRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(this.getClass().getSimpleName() + " : 해당 id " + id + "에 해당하는 객체가 없습니다."));

        entity.updatePassword(request.getData().getEmail());

        return Header.OK();
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
