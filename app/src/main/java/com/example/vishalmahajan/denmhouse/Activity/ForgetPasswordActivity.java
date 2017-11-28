package com.example.vishalmahajan.denmhouse.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vishalmahajan.denmhouse.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private Pattern pattern;
    ProgressDialog mProgressDialog;
    String responsemessage,message,email;
    EditText etEmail;
    Button submit;


    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mProgressDialog = new ProgressDialog(ForgetPasswordActivity.this);
        mProgressDialog.setIndeterminate(false);

        etEmail = (EditText) findViewById(R.id.etEmailForget);
        submit = (Button) findViewById(R.id.submitButton);
        pattern = Pattern.compile(EMAIL_PATTERN);
        submit.setOnClickListener(this);

    }

    public void getUserDetails(){
        email = etEmail.getText().toString();
        if(email.equals("")){
            Toast.makeText(ForgetPasswordActivity.this, "Please fill your email ", Toast.LENGTH_SHORT).show();
        }
         else if(!validate(email)){
            etEmail.setError("This email doesn't exist");
            etEmail.requestFocus();
        }
        else if(validate(email)){
            mProgressDialog.show();
            String url = "http://vertosys.com/denimhouse/webservices/forgotpassword.php" + "?email=" + email;
            ForgetPassword(url);
        }
    }

    public void ForgetPassword(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();
        try{
            jsonBody.put("email",email);
        }catch(JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());

                        try {
                            responsemessage = response.getString("success");
                            message = response.getString("message");
                            JSONObject data = response.getJSONObject("data");
                            String newPassword = data.getString("new_password");

                            if (responsemessage.equals("true")) {
                                mProgressDialog.dismiss();
                                Toast.makeText(ForgetPasswordActivity.this, "New Password :- "+ newPassword, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);

                            } else {
                                mProgressDialog.dismiss();
                                Toast.makeText(ForgetPasswordActivity.this, message, Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            mProgressDialog.dismiss();
                            Toast.makeText(ForgetPasswordActivity.this, "something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

    });
        requestQueue.add(jsonObjectRequest);
    }


    public boolean validate(final String hex) {
        Matcher matcher = pattern.matcher(hex);
        return matcher.matches();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.submitButton:
                getUserDetails();
                break;
        }
    }
}
