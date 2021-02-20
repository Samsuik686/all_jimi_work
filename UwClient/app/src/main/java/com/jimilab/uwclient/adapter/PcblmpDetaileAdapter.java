package com.jimilab.uwclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jimilab.uwclient.R;
import com.jimilab.uwclient.bean.pcb_bean.mission_detail_bean;
import com.jimilab.uwclient.bean.pcb_bean.mission_detail_son_bean;

import org.w3c.dom.Text;

import java.util.List;

public class PcblmpDetaileAdapter extends RecyclerView.Adapter<PcblmpDetaileAdapter.ViewHolder> {

    private List<mission_detail_son_bean> mlist;

    Context mcontext;

    String type;

    public PcblmpDetaileAdapter(List<mission_detail_son_bean> mlist,Context mcontext,String type){
        this.mcontext = mcontext;
        this.mlist = mlist;
        this.type = type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView number_id;
        TextView materialId;
        TextView number;
        TextView where;
        TextView cycle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number_id = itemView.findViewById(R.id.number_id);
            materialId = itemView.findViewById(R.id.materialId);
            number = itemView.findViewById(R.id.number);
            where = itemView.findViewById(R.id.where);
            cycle = itemView.findViewById(R.id.cycle);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bcp_son,parent,false);
        PcblmpDetaileAdapter.ViewHolder holder = new PcblmpDetaileAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (type.equals("in")){
            holder.where.setVisibility(View.VISIBLE);
            holder.cycle.setVisibility(View.GONE);
        }else if(type.equals("out")){
            holder.where.setVisibility(View.GONE);
            holder.cycle.setVisibility(View.VISIBLE);
        }
        holder.number_id.setText(String.valueOf(position+1));
        holder.materialId.setText(mlist.get(position).getMaterialId());
        holder.number.setText(mlist.get(position).getNumber());
        holder.where.setText(mlist.get(position).getWhere());
        holder.cycle.setText(mlist.get(position).getCycle());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

}
