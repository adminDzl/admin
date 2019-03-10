package com.wolves.dto;

/**
 * Created by gf on 2019/3/10.
 */
public class PageDataDTO {
    private Integer page;

    private Integer size;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PageDataDTO{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
