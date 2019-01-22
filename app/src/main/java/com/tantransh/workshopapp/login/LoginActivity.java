package com.tantransh.workshopapp.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.appdata.AppPreferences;
import com.tantransh.workshopapp.appdata.Validator;
import com.tantransh.workshopapp.home.DashboardActivity;
import com.tantransh.workshopapp.requestadapter.LoginRequestAdapter;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextView userIdET,passwordET;
    private BroadcastReceiver receiver;
    private AppPreferences ap;
    private CoordinatorLayout parentLayout;
    private LinearLayout pdBack;
    private LoginRequestAdapter requestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ap = AppPreferences.getInstance(this);
        initViews();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("Received action : "+intent.getAction());
                switch (Objects.requireNonNull(intent.getAction())){

                    case AppConstants.ACTION_LOGIN_SUCCESS:
                        startActivity(new Intent(context, DashboardActivity.class));
                        break;
                    case AppConstants.ACTION_LOGIN_FAILED:
                        Snackbar.make(parentLayout,"Invalid UserId or Password...!",Snackbar.LENGTH_LONG).show();
                        break;
                    case AppConstants.ACTION_LOGIN_ERROR:
                        Snackbar.make(parentLayout,"Something went wrong...!",Snackbar.LENGTH_LONG).show();
                        break;
                    case AppConstants.ACTION_CONNECTION_ERROR:
                        Snackbar.make(parentLayout,"Connection Error. Please try again...!",Snackbar.LENGTH_LONG).show();
                        break;
                    case AppConstants.ACTION_SERVER_ERROR:
                        Snackbar.make(parentLayout,"Server Error...!",Snackbar.LENGTH_LONG).show();
                        break;
                }
                pdBack.setVisibility(View.GONE);
            }
        };

        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_LOGIN_SUCCESS));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_LOGIN_FAILED));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_LOGIN_ERROR));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_CONNECTION_ERROR));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_SERVER_ERROR));
    }

    private void initViews() {
        userIdET  = findViewById(R.id.userIdET);
        passwordET = findViewById(R.id.passwordET);
        parentLayout = findViewById(R.id.parentLayout);
        pdBack = findViewById(R.id.pdLO);
        pdBack.setVisibility(View.GONE);
    }

    public void login(View view){
        String userId = userIdET.getText().toString();
        String password = passwordET.getText().toString();
        if(Validator.isValidString(userId,password)){
            pdBack.setVisibility(View.VISIBLE);
            requestAdapter = LoginRequestAdapter.getInstance(this);
            requestAdapter.login(userId,password);
        }
        else{
            Snackbar.make(parentLayout,"Please provide UserId and Password",Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onPause() {
        try{
            unregisterReceiver(receiver);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_LOGIN_SUCCESS));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_LOGIN_FAILED));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_LOGIN_ERROR));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_CONNECTION_ERROR));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_SERVER_ERROR));
    }
}
