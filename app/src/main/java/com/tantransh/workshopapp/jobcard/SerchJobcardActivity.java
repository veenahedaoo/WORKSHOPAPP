package com.tantransh.workshopapp.jobcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tantransh.workshopapp.R;

public class SerchJobcardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch_jobcard);
    }

    public void back(View view){
        finish();
    }


    public void goToJobCard(View view){

    }

}
