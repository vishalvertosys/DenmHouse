package com.example.vishalmahajan.denmhouse.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.example.vishalmahajan.denmhouse.Fragments.TabFragment3;
import com.example.vishalmahajan.denmhouse.Models.AllProductModel;
import com.example.vishalmahajan.denmhouse.Models.AllProductsModel;
import com.example.vishalmahajan.denmhouse.Models.TodayDealModel;
import com.example.vishalmahajan.denmhouse.Network.AppUrl;
import com.example.vishalmahajan.denmhouse.R;

import java.util.ArrayList;

/**
 * Created by Android-Dev2 on 8/14/2017.
 */

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<AllProductsModel> allproductModels;
    AsynResult<AllProductsModel> asynResultListener;
    AsynResult<AllProductsModel> asynResultListenerModel;
    AsynResult<AllProductsModel> asynResultListenerMinus;
    double total;
    double totalCount = 0;
    int multi, particularItemCount=0;

    public AllProductAdapter(Context mContext, ArrayList<AllProductsModel> allproductsModels, AsynResult<AllProductsModel> asynResultListener,
                             AsynResult<AllProductsModel> asynResultListenerModel, AsynResult<AllProductsModel> asynResultListenerMinus) {
        this.mContext = mContext;
        this.allproductModels = allproductsModels;
        this.asynResultListener = asynResultListener;
        this.asynResultListenerModel = asynResultListenerModel;
        this.asynResultListenerMinus = asynResultListenerMinus;
    }


    @Override
    public AllProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_product_list, parent, false);

        return new AllProductAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AllProductAdapter.MyViewHolder holder, final int position) {

        final AllProductsModel allproductModel = allproductModels.get(position);
        holder.title.setText(allproductModel.getProductName());
        holder.description.setText(allproductModel.getProductDescription());
        holder.newRate.setText(allproductModel.getProductPrice());
        Glide.with(mContext).load(AppUrl.IMAGE_URL+allproductModel.getProductImage()).into(holder.titleImage);

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = allproductModel.getItemCount();
                if(sum == 0){
                    Toast.makeText(v.getContext(), "you have nothing to show", Toast.LENGTH_SHORT).show();
                }else{
                    asynResultListener.passItemValue(allproductModels.get(position).getItemCount());
                    asynResultListener.success(allproductModels.get(position));
                    asynResultListenerModel.success(allproductModels.get(position));
                    Toast.makeText(v.getContext(), "your item is added to shopping cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

       // holder.itemCount.setText(String.valueOf(allproductModel.getItemCount()));

        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = allproductModels.get(position).getItemCount();
                sum = sum+1;
                allproductModel.setItemCount(sum);
                holder.itemCount.setText(String.valueOf(allproductModels.get(position).getItemCount()));

                multiply();
                subTotal();
            }

            private void multiply() {
                total = Double.parseDouble(allproductModels.get(position).getProductPrice());
                multi = allproductModels.get(position).getItemCount();
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
                allproductModel.setSubTotal(total);
                totalCount = totalCount + allproductModels.get(position).getSubTotal();
                allproductModels.get(position).setTotalCount(totalCount);
                // holder.subTotal.setText(String.valueOf(discountModel.getSubTotal()));
            }

            private void subTotal(){
                total = Double.parseDouble(allproductModels.get(position).getProductPrice());
                int multi = allproductModels.get(position).getItemCount();
                total = total * multi;
                allproductModel.setSubTotal(total);
            }
        });

        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allproductModels.get(position).getItemCount()>0)
                {
                    asynResultListenerMinus.success(allproductModels.get(position));
                    int sum= allproductModels.get(position).getItemCount();
                    sum =sum-1;
                    allproductModels.get(position).setItemCount(sum);
                    holder.itemCount.setText(String.valueOf(allproductModels.get(position).getItemCount()));

                    divide();
                    subTotal();
                }

            }

            private void divide(){
                if(allproductModels.get(position).getTotalCount() > 0){
                    double totalCount = allproductModels.get(position).getTotalCount();
                    double mini = Double.parseDouble(allproductModels.get(position).getProductPrice());
                    totalCount = totalCount - mini;
                    allproductModels.get(position).setTotalCount(totalCount);
                }
            }

            private void subTotal() {
                if (allproductModels.get(position).getItemCount() > 0) {
                    double subtotal3 = allproductModels.get(position).getSubTotal();
                    double mini = Double.parseDouble(allproductModels.get(position).getProductPrice());
                    subtotal3 = subtotal3 - mini;
                    allproductModels.get(position).setSubTotal(subtotal3);
                    totalCount = totalCount + allproductModels.get(position).getSubTotal();
                    allproductModels.get(position).setTotalCount(totalCount);
                    //holder.subTotal.setText(String.valueOf(discountModel.getSubTotal()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return  allproductModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, newRate, itemCount,  oldRate;
        ImageView titleImage, defaultImage;
        Button addButton;
        ImageButton minusButton, plusButton;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.allProductTitle);
            description = (TextView) view.findViewById(R.id.allProductDescription);
            newRate = (TextView) view.findViewById(R.id.allProductNewRate);
            titleImage = (ImageView) view.findViewById(R.id.allProductTitleImage);
            addButton = (Button) view.findViewById(R.id.addButton);
            itemCount = (TextView) view.findViewById(R.id.itemCount);
            plusButton = (ImageButton) view.findViewById(R.id.plusButton);
            minusButton = (ImageButton) view.findViewById(R.id.minusButton);

        }

    }
}
