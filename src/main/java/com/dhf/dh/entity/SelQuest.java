package com.dhf.dh.entity;

public class SelQuest {
    private Integer page;
    private Integer rows;
    //日期,暂定按照一天
    private String onlydate;
    //关键字 真实姓名
    private String keyword;
    //排序字段名称
    private String sort;
    //排序
    private String order;

    public SelQuest() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getOnlydate() {
        return onlydate;
    }

    public void setOnlydate(String onlydate) {
        this.onlydate = onlydate;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
