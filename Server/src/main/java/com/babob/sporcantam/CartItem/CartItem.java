package com.babob.sporcantam.CartItem;

import com.babob.sporcantam.Item.Item;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class CartItem {
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
    private Long shoppingCartID;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    protected String category;
    private String picture;

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

    @Override
    public String toString() {
        String string = "Item Title: " + item_title + "\nSeller: " + seller +
                "\nPrice: " + price + "\nStock Count:" + stock_count + "\nShipping Info: " + shipping_info +
                "\nUUID: " + UUID +"\nDescription: " + item_description +"\nPublish Date: " + publish_date;
        return string;
    }
   public CartItem(Item item) {
       this.picture = item.getPicture();
       this.category = item.getCategory();
        this.item_description = item.getDescription();
        this.item_title = item.getItem_title();
        this.price = item.getPrice();
        this.publish_date = item.getPublish_date();
        this.seller = item.getSeller();
        this.shipping_info = item.getShipping_info();
        this.stock_count = item.getStock_count();
        this.UUID = item.getUUID();
    }
    public CartItem(){}
    public Long getShoppingCartID() {
        return shoppingCartID;
    }

    public void setShoppingCartID(Long shoppingCartID) {
        this.shoppingCartID = shoppingCartID;
    }






}
