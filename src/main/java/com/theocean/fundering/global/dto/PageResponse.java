package com.theocean.fundering.global.dto;

import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
public class PageResponse<T> {
    private final List<T> content;
    private final int currentPage;
    private final boolean isLastPage;

    public PageResponse(final Slice<T> sliceContent) {
        content = sliceContent.getContent();
        currentPage = sliceContent.getNumber() + 1;
        isLastPage = sliceContent.isLast();

    }
}
