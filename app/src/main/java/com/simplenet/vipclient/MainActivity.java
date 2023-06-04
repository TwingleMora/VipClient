package com.simplenet.vipclient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_CLIENT_REQUEST =1;
    public static final int UPDATE_CLIENT_REQUEST =2;
    public static final String GET_CLIENT_PAID =  "com.simplenet.vipclient.GET_CLIENT_PAID";
    public static final String GET_CLIENT_ID = "com.simplenet.vipclient.GET_CLIENT_ID";
    public static final String GET_CLIENT_ADDRESS = "com.simplenet.vipclient.GET_CLIENT_ADDRESS";
    public static final String GET_CLIENT_ITEM = "com.simplenet.vipclient.GET_CLIENT_ITEM";
    public static final String GET_CLIENT_NAME = "com.simplenet.vipclient.GET_CLIENT_NAME";
    public static final String GET_CLIENT_ORDER = "com.simplenet.vipclient.GET_CLIENT_ORDER";
    public static final String GET_CLIENT_DATE = "com.simplenet.vipclient.GET_CLIENT_DATE";

    Button Cancel;
    Button Update;
FloatingActionButton fab  ;
JClientViewModel clientViewModel;
JOrderViewModel orderViewModel;
RecyclerView clientList;
    RecyclerView.LayoutManager layoutManager;
    MenuListAdapter listAdapterX;

Dialog clientDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clientDialog = new Dialog(this);
       clientList = findViewById(R.id.recyclerView);
       layoutManager = new LinearLayoutManager(this);

        clientList.setLayoutManager(layoutManager);

        clientList.setHasFixedSize(true);
        ClientListAdapter clientListAdapter = new ClientListAdapter();
        clientList.setAdapter(clientListAdapter);



    fab = findViewById(R.id.floatingActionButton);
      fab.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(MainActivity.this,AddOrder.class);
             int index = clientListAdapter.getItemCount()+1;
             String indexStr = String.valueOf(index)+"."+" ";
              intent.putExtra(GET_CLIENT_NAME,indexStr);
              startActivityForResult(intent,ADD_CLIENT_REQUEST);
          }
      });
        orderViewModel = ViewModelProviders.of(this).get(JOrderViewModel.class);
        clientViewModel = ViewModelProviders.of(this ).get(JClientViewModel.class);
        clientViewModel.getAllClients().observe(this, new Observer<List<Client>>() {

            @Override
            public void onChanged(List<Client> clients) {

                clientListAdapter.setClients(clients);
            }
        });
new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
     int x = direction;
     Client client = clientListAdapter.getClientAtPos(viewHolder.getAdapterPosition());
