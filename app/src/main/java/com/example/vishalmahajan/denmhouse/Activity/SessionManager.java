package com.example.vishalmahajan.denmhouse.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by VS-2 on 8/22/2017.
 */

public class SessionManager {

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "MyPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_ID = "userid";
    public static final String KeY_Fname = "fname";
    public static final String Key_Lname = "lname";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONENUMBER = "phoneNumber";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String email,String Fname,String lname,String password,String phoneNumber){
        Log.e("DataSession",email+Fname+lname);
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
       // editor.putString(KEY_ID, id);
        editor.putString(KeY_Fname, Fname);
        editor.putString(Key_Lname, lname);
        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_PHONENUMBER, phoneNumber);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin() {
        // Check login status
        if (this.isLoggedIn() == true) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainDashboardActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }


    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        //user.put(KEY_ID, pref.getString(KEY_ID, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(Key_Lname, pref.getString(Key_Lname, null));
        user.put(KeY_Fname, pref.getString(KeY_Fname, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_PHONENUMBER, pref.getString(KEY_PHONENUMBER, null));
        Log.e("DataSessionMAp",pref.getString(KeY_Fname, null)+pref.getString(KEY_EMAIL, null)+pref.getString(Key_Lname, null)+pref.getString(KEY_PASSWORD, null));

        // return user
        return user;
    }


    /**
     * Clear session details
     * */
    public  void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Splash Activity
        Intent i = new Intent(_context, SplashActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    }
