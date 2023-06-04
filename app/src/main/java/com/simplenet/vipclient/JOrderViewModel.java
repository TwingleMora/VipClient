package com.simplenet.vipclient;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class JOrderViewModel extends AndroidViewModel
{
        private OrderRepository repository;
        private LiveData<List<Order>> allOrders;
    public JOrderViewModel(@NonNull Application application) {
        super(application);

        repository = new OrderRepository(application);
        allOrders = repository.GetallOrders();
    }
        public void insert (Order order)
        {
            repository.AddOrder(order);
        }
        public void update  (Order order)
        {
            repository.UpdateOrder(order);
        }
        public void deleteAllOrders() {
        repository.DeletaallOrders();

    }
        public void delete (Order order)
        {
            repository.DeleteOrder(order);
        }
        public LiveData<List<Order>> getAllOrders(){
        return allOrders;
    }
    public LiveData<List<Order>>GetOrdersByName(String Name) {
        return repository.GetOrdersByName(Name);
    }

    }
