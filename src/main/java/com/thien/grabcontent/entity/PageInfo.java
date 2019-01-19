package com.thien.grabcontent.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="page")
public class PageInfo extends AbstractBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="cartoon_id")
    private int cartoonId;

    @Column(name="page_url")
    private String pageUrl;

    @Column(name="page_number")
    private String pageNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

        return id != null ? id.equals(pageInfo.id) : pageInfo.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
