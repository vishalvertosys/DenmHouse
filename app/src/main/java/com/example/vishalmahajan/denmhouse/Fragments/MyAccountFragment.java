package com.example.vishalmahajan.denmhouse.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vishalmahajan.denmhouse.Activity.SessionManager;
import com.example.vishalmahajan.denmhouse.R;

import org.json.JSONObject;

import java.util.HashMap;


public class MyAccountFragment extends Fragment {

    private TextView name,email,phoneNumber;
    ProgressDialog mProgressDialog;
    String URL,sessionEmail,sessionPassword,responsemessage,message;
    SessionManager sessionManager ;

    public MyAccountFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_account_fragment, container, false);
        name = (TextView) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);
        phoneNumber = (TextView) view.findViewById(R.id.phoneNumber);

        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getUserDetails();
        sessionEmail =user.get(SessionManager.KEY_EMAIL);
        sessionPassword =user.get(SessionManager.KEY_PASSWORD);


        hitApi();
        return view;
    }

    private void hitApi() {

        mProgressDialog = new ProgressDialog(getContext());
        //mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        URL ="http://vertosys.com/denimhouse/webservices/myaccount.php"+"?email="+sessionEmail+"&password="+sessionPassword;

        /*JSONObject jsonBody = new JSONObject();
        try{
            jsonBody.put("email",email);
            jsonBody.put("password",email);
        }catch(JSONException e){
            e.printStackTrace();
        }*/
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mProgressDialog.dismiss();
                        System.out.println(response);
                        Log.e("Response", response.toString());

                        try {
                            responsemessage = response.getString("success");
                            message = response.getString("message");
                            JSONObject data = response.getJSONObject("data");

                            String fName = data.getString("firstname");
                            String lName = data.getString("lastname");
                            String objEmail = data.getString("email");
                            String phone = data.getString("phone");


                            if (responsemessage.equals("true")) {

                                name.setText(fName+" "+lName);
                                email.setText(objEmail);
                                phoneNumber.setText(phone);
                            }
                        } catch (Exception e) {
                            mProgressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        queue.add(jsonObjectRequest);
    }
    /*
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                Log.e("Response", response.toString());
                try {
                     String resposne_message=response.getString("message");
                    String error = response.getString("errors");
                    if(resposne_message.contains("true"))
                    {
                        JSONObject jsonObject=new JSONObject(response.getString("data"));
                        String fName = jsonObject.getString("firstname");
                        String lName = jsonObject.getString("lastname");
                        String objEmail = jsonObject.getString("email");
                        String phone = jsonObject.getString("phone");

                        name.setText(fName+" "+lName);
                        email.setText(objEmail);
                        phoneNumber.setText(phone);
                        mProgressDialog.dismiss();
                    }


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
*/


}