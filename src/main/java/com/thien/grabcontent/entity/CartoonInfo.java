package com.thien.grabcontent.entity;

import javax.persistence.*;

@Entity
@Table(name="cartoon")
public class CartoonInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="cartoonName")
    private String cartoonName;

    @Column(name="endpoint")
    private String endpoint;

    @Column(name="chapter")
    private int chapter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCartoonName() {
        return cartoonName;
    }

    public void setCartoonName(String cartoonName) {
        this.cartoonName = cartoonName;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartoonInfo that = (CartoonInfo) o;

        if (id != that.id) return false;
        if (chapter != that.chapter) return false;
        if (!cartoonName.equals(that.cartoonName)) return false;
        return endpoint.equals(that.endpoint);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + cartoonName.hashCode();
        result = 31 * result + endpoint.hashCode();
        result = 31 * result + chapter;
        return result;
    }
}
