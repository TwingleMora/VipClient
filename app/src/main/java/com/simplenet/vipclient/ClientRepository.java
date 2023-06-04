package com.simplenet.vipclient;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ClientRepository {
    private ClientDao clientDao;
    private LiveData<List<Client>> allClients;
    private LiveData<List<Client>> someDayClients;
    private LiveData<List<Client>> notHiddenItems;
    private List<String> allOrders;

    public ClientRepository(Application application) {
        MyAppDataBase dataBase = MyAppDataBase.getInstance(application);
        clientDao = dataBase.ClientDao();//link clientDao to dataBase(MyAppDataBase)
        allClients = clientDao.GetAllClients();//Link allClients to clientDao.getAllClients()// we dont make async subclass for it why?
        notHiddenItems = clientDao.GetVeryAllClients();

        // allOrders =clientDao.getsAllOrders();
    }

    public void AddClient(Client client) {
        new AddClientAsync(clientDao).execute(client);
        ;
    }

    public void DeleteClient(Client client) {
        new DeleteClientAsync(clientDao).execute(client);
    }

    public void UpdateClient(Client client) {
        new UpdateClientAsync(clientDao).execute(client);
    }

    public void DeletaAllClients() {
        new DeleteAllClientsAsync(clientDao).execute();
    }

    public LiveData<List<Client>> GetAllClients() {

        return allClients;
    }

    public LiveData<List<Client>> NotHiddenClients() {

        return notHiddenItems;
    }
    public LiveData<List<Client>> ShowClientAtDay( String Date) {
        someDayClients = clientDao.ShowClientsByDay(Date);
        return someDayClients;
    }

    //public  List<String> getAllOrders()
//{
    //  return  allOrders;
//}
    private static class AddClientAsync extends AsyncTask<Client, Void, Void> {
        private ClientDao clientDao;

        AddClientAsync(ClientDao clientDao) {
            this.clientDao = clientDao;
        }

        @Override
        protected Void doInBackground(Client... clients) {
            clientDao.AddClient(clients[0]);
            return null;
        }
    }

    private static class UpdateClientAsync extends AsyncTask<Client, Void, Void> {
        private ClientDao clientDao;

        UpdateClientAsync(ClientDao clientDao) {
            this.clientDao = clientDao;
        }

        @Override
        protected Void doInBackground(Client... clients) {
            clientDao.UpdateClient(clients[0]);
            return null;
        }
    }

    private static class DeleteClientAsync extends AsyncTask<Client, Void, Void> {
        private ClientDao clientDao;

        DeleteClientAsync(ClientDao clientDao) {
            this.clientDao = clientDao;
        }

        @Override
        protected Void doInBackground(Client... clients) {
            clientDao.DeleteClient(clients[0]);
            return null;
        }
    }

    private static class DeleteAllClientsAsync extends AsyncTask<Void, Void, Void> {
        private ClientDao clientDao;

        DeleteAllClientsAsync(ClientDao clientDao) {
            this.clientDao = clientDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            clientDao.DeleteAllClients();
            return null;
        }
    }
   /* private static class  GetAllClientsAsync extends AsyncTask<Client,Void,Void>
    {
        private ClientDao clientDao;
        GetAllClientsAsync(ClientDao clientDao)
        {
            this.clientDao = clientDao;
        }
        @Override
        protected Void doInBackground(Client... clients) {
            clientDao.GetAllClients();
            return null;
        }
    }*/
}