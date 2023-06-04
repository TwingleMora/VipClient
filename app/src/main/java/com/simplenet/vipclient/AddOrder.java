package com.simplenet.vipclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class AddOrder extends AppCompatActivity {
    //private static final String DATABASE_NAME = "clients_db";
    public static final String EXTRA_ID = "com.simplenet.vipclient.EXTRA_ID";
    public static final String EXTRA_NAME = "com.simplenet.vipclient.EXTRA_NAME";
    public static final String EXTRA_ADDRESS = "com.simplenet.vipclient.EXTRA_ADDRESS";
    public static final String EXTRA_PAID = "com.simplenet.vipclient.EXTRA_PAID";
    public static final String EXTRA_REMAIN = "com.simplenet.vipclient.EXTRA_REMAIN";
    public static final String EXTRA_PRICE = "com.simplenet.vipclient.EXTRA_PRICE";
    public static final String EXTRA_DATE = "com.simplenet.vipclient.EXTRA_DATE";
    public static final String EXTRA_UPDATE_DATE = "com.simplenet.EXTRA_UPDATE_DATE";
    public static final String EXTRA_ORDERS = "com.simplenet.vipclient.EXTRA_ORDERS";
    JOrderViewModel jOrderViewModel;
    EditText name, paid, address,search;
    TextView remain, price,itemsCounter;/*,positionText*/
    Intent data;

    //Button delete;
    Button refreshPrices;
    RecyclerView ChooseOrdersList;
    RecyclerView DisplayOrdersList;
    MenuListAdapter ChooseListAdapter;
    MenuListAdapter DisplayListAdapter;
    RecyclerView.LayoutManager layoutManager1, layoutManager2;
    private JClientViewModel clientViewModel;
    List<Order> items = new ArrayList<Order>();
    List<Order> ChosenOrders;
    List<Order> ChosenOrdersDisplay;
    float prices = 0;
    int onUpdateClientId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        ChosenOrders = new ArrayList<Order>();
        ChosenOrdersDisplay = new ArrayList<Order>();
        name = findViewById(R.id.nameTxt);
        paid = findViewById(R.id.paidTxt);
        remain = findViewById(R.id.remainDisplay);
        price = findViewById(R.id.orders_Price);
        address = findViewById(R.id.addressTxt2);
        refreshPrices = findViewById(R.id.refresh_prices_btn);
        Intent intent = getIntent();
        onUpdateClientId = intent.getIntExtra(MainActivity.GET_CLIENT_ID, 0);//second i receive id
        String addressStr = intent.getStringExtra(MainActivity.GET_CLIENT_ADDRESS);
        String names = intent.getStringExtra(MainActivity.GET_CLIENT_NAME);
        float paids = intent.getFloatExtra(MainActivity.GET_CLIENT_PAID, 0);
        String orderss = intent.getStringExtra(MainActivity.GET_CLIENT_ORDER);
        search = findViewById(R.id.item_search);
        itemsCounter = findViewById(R.id.items_counter);


        paid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {


                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                float clientCash = 0, clientRemain = 0;
                if (charSequence.length() != 0) {
                    try {
                        clientCash = Float.parseFloat(paid.getText().toString());

                    } catch (Exception e) {
                        Log.d("ERROR", "please write numbers");
                    }
                    clientRemain = clientCash - prices;
                    remain.setText(String.valueOf(clientRemain));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        paid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    paid.getText().clear();// Custom it Settings Activity
            }
        });
//delete = findViewById(R.id.deleteBtn);
//insert = findViewById(R.id.insertBtn);
        //positionText = findViewById(R.id.posTxt);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp); // close button upside
        setTitle("Add an Client's Order");

        CreateLists();
        address.setText(addressStr);
        name.setText(names);
        name.setSelection(name.getText().length());// so the typing cursor will be at the end
        paid.setText(String.valueOf(paids));
        DataConverter converters = new DataConverter();
        /// Display 1
        if (orderss != null) {
            List<Order> ordersx = converters.toOrdersList(orderss);

            for (int i = 0; i < ordersx.size(); i++) {
                Order o = ordersx.get(i);


                    ChosenOrders.add(o);
                    DisplayWithCount(o);

                DisplayListAdapter.notifyDataSetChanged();
                GetPrice(ChosenOrders);
            }
        }
        jOrderViewModel = ViewModelProviders.of(this).get(JOrderViewModel.class);
        jOrderViewModel.getAllOrders().observe(this, new Observer<List<Order>>() {

            @Override
            public void onChanged(List<Order> orders) {
                String temp = search.getText().toString();
                    ChooseListAdapter.SetItem(orders);

                   ChooseListAdapter.notifyDataSetChanged();

                items = orders;
            }
        });
        refreshPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddOrder.this, "...", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < ChosenOrders.size(); i++) {
                    Order Left = ChosenOrders.get(i);
                    for (int x = 0; x < items.size(); x++) {
                        Order Right = items.get(x);
                        String LeftName = Left.getName();
                        String RightName = Right.getName();
                        if (LeftName.equals(RightName)) {
                            if (Left.getType().equals(Right.getType())) {
                                if (Left.getPrice() != Right.getPrice())
                                    ChosenOrders.get(i).setPrice(Right.getPrice());
                            }

                        }
                    }
                }
                DisplayListAdapter.notifyDataSetChanged();
                GetPrice(ChosenOrders);

            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
