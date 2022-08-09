package com.example.evgobusiness;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class transactionAdopter extends RecyclerView.Adapter<transactionAdopter.myViewHolder> {

    Context context;
    ArrayList<TransactionDetailsService>transactionArrayList;

    public transactionAdopter(Context context, ArrayList<TransactionDetailsService> transactionArrayList) {
        this.context = context;
        this.transactionArrayList = transactionArrayList;
    }

    @NonNull
    @Override
    public transactionAdopter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.items,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull transactionAdopter.myViewHolder holder, int position) {

        TransactionDetailsService transactionDetailsService = transactionArrayList.get(position);

        holder.nameC.setText(transactionDetailsService.customer_name);
        holder.emailC.setText(transactionDetailsService.email);
        holder.slotHrC.setText(transactionDetailsService.Slot_hour);
        holder.amountC.setText(transactionDetailsService.Amount);
        holder.dateC.setText(transactionDetailsService.Date_Time);
        holder.transactionIdC.setText(transactionDetailsService.TransactionID);
        holder.customer_QueryC.setText(transactionDetailsService.Customer_Query);

    }

    @Override
    public int getItemCount() {
        return transactionArrayList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        TextView nameC,emailC,slotHrC,amountC,dateC,transactionIdC,phoneNumberC,customer_QueryC;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            nameC = itemView.findViewById(R.id.nameC);
            emailC=itemView.findViewById(R.id.emailC);
            slotHrC=itemView.findViewById(R.id.slotHrC);
            amountC=itemView.findViewById(R.id.amountC);
            dateC=itemView.findViewById(R.id.dateC);
            transactionIdC=itemView.findViewById(R.id.transactionIdC);
            phoneNumberC=itemView.findViewById(R.id.phoneNumberC);
            customer_QueryC=itemView.findViewById(R.id.customer_Query);
        }
    }
}
