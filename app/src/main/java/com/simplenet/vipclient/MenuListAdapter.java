package com.simplenet.vipclient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {
    private List<Order> items;
    private int mResource;
    private OnItemClickListener mListener;
    private boolean deleteable;
    private  boolean doneAble = false;
    private boolean OrderAble = false;//yktb el trtyb y3ny
    private boolean askBeforeDeleting = false;

    public void setOrderAble(boolean orderAble) {
        OrderAble = orderAble;
    }

    public void setDoneAble(boolean doneAble) {
        this.doneAble = doneAble;
    }

    public MenuListAdapter() {

    }

    //  public MenuListAdapter(List<Order>orders,int mResource,boolean deleteable)
    public MenuListAdapter(List<Order> orders, int mResource, boolean deleteable) {
        this.items = orders;
        this.mResource = mResource;
        this.deleteable = deleteable;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);

        void onDeleteClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_name;
        TextView item_price;
        TextView item_type;
        TextView item_date;
        TextView item_order;
        ImageView deleteBtnImg;
        ImageView doneSign;

        MyViewHolder(View view, final OnItemClickListener listener, boolean deletable,int mResource,boolean doneAble) {
            super(view);

            item_name = view.findViewById(R.id.item_name_new);
            item_price = view.findViewById(R.id.item_price_new);
            item_type = view.findViewById(R.id.item_type_new);
            item_date = view.findViewById(R.id.item_date_new);

                if(mResource==R.layout.custom_menu_list) {
                    doneSign = view.findViewById(R.id.item_done_sign);
                    item_order = view.findViewById(R.id.item_order_new);

                }


            if (deletable == true) {
                deleteBtnImg = view.findViewById(R.id.deleteImg);
            }
            if (deletable == false) {
                deleteBtnImg = view.findViewById(R.id.deleteImg);
                if (deleteBtnImg != null) {
                    deleteBtnImg.setImageResource(R.drawable.not_deletable);
                }
            }
            if (deleteBtnImg != null && deletable == true) {
                deleteBtnImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            int pos = getAdapterPosition();
                            if (pos != RecyclerView.NO_POSITION) {
                                listener.onDeleteClick(pos);
                            }
                        }
                    }
                });
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            listener.onItemClick(pos);
                        }
                    }
                }
            });
        }

    }


    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mResource, parent, false);
        // return new MyViewHolder(v);
        MyViewHolder myViewHolder = new MyViewHolder(v, mListener, deleteable,mResource,doneAble);
        return myViewHolder;
    }

    @Override
    public int getItemCount() {

        return items.size();//size msh count Update:mfesh count fy el ArrayList
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order item = items.get(position);
        holder.item_name.setText(item.getName());
        if (item.getType().trim().isEmpty()) {
            holder.item_type.setText("Empty!!");
        } else {
            holder.item_type.setText(item.getType());
        }
        holder.item_price.setText(String.valueOf(item.getPrice()));

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM h:mm:ss a");
        String strDate = "";
        if (item.getDate() != null) {
            if (holder.item_date != null) {
                strDate = formatter.format(item.getDate());

                holder.item_date.setText(strDate);
            }
        }
        else
            holder.item_date.setText("");
        if(mResource==R.layout.custom_menu_list&&OrderAble)
        {
            String OrderdByNum ="";
            if(position==0)
                OrderdByNum= "st" ;
            else if (position==1)
                OrderdByNum = "nd";
            else if (position==2)
                OrderdByNum="rd";
            else
            {
                if(position!=0)
                    OrderdByNum="th";
            }
            String theFullSentance = String.valueOf(position+1)+OrderdByNum;
            holder.item_order.setText(theFullSentance);
            if(position==getItemCount()-1)// l2n elpos bybd2 mn 0 while getitemCount bybtdy mn 1
                setOrderAble(false);
        }
        if(mResource==R.layout.custom_menu_list&&doneAble) {
            if (item.isDone()) {
                holder.doneSign.setImageResource(R.drawable.ic_done_black_24dp);


            }
            else if (!item.isDone()) {
                holder.doneSign.setImageResource(0);
            }
        }

    }
    Order getOrderAt(int pos)
    {
return items.get(pos);
    }
    void SetItem(List<Order> items) {
        this.items = items;
    }
}
