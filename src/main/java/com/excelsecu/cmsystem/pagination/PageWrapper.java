package com.excelsecu.cmsystem.pagination;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageWrapper<T> {

    /**
     * 当前页
     */
    @JsonProperty("page_index")
    private long currentPage;
    /**
     * 每页大小
     */
    @JsonProperty("page_size")
    private long pageSize;
    /**
     * 总大小
     */
    @JsonProperty("total")
    private long totalSize;
    /**
     * 总页数
     */
    @JsonProperty("pages")
    private long totalPage;
    /**
     * 实体数据
     */
    private List<T> data;

    public static <T> PageWrapper wrap(List<T>list, IPage page){
        return PageWrapper.<T>builder()
                .currentPage(page.getCurrent())
                .pageSize(page.getSize())
                .totalPage(page.getPages())
                .totalSize(page.getTotal())
                .data(list)
                .build();
    }

    public static <T> PageWrapper wrap(List<T>list, Long pageIndex, Long pageSize, Long pages, Long totals) {
        return PageWrapper.<T>builder()
                .currentPage(pageIndex)
                .pageSize(pageSize)
                .totalPage(pages)
                .totalSize(totals)
                .data(list)
                .build();
    }


}
