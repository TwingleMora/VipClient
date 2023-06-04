package com.simplenet.vipclient;


import android.app.Activity;

public class ClientDataWindow extends Activity {
 /*   Button Cancel , Update;
    TextView Name;
    RecyclerView recyclerView;
    MenuListAdapter listAdapter;
RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_data);
        Button cancel = findViewById(R.id.window_client_close_btn);
        Intent intent =  getIntent();
        String nameStr = intent.getStringExtra(MainActivity.GET_CLIENT_NAME);
        String orderStr = intent.getStringExtra(MainActivity.GET_CLIENT_ORDER);
     DataConverter converter =new DataConverter();
     List<Order> orderList = converter.toOrdersList(orderStr);
        TextView name /*textOut*///= findViewById(R.id.window_client_name);
     //   String client_name= getName();
       // if(!client_name.equals("")) {
         //   name.setText(client_name);
        //}
    /*
        name.setText(nameStr);
        recyclerView = findViewById(R.id.window_client_orders_list);
        List<Order> orders =new ArrayList<>();
        orders = orderList;
        //orders = client.getOrders();
        layoutManager = new LinearLayoutManager(this);
        MenuListAdapter listAdapter = new MenuListAdapter(orders,R.layout.custom_menu_list,false);
recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapter);
        //Cancel = popupView.findViewById(R.id.window_client_close_btn);
        //Update = popupView.findViewById(R.id.window_client_edit_btn);

    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });
    }
    */
}
