package com.simplenet.vipclient;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Client.class,Order.class},version = 20)
public abstract class MyAppDataBase extends RoomDatabase {
    private static MyAppDataBase instance;
    public abstract  ClientDao ClientDao();//lyh cy kosyn w abstract
    public abstract  OrderDao OrderDao();

public static synchronized MyAppDataBase getInstance(Context context)
{
    if (instance == null)
    {
        instance = Room.databaseBuilder(context.getApplicationContext(),MyAppDataBase.class,"Casher_db").fallbackToDestructiveMigration().addCallback(roomCallBack).build();

    }
    return  instance;
}
private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
        new CreateDbAsyncTask(instance).execute();
    }
};
private static class CreateDbAsyncTask extends AsyncTask<Void,Void,Void>
{private ClientDao clientDao;
private OrderDao orderDao;
    CreateDbAsyncTask(MyAppDataBase myAppDataBase)
    {
        clientDao = myAppDataBase.ClientDao();
         orderDao = myAppDataBase.OrderDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
      //  String name, List<Order> orders, int paid, int remain, int totalPrice, Date date
//clientDao.DeleteAllClients();
//orderDao.AddOrder(new Order("Oreo",12,"dessert",new Date()));
  //      orderDao.AddOrder(new Order("Oreo CupCakes",25,"dessert",new Date()));


        return null;
    }
}

}
