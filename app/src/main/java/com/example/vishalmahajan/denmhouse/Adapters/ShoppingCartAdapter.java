package com.example.vishalmahajan.denmhouse.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vishalmahajan.denmhouse.Activity.AsynResult;
import com.example.vishalmahajan.denmhouse.Models.AllProductsModel;
import com.example.vishalmahajan.denmhouse.Network.AppUrl;
import com.example.vishalmahajan.denmhouse.R;

import java.util.ArrayList;


public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {

    private double total;
    private Context mContext;
    private ArrayList<AllProductsModel> shoppingCartModels;
    private AsynResult<AllProductsModel> asynResultListenerPlus;
    private AsynResult<AllProductsModel> asynResultListenerMinus;
    private AsynResult<AllProductsModel> asynResultListenerRemove;

    public ShoppingCartAdapter(Context mContext, ArrayList<AllProductsModel> shoppingCartModels,
                               AsynResult<AllProductsModel> asynResultListenerPlus, AsynResult<AllProductsModel> asynResultListenerMinus,
                               AsynResult<AllProductsModel> asynResultListenerRemove) {
        this.mContext = mContext;
        this.shoppingCartModels = shoppingCartModels;
        this.asynResultListenerPlus = asynResultListenerPlus;
        this.asynResultListenerMinus = asynResultListenerMinus;
        this.asynResultListenerRemove = asynResultListenerRemove;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_cart_list, parent, false);
        return new ShoppingCartAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AllProductsModel shoppingCartModel = shoppingCartModels.get(position);
        holder.title.setText(shoppingCartModel.getProductName());
        holder.type.setText(shoppingCartModel.getCategoryName());
        holder.productCode.setText(shoppingCartModel.getId());
        holder.price.setText("$" + shoppingCartModel.getProductPrice());
        // holder.subTotal.setText("$"+String.valueOf(shoppingCartModel.getSubTotal()));
        Glide.with(mContext).load(AppUrl.IMAGE_URL + shoppingCartModel.getProductImage()).into(holder.image);

        holder.itemCount.setText(String.valueOf(shoppingCartModel.getItemCount()));
        holder.subTotal.setText(String.valueOf(shoppingCartModel.getSubTotal()));

        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = shoppingCartModels.get(position).getItemCount();
                sum = sum + 1;
                shoppingCartModels.get(position).setItemCount(sum);
                holder.itemCount.setText(String.valueOf(shoppingCartModels.get(position).getItemCount()));

                multiply();
                asynResultListenerPlus.success(shoppingCartModels.get(position));
            }

            private void multiply() {
                total = Double.parseDouble(shoppingCartModels.get(position).getProductPrice());
                int multi = shoppingCartModels.get(position).getItemCount();
                total = total * multi;
                shoppingCartModels.get(position).setSubTotal(total);
                holder.subTotal.setText(String.valueOf(shoppingCartModels.get(position).getSubTotal()));
            }

        });

        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shoppingCartModel.getItemCount() > 0) {
                    int sum = shoppingCartModels.get(position).getItemCount();
                    sum = sum - 1;
                    shoppingCartModels.get(position).setItemCount(sum);
                    holder.itemCount.setText(String.valueOf(shoppingCartModels.get(position).getItemCount()));

                    divide();
                    asynResultListenerMinus.success(shoppingCartModels.get(position));
                }

            }

            private void divide() {
                if (shoppingCartModel.getItemCount() > 0) {
                    double subtotal3 = shoppingCartModels.get(position).getSubTotal();
                    double mini = Double.parseDouble(shoppingCartModels.get(position).getProductPrice());
                    subtotal3 = subtotal3 - mini;
                    shoppingCartModels.get(position).setSubTotal(subtotal3);
                    holder.subTotal.setText(String.valueOf(shoppingCartModels.get(position).getSubTotal()));
                }
            }

        });

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newPosition = holder.getAdapterPosition();

                //   if(position)
                // if(!shoppingCartModels.get(position).equals(null))
                /*if(shoppingCartModels != null && shoppingCartModels.size() > 0)*/
                asynResultListenerRemove.success(shoppingCartModels.get(position));
                shoppingCartModels.remove(newPosition);
                notifyItemRemoved(newPosition);
                notifyItemRangeChanged(newPosition, shoppingCartModels.size());
            }
        });
    }


    @Override
    public int getItemCount() {
        return shoppingCartModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, type, productCode, price, subTotal, itemCount;
        ImageView image;
        ImageButton plusButton, minusButton, removeButton;


        MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.shoppingCartlTitle);
            type = (TextView) view.findViewById(R.id.typeRemote);
            productCode = (TextView) view.findViewById(R.id.productCodeRemote);
            price = (TextView) view.findViewById(R.id.priceRemote);
            subTotal = (TextView) view.findViewById(R.id.subtotalRemote);
            itemCount = (TextView) view.findViewById(R.id.itemCount);
            image = (ImageView) view.findViewById(R.id.shoppingCartlTitleImage);
            plusButton = (ImageButton) view.findViewById(R.id.plusButton);
            minusButton = (ImageButton) view.findViewById(R.id.minusButton);
            removeButton = (ImageButton) view.findViewById(R.id.removeButton);
        }
    }
}