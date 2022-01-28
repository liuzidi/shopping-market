package com.lzd.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;


public class Category {
    @Id
    @Column(name = "category_id")
    private Byte categoryId;

    private String name;

    @Column(name = "last_update")
    private Date lastUpdate;

    /**
     * @return category_id
     */
    public Byte getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId
     */
    public void setCategoryId(Byte categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return last_update
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate
     */
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}