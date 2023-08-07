package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Banner;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerDto {

    private Long id;
    private byte[] image;
    private String altText;
    private String url;
    private String target;
    private int sortOrder;
    private boolean displayYn;
    private LocalDateTime regDate;

    long totalCount;
    long seq;

    public static List<BannerDto> of(List<Banner> banners) {
        if (banners != null) {
            List<BannerDto> bannerList = new ArrayList<>();
            for (Banner x : banners) {
                bannerList.add(of(x));
            }
            return bannerList;
        }

        return null;
    }

    public static BannerDto of(Banner banner) {
        return BannerDto.builder()
                .id(banner.getId())
                .image(banner.getImage())
                .altText(banner.getAltText())
                .url(banner.getUrl())
                .target(banner.getTarget())
                .sortOrder(banner.getSortOrder())
                .displayYn(banner.isDisplayYn())
                .regDate(banner.getRegDate())
                .build();
    }

    public String getRegDateText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return regDate != null ? regDate.format(formatter) : "";
    }

    public String getImageBase64() {
        return Base64.getEncoder().encodeToString(this.image);
    }
}