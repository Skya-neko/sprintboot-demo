package com.example.demo.model;

public class ProductVO {
    private String id;
    private String name;
    private int price;
    private String creatorId;
    private String creatorName;
    private long createdTime;
    private long updatedTime;

    public static ProductVO of(ProductPO productPO) {
        var vo = new ProductVO();
        vo.id = vo.getId();
        vo.name = vo.getName();
        vo.price = vo.getPrice();
        vo.creatorId = vo.getCreatorId();
//        vo.creatorName = vo.getCreatorName();
        vo.createdTime = vo.getCreatedTime();
        vo.updatedTime = vo.getUpdatedTime();
        return vo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }
}
