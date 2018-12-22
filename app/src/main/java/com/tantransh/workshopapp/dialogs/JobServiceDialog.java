package com.tantransh.workshopapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.tantransh.workshopapp.R;

public class JobServiceDialog extends Dialog{
    public Button updateButton,  cancelButton;
    public TextView messageTV;
    public JobServiceDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_job_service);
        updateButton = findViewById(R.id.updateButton);
        cancelButton = findViewById(R.id.cancelButton);
        messageTV = findViewById(R.id.messageTV);
    }
}
