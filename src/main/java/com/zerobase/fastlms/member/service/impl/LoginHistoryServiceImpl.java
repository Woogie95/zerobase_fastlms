package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.repository.LoginHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LoginHistoryServiceImpl {
    private final LoginHistoryRepository loginHistoryRepository;

    public LoginHistory saveLoginHistory(
            String userId,
            String ipAddress,
            String userAgent
    ) {
        LoginHistory loginHistory = LoginHistory.builder()
                .userId(userId)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .loginTime(LocalDateTime.now())
                .build();
        return loginHistoryRepository.save(loginHistory);
    }

    public LocalDateTime getLastLoginTime(String userId) {
        LoginHistory lastLogin = loginHistoryRepository.findByUserIdOrderByLoginTimeDesc(userId);
        if (lastLogin != null) {
            return lastLogin.getLoginTime();
        } else {
            return null;
        }
    }

    public List<LoginHistory> getAllLoginHistory(String userId) {
        return loginHistoryRepository.findAllByUserIdOrderByLoginTimeDesc(userId);
    }
}