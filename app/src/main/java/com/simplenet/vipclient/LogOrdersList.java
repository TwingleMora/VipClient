package com.simplenet.vipclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LogOrdersList extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager1;
    TextView price, dateTxt;
    Button OpenCalender, best_seller;
    List<Order> ordersUsedInOrderingTheList;
    RecyclerView DisplayMenu;
    MenuListAdapter menuListAdapter;
    JClientViewModel jClientViewModel;
    JOrderViewModel jOrderViewModel;
    List<Client> client;
    List<Client> new_client;
    List<Order> _orders;
    List<Order> new_orders;
    MyAppDataBase dataBase;
    Dialog calenderDialog;
    Date chosenDate;
    String selectedDayDate = "";
    float prices = 0;
    public boolean showAll;
    Switch aSwitch;
    public String AllOrderInString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_orders_list);
        calenderDialog = new Dialog(this);
        aSwitch = findViewById(R.id.log_run_calender_switch);
        OpenCalender = findViewById(R.id.log_open_calender_btn);
        client = new ArrayList<>();
        _orders = new ArrayList<>();
        new_client = new ArrayList<>();
        new_orders = new ArrayList<>();
        dateTxt = findViewById(R.id.log_date);
        best_seller = findViewById(R.id.log_best_seller);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        setTitle("Log ");
        ordersUsedInOrderingTheList = new ArrayList<>();
        aSwitch.setChecked(true);
        if (aSwitch.isChecked())
            dateTxt.setText("All Orders");
        else {
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("dd/mm/yyyy");

            dateTxt.setText(format.format(date));
        }
        price = findViewById(R.id.log_total_cash);

        menuListAdapter = new MenuListAdapter(_orders, R.layout.custom_menu_list, false);
        // DisplayMenu.setHasFixedSize(true);


        DisplayMenu = findViewById(R.id.log_orders_list);

        DisplayMenu.setHasFixedSize(true);

        layoutManager1 = new LinearLayoutManager(this);
        DisplayMenu.setLayoutManager(layoutManager1);
        DisplayMenu.setAdapter(menuListAdapter);
        jOrderViewModel = ViewModelProviders.of(LogOrdersList.this).get(JOrderViewModel.class);

        jClientViewModel = ViewModelProviders.of(this).get(JClientViewModel.class);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                showAll = b;

                jClientViewModel.NotHiddenClients().observe(LogOrdersList.this, new Observer<List<Client>>() {
                    @Override
                    public void onChanged(List<Client> clients) {
                        if (showAll) {//wdddddddddddddddddddddddddddd
                            dateTxt.setText("All Orders");
                            String totalOrders = "";
                            DataConverter converter = new DataConverter();
                            client = clients;
                            if (clients.size() != 0) {
                                for (int i = 0; i < client.size(); i++) {                                                 //[{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1},{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1}]

                                    //[{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1},{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1}],{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1},{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1}]
                                    String s = client.get(i).getOrderStr();                                               //it will add new json Strings before ] in the original string(the one when i=0 )     and it will be added after ","          ;
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
                            }
                            if (!totalOrders.trim().isEmpty()) {
                                AllOrderInString = totalOrders;
                                _orders = converter.toOrdersList(totalOrders);

                            }
                            menuListAdapter.SetItem(_orders);
                            menuListAdapter.notifyDataSetChanged();
                            GetPrice(_orders);
                        }
                    }
                });
                if (!b) {
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                    dateTxt.setText("choose the day ");
                    menuListAdapter.SetItem(new ArrayList<>());
                    menuListAdapter.notifyDataSetChanged();
                    GetPrice(_orders);
                }
            }
        });
        jOrderViewModel.getAllOrders().observe(LogOrdersList.this, new Observer<List<Order>>() {

            @Override
            public void onChanged(List<Order> orders) {
                ordersUsedInOrderingTheList = orders;
            }
        });
        jClientViewModel.NotHiddenClients().observe(LogOrdersList.this, new Observer<List<Client>>() {
            @Override
            public void onChanged(List<Client> clients) {
                if (aSwitch.isChecked()) {//wdddddddddddddddddddddddddddd
                    String totalOrders = "";
                    DataConverter converter = new DataConverter();
                    client = clients;
                    if (clients.size() != 0) {
                        for (int i = 0; i < client.size(); i++) {                                                 //[{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1},{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1}]

                            //[{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1},{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1}],{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1},{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1}]
                            String s = client.get(i).getOrderStr();                                               //it will add new json Strings before ] in the original string(the one when i=0 )     and it will be added after ","          ;
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
                    }
                    if (!totalOrders.trim().isEmpty()) {
                        AllOrderInString = totalOrders;
                        _orders = converter.toOrdersList(totalOrders);

                    }

                    menuListAdapter.SetItem(_orders);
                    menuListAdapter.notifyDataSetChanged();
                    GetPrice(_orders);
                }
            }
        });


        OpenCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aSwitch.setChecked(false);
                PopUpWindow();
            }
        });
        best_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aSwitch.setChecked(false);

                menuListAdapter.setOrderAble(true);
