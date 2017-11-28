package com.example.vishalmahajan.denmhouse.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishalmahajan.denmhouse.Adapters.DiscountAdapter;
import com.example.vishalmahajan.denmhouse.Adapters.TabsPagerAdapter;
import com.example.vishalmahajan.denmhouse.Adapters.TodayDealAdapter;
import com.example.vishalmahajan.denmhouse.DataBaseHelpers.DatabaseHelper;
import com.example.vishalmahajan.denmhouse.Fragments.MyAccountFragment;
import com.example.vishalmahajan.denmhouse.Fragments.ShoppingCartFragment;
import com.example.vishalmahajan.denmhouse.Fragments.TabFragment1;
import com.example.vishalmahajan.denmhouse.Fragments.TabFragment2;
import com.example.vishalmahajan.denmhouse.Fragments.TabFragment3;
import com.example.vishalmahajan.denmhouse.Models.AllProductModel;
import com.example.vishalmahajan.denmhouse.Models.AllProductsModel;
import com.example.vishalmahajan.denmhouse.Models.DiscountModel;
import com.example.vishalmahajan.denmhouse.Models.TodayDealModel;
import com.example.vishalmahajan.denmhouse.R;

import java.util.ArrayList;
import java.util.List;

public class MainDashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    public static final String MyPREFERENCES = "Session";
    SharedPreferences sharedpreferences;
    DatabaseHelper databaseHelper;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Context context;
    TextView counter,heading,itemCount1;
    RelativeLayout basket;
    TodayDealModel todayDealModel;
    int counter1;
    int count = 0;
    double price=0;
    ShoppingCartFragment shoppingCartFragment;
    List<AllProductsModel> discountModelList;
    List<AllProductsModel> todayDealModelList;
    List<AllProductsModel> allProductModelList;
    AsynResult<AllProductsModel> asynResult;
    String title;
    LinearLayout linearLayout;
    LinearLayout linearLayout1;
    LinearLayout searchBar;
    AsynResult1 callback;
    ArrayList<AllProductsModel> checkOutList = new ArrayList<>();
    double totalCount, particularItemCount;
    double totalCount1, totalCount2, totalCount3;

    public MainDashboardActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        databaseHelper = new DatabaseHelper(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        counter = (TextView)findViewById(R.id.textCounter);
        heading = (TextView) findViewById(R.id.heading);
        linearLayout = (LinearLayout) findViewById(R.id.shopping_cart);
        linearLayout1 = (LinearLayout) findViewById(R.id.all_products);
        itemCount1 = (TextView) findViewById(R.id.itemCount1);
        basket = (RelativeLayout) findViewById(R.id.relativeLayout);
        searchBar = (LinearLayout) findViewById(R.id.searchBar);
        basket.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
        linearLayout1.setOnClickListener(this);
        searchBar.setOnClickListener(this);

     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        ArrayList<AllProductsModel> model;
        model = new ArrayList<>(databaseHelper.getData());
        if(model != null && model.size() > 0) {
            for (int i = 0; i < model.size(); i++) {
                int c = model.get(i).getItemCount();
                count = count + c;
            }
        }
        String str = String.valueOf(databaseHelper.getCount());

        count = Integer.valueOf(str);

        counter.setText(String.valueOf(count));
      //  todayDealModel = (TodayDealModel) getIntent().getSerializableExtra("Data");

    }

    @Override
    public void onBackPressed() {
        getSupportActionBar().show();
        tabLayout.setVisibility(View.VISIBLE);
        heading.setText("DENIM HOUSE");
        basket.setVisibility(View.VISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (count != 0) {
            super.onBackPressed();
        } else {
           /* FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            transaction.replace(R.id.fragment_Container, tabFragment2, null);
            transaction.addToBackStack(null);
            transaction.commit();*/
            getSupportFragmentManager().popBackStack();
        }
    }

    public void dispatchInformations1(List<AllProductsModel> discountModels){
        this.discountModelList = discountModels;

    }
    public void dispatchInformations2(List<AllProductsModel> todayDealModel){
        this.todayDealModelList = todayDealModel;
    }

    public void  dispatchInformations3(List<AllProductsModel> allProductModel){
        this.allProductModelList = allProductModel;
    }

    public void passList(ArrayList<AllProductsModel> checkOutList){
        this.checkOutList.addAll(checkOutList);
    }

    public ArrayList<AllProductsModel> passCheckOutList(){
        return checkOutList;
    }


    AsynResult<AllProductsModel> asynResult1 = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel discountModel) {
          // String itemPrice = discountModel.getProductPrice();

          /* int sum = discountModel.getItemCount();
            counter.setText(sum);
            itemCount1.setText(sum);*/
            /*count = String.valueOf(getCounter1());*/
            //discountModel.setCounter(count);
            //counter.setText(discountModel.getCounter());
            //itemCount1.setText(discountModel.getCounter());
             /*counter.setText(count);
            itemCount1.setText(count);
            title = discountModel.getProductName();*/
        //    checkDuplicateItems();
        }

        @Override
        public void failure(String error) {


        }
        @Override
        public void passItemValue(int value){
            counter1 = value;

        }
    };

    AsynResult<AllProductsModel> asynResultMinus = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel discountModel) {
            totalCount = discountModel.getTotalCount();
            String Counter = counter.getText().toString();
            int count1 = Integer.parseInt(Counter);
            if(count1 > 0){
                count1 = count1 -1;
                counter.setText(String.valueOf(count1));
                itemCount1.setText(String.valueOf(count1));
            }
            discountModel.setTotalCount(totalCount);

            /*count = String.valueOf(minusCounter());
            counter.setText(count);
            itemCount1.setText(count);*/
            /*discountModel.setCounter(count);
            counter.setText(discountModel.getCounter());
            itemCount1.setText(discountModel.getCounter());*/
        }

        @Override
        public void failure(String error) {

        }
        @Override
        public void passItemValue(int value){

        }
    };



    AsynResult<AllProductsModel> asynResult2 = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel todayDealModel) {
             totalCount2 = todayDealModel.getTotalCount();

            if(counter.getText().equals("0")) {
                counter.setText(String.valueOf(counter1));
                itemCount1.setText(String.valueOf(counter1));

            }else{
                String count = counter.getText().toString();
                int totalCount = Integer.parseInt(count) + counter1;
                counter.setText(String.valueOf(totalCount));
                itemCount1.setText(String.valueOf(totalCount));
            }
            todayDealModel.setTotalCount(totalCount2);

            /*count = String.valueOf(getCounter1());
            counter.setText(count);
            itemCount1.setText(count);*/
        }

        @Override
        public void failure(String error) {

        }
        @Override
        public void passItemValue(int value){
            counter1 = value;
        }
    };


    AsynResult<AllProductsModel> asynResult3 = new AsynResult<AllProductsModel>() {
        @Override
        public void success(AllProductsModel allProductModel) {
            totalCount3 = allProductModel.getTotalCount();

            if(counter.getText().equals("0")) {
                counter.setText(String.valueOf(counter1));
                itemCount1.setText(String.valueOf(counter1));

            }else{
                String count = counter.getText().toString();
                int totalCount = Integer.parseInt(count) + counter1;
                counter.setText(String.valueOf(totalCount));
                itemCount1.setText(String.valueOf(totalCount));
            }
            allProductModel.setTotalCount(totalCount3);


            /*count = String.valueOf(getCounter1());
            counter.setText(count);
            itemCount1.setText(count);*/
        }

        @Override
        public void failure(String error) {

        }
        @Override
        public void passItemValue(int value){
            counter1 = value;
        }
    };


   /*  public void onSearchBarListener(AsynResult1 callback){
        this.callback = callback;
    }*/

    public String passCountValue(){
        String count = counter.getText().toString();
        return count;
    }

    public double passTotalCountValue(){
        double overAllCount= totalCount1 + totalCount2 + totalCount3;
        return overAllCount;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.myAccount) {
            // calling My Account Fragment
            showingMyAccountFragment();

        } else if (id == R.id.logOut) {
            SessionManager sessionManager = new SessionManager(MainDashboardActivity.this);
            sessionManager.logoutUser();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabFragment1(this, asynResult1, asynResultMinus), "Discounts");
        adapter.addFragment(new TabFragment2(this, asynResult2, asynResultMinus), "Today Deals");
        adapter.addFragment(new TabFragment3(this, asynResult3, asynResultMinus), "All Products");
        viewPager.setAdapter(adapter);
    }




    public void showingShoppingCartFragment(){
        tabLayout.setVisibility(View.GONE);
        heading.setText("Shopping Cart");
        basket.setVisibility(View.INVISIBLE);
        shoppingCartFragment = new ShoppingCartFragment(discountModelList, todayDealModelList, allProductModelList);
        Bundle bundle = new Bundle();
        shoppingCartFragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_Container, shoppingCartFragment, null);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void showingMyAccountFragment(){
        tabLayout.setVisibility(View.GONE);
        heading.setText("My Account");
        basket.setVisibility(View.INVISIBLE);
        MyAccountFragment myAccountFragment = new MyAccountFragment();
        Bundle bundle = new Bundle();
        myAccountFragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_Container, myAccountFragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /*public void onPassValue(AsynResult<AllProductsModel> asynResult){
        this.asynResult = asynResult;
    }*/

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.relativeLayout:

                if(counter.getText().equals("0")){
                 Toast.makeText(MainDashboardActivity.this, "You dont have any item on shopping cart", Toast.LENGTH_SHORT).show();
                }else {
                    showingShoppingCartFragment();
                }
                break;

            case R.id.shopping_cart:
                if(counter.getText().equals("0")){
                    Toast.makeText(MainDashboardActivity.this, "You dont have any item on shopping cart", Toast.LENGTH_SHORT).show();
                }else {
                    showingShoppingCartFragment();
                }
                break;
            case R.id.searchBar:
                /*TabFragment3 fragment3 = new TabFragment3();
                int Result = 1;
                if(asynResult != null)
                asynResult.passItemValue(Result);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, fragment3, null).commit();*/
        }

    }

}
