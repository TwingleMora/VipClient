package com.simplenet.vipclient;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class JClientViewModel extends AndroidViewModel {
    private ClientRepository repository;
    private LiveData<List<Client>> allClients;
    private LiveData<List<Client>> notHiddenClients;
    private List<String> allOrders;
   private LiveData<List<Client>> clientsAtSomeDay;
    public JClientViewModel(@NonNull Application application) {
        super(application);

        repository = new ClientRepository(application);
        allClients = repository.GetAllClients();
        notHiddenClients = repository.NotHiddenClients();

       // allOrders = repository.getAllOrders();
    }
    public void insert (Client client)
    {
        repository.AddClient(client);
    }
    public void update (Client client)
    {
        repository.UpdateClient(client);
    }
    public void deleteAllClients() {
        repository.DeletaAllClients();

    }
    public void delete (Client client)
    {
        repository.DeleteClient(client);
    }
    public LiveData<List<Client>> getAllClients(){
        return allClients;
    }
    public LiveData<List<Client>> getClientsAtSomeDay(String Date){
        return repository.ShowClientAtDay(Date);
    }

    public LiveData<List<Client>> NotHiddenClients(){return notHiddenClients;}
    //public List<String> getAllOrders(){
      //  return allOrders;
    //}

}

























