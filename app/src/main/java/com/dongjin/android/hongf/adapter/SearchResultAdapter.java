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
import com.dongjin.android.hongf.model.Item;

import java.util.ArrayList;

/**
 * Created by kimdongjin on 2017. 2. 2..
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    ArrayList<Item> items;
    Context context;
    public SearchResultAdapter(Context context){
        this.context=context;
        items=new ArrayList<>();
    }
    public void setAdapterData(ArrayList<Item> items){
        this.items=items;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_results,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Glide.with(context).load(items.get(position).getImageUrl()).into(holder.resultIg);
        holder.resultNmTv.setText(items.get(position).getTitle());
        holder.resultAdrsTv.setText(items.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView resultIg;
        TextView resultNmTv;
        TextView resultAdrsTv;
        public ViewHolder(View itemView) {
            super(itemView);
            resultIg=(ImageView)itemView.findViewById(R.id.igResult);
            resultNmTv=(TextView)itemView.findViewById(R.id.vhStoreName);
            resultAdrsTv=(TextView)itemView.findViewById(R.id.vhAddress);
        }
    }
}
