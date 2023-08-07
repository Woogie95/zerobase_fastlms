package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.BannerParam;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import com.zerobase.fastlms.admin.mapper.BannerMapper;
import com.zerobase.fastlms.course.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BannerService {

    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;

    private Sort getSortBySortOrderDesc() {
        return Sort.by(Sort.Direction.DESC, "sortOrder");
    }

    public boolean add(BannerInput parameter) {
        MultipartFile imageFile = parameter.getImage();
        byte[] imageBytes = null;

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                imageBytes = imageFile.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        Banner banner = Banner.builder()
                .image(imageBytes)
                .altText(parameter.getAltText())
                .url(parameter.getUrl())
                .target(parameter.getTarget())
                .sortOrder(parameter.getSortOrder())
                .displayYn(parameter.isDisplayYn())
                .build();

        bannerRepository.save(banner);
        return true;

    }

    public List<BannerDto> list(BannerParam bannerParam) {
        long totalCount = bannerMapper.selectListCount(bannerParam);

        List<BannerDto> list = bannerMapper.selectList(bannerParam);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (BannerDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - bannerParam.getPageStart() - i);
                i++;
            }
        }
        return list;
    }

    public BannerDto getById(long id) {
        return bannerRepository.findById(id).map(BannerDto::of).orElse(null);
    }

    public boolean saveOrUpdateBanner(BannerInput bannerInput) {
        MultipartFile imageFile = bannerInput.getImage();
        byte[] imageBytes = null;

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                imageBytes = imageFile.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        Banner banner = null;
        if (bannerInput.getId() != null && bannerInput.getId() > 0) {
            banner = bannerRepository.findById(bannerInput.getId())
                    .orElseThrow(() -> new IllegalArgumentException("배너가 존재하지 않습니다."));
        } else {
            banner = new Banner();
        }

        banner.setImage(imageBytes);
        banner.setAltText(bannerInput.getAltText());
        banner.setUrl(bannerInput.getUrl());
        banner.setTarget(bannerInput.getTarget());
        banner.setSortOrder(bannerInput.getSortOrder());
        banner.setDisplayYn(bannerInput.isDisplayYn());

        bannerRepository.save(banner);
        return true;
    }
}