package com.theocean.fundering.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class MyFundingHostResponseDTO {

    private final Long postId;
    private final String nickname;
    private final String celebName;
    private final String profileImage;
    private final String title;
    private final String thumbnail;
    private final String introduction;
    private final int targetPrice;
    private final LocalDateTime deadline;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}
