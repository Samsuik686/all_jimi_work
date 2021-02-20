package com.example.xinhaoxuan.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.xinhaoxuan.R;
import com.example.xinhaoxuan.bean.ShelvesBean;

import org.w3c.dom.Text;

import java.util.List;

public class ChooseShelvesAdapter extends RecyclerView.Adapter<ChooseShelvesAdapter.ViewHolder> {
    public List<ShelvesBean> list;

    public ChooseShelvesAdapter(List<ShelvesBean> list){
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShelvesBean shelves = list.get(position);
        holder.textView.setText(shelves.getName());
        if (shelves.isHaschoosed()){
            holder.textView.setBackgroundColor(Color.parseColor("#51D305"));
        }else {
            holder.textView.setBackgroundColor(Color.parseColor("#039CF2"));
        }
        if (shelves.isAvailable()==false){
            holder.textView.setBackgroundColor(Color.parseColor("#35363A"));
        }
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shelves.isAvailable()){
                    if (shelves.isAvailable()==true){
                        if (shelves.isHaschoosed()==true){
                            for (int i=0;i<list.size();i++){
                                list.get(i).setHaschoosed(false);
                            }
                        }
                        else {
                            for (int i=0;i<list.size();i++){
                                list.get(i).setHaschoosed(false);
                            }
                            shelves.setHaschoosed(!shelves.isHaschoosed());
                        }
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



    public String getOnclickedName(){
        String itemstringl="";
        for (int i=0;i<list.size();i++){
            if (list.get(i).isHaschoosed()==true){
                itemstringl = list.get(i).getName();
            }
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
