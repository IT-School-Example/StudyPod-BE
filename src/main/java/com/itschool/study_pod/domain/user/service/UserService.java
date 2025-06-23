package com.itschool.study_pod.domain.user.service;

import com.itschool.study_pod.domain.user.dto.request.*;
import com.itschool.study_pod.global.base.dto.Header;
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

import java.util.HashMap;
import java.util.Map;
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
                .suspended(false)
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

        entity.updateNickname(request.getData().getNickname());

        return Header.OK();
    }

    @Transactional
    public Header<Map<String, String>> getUserSummary(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));

        Map<String, String> result = new HashMap<>();

        result.put("displayName",
                user.getNickname() != null && !user.getNickname().isBlank()
                        ? user.getNickname()
                        : user.getName()
        );

        return Header.OK(result);
    }

    @Transactional
    public Header<Void> updateEmail(Long id, Header<UserEmailUpdateRequest> request) {

        User entity = getBaseRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(this.getClass().getSimpleName() + " : 해당 id " + id + "에 해당하는 객체가 없습니다."));

        entity.updateEmail(request.getData().getEmail());

        return Header.OK();
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
                // .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }


    @Transactional
    public Header<Void> findPassword(String email, Header<UserPasswordUpdateRequest> request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User : 해당 email [" + email + "]에 해당하는 유저가 없습니다."));

        // 👉 실제 인증 여부 검사는 별도의 VerificationService에서 확인했는지 전제합니다.

        String newPassword = request.getData().getPassword();
        String encryptedPassword = bCryptPasswordEncoder.encode(newPassword);

        user.updatePassword(encryptedPassword);

        return Header.OK();
    }


    /*public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }*/
  
    @Transactional
    public void deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("해당 사용자가 존재하지 않습니다.");
        }

        userRepository.deleteById(userId);
    }
}
