package com.dongjin.android.hongf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.model.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kimdongjin on 2017. 2. 13..
 */

public class SearchForReviewAdapter extends RecyclerView.Adapter<SearchForReviewAdapter.ViewHolder> {

    ArrayList<Store> stores;
    List<Store> stores2;
    Context context;
    public SearchForReviewAdapter(Context context){
        stores=new ArrayList<>();
        this.context=context;
    }
    public void setAdapterData(List<Store> stores){
        this.stores2=stores;
        this.stores.addAll(stores2);
        notifyDataSetChanged();

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_for_review,parent,false);


        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvTitle.setText(stores.get(position).getStorename());
        holder.tvAddress.setText(stores.get(position).getStoreaddress());

    }

    @Override
    public int getItemCount() {
        return stores.size();
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        stores.clear();
        if (charText.length() == 0) {
            stores.addAll(stores2);
        }
        else
        {
            for (Store store : stores)
            {
                if (store.getStorename().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    stores.add(store);
                }
            }
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvAddress;
        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle=(TextView)itemView.findViewById(R.id.tvTitle);
            tvAddress=(TextView)itemView.findViewById(R.id.tvAddress);



        }
    }
}