if(charSequence.length()!=0)
{
    String searchTemp = search.getText().toString();
    jOrderViewModel.GetOrdersByName(searchTemp).observe(AddOrder.this, new Observer<List<Order>>() {

        @Override
        public void onChanged(List<Order> orders) {

              ChooseListAdapter.SetItem(orders);

                ChooseListAdapter.notifyDataSetChanged();

            items = orders;
        }
    });
}
else
{
    jOrderViewModel.getAllOrders().observe(AddOrder.this, new Observer<List<Order>>() {

        @Override
        public void onChanged(List<Order> orders) {
            String temp = search.getText().toString();
            ChooseListAdapter.SetItem(orders);

            ChooseListAdapter.notifyDataSetChanged();

            items = orders;
        }
    });
}
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
  /*      insert.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
int position = Integer.parseInt(positionText.getText().toString());
InsertItem(position);
    }
});
*/
/*
  delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        int position = Integer.parseInt(positionText.getText().toString());
        DeleteItem(position);

    }
});*/


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("Name", name.getText().toString());
        outState.putString("Paid", paid.getText().toString());
        outState.putString("Address", address.getText().toString());
        outState.putFloat("Prices", prices);
        DataConverter converter = new DataConverter();
        List<Order> clientOrders = ChosenOrders;
        String StrOrders = converter.fromOrdersList(clientOrders);
        outState.putString("Orders", StrOrders);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        DataConverter converter = new DataConverter();
        name.setText(savedInstanceState.getString("Name"));
        paid.setText(savedInstanceState.getString("Paid"));
        prices = savedInstanceState.getFloat("Prices");
        address.setText(savedInstanceState.getString("Address"));
        ChosenOrders = converter.toOrdersList(savedInstanceState.getString("Orders"));
        CreateLists();

        ChooseListAdapter.notifyDataSetChanged();
        GetPrice(ChosenOrders);
    }
