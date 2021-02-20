package com.example.smt_kanban_android.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smt_kanban_android.R;
import com.example.smt_kanban_android.bean.WorkOrderBean;


import java.util.List;

public class UnloadComplateAdapter extends RecyclerView.Adapter<UnloadComplateAdapter.ViewHolder> {
    private List<WorkOrderBean> list;

    public UnloadComplateAdapter(List<WorkOrderBean> list){
        this.list = list;
    }

    @Override
    public UnloadComplateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        UnloadComplateAdapter.ViewHolder holder = new UnloadComplateAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(UnloadComplateAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        WorkOrderBean Device = list.get(position);
        holder.number.setText(String.valueOf(position+1));
        holder.linenumber.setText(Device.getLine());
        holder.workerorder.setText(Device.getZhidan());
        holder.model.setText(Device.getMachine_name());
        holder.all_number.setText(Device.getNest_quantity());
        holder.customer.setText(Device.getClient());
        holder.have_prodect.setText(Device.getAlready_product());
        holder.standard_prodect.setText(Device.getStandard_product());
        holder.not_good_num.setText(Device.getDefects_number());
        holder.not_good_rate.setText(Device.getDefects_rate());
        holder.line_bank_num.setText(Device.getConsecutive_number());
        holder.status.setText(Device.getState());
        if (position%2==0){
            holder.fatherlayout.setBackgroundColor(Color.parseColor("#E8E8E8"));
        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView number;
        TextView linenumber;
        TextView workerorder;
        TextView model;
        TextView all_number;
        TextView customer;
        TextView have_prodect;
        TextView standard_prodect;
        TextView not_good_num;
        TextView not_good_rate;
        TextView line_bank_num;
        TextView status;
        LinearLayout fatherlayout;
        public ViewHolder( View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
            linenumber = itemView.findViewById(R.id.linenumber);
            workerorder = itemView.findViewById(R.id.workerorder);
            model = itemView.findViewById(R.id.model);
            all_number = itemView.findViewById(R.id.all_number);
            customer = itemView.findViewById(R.id.customer);
            have_prodect = itemView.findViewById(R.id.have_prodect);
            standard_prodect = itemView.findViewById(R.id.standard_prodect);
            not_good_num = itemView.findViewById(R.id.not_good_num);
            not_good_rate = itemView.findViewById(R.id.not_good_rate);
            line_bank_num = itemView.findViewById(R.id.line_bank_num);
            status = itemView.findViewById(R.id.status);
            fatherlayout = itemView.findViewById(R.id.fatherlayout);
        }
    }
}
