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
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vishalmahajan.denmhouse.Activity.AsynResult;
import com.example.vishalmahajan.denmhouse.Activity.MainDashboardActivity;
import com.example.vishalmahajan.denmhouse.Adapters.AllProductAdapter;
import com.example.vishalmahajan.denmhouse.DataBaseHelpers.DatabaseHelper;
import com.example.vishalmahajan.denmhouse.Models.AllProductsModel;
import com.example.vishalmahajan.denmhouse.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabFragment3 extends Fragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Handler mHandler;
    EditText search;
    private RecyclerView recyclerView;
    private AllProductAdapter madapter;
    private ArrayList<AllProductsModel> allProductList;
    private ArrayList<AllProductsModel> filteredList = new ArrayList<>();
    AsynResult<AllProductsModel> asynResultListener;
    AsynResult<AllProductsModel> asynResultListenerMinus;
    Context context;
    AllProductsModel allProductModel;
    List<AllProductsModel> allProductsModelList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    public String parent_id = "3";

    public TabFragment3(Context context, AsynResult<AllProductsModel> asynResultListener, AsynResult<AllProductsModel> asynResultListenerMinus) {
        this.context = context;
        this.asynResultListener = asynResultListener;
        this.asynResultListenerMinus = asynResultListenerMinus;

          }

    public TabFragment3(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        search = (EditText) view.findViewById(R.id.search);

        allProductList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(context);

        //madapter = new AllProductAdapter(getContext(), allProductList, asynResultListener);

        setAdapter();
        getAllProducts();
        // doSearch();

        mHandler = new Handler();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (allProductList.size() > 0) {
                            allProductList.clear();
                            getAllProducts();
                            madapter.notifyDataSetChanged();
                        }

                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);

            }
        });
        return view;
    }

    /*private void doSearch() {

        if(search != null)
           search.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                filter(text);
            }

        });
    }*/

    /*public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        allProductList.clear();
        if (charText.length() == 0) {
            allProductList.addAll(filteredList);
        } else {
            for (AllProductsModel name : filteredList) {
                if (name.getProductName().toLowerCase(Locale.getDefault()).contains(charText)) {

                    allProductList.add(name);
                }
            }
        }
        madapter.notifyDataSetChanged();
    }
*/
    public void setAdapter() {
        madapter = new AllProductAdapter(getContext(), allProductList, asynResultListener, asynResultListenerModel, asynResultListenerMinus);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(madapter);
    }

    AsynResult<AllProductsModel> asynResultListenerModel = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel allProductsModel) {
            allProductsModel.setParent_id("2");
            allProductsModelList.add(allProductsModel);
            Boolean isItemExist = false;
            List<AllProductsModel> model=new ArrayList<>();
            model = databaseHelper.getData();
            for(AllProductsModel allProductsModels : model)
            {
                if(allProductsModel.getParent_id().equals(allProductsModels.getParent_id())&&allProductsModel.getId().equals(allProductsModels.getId()))
                {
                    int old_value = allProductsModel.getItemCount();
                    int new_value = allProductsModels.getItemCount();
                    int total= old_value+new_value;
                    allProductsModel.setItemCount(total);
                    isItemExist=true;
                    break;
                }else
                {
                    isItemExist=false;
                }
            }
            if(isItemExist)
            {

                databaseHelper.updateRecord(allProductsModel);
            }else{
                databaseHelper.insert(allProductsModel,parent_id);
            }

            ((MainDashboardActivity) getActivity()).dispatchInformations3(allProductsModelList);
        }

        @Override
        public void failure(String error) {

        }

        @Override
        public void passItemValue(int value) {

        }
    };

    public void getAllProducts() {

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String URL = "http://vertosys.com/denimhouse/webservices/products.php";

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

                        allProductModel = new AllProductsModel(id, productName, productDescription, productPrice, productImage, 0);
                        allProductList.add(allProductModel);
                        filteredList.addAll(allProductList);

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
