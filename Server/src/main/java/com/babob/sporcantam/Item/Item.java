package com.babob.sporcantam.Item;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    protected String item_title;
    protected String seller;
    protected Float price;
    protected String item_description;
    protected String shipping_info;
    protected Integer stock_count;
    protected LocalDate publish_date;
    protected String UUID;
    protected String category;

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public LocalDate getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(LocalDate publish_date) {
        this.publish_date = publish_date;
    }

    public String getShipping_info() {
        return shipping_info;
    }

    public void setShipping_info(String shipping_info) {
        this.shipping_info = shipping_info;
    }

    public Integer getStock_count() {
        return stock_count;
    }

    public void setStock_count(int stock_count) {
        this.stock_count = stock_count;
    }

    public String getDescription() {
        return item_description;
    }

    public void setDescription(String description) {
        this.item_description = description;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        String string = "Item Title: " + item_title + "\nSeller: " + seller +
                "\nPrice: " + price + "\nStock Count:" + stock_count + "\nShipping Info: " + shipping_info +
                "\nUUID: " + UUID +"\nDescription: " + item_description +"\nPublish Date: " + publish_date +
                "\nCategory: " + category;
        return string;
    }
}
