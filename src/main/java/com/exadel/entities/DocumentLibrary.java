package com.exadel.entities;

import java.util.Date;

public class DocumentLibrary {

    private String title;
    private String description;
    private int itemsCount;

    public DocumentLibrary() {

    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public int getItemsCount() {
        return itemsCount;
    }


    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }
}