public void DisplayWithCount(Order m)
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
    public void SaveClient() {
        float Paid = 0;
        float TotalPrice = 0;
        float clientRemain = 0;
        String Name = name.getText().toString();

        String addressXX = address.getText().toString();
        try {
            Paid = Float.parseFloat(paid.getText().toString());
            TotalPrice = prices;
            clientRemain = Float.parseFloat(remain.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "Note That Paid Field Is Empty :)", Toast.LENGTH_LONG).show();
        }
        List<Order> clientOrders = ChosenOrders;
        Date date = new Date();
Intent mExistDate = getIntent();
String dateStrFromIntent = mExistDate.getStringExtra(MainActivity.GET_CLIENT_DATE);
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy h:mm:ss a");

        String strDate = "";
        if (date != null)
            strDate = formatter.format(date);

        if (Name.trim().isEmpty() || clientOrders.isEmpty()) {
            Toast.makeText(this, "Please Insert The Name And The Order", Toast.LENGTH_LONG).show();
            return;
        }
        DataConverter converter = new DataConverter();
        String StrOrders = converter.fromOrdersList(clientOrders);
        //String ExistDate = formatter.format(Update_Date);
        data = new Intent();
        data.putExtra(EXTRA_ID, onUpdateClientId);//third i send it again
        data.putExtra(EXTRA_ADDRESS, addressXX);
        data.putExtra(EXTRA_NAME, Name);
        data.putExtra(EXTRA_PAID, Paid);
        data.putExtra(EXTRA_PRICE, TotalPrice);
        data.putExtra(EXTRA_REMAIN, clientRemain);
        data.putExtra(EXTRA_DATE, strDate);
        data.putExtra(EXTRA_ORDERS, StrOrders);
        data.putExtra(EXTRA_UPDATE_DATE,dateStrFromIntent);

        setResult(RESULT_OK, data);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_client_button, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_client:
                SaveClient();
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }
    }

    void GetPrice(List<Order> displayList) {
        prices = 0;
        price.setText(String.valueOf(prices));
        if (displayList != null) {
            for (int i = 0; i < displayList.size(); i++) {
                Order o = displayList.get(i);

                prices += o.getPrice();
                price.setText(String.valueOf(prices));
            }
        }

        float clientCash = 0, clientRemain = 0;
        try {
            clientCash = Float.parseFloat(paid.getText().toString());

        } catch (Exception e) {
            Log.d("ERROR", "please write numbers");
        }
        clientRemain = clientCash - prices;
        remain.setText(String.valueOf(clientRemain));
        itemsCounter.setText(String.valueOf(displayList.size())+" Item(s)");

    }

    void InsertItem(int pos) {
        String mName = name.getText().toString();
        //items.add(pos, new Order(mName, 12, "New"));

        ChooseListAdapter.notifyDataSetChanged();
    }

    void DeleteItem(int pos) {


           // Order t = ChosenOrdersDisplay.get(0);
            //int index = -1;
            /*for(int x = 0 ;x<ChosenOrdersDisplay.size();x++)
            {

                if(ChosenOrdersDisplay.get(x).getName().equals(ChosenOrders.get(pos).getName()))
                {
                //    t = ChosenOrdersDisplay.get(x);
                    index=x;
                    break;
                }
            }*/
            int orderId = ChosenOrdersDisplay.get(pos).getOrderId();
            int index =0;
        for(int x = 0 ;x<ChosenOrders.size();x++)
        {
            if(ChosenOrders.get(x).getOrderId()==orderId )
            {
                index = x;
                break;
            }
        }
            //if(index !=-1)
            {

                int tempoo = Integer.parseInt(ChosenOrdersDisplay.get(pos).getType()) - 1;
                if (tempoo > 0)
                {
                    Order ttx = ChosenOrdersDisplay.get(pos);
                    float pricee = ChosenOrders.get(index).getPrice();
                    ttx.setType(Integer.toString(tempoo));
                    ttx.setPrice(pricee*tempoo);

                }
                else
                {
                    ChosenOrdersDisplay.remove(pos);
                }

            }

ChosenOrders.remove(index);
        GetPrice(ChosenOrders);
        DisplayListAdapter.notifyDataSetChanged();

    }

    void AddOrder(int Pos) {
        Order SelectedItem = items.get(Pos);
        SelectedItem.setDate(new Date());
        Order tnt = new Order(SelectedItem);
        /// Display 2
        ChosenOrders.add(SelectedItem);
       // int counter =0;
        if(ChosenOrdersDisplay.size()!=0)
        {
            int counter =0;
            Order tt = ChosenOrdersDisplay.get(0);
            for (int x = 0; x < ChosenOrdersDisplay.size(); x++) {
                if (ChosenOrdersDisplay.get(x).getName().equals(SelectedItem.getName())) {
                    tt = ChosenOrdersDisplay.get(x);
                    counter++;
                    break;

                }
            }
            if(counter !=0)
            {

            int tempo = Integer.parseInt(tt.getType())+1;
                tt.setType(Integer.toString(tempo));
                float pricee = SelectedItem.getPrice();
                tt.setPrice( tempo * pricee);
            }
            else
            {

                tnt.setType("1");
                ChosenOrdersDisplay.add(tnt);
            }
        }

        else
            {

                tnt.setType("1");
            ChosenOrdersDisplay.add(tnt);
        }
        GetPrice(ChosenOrders);

        DisplayListAdapter.notifyDataSetChanged();


       /*
        String OrderdByNum ="";
        if(DisplayListAdapter.getItemCount()==1)
        OrderdByNum= "st" ;
                else if (DisplayListAdapter.getItemCount()==2)
                   OrderdByNum = "nd";
                else if (DisplayListAdapter.getItemCount()==3)
                    OrderdByNum="rd";
                    else
        {
            if(DisplayListAdapter.getItemCount()!=0)
                OrderdByNum="th";
        }
                    String theFullSentance = String.valueOf(DisplayListAdapter.getItemCount())+OrderdByNum;
        Toast.makeText(this, "The "+theFullSentance+" Item has been inserted", Toast.LENGTH_SHORT).show();
    */
    }


    void CreateLists() {
        ChooseOrdersList = findViewById(R.id.MenuView);
        DisplayOrdersList = findViewById(R.id.DisplayOrders);
        ChooseOrdersList.setHasFixedSize(true);
        //ChooseOrdersList.setNestedScrollingEnabled(false);
        DisplayOrdersList.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(this);
        layoutManager2 = new LinearLayoutManager(this);
        ChooseListAdapter = new MenuListAdapter(items, R.layout.custom_menu_list, false);
       // DisplayListAdapter = new MenuListAdapter(ChosenOrders, R.layout.adapter_view_layout, true);
        DisplayListAdapter = new MenuListAdapter(ChosenOrdersDisplay, R.layout.adapter_view_layout, true);
        DisplayOrdersList.setLayoutManager(layoutManager2);
        DisplayOrdersList.setAdapter(DisplayListAdapter);
        ChooseListAdapter.setOnItemClickListener(new MenuListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                AddOrder(pos);
            }

            @Override
            public void onDeleteClick(int pos) {

            }
        });
        DisplayListAdapter.setOnItemClickListener(new MenuListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {

            }

            @Override
            public void onDeleteClick(int pos) {
                DeleteItem(pos);
                Toast.makeText(getBaseContext(), "Deleted!", Toast.LENGTH_SHORT).show();
            }
        });
/*ChooseOrdersList.addOnScrollListener(new RecyclerView.OnScrollListener() {
    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

    }
});*/
        ChooseOrdersList.setLayoutManager(layoutManager1);
        ChooseOrdersList.setAdapter(ChooseListAdapter);
    }
}
