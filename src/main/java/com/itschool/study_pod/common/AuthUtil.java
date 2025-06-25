package com.itschool.study_pod.common;

import com.itschool.study_pod.global.base.account.Account;
import com.itschool.study_pod.global.base.account.AccountDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static Long getCurrentAccountId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof AccountDetails) {
            AccountDetails accountDetails = (AccountDetails) principal;
            return accountDetails.getId();
        }
        throw new IllegalArgumentException("인증된 사용자 정보를 확인할 수 없습니다.");
    }

    public static AccountDetails getCurrentAccountDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof AccountDetails) {
            AccountDetails accountDetails = (AccountDetails) principal;
            return accountDetails;
        }
        throw new IllegalArgumentException("인증된 사용자 정보를 확인할 수 없습니다.");
    }
}
