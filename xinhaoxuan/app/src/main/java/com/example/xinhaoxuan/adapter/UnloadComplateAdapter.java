package com.example.xinhaoxuan.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.xinhaoxuan.R;
import com.example.xinhaoxuan.bean.DeviceBean;
import com.example.xinhaoxuan.bean.ShelvesBean;

import java.util.List;

public class UnloadComplateAdapter extends RecyclerView.Adapter<UnloadComplateAdapter.ViewHolder> {
    private List<DeviceBean> list;

    public UnloadComplateAdapter(List<DeviceBean> list){
        this.list = list;
    }

    @Override
    public UnloadComplateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complete,parent,false);
        UnloadComplateAdapter.ViewHolder holder = new UnloadComplateAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(UnloadComplateAdapter.ViewHolder holder, int position) {
        DeviceBean Device = list.get(position);
        holder.textView.setText(Device.getName());
        if (Device.isHaschoosed()){
            holder.textView.setBackgroundColor(Color.parseColor("#51D305"));
        }else {
            holder.textView.setBackgroundColor(Color.parseColor("#039CF2"));
        }
        if (Device.isAvailable()==false){
            holder.textView.setBackgroundColor(Color.parseColor("#35363A"));
        }
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Device.isAvailable()){
                    if (Device.isAvailable()==true){
                        Device.setHaschoosed(!Device.isHaschoosed());
                        notifyDataSetChanged();
                    }

                }
            }
        });
    }

    public String getOnclickedItem(){
        String itemstringl="";
        for (int i=0;i<list.size();i++){
            if (list.get(i).isHaschoosed()==true){
                itemstringl += list.get(i).getId()+",";
            }
        }
        if (itemstringl.length()>0){
            itemstringl = itemstringl.substring(0, itemstringl.length() - 1);
        }
        return itemstringl;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder( View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item);
        }
    }
}
