package com.dongjin.android.hongf.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.model.Store;
import com.dongjin.android.hongf.view.StoreDetailActivity;

import java.util.ArrayList;

/**
 * Created by user on 2016-12-27.
 */

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder>
{

    Context context;
    ArrayList<Store> stores;

    public StoreListAdapter(Context context){
        stores=new ArrayList<>();
        this.context=context;
    }
    public void setAdapterData(ArrayList<Store> stores){
        this.stores=new ArrayList<>();
        this.stores=stores;
        notifyDataSetChanged();

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_list,parent,false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        if(stores.get(position).getImageUrl()!=null){
            Glide.with(context).load(stores.get(position).getImageUrl()).override(500,200).into(holder.image);
        }else{
            holder.image.setImageResource(android.R.drawable.ic_menu_camera);
        }
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,StoreDetailActivity.class);
                intent.putExtra("Store",stores.get(position));
                Log.e("STORE TAG",stores.get(position).getStorename());
                context.startActivity(intent);
            }
        });
        holder.storeName.setText(stores.get(position).getStorename());
        holder.storeInfo.setText("평점 "+stores.get(position).getAveragerating()+"  즐겨찾기 "+stores.get(position).getBookmarkcount()+
                "리뷰 "+stores.get(position).getReviewcount());


    }

    @Override
    public int getItemCount() {
        return stores.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView storeName;
        CardView cardItem;
        TextView storeInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            storeName=(TextView)itemView.findViewById(R.id.tvStoreName);
            image=(ImageView)itemView.findViewById(R.id.image);
            cardItem=(CardView)itemView.findViewById(R.id.cardItem);
            storeInfo=(TextView)itemView.findViewById(R.id.tvStoreInfo);

        }
    }
}
