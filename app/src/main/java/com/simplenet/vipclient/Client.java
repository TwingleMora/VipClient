package com.simplenet.vipclient;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "clients")
public class Client {
    @NonNull
    @PrimaryKey(autoGenerate = true)

    private int clientId;

    @ColumnInfo(name = "client_name")
    private String Name;
/*if you want to use this type converter,
you can simply @TypeConverter(s) (DataConverter.class)
put in above your objects
or add it to your app database class.*/

    @TypeConverters(DataConverter.class)
    @ColumnInfo(name = "client_order")
    private List<Order> orders;
private String orderStr;
    @ColumnInfo(name = "client_priority")
    private int priority;
    @ColumnInfo(name = "client_paid")
    private float Paid;
    @ColumnInfo(name = "client_remain")
    private float Remain;
    @ColumnInfo(name= "total_price")
    private float totalPrice;
    @TypeConverters(DateDataConvertor.class)
    @ColumnInfo(name = "client_date")
    private Date date;
    @ColumnInfo(name = "show_date")
    @TypeConverters({TimestampConvertor.class})
    private Date showingDate;
    private String client_date_string = getClient_date_string();
    @ColumnInfo(name = "client_appearance")
    private boolean doesntAppear ;// by default = false which means that it Appears
    private  String Address;
    public Client(){}
//(String name, List<Order> orders, int priority, int paid, int remain, int totalPrice, String date)

   /* public Client(String name, Order orders, String date, int priority) {
        this.Name = name;
      //  this.orders = orders;
        this.date = date;
        this.priority = priority;
    }*/
   //String name, List<Order> orders, int paid, int remain, int totalPrice, Date date

    public Client(String name, List<Order> orders, float paid, float remain, float totalPrice, Date date) {
     //String name, List<Order> orders, int paid, int remain, int totalPrice, Date date

        Name = name;
        this.orders = orders;
        Paid = paid;
        Remain = remain;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getClient_date_string() {
        String none = "";
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (getDate() != null) {
            try {
                return dateFormat.format(getDate());
            } catch (Exception e) {

                return none;
            }
        }
            return none;

    }

    public void setClient_date_string(String client_date_string) {
        this.client_date_string = client_date_string;
    }

    public Date getShowingDate() {
        return showingDate;
    }

    public void setShowingDate(Date showingDate) {
        this.showingDate = showingDate;
    }

    public boolean isDoesntAppear() {
        return doesntAppear;
    }

    public void setDoesntAppear(boolean doesntAppear) {
        this.doesntAppear = doesntAppear;
    }

    public String getOrderStr() {
        DataConverter converter = new DataConverter();
        orderStr = converter.fromOrdersList(getOrders());
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public void setName(String name) {
        Name = name;
    }
    public String getName() {
        return Name;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getPriority() {
        return priority;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }



    public List<Order> getOrders() {
        if (orders == null)
        {
            orders = new ArrayList<Order>();
            return  orders;
        }
        return orders;
    }


    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


    public static String Date_toString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat( "yyyy-mm-dd h:mm:ss a");

        return dateFormat.format(date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPaid() {
        return Paid;
    }

    public void setPaid(float paid) {
        Paid = paid;
    }

    public float getRemain() {
        return Remain;
    }

    public void setRemain(float remain) {
        Remain = remain;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /*    public Order getOrders() {
            return orders;
        }
    */
  /*  public Date getDate() {
        return date;
    }
*/

}

