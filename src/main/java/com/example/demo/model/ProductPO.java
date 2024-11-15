package com.example.demo.model;

import java.time.Instant;

public class ProductPO {
    private String id;
    private String name;
    private int price;
    private String creatorId;
    private long createdTime;
    private long updatedTime;

    public static ProductPO of(String id, String name, int price, String creatorId) {
        var po = new ProductPO();
        po.id = id;
        po.name = name;
        po.price = price;
        po.creatorId = creatorId;
        po.createdTime = Instant.now().getEpochSecond();
        po.updatedTime = po.createdTime;

        return po;
    }

    /**
     * 由於後續會由 ProductCreateRequest 類別接收前端的請求，因此也要準備一個轉換成 ProductPO 的方法。
     *
     * @param req
     * @return ProductPO
     */
    public static ProductPO of(ProductCreateRequest req) {
        var po = new ProductPO();

        po.id = req.getId();
        po.name = req.getName();
        po.price = req.getPrice();
        po.creatorId = req.getCreatorId();

        return po;
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
