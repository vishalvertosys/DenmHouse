package com.example.vishalmahajan.denmhouse.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vishalmahajan.denmhouse.Activity.AsynResult;

import com.example.vishalmahajan.denmhouse.Activity.MainDashboardActivity;
import com.example.vishalmahajan.denmhouse.Adapters.DiscountAdapter;
import com.example.vishalmahajan.denmhouse.DataBaseHelpers.DatabaseHelper;
import com.example.vishalmahajan.denmhouse.Models.AllProductsModel;
import com.example.vishalmahajan.denmhouse.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.vishalmahajan.denmhouse.DataBaseHelpers.DatabaseHelper.TABLE_ITEM;


public class TabFragment1 extends Fragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Handler mHandler;
    private RecyclerView recyclerView;
    private DiscountAdapter madapter;
    private ArrayList<AllProductsModel> discountList;
    AsynResult<AllProductsModel> asynResultListener;
    AsynResult<AllProductsModel> asynResultListenerMinus;
    Context context;
    AllProductsModel discountModel;
    List<AllProductsModel> discountModelList=new ArrayList<>();
    public String parent_id = "1";

    DatabaseHelper databaseHelper;
    public TabFragment1(Context context, AsynResult<AllProductsModel> asynResultListener, AsynResult<AllProductsModel> asynResultListenerMinus) {
        // Required empty public constructor
        this.context = context;
        this.asynResultListener = asynResultListener;
        this.asynResultListenerMinus = asynResultListenerMinus;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        discountList = new ArrayList<>();
        databaseHelper=new DatabaseHelper(context);

      //  madapter = new DiscountAdapter(getContext(), discountList, asynResultListener);

        setAdapter();
        discountProducts();
        mHandler = new Handler();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        mSwipeRefreshLayout.setOnRefreshListener(new  SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(discountList.size() > 0){
                            discountList.clear();
                            discountProducts();
                            madapter.notifyDataSetChanged();
                            recyclerView.getItemAnimator().setChangeDuration(0);
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                },
                        3000);
            }
        });
        return view;
    }

    public void setAdapter(){
        madapter = new DiscountAdapter(getContext(), discountList, asynResultListener, asynResultListenerModel, asynResultListenerMinus);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(madapter);
    }

    AsynResult<AllProductsModel> asynResultListenerModel = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel discountModel) {
            discountModel.setParent_id("1");
            discountModelList.add(discountModel);
          //  databaseHelper.insert(discountModel,parent_id);

            Boolean isItemExist = false;
            List<AllProductsModel> model=new ArrayList<>();
            model = databaseHelper.getData();
            for(AllProductsModel allProductsModel : model)
            {
                if(discountModel.getParent_id().equals(allProductsModel.getParent_id())&&discountModel.getId().equals(allProductsModel.getId()))
                {
                    int old_value = discountModel.getItemCount();
                    int new_value = allProductsModel.getItemCount();
                    int total= old_value+new_value;
                    discountModel.setItemCount(total);
                    isItemExist=true;
                    break;
                }else
                {
                    isItemExist=false;
                }
            }
            if(isItemExist)
            {

                databaseHelper.updateRecord(discountModel);
            }else{
                databaseHelper.insert(discountModel,parent_id);
            }




            ((MainDashboardActivity)getActivity()).dispatchInformations1(discountModelList);

        }
        @Override
        public void failure(String error) {

        }
        @Override
        public void passItemValue(int value){

        }
    };
    public void discountProducts() {

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String URL = "http://vertosys.com/denimhouse/webservices/categorywiseproduct.php"+"?category="+"discounts";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                Log.e("Response", response.toString());
                try {
                    response.getString("message");
                    Log.e("Data", response.getString("message"));
                    JSONArray jsonObj = new JSONArray(response.getString("data"));
                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject jsonObject1 = jsonObj.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String productName = jsonObject1.getString("prod_name");
                        String categoryId = jsonObject1.getString("category_id");
                        String productPrice = jsonObject1.getString("prod_price");
                        String discountType = jsonObject1.getString("discount_type");
                        String discountValue = jsonObject1.getString("discount_value");
                        String discountPrice = jsonObject1.getString("discount_price");
                        String productDescription = jsonObject1.getString("prod_desc");
                        String productImage = jsonObject1.getString("prod_image");
                        String status = jsonObject1.getString("status");
                        String categoryName = jsonObject1.getString("category_name");

                        discountModel = new AllProductsModel(id,productName, productDescription, productPrice, productImage, 0);
                        discountList.add(discountModel);

                    }
                    madapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsonObjectRequest);
    }
}
