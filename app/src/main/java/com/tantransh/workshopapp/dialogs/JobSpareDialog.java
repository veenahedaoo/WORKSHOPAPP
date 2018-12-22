package com.tantransh.workshopapp.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.tantransh.workshopapp.R;

public class JobSpareDialog extends Dialog {

    public TextView messageTV;
    public Button cancelButton, updateButton, newButton;

    @SuppressLint("WrongViewCast")
    public JobSpareDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_job_spare);
        messageTV = findViewById(R.id.messageTV);
        cancelButton = findViewById(R.id.cancelButton);
        updateButton = findViewById(R.id.updateButton);
        newButton = findViewById(R.id.newButton);
    }
}
