package com.example.vishalmahajan.denmhouse.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishalmahajan.denmhouse.Adapters.CheckOutAdapter;
import com.example.vishalmahajan.denmhouse.Fragments.ShoppingCartFragment;
import com.example.vishalmahajan.denmhouse.Models.AllProductsModel;
import com.example.vishalmahajan.denmhouse.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckOut extends AppCompatActivity implements View.OnClickListener{

    SessionManager sessionManager ;
    TextView name,email,phoneNumber;
    String firstNameS,lastNameS,emailS,phoneNumberS;
    private RecyclerView recyclerView;
    private CheckOutAdapter adapter;
    private ArrayList<AllProductsModel> checkOutList;
    private ArrayList<AllProductsModel> finalCheckOutList;
    Button sendMail;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        checkOutList = new ArrayList<>();
       // Bundle bundle = getIntent().getExtras();
        checkOutList = getIntent().getParcelableArrayListExtra("key");
       // checkOutList = ((MainDashboardActivity)getApplicationContext()).passCheckOutList();

        sendMail = (Button) findViewById(R.id.sendMail);

        sessionManager=new SessionManager(CheckOut.this);
        HashMap<String, String> user = sessionManager.getUserDetails();

        firstNameS = user.get(SessionManager.KeY_Fname);
        lastNameS = user.get(SessionManager.Key_Lname);
        emailS = user.get(SessionManager.KEY_EMAIL);
        phoneNumberS = user.get(SessionManager.KEY_PHONENUMBER);

        name = (TextView) findViewById(R.id.name);
        name.setText(firstNameS+" "+lastNameS);
        email = (TextView) findViewById(R.id.email);
        email.setText(emailS);
        phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        phoneNumber.setText(phoneNumberS);
        sendMail.setOnClickListener(this);

        setAdapter();

    }

    public void setAdapter(){

        adapter = new CheckOutAdapter(this, checkOutList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }



   /* public void passList(ArrayList<AllProductsModel> checkOutList){
        this.checkOutList = checkOutList;

    }*/


    public void sendDetailsOfUser(){
        String userName, userEmail, userPhoneNumber;
        userName = name.getText().toString();
        userEmail = email.getText().toString();
        userPhoneNumber = phoneNumber.getText().toString();
        Log.i("Send email", "");
        String[] TO = {"vishal.vertosys@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "name:- " + userName +"\n"+"email:- " + userEmail +"\n"+"phone No:- " + userPhoneNumber);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CheckOut.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendMail:
                sendDetailsOfUser();
        }
    }


}
