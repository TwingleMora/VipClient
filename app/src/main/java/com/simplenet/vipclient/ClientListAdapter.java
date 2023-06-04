package com.simplenet.vipclient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ViewHolder> {
    private List<Client> clients = new ArrayList<Client>();
    OnItemClickListenger listenger;

    public  class ViewHolder extends RecyclerView.ViewHolder
    {
       private TextView name , totalPrice , remain , date,priority;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.client_name_display);
            totalPrice = itemView.findViewById(R.id.client_price_display);
            remain = itemView.findViewById(R.id.client_remain_display);
            date = itemView.findViewById(R.id.client_date);
           // priority = itemView.findViewById(R.id.client_priority);
itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        if(listenger!=null&&position!=RecyclerView.NO_POSITION) {
            Client c = clients.get(position);
            listenger.onItemClick(c);
        }
        }
});
        }
    }
    interface OnItemClickListenger
    {
        void onItemClick(Client client);

    }
    public void SetOnItemClickListenger(OnItemClickListenger listenger)
    {
        this.listenger = listenger;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_client_list,parent,false);
        return  new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Client clientHolder = clients.get(position);
        holder.name.setText(clientHolder.getName());
       // holder.priority.setText(String.valueOf(clientHolder.getPriority()));
        holder.totalPrice.setText(String.valueOf(clientHolder.getTotalPrice()));
        holder.remain.setText(String.valueOf(clientHolder.getRemain()));
        SimpleDateFormat formatter =  new SimpleDateFormat("E, dd MMM yyyy h:mm:ss a");
        String strDate="";
        if(clientHolder.getDate()!=null)
       strDate = formatter.format(clientHolder.getDate());




        holder.date.setText(strDate);
        // holder.paid.setText(String.valueOf(clientHolder.getPaid()));

    }

    @Override
    public int getItemCount() {
        return clients.size();
    }
    public void setClients(List<Client> clients)
    {
        this.clients = clients;
        notifyDataSetChanged();
    }
    public Client getClientAtPos(int pos)
    {
        Client c = new Client();
        c = clients.get(pos);
    return  c;
    }

}
