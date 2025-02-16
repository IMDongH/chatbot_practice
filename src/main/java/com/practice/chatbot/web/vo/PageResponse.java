package com.practice.chatbot.web.vo;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {

    private int size;
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private List<T> content;
    private List<PageSort> sort;


    public PageResponse<T> of(Page<T> page) {
        this.setSize(page.getSize());
        this.setTotalElements(page.getTotalElements());
        this.setTotalPages(page.getTotalPages());
        this.setCurrentPage(page.getPageable().getPageNumber() + 1);
        this.setSort(PageSort.of(page.getSort()));
        this.setContent(page.getContent());

        return this;
    }

    @Builder
    @Getter
    public static class PageSort {

        private String direction;
        private String property;

        public static List<PageSort> of(Sort sort) {
            return sort.stream()
                    .map(item -> PageSort.builder().direction(item.getDirection().toString())
                            .property(item.getProperty()).build()).toList();
        }
    }
}
