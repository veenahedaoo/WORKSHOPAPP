package com.tantransh.workshopapp.jobbooking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.appdata.BookingDetails;
import com.tantransh.workshopapp.appdata.JobConcernItem;
import com.tantransh.workshopapp.appdata.JobConcernList;
import com.tantransh.workshopapp.appdata.Validator;
import com.tantransh.workshopapp.services.ServiceDispatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JobInformationActivity extends AppCompatActivity {
    private ServiceDispatcher serviceDispatcher;
    private EditText firstNameET, lastNameET, contactET;
    private BookingDetails bookingDetails;
    private LinearLayout driverLLO;
    private JobConcernList itemList, userItems;
    private Gson gson;
    private Spinner itemSpinner;
    private List<String> itemNameList;
    private Button addItemsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_information);
        serviceDispatcher = ServiceDispatcher.getInstance(this);
        initViews();
    }

    private void initViews() {
        gson = new Gson();
        itemList = JobConcernList.getInstance();
        userItems = JobConcernList.getInstance();
        firstNameET = findViewById(R.id.firstNameET);
        lastNameET = findViewById(R.id.lastNameET);
        contactET = findViewById(R.id.contactET);
        driverLLO = findViewById(R.id.driverInfoLLO);
        bookingDetails = BookingDetails.getInstance();
        itemSpinner = findViewById(R.id.item_list_rv);
        itemNameList = new ArrayList<>();
        serviceDispatcher.getItemList(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response : "+response);
                try {
                    JSONArray itemsArr = response.getJSONArray("result");
                    for(int i = 0; i<itemsArr.length(); i++){
                        JobConcernItem jobConcernItem = gson.fromJson(itemsArr.getJSONObject(i).toString(),JobConcernItem.class);
                        itemList.addItem(jobConcernItem);
                        itemNameList.add(jobConcernItem.itemName);
                    }
                    ArrayAdapter<String> itemsAdapter;
                    itemsAdapter = new ArrayAdapter<String>(JobInformationActivity.this,android.R.layout.simple_spinner_item,itemNameList);
                    itemSpinner.setAdapter(itemsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error : "+error);
            }
        });
    }

    public void moveNext(View view){
        hideKeyboard();
        final String firstName = firstNameET.getText().toString();
        final String lastName = lastNameET.getText().toString();
        final String contact = contactET.getText().toString();
        bookingDetails.setRepContact(contact);
        bookingDetails.setRepFName(firstName);
        bookingDetails.setRepLName(lastName);

    }

    public void back(View view){
        finish();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void cancel(View view){
        finish();
    }

    private void showMessage(String message){
        Snackbar.make(findViewById(R.id.parentLayout),message,Snackbar.LENGTH_LONG).show();
    }

    public void showDriver(View view){
        driverLLO.setVisibility(View.VISIBLE);
    }
}
