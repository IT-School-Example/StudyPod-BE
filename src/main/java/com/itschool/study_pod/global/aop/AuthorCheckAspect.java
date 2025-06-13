package com.itschool.study_pod.global.aop;

import com.itschool.study_pod.domain.studyboard.entity.StudyBoard;
import com.itschool.study_pod.domain.studyboard.repository.StudyBoardRepository;
import com.itschool.study_pod.global.base.account.AccountDetails;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthorCheckAspect {

    private final StudyBoardRepository studyBoardRepository;

    @Before("@annotation(authorCheck) && args(id, ..)")
    public void checkAuthor(AuthorCheck authorCheck, Long id) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }

        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();

        StudyBoard board = studyBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시물이 없습니다."));

        if(!accountDetails.getUsername().equals(board.getCreatedBy())) {
            throw new AccessDeniedException("작성자가 아닙니다.");
        }
    }
}
