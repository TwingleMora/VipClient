package com.simplenet.vipclient;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DataConverter {
@TypeConverter
    public String fromOrdersList(List<Order> orders)
{
    if(orders == null)
    {
        return (null);
    }
    Gson gson = new Gson();
    Type type = new TypeToken<List<Order>>(){

    }.getType();
    String json = gson.toJson(orders,type);
    return json;
}
@TypeConverter
    public List<Order> toOrdersList(String ordersString)
{
    if(ordersString == null)
    {
        return (null);
    }
    Gson gson = new Gson();
    Type type = new TypeToken<List<Order>>(){
    }.getType();
    List<Order> orders =gson.fromJson(ordersString,type);
    return orders;
    }
    public String toOrdersStringII(List<String> ordersStringX) {
       String orders= new String();
    for (int i =0 ;i<ordersStringX.size();i++)
    {
        String x = ordersStringX.get(i);
        orders += x;
    }



        return orders;
    }
}
