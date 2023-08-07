package com.zerobase.fastlms.main.controller;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerParam;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.components.MailComponents;
import com.zerobase.fastlms.member.service.impl.LoginHistoryServiceImpl;
import com.zerobase.fastlms.member.service.impl.MemberServiceImpl;
import com.zerobase.fastlms.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final MailComponents mailComponents;
    private final LoginHistoryServiceImpl loginHistoryService; // 주입
    private final MemberServiceImpl memberService;
    private final BannerService bannerService;

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {

        /*
        String email = "satcop@naver.com";
        String subject = " 안녕하세요. 제로베이스 입니다. ";
        String text = "<p>안녕하세요.</p><p>반갑습니다.</p>";

        mailComponents.sendMail(email, subject, text);
        */

        String userAgent = RequestUtils.getUserAgent(request);
        String clientsIp = RequestUtils.getClientIP(request);
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info(userAgent);
        log.info(clientsIp);
        log.info(userId);
        if (userId != "anonymousUser") {
            loginHistoryService.saveLoginHistory(userId, clientsIp, userAgent);
            memberService.updateLastLogin(userId, LocalDateTime.now());

            BannerParam bannerParam = new BannerParam();
            bannerParam.init(); // 파라미터 초기화
            List<BannerDto> bannerList = bannerService.list(bannerParam); // 배너 목록 가져오기

            model.addAttribute("banners", bannerList);
        }

        return "index";
    }



    @RequestMapping("/error/denied")
    public String errorDenied() {

        return "error/denied";
    }



}