package com.tantransh.workshopapp.jobbooking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.appdata.AppServices;
import com.tantransh.workshopapp.appdata.BookingDetails;
import com.tantransh.workshopapp.appdata.Validator;
import com.tantransh.workshopapp.jobbooking.data.MakeList;
import com.tantransh.workshopapp.jobbooking.data.VehicleInformation;
import com.tantransh.workshopapp.requestadapter.GeneralRequestAdapter;
import com.tantransh.workshopapp.requestadapter.VehicleRequestAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class VehicleInformationActivity extends AppCompatActivity {

    private TextView insuranceDateTV;
    private TextView vehicleNoHeadingTV, chassisNoHeadingTV, makeHeadingTV, modelHeadingTV, insuranceHeadingTV, otherMakeHeadingTV, kilometerReadingHeadingTV;
    private String  displayDate;
    private EditText vehicleRegET, chassisET, engineNoET, makeET, modelET, modelYearET,milageET;
    private Spinner makeSpinner,insuranceSpinner;
    private RadioButton insYRB, insNRB;
    private VehicleRequestAdapter vehicleRequestAdapter;
    private BroadcastReceiver receiver;
    private LinearLayout progressLLO;
    private GeneralRequestAdapter requestAdapter;
    private ArrayAdapter insAdapter, makeAdapter;
    private List<String> makeArray;
    private List<String> insuranceArray;
    private String vehicleNo, chassisNo, engineNo,make, makeId, model, modelYear, insuranceType, insuranceDate, insured, kilometers;
    private boolean isRegisteredVehicle;

    private MakeList makeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_information);
        initViews();
        initHeadings();
        initData();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                BookingDetails bookingDetails ;
                switch (action){
                    case AppConstants.ACTION_ADD_VEHICLE_SUCCESS:
                        bookingDetails = BookingDetails.getInstance();
                        bookingDetails.setVehicleRegNo(vehicleRegET.getText().toString().toUpperCase());
                        bookingDetails.setKilometers(kilometers);
                        startActivity(new Intent(context,CustomerInformationActivity.class));

                        break;

                    case AppConstants.ACTION_ADD_VEHICLE_FAILED:
                        break;

                    case AppConstants.ACTION_CONNECTION_ERROR:
                        break;

                    case AppConstants.ACTION_LIST_LOADED:
                        System.out.println("Make list loaded");

                        MakeList makeList = (MakeList) intent.getSerializableExtra(AppConstants.EXTRA_MAKE_LIST);
                        initMakeList(makeList);
                        break;

                    case AppConstants.ACTION_VEHICLE_INFO_LOADED:
                        isRegisteredVehicle = true;
                        VehicleInformation vehicleInformation = (VehicleInformation) intent.getSerializableExtra(AppConstants.EXTRA_VEHICLE_INFO);
                        initVehicleInformation(vehicleInformation);
                        break;

                    case AppConstants.ACTION_NO_RECORDS:
                        showError("Vehicle Information not found");
                        isRegisteredVehicle = false;
                        setDefaults();
                        break;

                    case AppConstants.ACTION_INVALID_VEHICLE_NO:
                        showError("Invalid vehicle no.");
                        break;

                    case AppConstants.ACTION_VEHICLE_INFO_UPDATED:
                        bookingDetails = BookingDetails.getInstance();
                        bookingDetails.setVehicleRegNo(vehicleRegET.getText().toString().toUpperCase());
                        bookingDetails.setKilometers(kilometers);
                        startActivity(new Intent(context,CustomerInformationActivity.class));

                        break;

                    case AppConstants.ACTION_SERVER_ERROR:
                        String message = intent.getStringExtra("msg");
                        showError(message);
                }

                progressLLO.setVisibility(View.GONE);
            }
        };

        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_ADD_VEHICLE_FAILED));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_ADD_VEHICLE_SUCCESS));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_CONNECTION_ERROR));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_LIST_LOADED));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_VEHICLE_INFO_LOADED));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_NO_RECORDS));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_INVALID_VEHICLE_NO));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_VEHICLE_INFO_UPDATED));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_SERVER_ERROR));

        requestAdapter = GeneralRequestAdapter.getInstance(this);
        requestAdapter.getMakeList();


    }

    private void initData() {
        vehicleRequestAdapter = VehicleRequestAdapter.getInstance(this);
    }

    private void initMakeList(MakeList makeList){
        makeArray = new ArrayList<>();
        makeArray.add("Select Make");
        for(int i = 0; i<makeList.getSize(); i++){

            makeArray.add(makeList.getItem(i).getMake_name());
        }
        makeArray.add("Other");
        makeAdapter = new ArrayAdapter<>(VehicleInformationActivity.this,R.layout.spinner_item,makeArray);
        makeSpinner.setAdapter(makeAdapter);
    }

    private void initHeadings(){
        vehicleNoHeadingTV = findViewById(R.id.vehicleNoHeadingTV);
        chassisNoHeadingTV = findViewById(R.id.chassisNoHeadingTV);
        makeHeadingTV = findViewById(R.id.makeHeadingTV);
        otherMakeHeadingTV = findViewById(R.id.otherMakeHeadingTV);
        modelHeadingTV = findViewById(R.id.modelHeadingTV);
        insuranceHeadingTV = findViewById(R.id.insuranceHeadingTV);
        kilometerReadingHeadingTV = findViewById(R.id.kilometerReadingHeadingTV);
        vehicleNoHeadingTV.setText(AppServices.getRequiredFormatString("Vehicle Registration Number"));
        chassisNoHeadingTV.setText(AppServices.getRequiredFormatString("Vehicle Identification Number"));
        makeHeadingTV.setText(AppServices.getRequiredFormatString("Make"));
        modelHeadingTV.setText(AppServices.getRequiredFormatString("Model"));
        insuranceHeadingTV.setText(AppServices.getRequiredFormatString("Insurance"));
        kilometerReadingHeadingTV.setText(AppServices.getRequiredFormatString("Kilometer(s) Reading"));
    }
    private void initViews() {

        milageET = findViewById(R.id.milageET);
        makeList = MakeList.getInstance();
        progressLLO = findViewById(R.id.progressLO);
        insuranceDateTV = findViewById(R.id.insuranceDateTV);
        vehicleRegET = findViewById(R.id.vehicleRegNoET);
        chassisET = findViewById(R.id.chassisNoET);
        engineNoET = findViewById(R.id.engineNoET);
        insuranceSpinner = findViewById(R.id.insuranceTypeSP);
        modelET = findViewById(R.id.modelET);
        makeET = findViewById(R.id.makeET);
        modelYearET = findViewById(R.id.modelYearET);
        insYRB = findViewById(R.id.insYRB);
        insNRB = findViewById(R.id.insNRB);
        makeSpinner = findViewById(R.id.makeSP);
        insuranceArray = new ArrayList<>();
        insuranceArray.add("Select Insurance Type");
        insuranceArray.add("ZERO DEP");
        insuranceArray.add("COMPREHENSIVE");
        insuranceArray.add("THIRD PARTY");
        insAdapter = new ArrayAdapter(this,R.layout.spinner_item,insuranceArray);
        insuranceSpinner.setAdapter(insAdapter);
        makeET.setEnabled(false);
        initEvents();
    }

    private void initEvents(){
        makeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i);
                makeET.setEnabled(false);
                if(i==0){
                    make = "";
                    makeId = "";
                }
                else if(i <= makeList.getSize()){
                    make = makeList.getItem(i-1).getMake_name();
                    makeId = makeList.getItem(i-1).getMake_id();
                }
                else if(i>makeList.getSize()){
                    makeId = "-1";
                    make = makeET.getText().toString();
                    makeET.setEnabled(true);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                make = "";
                makeId = "";
            }
        });

        insuranceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0){
                    insuranceType = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                insuranceType = null;
            }
        });

        vehicleRegET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    searchVehicle(view);
                }
            }
        });

        insYRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    findViewById(R.id.insDateIV).setEnabled(true);
                    insured = "YES";
                    insuranceSpinner.setEnabled(true);
                }
            }
        });

        insNRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    findViewById(R.id.insDateIV).setEnabled(false);
                    insuranceDateTV.setText(null);
                    insuranceSpinner.setEnabled(false);
                    insuranceSpinner.setSelection(0);
                    insuranceDate = "";
                    insuranceType = "";
                    insured = "NO";

                }
            }
        });

        milageET.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    /* do something */
                    moveNext(view);
                }
                return false;
            }
        });
    }

    public void back(View view){
        finish();
    }

    public void moveNext(View view){
        if(isRegisteredVehicle){

            updateVehicle();

        }
        else{
            addVehicle();

        }



    }

    private String showError(String msg) {
        hideKeyboard();
        Snackbar.make(findViewById(R.id.parentLayout),msg,Snackbar.LENGTH_LONG).show();
        return null;
    }

    public void selectDate(View view){
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Asia/Calcutta"));
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar selCalendar = Calendar.getInstance();
                selCalendar.set(year,month,dayOfMonth);
                System.out.println(selCalendar.getTime());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                displayDate = sdf1.format(selCalendar.getTime().getTime());
                insuranceDate = sdf2.format(selCalendar.getTimeInMillis());
                insuranceDateTV.setText(displayDate);
                Toast.makeText(VehicleInformationActivity.this, ""+insuranceDate, Toast.LENGTH_SHORT).show();
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_ADD_VEHICLE_FAILED));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_ADD_VEHICLE_SUCCESS));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_CONNECTION_ERROR));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_LIST_LOADED));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_VEHICLE_INFO_LOADED));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_NO_RECORDS));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_INVALID_VEHICLE_NO));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_VEHICLE_INFO_UPDATED));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_SERVER_ERROR));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void addVehicle(){
        hideKeyboard();
        vehicleNo = vehicleRegET.getText().toString().toUpperCase();
        vehicleRegET.setText(vehicleNo);
        chassisNo = chassisET.getText().toString();
        engineNo = engineNoET.getText().toString();
        if(makeId.equals("-1")){
            make = makeET.getText().toString();
        }
        model = modelET.getText().toString();
        modelYear = modelYearET.getText().toString();

        kilometers = milageET.getText().toString();

        if(Validator.isValidString(vehicleNo,chassisNo,make,model,insured,kilometers)){
            if(!Validator.isValidVehicleNo(vehicleNo)){
                vehicleRegET.setError("Invalid Vehicle No.");
                return;
            }
            if(insYRB.isChecked() && !Validator.isValidString(insuranceDate,insuranceType)){
                showError("Please select Insurance date and Insurance type");
                return;
            }
            else if(insNRB.isChecked()){
                insuranceDate = "";
                insuranceType = "";
            }
            VehicleInformation vehicleInformation = VehicleInformation.getInstance(vehicleNo,chassisNo,engineNo,make,makeId,model,modelYear,insured,insuranceDate,insuranceType);
            vehicleRequestAdapter = VehicleRequestAdapter.getInstance(this);
            progressLLO.setVisibility(View.VISIBLE);
            vehicleRequestAdapter.registerVehicle(vehicleInformation);
            //startActivity(new Intent(this,CustomerInformationActivity.class));

        }
        else{
            checkRequiredFields();
        }
    }

    private void updateVehicle(){
        hideKeyboard();
        vehicleNo = vehicleRegET.getText().toString().toUpperCase();
        vehicleRegET.setText(vehicleNo);
        chassisNo = chassisET.getText().toString();
        engineNo = engineNoET.getText().toString();
        if(engineNo.equals("N/A"))
            engineNo = "";
        if(makeId.equals("-1")){
            make = makeET.getText().toString();
        }
        model = modelET.getText().toString();
        modelYear = modelYearET.getText().toString();
        vehicleRegET.setError(null);
        insured = insYRB.isChecked()?"YES":insNRB.isChecked()?"NO":showError("Please specify vehicle insurance");
        kilometers = milageET.getText().toString();
        if(insured.equals("YES") && insuranceDate==null){
            insuranceDate = Validator.getSqlDate(insuranceDateTV.getText().toString());
        }
        if(Validator.isValidString(vehicleNo,chassisNo,make,model,insured,kilometers)){
            if(!Validator.isValidVehicleNo(vehicleNo)){
                vehicleRegET.setError("Invalid Vehicle No.");
                return;
            }
            if(insYRB.isChecked() && !Validator.isValidString(insuranceDate,insuranceType)){
                showError("Please select insurance date and insurance type");
                return;
            }

            if(insNRB.isChecked()){
                insured = "NO";
                insuranceType = "";
                insuranceDate = "";
            }

            VehicleInformation vehicleInformation = VehicleInformation.getInstance(vehicleNo,chassisNo,engineNo,make,makeId,model,modelYear,insured,insuranceDate,insuranceType);
            vehicleRequestAdapter = VehicleRequestAdapter.getInstance(this);
            progressLLO.setVisibility(View.VISIBLE);
            vehicleRequestAdapter.updateVehicle(vehicleInformation);
            //startActivity(new Intent(this,CustomerInformationActivity.class));

        }
        else{
            checkRequiredFields();
        }

    }

    private void clearError(){
        vehicleRegET.setError(null);
        chassisET.setError(null);
        modelET.setError(null);
        milageET.setError(null);
    }

    public void cancel(View view){
        finish();
    }

    private void checkRequiredFields(){
        hideKeyboard();
        clearError();
        if(!Validator.isValidString(vehicleNo)){
            vehicleRegET.setError("Please specify vehicle no.");
        }
        if(!Validator.isValidString(chassisNo)){
            chassisET.setError("Please specify chassis no.");
        }
        if(!Validator.isValidString(make)){
            showError("Please specify make");
        }
        if(!Validator.isValidString(model)){
            modelET.setError("Please specify model");
        }

        if(!Validator.isValidString(insured)){
            showError("Please specify insurance");
        }

        if(!Validator.isValidString(kilometers)){
            milageET.setError("Please specify kilometers reading");
        }
    }

    public void searchVehicle(View view){
        vehicleNo = vehicleRegET.getText().toString().toUpperCase();
        vehicleRegET.setText(vehicleNo);
        clearError();
        if(Validator.isValidString(vehicleNo) && Validator.isValidVehicleNo(vehicleNo)){
            searchVehicle(vehicleNo);
        }
        else{
            if(!Validator.isValidString(vehicleNo)){
                vehicleRegET.setError("Please specify Vehicle No.");
            }
            else{
                vehicleRegET.setError("Invalid vehicle no.");
            }

        }
    }

    private void searchVehicle(String vehicleNo){
        hideKeyboard();
        progressLLO.setVisibility(View.VISIBLE);
        vehicleRequestAdapter.searchVehicle(vehicleNo);
    }

    private void initVehicleInformation(VehicleInformation vehicleInformation){
        hideKeyboard();
        vehicleRegET.setText(vehicleInformation.getVehicleRegNo().toUpperCase());
        chassisET.setText(vehicleInformation.getChassisNo());
        if(vehicleInformation.getInsurance().equals("YES")){
            insYRB.setChecked(true);
            System.out.println(vehicleInformation.getInsurance());
        }
        else{
            insNRB.setChecked(true);

        }

        if(Validator.isValidString(vehicleInformation.getEngineNo())){
            engineNoET.setText(vehicleInformation.getEngineNo());
        }
        else{
            engineNoET.setText("N/A");
        }

        if(makeList!=null && makeList.getSize()>0){
            int pos = makeAdapter.getPosition(makeList.getMakeName(vehicleInformation.getMakeId()));

            makeSpinner.setSelection(pos);
        }
        modelET.setText(vehicleInformation.getModel());
        modelYearET.setText(vehicleInformation.getModelYear());

        int insPos = insAdapter.getPosition(vehicleInformation.getInsuranceType());
        insuranceSpinner.setSelection(insPos);



        insuranceDateTV.setText(Validator.getFormattedtDate(vehicleInformation.getInsuranceDate()));
        chassisET.setEnabled(false);
        makeSpinner.setEnabled(false);
        modelET.setEnabled(false);
        //modelYearET.setEnabled(false);
        makeET.setEnabled(false);


    }

    private void setDefaults(){
        engineNoET.setText(null);
        chassisET.setText(null);
        makeSpinner.setSelection(0);
        makeET.setText(null);
        modelYearET.setText(null);
        modelET.setText(null);
        insuranceDateTV.setText(null);
        insuranceSpinner.setSelection(0);
        chassisNo = null;
        engineNo = null;
        make = null;
        makeId = null;
        model = null;
        modelYear = null;
        insured = null;
        insuranceDate = null;
        insuranceType = null;
        kilometers = null;
        chassisET.setEnabled(true);
        makeSpinner.setEnabled(true);
        modelET.setEnabled(true);
        modelYearET.setEnabled(true);
        makeET.setEnabled(false);
        insYRB.setChecked(false);
        insNRB.setChecked(false);
        isRegisteredVehicle = false;

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


}
