package com.itschool.study_pod.domain.admin.service;

import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupResponse;
import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.studygroup.repository.StudyGroupRepository;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.domain.admin.dto.request.AdminPasswordUpdateRequest;
import com.itschool.study_pod.domain.admin.dto.request.AdminRequest;
import com.itschool.study_pod.domain.admin.dto.response.AdminResponse;
import com.itschool.study_pod.domain.admin.entity.Admin;
import com.itschool.study_pod.global.enumclass.AccountRole;
import com.itschool.study_pod.domain.admin.repository.AdminRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService extends CrudService<AdminRequest, AdminResponse, Admin> {

    private final PasswordEncoder bCryptPasswordEncoder;

    private final AdminRepository adminRepository;

    // ✅ 추가
    private final UserRepository userRepository;
    private final StudyGroupRepository studyGroupRepository;

    @Override
    protected JpaRepository<Admin, Long> getBaseRepository() {
        return adminRepository;
    }

    @Override
    protected Admin toEntity(AdminRequest request) {
        return Admin.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .role(AccountRole.ROLE_ADMIN)
                .build();
    }


    /*
     * 비밀번호 수정하기
     * */
    @Transactional
    public Header<Void> updatePassword(Long id, Header<AdminPasswordUpdateRequest> request) {

        Admin entity = getBaseRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(this.getClass().getSimpleName() + " : 해당 id " + id + "에 해당하는 객체가 없습니다."));

        entity.updatePassword(request.getData().getPassword());

        return Header.OK();
    }

    /**
     * ✅ 추가: 회원 삭제 처리 (논리 삭제)
     */
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."));

        userRepository.delete(user);
    }

    /**
     * ✅ 추가: 회원 정지 처리
     */
    @Transactional
    public void suspendUser(Long userId, String reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."));
        user.setSuspended(true);
        user.setSuspendReason(reason);
        userRepository.save(user);
    }

    /**
     * ✅ 추가: 스터디 그룹 조회
     */
    public Header<List<StudyGroupResponse>> getAllStudyGroups() {
        List<StudyGroup> groups = studyGroupRepository.findAll(); // 🔄 adminId 없이 전체 조회
        List<StudyGroupResponse> responseList = groups.stream()
                .map(StudyGroupResponse::fromEntity)
                .collect(Collectors.toList());
        return Header.OK(responseList);
    }


    /**
     * ✅ 추가: 스터디 그룹 삭제
     */
    @Transactional
    public void deleteStudyGroup(Long studyGroupId) {
        StudyGroup group = studyGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "스터디 그룹을 찾을 수 없습니다."));

        studyGroupRepository.delete(group);
    }

    /**
     * ✅ 추가: 스터디 그룹 정지
     */
    @Transactional
    public void suspendStudyGroup(Long studyGroupId, String reason) {
        if (reason == null || reason.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "정지 사유는 필수입니다.");
        }

        StudyGroup group = studyGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "스터디 그룹을 찾을 수 없습니다. ID=" + studyGroupId));

        group.setSuspended(true);
        group.setSuspendReason(reason);
        studyGroupRepository.save(group);
    }


    @Transactional(readOnly = true)
    public Header<Page<UserResponse>> findAllUsers(Pageable pageable) {
        Page<UserResponse> users = userRepository.findAll(pageable)
                .map(UserResponse::fromEntity);
        return Header.OK(users);
    }


}
