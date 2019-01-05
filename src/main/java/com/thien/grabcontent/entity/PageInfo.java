package com.thien.grabcontent.entity;


import javax.persistence.*;

@Entity
@Table(name="page")
public class PageInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="cartoon_id")
    private int cartoonId;

    @Column(name="page_url")
    private String pageUrl;

    @Column(name="page_number")
    private String pageNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartoonId() {
        return cartoonId;
    }

    public void setCartoonId(int cartoonId) {
        this.cartoonId = cartoonId;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageInfo pageInfo = (PageInfo) o;

        if (id != pageInfo.id) return false;
        if (cartoonId != pageInfo.cartoonId) return false;
        if (!pageUrl.equals(pageInfo.pageUrl)) return false;
        return pageNumber.equals(pageInfo.pageNumber);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + cartoonId;
        result = 31 * result + pageUrl.hashCode();
        result = 31 * result + pageNumber.hashCode();
        return result;
    }
}
