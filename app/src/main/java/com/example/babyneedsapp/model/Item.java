package com.example.babyneedsapp.model;

public class Item {
    private int id;
    private String itemName;
    private String itemcolor;
    private int itemQuantity;
    private int itemsize;
    private  String dateItemAdded;

    public Item(){

    }

    public Item(int id, String itemName, String itemcolor, int itemQuantity, int itemsize, String dateItemAdded) {
        this.id = id;
        this.itemName = itemName;
        this.itemcolor = itemcolor;
        this.itemQuantity = itemQuantity;
        this.itemsize = itemsize;
        this.dateItemAdded = dateItemAdded;
    }

    public Item(String itemName, String itemcolor, int itemQuantity, int itemsize, String dateItemAdded) {
        this.itemName = itemName;
        this.itemcolor = itemcolor;
        this.itemQuantity = itemQuantity;
        this.itemsize = itemsize;
        this.dateItemAdded = dateItemAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemcolor() {
        return itemcolor;
    }

    public void setItemcolor(String itemcolor) {
        this.itemcolor = itemcolor;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemsize() {
        return itemsize;
    }

    public void setItemsize(int itemsize) {
        this.itemsize = itemsize;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }
}
