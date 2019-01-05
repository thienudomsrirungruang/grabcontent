package com.thien.grabcontent.entity;

import com.thien.grabcontent.Constants;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.ZonedDateTime;

@MappedSuperclass
public class AbstractBaseEntity {

    @Column(name="create_by")
    private String createBy;

    @Column(name="create_date")
    private ZonedDateTime createDate;

    @Column(name="last_modified_by")
    private String lastModifiedBy;

    @Column(name="last_modified_date")
    private ZonedDateTime lastModifiedDate;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @PrePersist
    void onCreate(){
        this.createDate = ZonedDateTime.now();
        this.lastModifiedDate = ZonedDateTime.now();
        if(createBy == null){
            createBy = Constants.PROJECT_NAME;
        }
        if(lastModifiedBy == null){
            lastModifiedBy = Constants.PROJECT_NAME;
        }
    }

    @PreUpdate
    void onUpdate(){
        this.lastModifiedDate = ZonedDateTime.now();
        if(lastModifiedBy == null){
            lastModifiedBy = Constants.PROJECT_NAME;
        }
    }
}
