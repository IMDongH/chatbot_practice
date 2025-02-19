package com.practice.chatbot.web.vo;

import java.util.Objects;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

public class PageRequest {

    private int page = 1;
    private int size = 10;
    private String sortField = "createdAt";
    private String sortDirection = "desc";

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        int DEFAULT_SIZE = 10;
        int MAX_SIZE = 50;
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Pageable of() {
        Direction direction = Direction.DESC;

        if (Objects.nonNull(sortDirection)) {
            direction = sortDirection.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC;
        }
        return org.springframework.data.domain.PageRequest.of(page - 1, size, direction, sortField);
    }
}
