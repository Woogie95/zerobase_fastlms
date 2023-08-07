package com.zerobase.fastlms.admin.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class BannerInput {
    private Long id;
    private MultipartFile image;
    private String altText;
    private String url;
    private String target;
    private int sortOrder;
    private boolean displayYn;
}