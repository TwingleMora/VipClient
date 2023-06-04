package com.simplenet.vipclient;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClientDao {

    @Insert
    public void AddClient (Client client);
    //@Query("SELECT * FROM clients WHERE clientId =:clientId ")
   // Client fetchOneClientbyclientId (int clientId);
    @Update
        void UpdateClient (Client client);

    @Delete
        void DeleteClient (Client client);

    @Query("DELETE FROM clients")
        void DeleteAllClients();

    @Query("SELECT * FROM clients WHERE client_appearance LIKE 0 ORDER BY client_date DESC")//Desc تنازلي

       LiveData <List<Client>> GetAllClients ();                     //(LiveData) so data automatically updated
    @Query("SELECT * FROM clients  ORDER BY client_date DESC")//Desc تنازلي

    LiveData <List<Client>> GetVeryAllClients ();//there is no hiding items
    @Query("SELECT * FROM clients WHERE client_date_string = :date")
    LiveData<List<Client>> ShowClientsByDay(String date);
}