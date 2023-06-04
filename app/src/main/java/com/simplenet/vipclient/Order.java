package com.simplenet.vipclient;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "Menu")
public class Order {
    public Order(Order o)
    {
        this.done =    o.done;
        this.Name =    o.Name;
        this.Type =    o.Type;
        this.orderId = o.orderId;
        this.Price=    o.Price;
        this.date =    o.date;

    }
    boolean iCountIt = false;
    @PrimaryKey(autoGenerate = true)
    private int orderId;
    @ColumnInfo(name = "item_Name")
private String Name;
    @ColumnInfo(name = "item_Price")

    private float Price;
    @ColumnInfo(name = "item_Type")

    private String Type;
@TypeConverters(DateDataConvertor.class)
@ColumnInfo(name = "item_Date")
private Date date;
boolean done = false;

    public Order(String name, float price, String type,Date date) {
        Name = name;
        Price = price;
        Type = type;
        this.date = date;
    }
public  Order()
{

}

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