List<Order> temp = getBestSeller();
                menuListAdapter.SetItem(temp);

                price.setText("Not Available");
                dateTxt.setText("Best Sellers All The Time");
                menuListAdapter.notifyDataSetChanged();


            }
        });
    }

    List<Order> getBestSeller() {
        List<Order> temp = new ArrayList<>();

                    if (!AllOrderInString.trim().isEmpty()) {
                        int indexOfTheComingO = 0;
                        int indexOfO = 0;
                        int indexOfN = 0;
                        int theSmallestOrdinalPlacaOAndXHad = 0;//dh 3lshan lw 3ndy msln item zhar 5 mrat get krnto m3 order mogod fy ellist el index bta3o wa7d fa elmfrod yb2a el item(2bo 5) fy elmkan wa7d w el item(2bo 2rb3a ) ynzl fy elmkan 2 ...bs lw krnt 2bo 5 tany m3 2bo 3 el fy el makan 2 fkda 2bo 5 hyb2 fy el mkan 2 w 2bo 3 hynzl llmkan 3 ..w laken 2bo 5 2kbr mn el2tnyn f2na 3ayzo yt7t fy 2s8r mkan galo fy elmokrn
                        // w lnfrd 2n 2bo 5 2tkarn m3 2bo 6 el fy elmkan 4 yb2a  msh hy3nd 3la(theSmallestOrdinalPlacaOAndXHad condition ) wlakn hys2l lw el6 2kbe mn el 5 yba 2bo 5 hya5d el mkan(bta3  2bo 6 + 1)
                        for (int i = 0; i < ordersUsedInOrderingTheList.size(); i++) {
                            Order o = ordersUsedInOrderingTheList.get(i);
                            String itemName = o.getName();
                            int x = GetItemsCount(AllOrderInString, o.getName());
                            indexOfO = i;
                            theSmallestOrdinalPlacaOAndXHad = -1;
                            {
                                for (int z = 0; z < temp.size(); z++) {
                                    Order n = temp.get(z);
                                    indexOfN = z;
                                    int x2 = GetItemsCount(AllOrderInString, n.getName());
                                    if (x2 >= x) {
                                        indexOfTheComingO = indexOfN + 1;
                                    } else {
                                        if (theSmallestOrdinalPlacaOAndXHad >= 0 && theSmallestOrdinalPlacaOAndXHad < indexOfN) {
                                            indexOfTheComingO = theSmallestOrdinalPlacaOAndXHad;
                                        } else {
                                            indexOfTheComingO = indexOfN;
                                            theSmallestOrdinalPlacaOAndXHad = indexOfN;
                                        }
                                    }
                                }
                            }
                            o.setPrice(x);//x is item counter
                            o.setDate(null);
                            temp.add(indexOfTheComingO, o);

                        }
                    }


        menuListAdapter.setOrderAble(true);
        menuListAdapter.notifyDataSetChanged();
return temp;

    }

    int GetItemsCount(String TheJsonList, String ItemName) {
        int Counter = 0;
        //String Temp = TheJsonList;//[{"Name":"xxx","Price":55.0,"Type":"","date":"Jul 22, 2019 7:46:42 AM","done":false,"orderId":2},{"Name":"xxx","Price":55.0,"Type":"","date":"Jul 22, 2019 7:46:42 AM","done":false,"orderId":2},{"Name":"xxx","Price":55.0,"Type":"","date":"Jul 22, 2019 7:46:42 AM","done":false,"orderId":2},{"Name":"ghhgg","Price":58.0,"Type":"","date":"Jul 22, 2019 7:46:48 AM","done":false,"orderId":1},{"Name":"ghhgg","Price":58.0,"Type":"","date":"Jul 22, 2019 7:46:48 AM","done":false,"orderId":1},{"Name":"zzz","Price":2.0,"Type":"","date":"Jul 22, 2019 7:46:49 AM","done":false,"orderId":3},{"Name":"gg","Price":23.0,"Type":"","date":"Jul 22, 2019 7:46:51 AM","done":false,"orderId":4}]
       //String findThisWord = "\""+ItemName+"\"";
        DataConverter dataConverter = new DataConverter();
        List<Order> orders = dataConverter.toOrdersList(TheJsonList);
          for (int i= 0;i<orders.size();i++)
          {Order orderHolder = orders.get(i);
              if(orderHolder.getName().equals(ItemName))
                  Counter++;
          }

        return Counter;

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


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Date", selectedDayDate);
        outState.putString("DateTxt", dateTxt.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selectedDayDate = savedInstanceState.getString("Date");
        dateTxt.setText(savedInstanceState.getString("DateTxt"));
    }

    void PopUpWindow() {
        calenderDialog.setContentView(R.layout.activity_date);
        Button Cancel;
        CalendarView calendarView;

        Cancel = calenderDialog.findViewById(R.id.activity_date_cancel);
        calendarView = calenderDialog.findViewById(R.id.activity_date_calender_access);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date tempDate = null;
        String dateStr = "";
        try {
            if (!selectedDayDate.trim().isEmpty())
                tempDate = simpleDateFormat.parse(selectedDayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (tempDate != null) {
            dateStr = simpleDateFormat.format(tempDate);
        }
        try {
            if (dateStr != "") {
                calendarView.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(dateStr).getTime(), true, true);
            }
        } catch (Exception e) {
            Date date = Calendar.getInstance().getTime();
            selectedDayDate = simpleDateFormat.format(date);
        }
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                String day = "", month = "", year = "";
                if (i1 < 10) {
                    month = "0" + (i1 + 1);
                    //selectedDayDate = i2 + "/" + "0" + (i1 + 1) + "/" + i;
                } else {
                    month = "" + (i1 + 1);
                    // selectedDayDate = i2 + "/" + (i1 + 1) + "/" + i;
                }
                if (i2 < 10) {
                    day = "0" + i2;
                    selectedDayDate = "0" + i2 + "/" + "0" + (i1 + 1) + "/" + i;
                } else {
                    day = "" + i2;

                }
                selectedDayDate = day + "/" + month + "/" + i;


                dateTxt.setText(selectedDayDate);
                //-----------------------------------------------------------------------------
                if (selectedDayDate != "" || !selectedDayDate.trim().isEmpty()) {
                    jClientViewModel.getClientsAtSomeDay(selectedDayDate).observe(LogOrdersList.this, new Observer<List<Client>>() {
                        @Override
                        public void onChanged(List<Client> clients) {
                            if (aSwitch.isChecked() == false && selectedDayDate != "") {//wdddddddddddddddd

                                String totalOrders = "";
                                DataConverter converter = new DataConverter();
                                new_client = clients;
                                if (new_client.size() != 0) {
                                    for (int i = 0; i < new_client.size(); i++) {                                                 //[{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1},{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1}]

                                        //[{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1},{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1}],{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1},{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1}]
                                        String s = new_client.get(i).getOrderStr();                                               //it will add new json Strings before ] in the original string(the one when i=0 )     and it will be added after ","          ;
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
                                    new_orders = new ArrayList<>();
                                }
                                if (!totalOrders.trim().isEmpty())
                                    new_orders = converter.toOrdersList(totalOrders);

                                menuListAdapter.SetItem(new_orders);
                                menuListAdapter.notifyDataSetChanged();
                                GetPrice(new_orders);
                            }
                        }
                    });
                }
            }

        });
        if (aSwitch.isChecked()) {
            dateTxt.setText("All Orders");
            menuListAdapter.SetItem(new ArrayList<Order>());
            menuListAdapter.notifyDataSetChanged();
        }

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //wdddddddddddddddd

                calenderDialog.dismiss();
            }

        });


        Long Ldate = calendarView.getDate();
        Date datee = new Date(calendarView.getDate());

        String day = "", month = "", year = "";
        DateFormat dayformat = new SimpleDateFormat("dd");
        DateFormat monthformat = new SimpleDateFormat("MM");
        DateFormat yearformat = new SimpleDateFormat("yyyy");
        day = dayformat.format(datee);
        month = monthformat.format(datee);
        year = yearformat.format(datee);


        selectedDayDate = day + "/" + month + "/" + year;
        dateTxt.setText(selectedDayDate);
        DataConverter converter = new DataConverter();
        //-----------------------------------------------------------------------------
        if (selectedDayDate != "" || !selectedDayDate.trim().isEmpty()) {
            jClientViewModel.getClientsAtSomeDay(selectedDayDate).observe(LogOrdersList.this, new Observer<List<Client>>() {
                @Override
                public void onChanged(List<Client> clients) {
//make the button update the list

                    new_client = clients;
                    String totalOrdersX = "";
                    if (new_client.size() != 0) {
                        for (int i = 0; i < new_client.size(); i++) {                                                 //[{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1},{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1}]

                            //[{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1},{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1}],{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1},{"Name":"ee","Price":12.0,"Type":"er","date":"Jul 18, 2019 1:18:41 AM","orderId":1}]
                            String s = new_client.get(i).getOrderStr();                                               //it will add new json Strings before ] in the original string(the one when i=0 )     and it will be added after ","          ;
                            if (i != 0) {
                                String x = "," + s.substring(1, s.length() - 1);
                                StringBuffer stringBuffer = new StringBuffer(totalOrdersX);
                                stringBuffer.insert(totalOrdersX.length() - 1, x);
                                totalOrdersX = stringBuffer.toString();
                            } else
                                totalOrdersX += s;

                        }
                    } else {
                        totalOrdersX = "";
                        new_orders = new ArrayList<>();
                    }
                    if (!totalOrdersX.trim().isEmpty())
                        new_orders = converter.toOrdersList(totalOrdersX);

                    menuListAdapter.SetItem(new_orders);
                    menuListAdapter.notifyDataSetChanged();
                    GetPrice(new_orders);
                }


            });
        }

        calenderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        calenderDialog.show();
    }

}









