package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.BannerParam;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.course.controller.BaseController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminBannerController extends BaseController {

    private final BannerService bannerService;

    @GetMapping(value = {"/admin/banner/add.do", "/admin/banner/update.do"})
    public String addOrEditBanner(Model model, HttpServletRequest request
            , BannerInput bannerInput) {

        boolean update = request.getRequestURI().contains("/update.do");
        BannerDto bannerDto = new BannerDto();

        if (update) {
            long id = bannerInput.getId();
            BannerDto existingBanner = bannerService.getById(id);
            if (existingBanner == null) {
                model.addAttribute("message", "배너가 존재하지 않습니다.");
                return "common/error";
            }
            bannerDto = existingBanner;
        }

        model.addAttribute("update", update);
        model.addAttribute("bannerDto", bannerDto);

        return "admin/banner/add";
    }

    @PostMapping("/admin/banner/add.do")
    public String saveBanner(BannerInput bannerInput) {
        boolean result = bannerService.saveOrUpdateBanner(bannerInput);
        if (result) {
            return "redirect:/admin/banner/list.do";
        } else {
            return "common/error";
        }
    }

    @GetMapping("/admin/banner/list.do")
    public String list(Model model, BannerParam bannerParam) {

        bannerParam.init();
        List<BannerDto> bannerList = bannerService.list(bannerParam);

        long totalCount = 0;
        if (!CollectionUtils.isEmpty(bannerList)) {
            totalCount = bannerList.get(0).getTotalCount();
        }
        String queryString = bannerParam.getQueryString();
        String pagerHtml = getPaperHtml(
                totalCount,
                bannerParam.getPageSize(),
                bannerParam.getPageIndex(),
                queryString
        );

        model.addAttribute("list", bannerList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/banner/list";
    }

}
