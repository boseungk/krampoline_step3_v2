package com.theocean.fundering.domain.celebrity.dto;

import com.theocean.fundering.domain.celebrity.domain.Celebrity;
import com.theocean.fundering.domain.celebrity.domain.constant.CelebCategory;
import com.theocean.fundering.domain.celebrity.domain.constant.CelebGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor // Type definition error 방지
public class CelebRequestDTO {
    private String celebName;
    private CelebGender celebGender;
    private CelebCategory celebCategory;
    private String celebGroup;

    public Celebrity mapToEntity() {
        return Celebrity.builder()
                .celebName(celebName)
                .celebGender(celebGender)
                .celebCategory(celebCategory)
                .celebGroup(celebGroup)
                .build();
    }
}
