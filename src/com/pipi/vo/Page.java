package com.pipi.vo;

/**
 * 分页
 * Created by yahto on 18/05/2017.
 */
public class Page {
    private Integer totalCount;
    private Integer startPage;
    private Integer pageSize;
    private Integer pageCount;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        int flag = totalCount % pageSize;
        if (flag == 0){
            pageCount = totalCount/pageSize;
        }else {
            pageCount = totalCount/pageSize + 1;
        }
        return pageCount;
    }
}
