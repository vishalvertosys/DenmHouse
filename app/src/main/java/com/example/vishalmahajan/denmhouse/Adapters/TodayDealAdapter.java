package com.example.vishalmahajan.denmhouse.Adapters;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vishalmahajan.denmhouse.Activity.AsynResult;
import com.example.vishalmahajan.denmhouse.Models.AllProductsModel;
import com.example.vishalmahajan.denmhouse.Models.TodayDealModel;
import com.example.vishalmahajan.denmhouse.Fragments.TabFragment2;
import com.example.vishalmahajan.denmhouse.Network.AppUrl;
import com.example.vishalmahajan.denmhouse.R;

import java.util.ArrayList;

/**
 * Created by vishal mahajan on 8/12/2017.
 */
public class TodayDealAdapter extends RecyclerView.Adapter<TodayDealAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<AllProductsModel> todayDealModels;
    AsynResult<AllProductsModel> asynResultListener;
    AsynResult<AllProductsModel> asynResultListenerModel;
    AsynResult<AllProductsModel> asynResultListenerMinus;
    double total;
    double totalCount = 0;
    int multi, particularItemCount=0;

    public TodayDealAdapter(Context mContext, ArrayList<AllProductsModel> todayDealModels, AsynResult<AllProductsModel> asynResultListener,
                            AsynResult<AllProductsModel> asynResultListenerModel, AsynResult<AllProductsModel> asynResultListenerMinus) {
        this.mContext = mContext;
        this.todayDealModels = todayDealModels;
        this.asynResultListener = asynResultListener;
        this.asynResultListenerModel = asynResultListenerModel;
        this.asynResultListenerMinus = asynResultListenerMinus;
    }


    @Override
    public TodayDealAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.today_deal_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TodayDealAdapter.MyViewHolder holder, final int position) {
        final AllProductsModel todayDealModel = todayDealModels.get(position);
        holder.title.setText(todayDealModel.getProductName());
        holder.description.setText(todayDealModel.getProductDescription());
        holder.newRate.setText(todayDealModel.getProductPrice());
        Glide.with(mContext).load(AppUrl.IMAGE_URL+todayDealModel.getProductImage()).into(holder.titleImage);
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = todayDealModel.getItemCount();
                if(sum == 0){
                    Toast.makeText(v.getContext(), "you have nothing to show", Toast.LENGTH_SHORT).show();
                }else{
                    asynResultListener.passItemValue(todayDealModels.get(position).getItemCount());
                    asynResultListener.success(todayDealModels.get(position));
                    asynResultListenerModel.success(todayDealModels.get(position));
                    Toast.makeText(v.getContext(), "Your item is added to shopping cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

      // holder.itemCount.setText(String.valueOf(todayDealModel.getItemCount()));

        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = todayDealModels.get(position).getItemCount();
                sum = sum+1;
                todayDealModels.get(position).setItemCount(sum);
                holder.itemCount.setText(String.valueOf(todayDealModels.get(position).getItemCount()));

                multiply();
                subTotal();
            }

            private void multiply() {
                total = Double.parseDouble(todayDealModels.get(position).getProductPrice());
                multi = todayDealModels.get(position).getItemCount();
                if(multi >= 1) {
                    particularItemCount = multi-1;
                    multi = multi - particularItemCount;
                }else{
                    multi = multi - particularItemCount;
                }
                /*if(particularItemCount > 1){
                   multi = discountModel.getItemCount() - particularItemCount;
                }else {
                    multi = discountModel.getItemCount();
                }*/
                total = total * multi;
                todayDealModels.get(position).setSubTotal(total);
                totalCount = totalCount + todayDealModels.get(position).getSubTotal();
                todayDealModels.get(position).setTotalCount(totalCount);
                // holder.subTotal.setText(String.valueOf(discountModel.getSubTotal()));
            }
            private void subTotal(){
                total = Double.parseDouble(todayDealModels.get(position).getProductPrice());
                int multi = todayDealModels.get(position).getItemCount();
                total = total * multi;
                todayDealModels.get(position).setSubTotal(total);
            }
        });

        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(todayDealModel.getItemCount()>0)
                {
                    asynResultListenerMinus.success(todayDealModels.get(position));
                    int sum= todayDealModels.get(position).getItemCount();
                    sum =sum-1;
                    todayDealModels.get(position).setItemCount(sum);
                    holder.itemCount.setText(String.valueOf(todayDealModels.get(position).getItemCount()));

                    divide();
                    subTotal();
                }

            }
            private void divide(){
                if(todayDealModel.getTotalCount() > 0){
                    double totalCount = todayDealModels.get(position).getTotalCount();
                    double mini = Double.parseDouble(todayDealModels.get(position).getProductPrice());
                    totalCount = totalCount - mini;
                    todayDealModels.get(position).setTotalCount(totalCount);

                }
            }

            private void subTotal() {
                if (todayDealModel.getItemCount() > 0) {
                    double subtotal3 = todayDealModels.get(position).getSubTotal();
                    double mini = Double.parseDouble(todayDealModels.get(position).getProductPrice());
                    subtotal3 = subtotal3 - mini;
                    todayDealModels.get(position).setSubTotal(subtotal3);
                    totalCount = totalCount + todayDealModels.get(position).getSubTotal();
                    todayDealModels.get(position).setTotalCount(totalCount);
                    //holder.subTotal.setText(String.valueOf(discountModel.getSubTotal()));
                }
            }

        });
    }

    @Override
    public int getItemCount() {
       /* return todayDealModels.size();*/
        return  todayDealModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, newRate, itemCount, oldRate;
        ImageView titleImage, defaultImage;
        Button addButton;
        ImageButton minusButton, plusButton;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.todayDealTitle);
            description = (TextView) view.findViewById(R.id.todayDealDescription);
            newRate = (TextView) view.findViewById(R.id.todayDealNewRate);
            titleImage = (ImageView) view.findViewById(R.id.todayDealTitleImage);
            addButton = (Button) view.findViewById(R.id.addButton);
            itemCount = (TextView) view.findViewById(R.id.itemCount);
            plusButton = (ImageButton) view.findViewById(R.id.plusButton);
            minusButton = (ImageButton) view.findViewById(R.id.minusButton);
        }

    }
}
