package com.example.vishalmahajan.denmhouse.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vishalmahajan.denmhouse.Activity.AsynResult;

import com.example.vishalmahajan.denmhouse.Activity.CheckOut;
import com.example.vishalmahajan.denmhouse.Activity.MainDashboardActivity;
import com.example.vishalmahajan.denmhouse.Adapters.ShoppingCartAdapter;

import com.example.vishalmahajan.denmhouse.DataBaseHelpers.DatabaseHelper;
import com.example.vishalmahajan.denmhouse.Models.AllProductsModel;

import com.example.vishalmahajan.denmhouse.R;


import java.util.ArrayList;

import java.util.LinkedHashSet;
import java.util.List;

public class ShoppingCartFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    // private ArrayList<AllProductsModel> shoppingCartList;

    int count = 0;
    double totalItemCount;
    TextView itemsCount, totalCount;
    Button checkOut;
    Context context;


    List<AllProductsModel> discountModels;
    List<AllProductsModel> todayDealModels;
    List<AllProductsModel> allProductsModels;
    ArrayList<AllProductsModel> combinedList;
    DatabaseHelper databaseHelper;

    public ShoppingCartFragment(List<AllProductsModel> discountModels, List<AllProductsModel> todayDealModels, List<AllProductsModel> allProductsModels) {
        this.discountModels = discountModels;
        this.todayDealModels = todayDealModels;
        this.allProductsModels = allProductsModels;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        databaseHelper = new DatabaseHelper(context);
    }


    //  @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopping_cart_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        itemsCount = (TextView) view.findViewById(R.id.itemsCount);
        totalCount = (TextView) view.findViewById(R.id.totalCount);
        checkOut = (Button) view.findViewById(R.id.checkOutButton);
        // shoppingCartList = new ArrayList<>();
        combinedList = new ArrayList<>();

        ImageView textView = (ImageView) view.findViewById(R.id.back_button);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainDashboardActivity.class);
                intent.putExtra("Key_Data", "0");
                intent.putExtra("Key_Data1", "1");

                startActivity(intent);
            }
        });


        MainDashboardActivity mainDashboardActivity = (MainDashboardActivity) getActivity();

        checkOut.setOnClickListener(this);
        setAdapter();
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        return true;
                    }
                }
                return false;
            }
        });

        return view;

    }


    // @RequiresApi(api = Build.VERSION_CODES.M)
    public void setAdapter() {
  /*      //madapter = new ShoppingCartAdapter(getContext(),discountModels,asynResultListener);
        if (discountModels != null) {
            combinedList.addAll(discountModels);
        }
        if (todayDealModels != null) {
            combinedList.addAll(todayDealModels);
        }
        if (allProductsModels != null) {
            combinedList.addAll(allProductsModels);
        }*/
        combinedList = new ArrayList<>(databaseHelper.getData());
        //combinedList = new ArrayList<>(new LinkedHashSet<>(combinedList));

    /*    List<AllProductsModel> model = new ArrayList<>();
        for (int i = 0; i <= combinedList.size(); i++) {
            if (combinedList.get(i).getParent_id() == model.get(i+1).getParent_id() && combinedList.get(i).getId() == model.get(i).getId()){
                String combinesCount = String.valueOf(combinedList.get(i).getItemCount());
                String modelCount = String.valueOf(model.get(i).getItemCount());
                String totalCount = combinesCount + modelCount;
            }
        }*/
        ShoppingCartAdapter madapter = new ShoppingCartAdapter(getContext(), combinedList, asynResultListenerPlus, asynResultListenerMinus, asynResultListenerRemove);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(madapter);

    }

    AsynResult<AllProductsModel> asynResultListenerPlus = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel discountModel) {
            String totalCount1 = itemsCount.getText().toString();
            count = Integer.parseInt(totalCount1) + 1;
            itemsCount.setText(String.valueOf(count));

            String itemsCount = totalCount.getText().toString();
            totalItemCount = Double.parseDouble(itemsCount) + Double.parseDouble(discountModel.getProductPrice());
            totalCount.setText(String.valueOf(totalItemCount));
            /*subTotal = discountModel.getSubTotal();
            priceCount = priceCount + subTotal;
            totalCount.setText(String.valueOf(priceCount));*/

        }

        @Override
        public void failure(String error) {

        }

        @Override
        public void passItemValue(int value) {

        }
    };

    AsynResult<AllProductsModel> asynResultListenerMinus = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel discountModel) {

            String totalCount1 = itemsCount.getText().toString();
            count = Integer.parseInt(totalCount1) - 1;
            itemsCount.setText(String.valueOf(count));

            String itemsCount = totalCount.getText().toString();
            totalItemCount = Double.parseDouble(itemsCount) - Double.parseDouble(discountModel.getProductPrice());
            totalCount.setText(String.valueOf(totalItemCount));

                /*int newPrice = Integer.parseInt(totalCount2);
                newPrice = newPrice - Integer.parseInt(productPrice);
                totalCount.setText(String.valueOf(newPrice));*/

        }

        @Override
        public void failure(String error) {

        }

        @Override
        public void passItemValue(int value) {

        }
    };


    AsynResult<AllProductsModel> asynResultListenerRemove = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel discountModel) {

            double subTotal;
            double newTotalCount;
            String totalCount1 = itemsCount.getText().toString();
            databaseHelper.delete(discountModel);
            int eachItemCount = discountModel.getItemCount();
            int newItemCount = Integer.parseInt(totalCount1) - eachItemCount;
            itemsCount.setText(String.valueOf(newItemCount));


            String itemsCount = totalCount.getText().toString();

            subTotal = discountModel.getSubTotal();
            newTotalCount = Double.parseDouble(itemsCount) - subTotal;
            totalCount.setText(String.valueOf(newTotalCount));




            /*int eachItemCount = discountModel.getItemCount();
            String totalItemCountString = itemsCount.getText().toString();
            int totalItemCountInt = Integer.parseInt(totalItemCountString) - eachItemCount;
            itemsCount.setText(String.valueOf(totalItemCountInt));
            subTotal = discountModel.getSubTotal();
            priceCount = priceCount + subTotal;
            totalCount.setText(String.valueOf(priceCount));*/
        }

        @Override
        public void failure(String error) {

        }

        @Override
        public void passItemValue(int value) {

        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.checkOutButton:
                Intent intent = new Intent(getActivity(), CheckOut.class);
                intent.putParcelableArrayListExtra("key", combinedList);
                getActivity().startActivity(intent);
        }
    }
}