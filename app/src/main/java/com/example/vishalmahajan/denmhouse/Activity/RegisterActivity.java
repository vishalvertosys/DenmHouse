
package com.example.vishalmahajan.denmhouse.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vishalmahajan.denmhouse.Network.AppUrl;
import com.example.vishalmahajan.denmhouse.R;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    private Pattern pattern;
    private Matcher matcher;
    String fname,lname,email,password,phoneNumber;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    EditText etFirstName,etEmail,etPhoneNumber,etPassword,etLastName;
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        pattern = Pattern.compile(EMAIL_PATTERN);

        etFirstName = (EditText)findViewById(R.id.firstName);
        etLastName = (EditText)findViewById(R.id.lastName);
        etEmail = (EditText)findViewById(R.id.etEmailRegister);
        etPhoneNumber = (EditText)findViewById(R.id.phoneNumber);
        etPassword = (EditText)findViewById(R.id.etPasswordRegister);
        registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(this);
    }
    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }

    public void registerDetails(){
        fname = etFirstName.getText().toString();
        lname = etLastName.getText().toString();
        email = etEmail.getText().toString();
        phoneNumber = etPhoneNumber.getText().toString();
        password = etPassword.getText().toString();

        Log.e("Data", fname + lname + password + phoneNumber + email);


        if(fname.equals("")==true && email.equals("")==true && phoneNumber.equals("")==true && password.equals("")==true){
            Toast.makeText(RegisterActivity.this,"All Field Require",Toast.LENGTH_SHORT).show();

        }
        else if(fname.equals("")==true){
            etFirstName.setError("Enter First Name");
            etFirstName.requestFocus();

        }
        else if(validate(email)==false){
            etEmail.setError("Invalid Email.Enter valid Email");
            etEmail.requestFocus();
        }
        else if(phoneNumber.equals("")==true){
            etPhoneNumber.setError("Enter the Phone Number");
            etPhoneNumber.requestFocus();
        }
        else if(password.equals("")==true){
            etPassword.setError("Enter the Password");
            etPassword.requestFocus();
        }
        else if(fname.equals("")==false && email.equals("")==false && validate(email)==true && phoneNumber.equals("")==false && password.equals("")==false){
           // Toast.makeText(RegisterActivity.this,"Wait for next action",Toast.LENGTH_SHORT).show();
           progressBar.setVisibility(View.VISIBLE);
            String url = AppUrl.BASE_URL + AppUrl.SIGN_UP +"firstname="+fname+"&lastname="+lname+"&email="+email+"&phone="+phoneNumber+"&password="+password;
            registration(url,email,fname,lname,phoneNumber);
        }

    }

    private void registration(String url, final String email, final String fname, final String lname, final String phoneNumber) {

        // TODO Auto-generated method stub

        RequestQueue queue = Volley.newRequestQueue(this);

        final StringRequest jsObjRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {

                    @Override
                    public void onResponse(String response)
                    {
                        // TODO Auto-generated method stub
                        //05-19 17:21:05.094 24882-24882/com.parkitalia.android E/Response:
                        // {"user_id":"174","resultText":"Register successfully"}

                        Log.d("Signupdata", "response" + response);

                        try
                        {
                            JSONObject json = new JSONObject(response);
                            String responseresult=json.getString("success");
                            if(responseresult.equals("true")){
/*
                                String responseData=json.getString("response");
                                JSONObject jsonObject=new JSONObject(responseData);
                                String uid= jsonObject.getString("user_id");
                                Log.e("UID",uid);
*/
                                progressBar.setVisibility(View.INVISIBLE);
                                SessionManager sessionManager = new SessionManager(RegisterActivity.this);
                                sessionManager.createLoginSession(email,fname,lname,password,phoneNumber);
                                Intent intent = new Intent(RegisterActivity.this, MainDashboardActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }

//
                            else
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();

                            }

                        }
                        catch (Exception e)
                        {
                            progressBar.setVisibility(View.INVISIBLE);

                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                // TODO Auto-generated method stub

            }
        }
        );

        queue.add(jsObjRequest);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this, SplashActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.registerButton:
                registerDetails();
                break;

        }
    }
}
