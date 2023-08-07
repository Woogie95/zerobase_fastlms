package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.LoginHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginHistoryDto {
    private String userId;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime loginTime;

    long seq;

    public static LoginHistoryDto of(LoginHistory loginHistory) {
        return LoginHistoryDto.builder()
                .userId(loginHistory.getUserId())
                .ipAddress(loginHistory.getIpAddress())
                .userAgent(loginHistory.getUserAgent())
                .loginTime(loginHistory.getLoginTime())
                .build();
    }

    public String getLastLoginText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return loginTime != null ? loginTime.format(formatter) : "";
    }

}