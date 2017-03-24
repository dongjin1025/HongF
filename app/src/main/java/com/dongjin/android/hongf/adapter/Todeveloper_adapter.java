package com.dongjin.android.hongf.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.model.Deve;
import com.dongjin.android.hongf.view.Activity_deve_detail;

import java.util.ArrayList;

/**
 * Created by kimdongjin on 2017. 3. 22..
 */

public class Todeveloper_adapter extends RecyclerView.Adapter<Todeveloper_adapter.ViewHolder> {


    Context context;
    ArrayList<Deve> deves;
    public Todeveloper_adapter(Context context){
        this.context=context;
        deves=new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todeveloper,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.title.setText(deves.get(position).getTitle().toString());
        holder.date.setText(deves.get(position).getDate().toString());
        holder.cardView.setTag(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            String posi= ""+holder.cardView.getTag();
            @Override
            public void onClick(View v) {
                Intent intetn=new Intent(context, Activity_deve_detail.class);
                intetn.putExtra("index",posi);
                context.startActivity(intetn);
            }
        });

    }
    public void setData(ArrayList<Deve> deves){
        this.deves=deves;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return deves.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView date;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.dev_Card);
            title=(TextView)itemView.findViewById(R.id.develist_tv_title);
            date=(TextView)itemView.findViewById(R.id.develist_tv_date);

        }

    }

}
