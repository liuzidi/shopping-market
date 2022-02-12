package com.lzd.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
public class IndexImg {

    @Id
    @Column(name = "img_id")
    private String imgId;

    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 背景色 背景颜色
     */
    @Column(name = "img_bg_color")
    private String imgBgColor;

    /**
     * 商品id 商品id
     */
    @Column(name = "prod_id")
    private String prodId;

    /**
     * 商品分类id 商品分类id
     */
    @Column(name = "category_id")
    private String categoryId;

    /**
     * 轮播图类型 轮播图类型，用于判断，可以根据商品id或者分类进行页面跳转，1：商品 2：分类
     */
    @Column(name = "index_type")
    private Integer indexType;

    /**
     * 轮播图展示顺序 轮播图展示顺序，从小到大
     */
    private Integer seq;

    /**
     * 是否展示:1表示正常显示，0表示下线 是否展示，1：展示    0：不展示
     */
    private Integer status;

    /**
     * 创建时间 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间 更新
     */
    @Column(name = "update_time")
    private Date updateTime;
}