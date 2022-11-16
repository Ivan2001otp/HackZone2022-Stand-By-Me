package com.example.standbyme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.standbyme.Major.MajorActivity;
import com.example.standbyme.Model.CustomerSlot;
import com.example.standbyme.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public List<CustomerSlot>recycler_list;
    public Context context;
    public OnCustomerClickListener onCustomerClickListener;

    public RecyclerViewAdapter(List<CustomerSlot> list, OnCustomerClickListener onCustomerClickListener, final Context context){
        this.context = context;
        this.recycler_list = list;
        this.onCustomerClickListener = onCustomerClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todo_row_book_slot,parent,false);
        return new ViewHolder(view,onCustomerClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CustomerSlot cs = recycler_list.get(position);
            holder.username.setText(cs.getUserName());
            holder.bookedTimeSlot.setText(cs.getSlot_book());
    }

    @Override
    public int getItemCount() {
        return recycler_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView username;
        TextView bookedTimeSlot;
        OnCustomerClickListener onCustomerClickListener;

        public ViewHolder(@NonNull View itemView,OnCustomerClickListener onCustomerClickListener) {
            super(itemView);
            username = itemView.findViewById(R.id.row_name);
            bookedTimeSlot = itemView.findViewById(R.id.row_slot_booked);
            this.onCustomerClickListener = onCustomerClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCustomerClickListener.onClickCustomerSlot(getAdapterPosition());
        }
    }

    public interface OnCustomerClickListener{
        void onClickCustomerSlot(int position);
    }
}
