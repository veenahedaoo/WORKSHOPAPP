package com.tantransh.workshopapp.jobbooking;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.appdata.BookingDetails;
import com.tantransh.workshopapp.appdata.Validator;
import com.tantransh.workshopapp.dialogs.JobServiceDialog;
import com.tantransh.workshopapp.home.DashboardActivity;
import com.tantransh.workshopapp.jobbooking.data.ImageList;
import com.tantransh.workshopapp.jobbooking.data.JobServiceList;
import com.tantransh.workshopapp.jobbooking.data.ServiceInfo;
import com.tantransh.workshopapp.jobbooking.data.ServiceList;
import com.tantransh.workshopapp.listadapters.ImageListAdapter;
import com.tantransh.workshopapp.listadapters.ServiceListAdapter;
import com.tantransh.workshopapp.requestadapter.BookingRequestAdapter;
import com.tantransh.workshopapp.requestadapter.GeneralRequestAdapter;

import java.util.ArrayList;
import java.util.List;

public class LaborInformationActivity extends AppCompatActivity {
    protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    private Uri imageUri;
    private FloatingActionButton captureImageButton;
    private ImageList imageList;
    private RecyclerView imagesRV, servicesRV;
    private GeneralRequestAdapter generalRequestAdapter;
    private ServiceList serviceList;
    private List<String> serviceArr;
    private ArrayAdapter<String> serviceAdapter;
    private Spinner serviceSpinner;
    private BroadcastReceiver receiver;
    private EditText amtET;
    private ServiceInfo selectedService;
    private JobServiceList jobServiceList;
    private BookingRequestAdapter bookingRequestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labor_information);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initViews();
        initData();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch(action){
                    case AppConstants.ACTION_SERVICE_LIST_LOADED:
                        ServiceList serviceList = (ServiceList) intent.getSerializableExtra(AppConstants.EXTRA_SERVICE_LIST);
                        initServiceList(serviceList);
                        break;

                    case AppConstants.ACTION_PICTURE_UPLOADED:
                        Toast.makeText(context, "Service booked successfully", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, DashboardActivity.class));
                        ImageList.getInstance().clear();
                        break;
                }
            }
        };

        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_SERVICE_LIST_LOADED));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_PICTURE_UPLOADED));
    }



    private void initData() {
        imageList = ImageList.getInstance();
        serviceArr = new ArrayList<>();
        jobServiceList = JobServiceList.getInstance();
        jobServiceList.clear();
        generalRequestAdapter = GeneralRequestAdapter.getInstance(this);
        serviceList = ServiceList.getInstance();
        bookingRequestAdapter = BookingRequestAdapter.getInstance(this);
        if(serviceList.getSize()>0){
            initServiceList(serviceList);
        }
        else{
            generalRequestAdapter.getServices();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_SERVICE_LIST_LOADED));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_PICTURE_UPLOADED));
    }

    private void initServiceList(ServiceList serviceList) {
        serviceArr.add("Select Service");
        for(int i =0; i<serviceList.getSize();i++){
            serviceArr.add(serviceList.getService(i).getServiceName());
        }
        serviceAdapter = new ArrayAdapter<>(this,R.layout.spinner_item,serviceArr);
        serviceSpinner.setAdapter(serviceAdapter);
    }

    private void initViews() {
        captureImageButton =  findViewById(R.id.captureImageButton);
        imagesRV = findViewById(R.id.imagesRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        imagesRV.setLayoutManager(linearLayoutManager);

        serviceSpinner = findViewById(R.id.serviceSpinner);
        amtET = findViewById(R.id.amtET);
        servicesRV = findViewById(R.id.servicesRV);
        servicesRV.setLayoutManager(new LinearLayoutManager(this));
        initViewEvents();
    }

    private void initViewEvents(){
        captureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage(v);
            }
        });

        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0)
                selectedService = serviceList.getService(i-1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedService = null;
            }
        });



    }

    public void clearFields(){
        selectedService = null;
        amtET.setText(null);
        serviceSpinner.setSelection(0);
        showImages(imageList);
        hideKeyboard();
    }


    public void back(View view){
        finish();
    }

    public void captureImage(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                //use imageUri here to access the image

                Bundle extras = data.getExtras();

                Bitmap bmp = (Bitmap) extras.get("data");
                imageList.addImage(bmp);
                for(int i=0;i<imageList.getSize();i++){

                }

                System.out.println("Images : "+imageList.getSize());
                showImages(imageList);
                // here you will get the image as bitmap


            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT);
            }
        }

    }

    public void showImages(ImageList imageList){
        System.out.println(imagesRV.isComputingLayout());

        hideKeyboard();
        ImageListAdapter imageListAdapter = new ImageListAdapter(getApplicationContext(),imageList);
        imagesRV.setAdapter(imageListAdapter);
        servicesRV.smoothScrollToPosition(imageList.getSize());
    }

    public void addJobServices(View view){
        hideKeyboard();
        String amt = amtET.getText().toString();
        if(Validator.isValidString(amt) && selectedService != null){
            ServiceInfo serviceInfo = new ServiceInfo();
            serviceInfo.setServiceId(selectedService.getServiceId());
            serviceInfo.setServiceName(selectedService.getServiceName());
            serviceInfo.setAmount(amt);
            addJobServices(serviceInfo);
        }
        else{
            if(selectedService==null){
                showMessage("Please select service");
            }
            else if(!Validator.isValidString(amt)){
                showMessage("Please specify amount");
            }
        }

    }

    public void addJobServices(final ServiceInfo serviceInfo){
        if(jobServiceList.searchService(serviceInfo.getServiceId())){
            if(!jobServiceList.comparePrice(serviceInfo.getServiceId(),serviceInfo.getAmount())){
                final JobServiceDialog dialog = new JobServiceDialog(this);
                dialog.setCancelable(false);
                Window dialogWindow = dialog.getWindow();
                dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                dialog.messageTV.setText(serviceInfo.getServiceName()+" is already in list with different price. Would you like to update price");

                dialog.updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        jobServiceList.updateAmount(serviceInfo.getServiceId(),serviceInfo.getAmount());
                        dialog.dismiss();
                        clearFields();
                        showJobServices(jobServiceList);
                    }
                });

                dialog.cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        clearFields();
                        showJobServices(jobServiceList);
                    }
                });
            }
        }
        else{
            jobServiceList.addService(serviceInfo);
            showJobServices(jobServiceList);
            clearFields();
        }
    }

    public void showJobServices(JobServiceList jobServiceList){
        hideKeyboard();
        System.out.println(jobServiceList.getSize()+" services added");
        ServiceListAdapter adapter = new ServiceListAdapter(jobServiceList);
        servicesRV.setAdapter(adapter);
    }


    public void cancel(View view){
        finish();
    }

    private void showMessage(String message){
        hideKeyboard();
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

    public void moveNext(View view){
        BookingDetails bookingDetails = BookingDetails.getInstance();
        if(jobServiceList.getSize()+bookingDetails.getJobSpareList().getSize() <= 0){
            showMessage("Please select at-least one service or spare to book service");
        }
        else{
            bookingDetails.setJobServiceList(jobServiceList);
            bookingDetails.setBookingDate(Validator.getCurrentDate());
            bookingRequestAdapter.bookJob(bookingDetails);
        }
    }
}

