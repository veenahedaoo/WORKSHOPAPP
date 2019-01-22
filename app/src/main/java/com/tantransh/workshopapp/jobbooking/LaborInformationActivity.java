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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.appdata.BookingDetails;
import com.tantransh.workshopapp.appdata.Validator;
import com.tantransh.workshopapp.home.DashboardActivity;
import com.tantransh.workshopapp.jobbooking.data.ImageList;
import com.tantransh.workshopapp.listadapters.ImageListAdapter;
import com.tantransh.workshopapp.requestadapter.BookingRequestAdapter;

public class LaborInformationActivity extends AppCompatActivity {
    protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    private Uri imageUri;
    private FloatingActionButton captureImageButton;
    private ImageList imageList;
    private RecyclerView imagesRV;
    private BroadcastReceiver receiver;
    private BookingRequestAdapter bookingRequestAdapter;
    private LinearLayout progressLO;
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
                assert action != null;
                switch(action){

                    case AppConstants.ACTION_PICTURE_UPLOADED:
                        Toast.makeText(context, "Service booked successfully", Toast.LENGTH_SHORT).show();
                        progressLO.setVisibility(View.GONE);
                        finishAffinity();
                        context.startActivity(new Intent(context, DashboardActivity.class));
                        ImageList.getInstance().clear();
                        break;

                    case AppConstants.ACTION_DUPLICATE_SERVICE_BOOKING:
                        Toast.makeText(context, "Duplicate service booking", Toast.LENGTH_SHORT).show();
                        progressLO.setVisibility(View.GONE);
                }
            }
        };

        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_PICTURE_UPLOADED));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_DUPLICATE_SERVICE_BOOKING));
    }



    private void initData() {
        imageList = ImageList.getInstance();
        bookingRequestAdapter = BookingRequestAdapter.getInstance(this);
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



    private void initViews() {
        captureImageButton =  findViewById(R.id.captureImageButton);
        imagesRV = findViewById(R.id.imagesRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        progressLO = findViewById(R.id.progressLO);
        imagesRV.setLayoutManager(linearLayoutManager);

        initViewEvents();
    }

    private void initViewEvents(){
        captureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage(v);
            }
        });
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

                assert extras != null;
                Bitmap bmp = (Bitmap) extras.get("data");
                imageList.addImage(bmp);

                System.out.println("Images : "+imageList.getSize());
                showImages(imageList);
                // here you will get the image as bitmap


            }
        }

    }

    public void showImages(ImageList imageList){
        System.out.println(imagesRV.isComputingLayout());

        hideKeyboard();
        ImageListAdapter imageListAdapter = new ImageListAdapter(getApplicationContext(),imageList);
        imagesRV.setAdapter(imageListAdapter);
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
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void moveNext(View view){
        BookingDetails bookingDetails = BookingDetails.getInstance();
        bookingDetails.setBookingDate(Validator.getCurrentDateTime());
        progressLO.setVisibility(View.VISIBLE);
        bookingRequestAdapter.bookJob(bookingDetails);
    }
}

