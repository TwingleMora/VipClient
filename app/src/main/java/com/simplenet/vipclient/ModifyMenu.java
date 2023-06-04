package com.simplenet.vipclient;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModifyMenu extends AppCompatActivity {
    EditText name , type,price,search;
    RecyclerView DisplayMenu;
String UpdateName , UpdateType ;
float UpdatePrice;
int UpdateId;
Date UpdateDate;

    RecyclerView.LayoutManager layoutManager1;
    Button Add,Update,ClearBtn;
    MenuListAdapter  menuListAdapter;
    JOrderViewModel jOrderViewModel;
    JClientViewModel jClientViewModel;
    List<Order> mOrders ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_menu);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        setTitle("Modify Menu");

        name = findViewById(R.id.item_name_menu_input);
        type =findViewById(R.id.item_type_menu_input);
        price = findViewById(R.id.item_price_menu_input);
        Add =findViewById(R.id.item_menu_add_input);
        Update = findViewById(R.id.item_menu_update_input);
        search = findViewById(R.id.item_menu_search_input);
        ClearBtn = findViewById(R.id.item_menu_clear_input);
mOrders = new ArrayList<>();
        menuListAdapter= new MenuListAdapter(mOrders,R.layout.adapter_view_layout,true);
        // DisplayMenu.setHasFixedSize(true);


DisplayMenu = findViewById(R.id.item_menu_display_output);

        DisplayMenu.setHasFixedSize(true);

        layoutManager1 = new LinearLayoutManager(this);
DisplayMenu.setLayoutManager(layoutManager1);
        DisplayMenu.setAdapter(menuListAdapter);
        jOrderViewModel = ViewModelProviders.of(this).get(JOrderViewModel.class);
        jClientViewModel =ViewModelProviders.of(this).get(JClientViewModel.class);
        jOrderViewModel.getAllOrders().observe(this, new Observer<List<Order>>() {

            @Override
            public void onChanged(List<Order> orders)
            {
                menuListAdapter.SetItem(orders);
                menuListAdapter.notifyDataSetChanged();
                mOrders = orders;
            }
        });
        search.addTextChangedListener(new TextWatcher() {
                                          @Override
                                          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                          }

                                          @Override
                                          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                              if (charSequence.length() != 0) {
                                                  String searchTemp = search.getText().toString();
                                                  jOrderViewModel.GetOrdersByName(searchTemp).observe(ModifyMenu.this, new Observer<List<Order>>() {

                                                      @Override
                                                      public void onChanged(List<Order> orders) {

                                                          menuListAdapter.SetItem(orders);

                                                          menuListAdapter.notifyDataSetChanged();
                                                          mOrders = orders;

                                                      }
                                                  });
                                              } else {
                                                  jOrderViewModel.getAllOrders().observe(ModifyMenu.this, new Observer<List<Order>>() {

                                                      @Override
                                                      public void onChanged(List<Order> orders) {
                                                          String temp = search.getText().toString();
                                                          menuListAdapter.SetItem(orders);

                                                          menuListAdapter.notifyDataSetChanged();
                                                          mOrders = orders;
                                                         // items = orders;
                                                      }
                                                  });
                                              }
                                          }

                                          @Override
                                          public void afterTextChanged(Editable editable) {

                                          }
                                      });
        menuListAdapter.setOnItemClickListener(new MenuListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
Order o = mOrders.get(pos);
UpdateName = o.getName();
UpdateType = o.getType();
UpdateId = o.getOrderId();
UpdateDate = o.getDate();
UpdatePrice = o.getPrice();
name.setText(UpdateName);
type.setText(UpdateType);
price.setText(String.valueOf(UpdatePrice));

            }

            @Override
            public void onDeleteClick(int pos) {
            Order o = mOrders.get(pos);
                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyMenu.this);
                builder.setCancelable(false).setTitle("Warning").setMessage("Are You Sure You Want To Delete This Menu Item Data Permanently ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                jOrderViewModel.delete(o);
                                Toast.makeText(ModifyMenu.this, "Deleted!", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog  alertDialog = builder.create();
                alertDialog.show();


            }
        });
Add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

String namex = name.getText().toString();
String typeX =type.getText().toString();
        float priceX = 0;
