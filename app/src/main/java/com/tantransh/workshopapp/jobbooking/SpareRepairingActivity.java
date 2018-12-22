package com.tantransh.workshopapp.jobbooking;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.appdata.BookingDetails;
import com.tantransh.workshopapp.appdata.Validator;
import com.tantransh.workshopapp.dialogs.JobSpareDialog;
import com.tantransh.workshopapp.jobbooking.data.JobSpareList;
import com.tantransh.workshopapp.jobbooking.data.SpareInfo;
import com.tantransh.workshopapp.jobbooking.data.SparesList;
import com.tantransh.workshopapp.listadapters.SpareListAdapter;
import com.tantransh.workshopapp.requestadapter.GeneralRequestAdapter;

import java.util.ArrayList;
import java.util.List;

public class SpareRepairingActivity extends AppCompatActivity {

    private GeneralRequestAdapter generalRequestAdapter;
    private BroadcastReceiver receiver;
    private SparesList sparesList;
    private List<String> spareArrayList;
    private ArrayAdapter<String> spareAdapter;
    private Spinner spareSpinner;
    private SpareInfo currentSelectedSpare;
    private EditText amtET,qtyET;
    private JobSpareList jobSpareList;
    private RecyclerView jobSpareListRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare_repairing);
        initData();
        initViews();
        initEvents();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action){
                    case AppConstants.ACTION_SPARE_LIST_LOADED:
                        sparesList = (SparesList) intent.getSerializableExtra(AppConstants.EXTRA_SPARE_LIST);
                        initSpareList(sparesList);
                        break;
                }
            }
        };

        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_SPARE_LIST_LOADED));
    }

    public void cancel(View view){
        finish();
    }

    private void initEvents() {
        spareSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    currentSelectedSpare = sparesList.getSpare(i-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initViews() {
        spareSpinner = findViewById(R.id.spareSpinner);
        amtET = findViewById(R.id.amtET);
        qtyET = findViewById(R.id.qtyET);
        jobSpareListRV = findViewById(R.id.spareListRV);
        jobSpareListRV.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initSpareList(SparesList sparesList) {
        spareArrayList.add("Select Spare");
        for(int i = 0; i<sparesList.getSize(); i++){
            String spareName = sparesList.getSpare(i).getSpareName();
            if(Validator.isValidString(sparesList.getSpare(i).getMake()))
                spareName = spareName+"("+sparesList.getSpare(i).getMake()+")";
            spareArrayList.add(spareName);
        }

        spareAdapter = new ArrayAdapter<>(this,R.layout.spinner_item,spareArrayList);
        spareSpinner.setAdapter(spareAdapter);
    }

    private void initData() {
        spareArrayList = new ArrayList<>();
        jobSpareList = JobSpareList.getInstance();
        jobSpareList.clear();
        generalRequestAdapter = GeneralRequestAdapter.getInstance(this);
        generalRequestAdapter.getSpares();
    }

    public void moveNext(View view){

        BookingDetails bookingDetails = BookingDetails.getInstance();
        bookingDetails.setJobSpareList(jobSpareList);
        startActivity(new Intent(this,LaborInformationActivity.class));
    }

    public void back(View view){
        finish();
    }

    public void addSpareInJobList(View view){
        String amt = amtET.getText().toString();
        String qty = qtyET.getText().toString();
        if(Validator.isValidString(amt,qty) && currentSelectedSpare!=null){
            SpareInfo spareInfo = SpareInfo.getInstance();
            spareInfo.setSpareId(currentSelectedSpare.getSpareId());
            spareInfo.setSpareName(currentSelectedSpare.getSpareName());
            spareInfo.setAmount(amt);
            spareInfo.setMake(currentSelectedSpare.getMake());
            spareInfo.setUnit(currentSelectedSpare.getUnit());
            spareInfo.setQty(qty);
            addJobSpares(spareInfo);
        }
        else{
            if(currentSelectedSpare==null && !Validator.isValidString(amt,qty)){
                showMessage("Please select spare and add qty and amount");
            }
            else if(currentSelectedSpare==null){
                showMessage("Please select spare");
            }
            else if(!Validator.isValidString(qty,amt)){
                showMessage("Please specify price and qty");
            }
        }
    }

    private void addJobSpares(final SpareInfo spareInfo){
        if(jobSpareList.searchSpare(spareInfo.getSpareId())){
            if(jobSpareList.matchPrice(spareInfo.getSpareId(),spareInfo.getAmount())){
                System.out.println("Price matched");
                jobSpareList.updateQty(spareInfo.getSpareId(),spareInfo.getQty());
            }
            else{
                final JobSpareDialog dialog = new JobSpareDialog(this);
                dialog.show();
                dialog.setCancelable(false);
                Window dialogWindow = dialog.getWindow();
                dialogWindow.setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
                dialog.messageTV.setText(spareInfo.getSpareName()+" is already added with different price. Please choose your action.");
                dialog.updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        jobSpareList.updatePriceAndQty(spareInfo.getSpareId(), spareInfo.getAmount(), spareInfo.getQty());
                        dialog.dismiss();
                        initJobSpareList(jobSpareList);
                    }
                });

                dialog.newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        jobSpareList.setSpare(spareInfo);
                        dialog.dismiss();
                        initJobSpareList(jobSpareList);
                    }
                });

                dialog.cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });
            }
        }
        else{
            jobSpareList.setSpare(spareInfo);
        }

        initJobSpareList(jobSpareList);

    }

    private void clearFields(){
        qtyET.setText(null);
        amtET.setText(null);
        spareSpinner.setSelection(0);
        currentSelectedSpare= null;
    }

    private void initJobSpareList(JobSpareList jobSpareList){
        clearFields();
        SpareListAdapter adapter = new SpareListAdapter(this,jobSpareList);
        jobSpareListRV.setAdapter(adapter);
    }

    private void showMessage(String message){
        Snackbar.make(findViewById(R.id.parentLayout),message,Snackbar.LENGTH_LONG).show();
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

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_SPARE_LIST_LOADED));
    }
}
