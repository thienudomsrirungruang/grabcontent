package com.thien.grabcontent.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "page")
public class PageInfo extends AbstractBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "page_url")
    private String pageUrl;

    @Column(name = "page_number")
    private int pageNumber;

    @ManyToOne
    @JoinColumn(name = "cartoon_id")
    private CartoonInfo cartoonInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CartoonInfo getCartoonInfo() {
        return cartoonInfo;
    }

    public void setCartoonInfo(CartoonInfo cartoonInfo) {
        this.cartoonInfo = cartoonInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageInfo pageInfo = (PageInfo) o;

        return id != null ? id.equals(pageInfo.id) : pageInfo.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