try{priceX = Float.parseFloat(price.getText().toString());}
catch (Exception e)
{
    price.setText("0");
}
if(priceX==0||namex.trim().isEmpty())
{
    Toast.makeText(ModifyMenu.this, "Enter Item Name And Price", Toast.LENGTH_SHORT).show();
    View view1 = ModifyMenu.this.getCurrentFocus();

    if(view1!=null)
    {
        InputMethodManager methodManager =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // methodManager.hideSoftInputFromWindow(view1.getWindowToken(),0);//3shan 2shel elkeyboard lma 2dos Add
        name.requestFocus();
        methodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.SHOW_IMPLICIT);
    }
}
else {
    Order o = new Order(namex, priceX, typeX, new Date());
    jOrderViewModel.insert(o);
    name.setText("");
    type.setText("");
    price.setText("");
    UpdateId = 0;
    Toast.makeText(ModifyMenu.this, "Added", Toast.LENGTH_SHORT).show();
    View view1 = ModifyMenu.this.getCurrentFocus();

    if(view1!=null)
    {
        InputMethodManager methodManager =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
       // methodManager.hideSoftInputFromWindow(view1.getWindowToken(),0);//3shan 2shel elkeyboard lma 2dos Add
     name.requestFocus();
        methodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.SHOW_IMPLICIT);
    }


}
//name.setFoc
    }

});
Update.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String namex = name.getText().toString();
        String typeX = type.getText().toString();
        float priceX = 0;
        try {
            priceX = Float.parseFloat(price.getText().toString());
        } catch (Exception e) {

        }
        if (UpdateId != 0) {
            if (priceX == 0 || namex.trim().isEmpty()) {
                Toast.makeText(ModifyMenu.this, "Enter Item Name And Price", Toast.LENGTH_SHORT).show();
            } else {
                Order o = new Order(namex, priceX, typeX, UpdateDate);
                o.setOrderId(UpdateId);
                jOrderViewModel.update(o);
                jClientViewModel.getAllClients().observe(ModifyMenu.this, new Observer<List<Client>>() {
                    @Override
                    public void onChanged(List<Client> clients) {
      /*  List<Order> temp = getAllClientsOrders(clients);
        List<Order> UpdatedClientsOrdersList = new ArrayList<>();
                        for (Order clientOrder:temp) {
                            if(clientOrder.getOrderId()==o.getOrderId())
                                clientOrder.setName(o.getName());
                            clientOrder.setType(o.getType());

                        }*/

      for(Client c:clients)
      {    List<Order> UpdatedClientsOrdersList = new ArrayList<>();
          List<Order> temp = c.getOrders();
          for(Order clientOrder:temp)
          {
              if(clientOrder.getOrderId()==o.getOrderId()) {
                  clientOrder.setName(o.getName());
                  clientOrder.setType(o.getType());
              }
              UpdatedClientsOrdersList.add(clientOrder);


          }
          Client tempClient ;
          tempClient  = c;
          tempClient.setOrders(UpdatedClientsOrdersList);
          jClientViewModel.update(tempClient);
      }
                    }
                });
                Toast.makeText(ModifyMenu.this, "Updated", Toast.LENGTH_SHORT).show();
                View view1 = ModifyMenu.this.getCurrentFocus();
                if(view1!=null)
                {
                    InputMethodManager methodManager =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    methodManager.hideSoftInputFromWindow(view1.getWindowToken(),0);//3shan 2shel elkeyboard lma 2dos Update

                }
            }
        }
        else
            Toast.makeText(ModifyMenu.this, "Select Item To Modify", Toast.LENGTH_SHORT).show();
    }

});
        ClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {   name.setText("");
                type.setText("");
                price.setText("");
                View view1 = ModifyMenu.this.getCurrentFocus();
                if(view1!=null) {
                    InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    // methodManager.hideSoftInputFromWindow(view1.getWindowToken(),0);//3shan 2shel elkeyboard lma 2dos Add
                    name.requestFocus();
                    methodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
    }

    List<Order> getAllClientsOrders(List<Client> clients)
    {List<Order> temp = new ArrayList<>();
        String totalOrders = "";
        DataConverter converter = new DataConverter();

        if (clients.size() != 0) {
            for (int i = 0; i < clients.size(); i++) {                                                 //[{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1},{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1}]

                //[{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1},{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1}],{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1},{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1}]
                String s = clients.get(i).getOrderStr();                                               //it will add new json Strings before ] in the original string(the one when i=0 )     and it will be added after ","          ;
                if (i != 0) {
                    String x = "," + s.substring(1, s.length() - 1);
                    StringBuffer stringBuffer = new StringBuffer(totalOrders);
                    stringBuffer.insert(totalOrders.length() - 1, x);
                    totalOrders = stringBuffer.toString();
                } else
                    totalOrders += s;

            }
        } else {
            totalOrders = "";
            temp = new ArrayList<>();
        }
        if (!totalOrders.trim().isEmpty())
            temp = converter.toOrdersList(totalOrders);
        return temp;

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("Name",name.getText().toString());
        outState.putString("Type",type.getText().toString());
        outState.putString("Price",price.getText().toString());
        DataConverter converter = new DataConverter();
        outState.putInt("itemId",UpdateId);
       // List<Order> Menu = ChosenOrders;
        //String StrOrders = converter.fromOrdersList(clientOrders);
        //outState.putString("Orders",StrOrders);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        DataConverter converter = new DataConverter();
        name.setText(savedInstanceState.getString("Name"));
        type.setText(savedInstanceState.getString("Type"));
        price.setText( savedInstanceState.getString("Price"));
        //ChosenOrders= converter.toOrdersList(savedInstanceState.getString("Orders"));
        UpdateId = savedInstanceState.getInt("itemId");
        CreateLists();

       // ChooseListAdapter.notifyDataSetChanged();
        //GetPrice(ChosenOrders);
    }

    void CreateLists() {


        }
}
