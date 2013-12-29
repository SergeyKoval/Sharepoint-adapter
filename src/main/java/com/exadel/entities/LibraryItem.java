package com.exadel.entities;

import com.exadel.constants.LibraryItemType;

public class LibraryItem {

    private String itemName;
    private LibraryItemType type;


    public String getItemName() {
        return itemName;
    }


    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public LibraryItemType getType() {
        return type;
    }


    public void setType(LibraryItemType type) {
        this.type = type;
    }
}
