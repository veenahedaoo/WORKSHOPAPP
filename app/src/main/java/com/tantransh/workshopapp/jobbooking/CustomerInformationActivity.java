package com.tantransh.workshopapp.jobbooking;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.appdata.AppServices;
import com.tantransh.workshopapp.appdata.BookingDetails;
import com.tantransh.workshopapp.appdata.Validator;
import com.tantransh.workshopapp.jobbooking.data.CustomerInformation;
import com.tantransh.workshopapp.jobbooking.data.StateInfo;
import com.tantransh.workshopapp.jobbooking.data.StateList;
import com.tantransh.workshopapp.requestadapter.CustomerRequestAdapter;
import com.tantransh.workshopapp.requestadapter.GeneralRequestAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomerInformationActivity extends AppCompatActivity {
    private TextView contactHeadingTV, customerNameHeadingTV, addressHeadingTV, plotNoHeadingTV, areaHeadingTV, stateHeadingTV, cityHeadingTV;
    private EditText firstNameET, lastNameET, contactET, plotET, areaET, cityET, landmarkET, postalCodeET, emailET, gstinET, altContactET;
    private Spinner stateSpinner;
    private CustomerRequestAdapter customerRequestAdapter;
    private GeneralRequestAdapter generalRequestAdapter;
    private StateList stateList;
    private BroadcastReceiver receiver;
    private List<String> stateArrayList;
    private CustomerInformation customerInformation;
    private String firstName, lastName, contactNo, email, gstin, plotNo, area, landmark, state, stateId, city, postalCode, alternateContact;
    private boolean isRegisteredCustomer;
    private ArrayAdapter<String> stateAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);
        initViews();
        initHeadings();
        initEvents();
        initData();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                System.out.println(action);
                switch (action){
                    case AppConstants.ACTION_STATE_LIST_LOADED:
                        StateList stateList = (StateList) intent.getSerializableExtra(AppConstants.EXTRA_STATE_LIST);
                        System.out.println("State list loaded");
                        initStateList(stateList);
                        break;

                    case AppConstants.ACTION_DUPLICATE_CONTACT:
                        showError("Already Registered contact number");
                        break;

                    case AppConstants.ACTION_SERVER_ERROR:
                        showError("Something went wrong");
                        break;

                    case AppConstants.ACTION_CUSTOMER_ADDED:
                        context.startActivity(new Intent(context,JobInformationActivity.class));
                        break;

                    case AppConstants.ACTION_CUSTOMER_FOUND:
                        isRegisteredCustomer = true;
                        customerInformation = (CustomerInformation) intent.getSerializableExtra(AppConstants.EXTRA_CUTOMER_INFORMATION);
                        initCustomerInformation(customerInformation);

                        break;

                    case AppConstants.ACTION_CUSTOMER_UPDATED:
                        context.startActivity(new Intent(context,JobInformationActivity.class));
                        break;

                    case AppConstants.ACTION_NO_RECORDS:
                        isRegisteredCustomer = false;
                        showError("No customer found. Add new customer");
                        clearAll();
                        break;

                }
            }
        };

        registerReceiver( receiver, new IntentFilter(AppConstants.ACTION_STATE_LIST_LOADED));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_DUPLICATE_CONTACT));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_SERVER_ERROR));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_CUSTOMER_FOUND));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_CUSTOMER_ADDED));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_CUSTOMER_UPDATED));

    }

    private void clearAll(){
        firstNameET.setText(null);
        altContactET.setText(null);
        lastNameET.setText(null);
        plotET.setText(null);
        areaET.setText(null);
        landmarkET.setText(null);
        stateSpinner.setSelection(0);
        cityET.setText(null);
        postalCodeET.setText(null);
        emailET.setText(null);
        gstinET.setText(null);
    }

    private void initHeadings() {
        contactHeadingTV = findViewById(R.id.contactHeadingTV);
        customerNameHeadingTV = findViewById(R.id.customerNameHeadingTV);
        addressHeadingTV = findViewById(R.id.addressHeadingTV);
        plotNoHeadingTV = findViewById(R.id.plotNoHeadingTV);
        areaHeadingTV = findViewById(R.id.areaHeadingTV);
        stateHeadingTV = findViewById(R.id.stateHeadingTV);
        cityHeadingTV = findViewById(R.id.cityHeadingTV);
        cityHeadingTV.setText(AppServices.getRequiredFormatString("City"));
        contactHeadingTV.setText(AppServices.getRequiredFormatString("Contact No"));
        customerNameHeadingTV.setText(AppServices.getRequiredFormatString("Customer Name"));
        addressHeadingTV.setText(AppServices.getRequiredFormatString("Address"));
        plotNoHeadingTV.setText(AppServices.getRequiredFormatString("Plot No."));
        areaHeadingTV.setText(AppServices.getRequiredFormatString("Area"));
        stateHeadingTV.setText(AppServices.getRequiredFormatString("State"));

    }

    private void initCustomerInformation(CustomerInformation customerInformation) {
        hideKeyboard();
        clearErrors();
        firstNameET.setText(customerInformation.getFirstName());
        lastNameET.setText(customerInformation.getLastName());
        contactET.setText(customerInformation.getContact());
        plotET.setText(customerInformation.getPlotNo());
        areaET.setText(customerInformation.getArea());
        landmarkET.setText(customerInformation.getLandmark());
        postalCodeET.setText(customerInformation.getPostalCode());
        cityET.setText(customerInformation.getCity());
        emailET.setText(customerInformation.getEmail());
        gstinET.setText(customerInformation.getGstin());
        altContactET.setText(customerInformation.getAlternateContact());
        System.out.println(customerInformation.getStateId());
        if(stateAdapter!=null){
            int statePos = stateAdapter.getPosition(stateList.getStateName(customerInformation.getStateId()));
            System.out.println(stateList.getStateName(customerInformation.getStateId()));
            System.out.println(statePos);
            stateSpinner.setSelection(statePos);
        }
        else{
            System.out.println("Adapter is null");
        }
    }

    private void initStateList(StateList stateList){
        stateArrayList = new ArrayList<>();
        stateArrayList.add("Select State");
        for(int i = 0; i<stateList.getSize(); i++){
            StateInfo stateInfo = stateList.getState(i);
            stateArrayList.add(stateInfo.getState_name());
        }

        stateAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,stateArrayList);
        stateSpinner.setAdapter(stateAdapter);

        BookingDetails bookingDetails = BookingDetails.getInstance();
        if(Validator.isValidString(bookingDetails.getVehicleRegNo())){
            customerRequestAdapter.searchCustomerByVehicle(bookingDetails.getVehicleRegNo());
        }
    }

    private void initData() {
        customerRequestAdapter = CustomerRequestAdapter.getInstance(this);
        generalRequestAdapter = GeneralRequestAdapter.getInstance(this);

        stateList = StateList.getInstance();
        if(stateList.getSize()<=0){
            generalRequestAdapter.getStateList();
        }
        else{
            initStateList(stateList);
        }


    }

    public void moveNext(View view){
        if(isRegisteredCustomer){
            updateCustomer();
        }
        else{
            addCustomer();
        }

    }

    public void back(View view){
        finish();
    }

    private void initViews(){
        firstNameET = findViewById(R.id.firstNameET);
        lastNameET = findViewById(R.id.lastNameET);
        contactET = findViewById(R.id.contactET);
        plotET = findViewById(R.id.plotNoET);
        areaET = findViewById(R.id.areaET);
        landmarkET = findViewById(R.id.landmarkET);
        postalCodeET = findViewById(R.id.postalET);
        emailET = findViewById(R.id.emailET);
        gstinET = findViewById(R.id.gstinET);
        stateSpinner = findViewById(R.id.stateSP);
        cityET = findViewById(R.id.cityET);
        altContactET = findViewById(R.id.altContactET);
    }

    private void initEvents(){
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stateId = stateList.getStateId(stateArrayList.get(i));
                state = stateArrayList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                stateId = "";
                state = "";
            }
        });

        contactET.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    /* do something */
                    hideKeyboard();
                    searchCustomer(view);
                }
                return false;
            }
        });

        contactET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    hideKeyboard();
                    searchCustomer(view);
                }
            }
        });
    }
    public void addCustomer(){
        hideKeyboard();
        firstName = firstNameET.getText().toString();
        lastName = lastNameET.getText().toString();
        email = emailET.getText().toString();
        gstin = gstinET.getText().toString();
        plotNo = plotET.getText().toString();
        area = areaET.getText().toString();
        contactNo = contactET.getText().toString();
        landmark = landmarkET.getText().toString();
        city = cityET.getText().toString();
        postalCode = postalCodeET.getText().toString();
        alternateContact = altContactET.getText().toString();
        if(Validator.isValidString(firstName,lastName,plotNo,area,contactNo,city,state)){
            if(Validator.isValidString(alternateContact) && !Validator.isValidPhone(alternateContact)){
                altContactET.setError("Invalid contact no.");
                return;
            }
            customerInformation = CustomerInformation.getInstance(null,firstName,lastName,contactNo,alternateContact,email,plotNo,area,landmark,stateId,state,city,postalCode,gstin);
            customerRequestAdapter.addCustomer(customerInformation);
        }
        else{
            showError("Please specify all required information");
        }
    }

    public void updateCustomer(){
        hideKeyboard();
        firstName = firstNameET.getText().toString();
        lastName = lastNameET.getText().toString();
        email = emailET.getText().toString();
        gstin = gstinET.getText().toString();
        plotNo = plotET.getText().toString();
        area = areaET.getText().toString();
        contactNo = contactET.getText().toString();
        landmark = landmarkET.getText().toString();
        city = cityET.getText().toString();
        postalCode = postalCodeET.getText().toString();
        alternateContact = altContactET.getText().toString();
        if(Validator.isValidString(firstName,lastName,plotNo,area,contactNo,city,state)){
            customerInformation.setFirstName(firstName);
            customerInformation.setLastName(lastName);
            customerInformation.setEmail(email);
            customerInformation.setGstin(gstin);
            customerInformation.setPlotNo(plotNo);
            customerInformation.setArea(area);
            customerInformation.setContact(contactNo);
            customerInformation.setLandmark(landmark);
            customerInformation.setCity(city);
            customerInformation.setPostalCode(postalCode);
            customerInformation.setStateId(stateId);
            if(Validator.isValidString(alternateContact) && !Validator.isValidPhone(alternateContact)){
                altContactET.setError("Invalid Contact no.");
                return;
            }
            customerInformation.setAlternateContact(alternateContact);
            customerRequestAdapter.updateCustomer(customerInformation);


        }
        else{
            showError("Please specify all required information");
        }
    }

    public void searchCustomer(String phoneNo){
        customerRequestAdapter.searchCustomerByContact(phoneNo);
    }



    private void showError(String message){
        Snackbar.make(findViewById(R.id.parentLayout),message,Snackbar.LENGTH_LONG).show();
    }

    public void searchCustomer(View view){
        clearErrors();
        String phoneNo = contactET.getText().toString();

        if(Validator.isValidString(phoneNo) && Validator.isValidPhone(phoneNo)){
            searchCustomer(phoneNo);
        }
        else{
            if(!Validator.isValidString(phoneNo)){
                contactET.setError("This field is required");
            }
            else{
                contactET.setError("Invalid contact no.");
            }
        }
    }

    public void clearErrors(){
        contactET.setError(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_STATE_LIST_LOADED));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_DUPLICATE_CONTACT));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_SERVER_ERROR));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_CUSTOMER_FOUND));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_CUSTOMER_ADDED));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_CUSTOMER_UPDATED));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_NO_RECORDS));
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
}