if(x==4)
{


    client.setDoesntAppear(true);//hide it
    clientViewModel.update(client);
}
else if(x==8)
{
    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
    builder.setMessage("Are You Sure You Want To Delete This Client's Data Permanently ?").setCancelable(false).setTitle("Warning")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    clientViewModel.delete(client);
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
           clientListAdapter.notifyDataSetChanged();
            dialogInterface.cancel();
        }
    });
    AlertDialog alertDialog2 = builder.create();
    alertDialog2.show();

}




       //viewHolder.itemView.setVisibility(View.GONE);
   //viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));

   //clientViewModel.delete(client);
    }
}).attachToRecyclerView(clientList);
clientListAdapter.SetOnItemClickListenger(new ClientListAdapter.OnItemClickListenger() {
    @Override
    public void onItemClick(Client client) {
      //Intent intent = new Intent(MainActivity.this,ClientDataWindow.class);
     //intent.putExtra(GET_CLIENT_NAME,client.getName());
     //DataConverter converter = new DataConverter();
   //String ordersStr =  converter.fromOrdersList(client.getOrders());
      //  intent.putExtra(GET_CLIENT_ORDER,ordersStr);
        //startActivity(intent);
        ShowPopup(client);


    }
});

    }
     void DisplayWithCount(Order m,List<Order> ChosenOrdersDisplay)
    {
        Order o = new Order(m);
        if(ChosenOrdersDisplay.size()!=0) {

            int count = 0;

            Order p = ChosenOrdersDisplay.get(0);
            for (int j = 0; j < ChosenOrdersDisplay.size(); j++)
            {

                p = ChosenOrdersDisplay.get(j);
                String name1 = o.getName();
                String name2 = p.getName();

                if (name1.equals(name2))
                {

                    count++;
                    break;

                }
            }
            if(count==0)
            {
                o.setType("1");
                ChosenOrdersDisplay.add(o);
            }
            else
            {
                int tempo = Integer.parseInt(p.getType())+1;
                p.setType(Integer.toString(tempo));
                p.setPrice( tempo * p.getPrice());
                //ChosenOrdersDisplay.add(o);
            }
        }
        else {
            o.setType("1");
            ChosenOrdersDisplay.add(o);
        }
    }
    void ShowPopup(Client c)
    {
        clientDialog.setContentView(R.layout.client_data);
        Button Cancel , Update;
        TextView name ,Address;
        RecyclerView recyclerView;

        RecyclerView.LayoutManager layoutManager;

Update = clientDialog.findViewById(R.id.window_client_edit_btn);

         Cancel = clientDialog.findViewById(R.id.window_client_close_btn);

        String nameStr = c.getName();
        String addressStr = c.getAddress();
       // String orderStr = intent.getStringExtra(MainActivity.GET_CLIENT_ORDER);
      //  DataConverter converter =new DataConverter();
        List<Order> orderList =c.getOrders();                     // converter.toOrdersList(orderStr);
        List<Order> displayOrderList = new ArrayList<Order>();
        name /*textOut*/= clientDialog.findViewById(R.id.window_client_name);
        Address = clientDialog.findViewById(R.id.window_client_address);
        //   String client_name= getName();
        // if(!client_name.equals("")) {
        //   name.setText(client_name);
        //}
        name.setText(nameStr+"'s Order");
        if(addressStr.trim().isEmpty())
        {
            Address.setText("No Address");
        //    Address.setTextColor(getResources().getColor(R.color.error,null));
        }
        else {
         //   Address.setTextColor(getResources().getColor(R.color.normal_text_color, null));
            Address.setText(addressStr);
        }
        recyclerView = clientDialog.findViewById(R.id.window_client_orders_list);
        layoutManager = new LinearLayoutManager(clientDialog.getContext());
for(int x =0 ; x<orderList.size();x++)
{
    DisplayWithCount(orderList.get(x),displayOrderList);
}
        listAdapterX = new MenuListAdapter(displayOrderList,R.layout.custom_menu_list,false);

        listAdapterX.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapterX);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int x = direction;
                Order order = listAdapterX.getOrderAt(viewHolder.getAdapterPosition());
                if(x==4)
                {
int index =viewHolder.getAdapterPosition();
                    listAdapterX.setDoneAble(true);
                    for(int orderListIndex =0 ;orderListIndex<orderList.size();orderListIndex++)
                    {
                            if(orderList.get(orderListIndex).getName().equals(displayOrderList.get(index).getName()))
                            {
                                orderList.get(orderListIndex).setDone(true);
                            }
                    }
                    displayOrderList.get(index).setDone(true);
                    Order o  =  displayOrderList.get(index);
                    displayOrderList.remove(index);//remove and add again at the end
                    displayOrderList.add(displayOrderList.size(),o);//so i can add it at the end
                    c.setOrders(orderList);
                    clientViewModel.update(c);
                    listAdapterX.notifyDataSetChanged();
                }
                else if(x==8)
                {int index =viewHolder.getAdapterPosition();
                    listAdapterX.setDoneAble(true);
                    for(int orderListIndex =0 ;orderListIndex<orderList.size();orderListIndex++)
                    {
                        if(orderList.get(orderListIndex).getName().equals(displayOrderList.get(index).getName()))
                        {
                            orderList.get(orderListIndex).setDone(false);
                        }
                    }
                    displayOrderList.get(index).setDone(false);
                    Order o  =  displayOrderList.get(index);
                    displayOrderList.remove(index);//remove and add again at the end
                    displayOrderList.add(0,o);//so i can add it at the end
                    c.setOrders(orderList);
                    clientViewModel.update(c);
                    listAdapterX.notifyDataSetChanged();

                    //xx.getOrders().get(viewHolder.getAdapterPosition()).setDone(false);
                    //clientViewModel.update(xx);
                    //listAdapterX.notifyDataSetChanged();*/
                }
                }
        }).attachToRecyclerView(recyclerView);

        //orders = client.getOrders();

        listAdapterX.setDoneAble(true);
        listAdapterX.notifyDataSetChanged();
        //Cancel = popupView.findViewById(R.id.window_client_close_btn);
        //Update = popupView.findViewById(R.id.window_client_edit_btn);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientDialog.dismiss();
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new dialogeDismiss(clientDialog).execute();
                Intent intent = new Intent(MainActivity.this,AddOrder.class);
                intent.putExtra(GET_CLIENT_ID,c.getClientId());//first i send id
                intent.putExtra(GET_CLIENT_NAME,c.getName());
               intent.putExtra(GET_CLIENT_PAID,c.getPaid());
               intent.putExtra(GET_CLIENT_ADDRESS,c.getAddress());
               DateFormat format = new SimpleDateFormat("E, dd MMM yyyy h:mm:ss a");
               String dateSttr =   format.format(c.getDate());
               intent.putExtra(GET_CLIENT_DATE,dateSttr);
                DataConverter converter = new DataConverter();
                String ordersStr =  converter.fromOrdersList(c.getOrders());
                  intent.putExtra(GET_CLIENT_ORDER,ordersStr);

                startActivityForResult(intent,UPDATE_CLIENT_REQUEST);
            }
        });
      //  clientDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        clientDialog.show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.delete_all_clients:
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are You Sure You Want To Delete All Clients' Data Permanently ?").setCancelable(false).setTitle("Warning")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            clientViewModel.deleteAllClients();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialogx= builder.create();
            alertDialogx.show();


                return true;
            case R.id.modify_menu:
                Intent intent = new Intent(MainActivity.this,ModifyMenu.class);
             startActivity(intent);
                return true;
            case R.id.advanced_option:
                Intent intentII = new Intent(MainActivity.this,LogOrdersList.class);
                startActivity(intentII);
                return true;
            case R.id.about_me:
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setMessage("Programmed by Amr W. Elkamash\nEmail: elkamashamr@gmail.com \nVersion : 1.0");
                    alertDialog.setTitle("About");
                    alertDialog.setCancelable(true);
                    alertDialog.create().show();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==ADD_CLIENT_REQUEST&&resultCode==RESULT_OK)
    { DataConverter converter = new DataConverter();
        String name = data.getStringExtra(AddOrder.EXTRA_NAME);
        String addresss = data.getStringExtra(AddOrder.EXTRA_ADDRESS);
        float paid = data.getFloatExtra(AddOrder.EXTRA_PAID,0);
        float remain = data.getFloatExtra(AddOrder.EXTRA_REMAIN,0);
        float price = data.getFloatExtra(AddOrder.EXTRA_PRICE,0);
        String dateStr = data.getStringExtra(AddOrder.EXTRA_DATE);
        DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy h:mm:ss a");
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String ordersStr = data.getStringExtra(AddOrder.EXTRA_ORDERS);
        List<Order> orders = converter.toOrdersList(ordersStr);
        Client client = new Client(name ,orders,paid,remain,price,date);
        client.setAddress(addresss);
        clientViewModel.insert(client);

        Toast.makeText(this,"Done",Toast.LENGTH_LONG).show();
   /*  String Name = name.getText().toString();
        String Paid = paid.getText().toString();
        float TotalPrice = prices;
        float clientRemain = Float.parseFloat(remain.getText().toString());
        List<Order> clientOrders = ChosenOrders;
        String orderDate = Client.Date_toString(Client.GetTheDate());*/
    }
    else  if(requestCode==UPDATE_CLIENT_REQUEST&&resultCode==RESULT_OK) {
        DataConverter converter = new DataConverter();
       int id = data.getIntExtra(AddOrder.EXTRA_ID,0);//forth i receiver it agaun
        String name = data.getStringExtra(AddOrder.EXTRA_NAME);
        String addresss = data.getStringExtra(AddOrder.EXTRA_ADDRESS);

        float paid = data.getFloatExtra(AddOrder.EXTRA_PAID,0);
        float remain = data.getFloatExtra(AddOrder.EXTRA_REMAIN,0);
        float price = data.getFloatExtra(AddOrder.EXTRA_PRICE,0);
        String dateStr = data.getStringExtra(AddOrder.EXTRA_UPDATE_DATE);
        DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy h:mm:ss a");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
           // e.printStackTrace();
        }
        String ordersStr = data.getStringExtra(AddOrder.EXTRA_ORDERS);
        List<Order> orders = converter.toOrdersList(ordersStr);
        Client client = new Client(name ,orders,paid,remain,price,date);
        client.setAddress(addresss);
        client.setClientId(id);
        clientViewModel.update(client);

        Toast.makeText(this,"Updated",Toast.LENGTH_LONG).show();

    }
    else
    {
        Toast.makeText(this,"Home! ",Toast.LENGTH_LONG).show();
    }

    }
//void openM (View v){...} Look At activity_main.xml
private static class  dialogeDismiss extends AsyncTask<Void,Void,Void>
{
    private Dialog dialog;
    dialogeDismiss(Dialog m)
    {
      dialog = m;
    }
    @Override
    protected Void doInBackground(Void... voids) {
       dialog.dismiss();
        return null;
    }
}

}