package com.thien.grabcontent.dto;

public class PageInfoDTO {

    private Long id;
    private Long cartoonId;
    private String pageUrl;
    private int pageNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartoonId() {
        return cartoonId;
    }

    public void setCartoonId(Long cartoonId) {
        this.cartoonId = cartoonId;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
