package com.dongjin.android.hongf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.model.Store;

import java.util.ArrayList;

/**
 * Created by kimdongjin on 2017. 3. 2..
 */

public class MyPageAdapter extends RecyclerView.Adapter<MyPageAdapter.ViewHolder> {



    ArrayList<Store> stores;
    Context context;


    public MyPageAdapter(ArrayList<Store> stores,Context context){
        this.stores=new ArrayList<>();
        this.stores=stores;
        this.context=context;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String uri=stores.get(position).getImageUrl();
        if(uri!=null) {
            Glide.with(context).load(uri).into(holder.image);
        }else{
            holder.image.setImageResource(R.drawable.korean);
        }
        holder.tvStoreName.setText(stores.get(position).getStorename());

    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tvStoreName;
        TextView tvStoreInfo;
        public ViewHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image);
            tvStoreName=(TextView)itemView.findViewById(R.id.tvStoreName);
            tvStoreInfo=(TextView)itemView.findViewById(R.id.tvStoreInfo);
        }
    }
}
