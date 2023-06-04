package com.simplenet.vipclient;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    public void AddOrder (Order order);
    //@Query("SELECT * FROM clients WHERE clientId =:clientId ")
    // Client fetchOneClientbyclientId (int clientId);
    @Update
    void UpdateOrder (Order order);

    @Delete
    void DeleteOrder (Order order);

    @Query("DELETE FROM Menu")
    void DeleteAllOrders();

    @Query("SELECT * FROM Menu ORDER BY item_Date DESC")//Desc تنازلي

    LiveData<List<Order>> GetAllOrders ();
    @Query("SELECT * FROM Menu WHERE item_Name LIKE '%'|| :Name||'%'")//Desc تنازلي

    LiveData<List<Order>> GetOrdersByName (String Name);
}
