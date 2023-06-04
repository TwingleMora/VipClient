package com.simplenet.vipclient;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class OrderRepository {
    private  OrderDao orderDao; 
    private LiveData<List<Order>> allOrders;

    public OrderRepository(Application application) {
        MyAppDataBase dataBase = MyAppDataBase.getInstance(application);
        orderDao = dataBase.OrderDao();//link orderDao to dataBase(MyAppDataBase)
        allOrders = orderDao.GetAllOrders();//Link allOrders to orderDao.getallOrders()// we dont make async subclass for it why?
    }

    public void AddOrder(Order Order) {
        new OrderRepository.AddOrderAsync(orderDao).execute(Order);

    }

    public void DeleteOrder(Order Order) {
        new OrderRepository.DeleteOrderAsync(orderDao).execute(Order);
    }

    public void UpdateOrder(Order Order) {
        new OrderRepository.UpdateOrderAsync(orderDao).execute(Order);
    }

    public void DeletaallOrders() {
        new OrderRepository.DeleteAllOrdersAsync(orderDao).execute();
    }
    public LiveData<List<Order>>GetallOrders()
    {

        return  allOrders;
    }
    public LiveData<List<Order>>GetOrdersByName(String Name) {
        return orderDao.GetOrdersByName(Name);
    }
    private static class  AddOrderAsync extends AsyncTask<Order,Void,Void>
    {
        private OrderDao orderDao;
        AddOrderAsync(OrderDao orderDao)
        {
            this.orderDao = orderDao;
        }
        @Override
        protected Void doInBackground(Order... Orders) {
            orderDao.AddOrder(Orders[0]);
            return null;
        }
    }
    private static class  UpdateOrderAsync extends AsyncTask<Order,Void,Void>
    {
        private OrderDao orderDao;
        UpdateOrderAsync(OrderDao orderDao)
        {
            this.orderDao = orderDao;
        }
        @Override
        protected Void doInBackground(Order... Orders) {
            orderDao.UpdateOrder(Orders[0]);
            return null;
        }
    }
    private static class  DeleteOrderAsync extends AsyncTask<Order,Void,Void>
    {
        private OrderDao orderDao;
        DeleteOrderAsync(OrderDao orderDao)
        {
            this.orderDao = orderDao;
        }
        @Override
        protected Void doInBackground(Order... Orders) {
            orderDao.DeleteOrder(Orders[0]);
            return null;
        }
    }
    private static class  DeleteAllOrdersAsync extends AsyncTask<Void,Void,Void>
    {
        private OrderDao orderDao;
        DeleteAllOrdersAsync(OrderDao orderDao)
        {
            this.orderDao = orderDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            orderDao.DeleteAllOrders();
            return null;
        }
    }
   /* private static class  GetallOrdersAsync extends AsyncTask<Order,Void,Void>
    {
        private orderDao orderDao;
        GetallOrdersAsync(orderDao orderDao)
        {
            this.orderDao = orderDao;
        }
        @Override
        protected Void doInBackground(Order... Orders) {
            orderDao.GetallOrders();
            return null;
        }
    }*/
}
