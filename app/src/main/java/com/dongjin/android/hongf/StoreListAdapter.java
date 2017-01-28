package com.dongjin.android.hongf;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongjin.android.hongf.view.StoreDetail;

/**
 * Created by user on 2016-12-27.
 */

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder>
{

    Context context;

    public StoreListAdapter(Context context){

        this.context=context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_list,parent,false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        holder.image.setImageResource(R.drawable.food02);
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,StoreDetail.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        CardView cardItem;

        public ViewHolder(View itemView) {
            super(itemView);

            image=(ImageView)itemView.findViewById(R.id.image);
            cardItem=(CardView)itemView.findViewById(R.id.cardItem);

        }
    }
}