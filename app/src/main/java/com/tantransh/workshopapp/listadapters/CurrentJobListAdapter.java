package com.tantransh.workshopapp.listadapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.currentjobs.CurrentJobInfo;
import com.tantransh.workshopapp.currentjobs.CurrentJobList;

public class CurrentJobListAdapter extends RecyclerView.Adapter<CurrentJobListAdapter.Holder>{

    private CurrentJobList currentJobList;
    private Context context;

    @SuppressLint("StaticFieldLeak")
    private static CurrentJobListAdapter instance;
    private CurrentJobListAdapter(Context context,CurrentJobList currentJobList){
        this.context = context;
        this.currentJobList = currentJobList;
    }

    public static CurrentJobListAdapter getInstance(Context context,CurrentJobList currentJobList){
        if (instance==null)
            instance = new CurrentJobListAdapter(context,currentJobList);

        instance.currentJobList = currentJobList;
        return instance;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_currentjob_list_item,viewGroup,false);
        return new Holder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        CurrentJobInfo currentJobInfo = currentJobList.getJob(i);
        holder.jobCardTV.setText(currentJobInfo.getBookingId());
        holder.vehicleRegNoTV.setText(currentJobInfo.getVehicleNo());
        holder.makeModelTV.setText(currentJobInfo.getMakeName()+" ("+currentJobInfo.getModel()+")");
        holder.customerNameTV.setText(currentJobInfo.getCustomerName());
        holder.customerContactTV.setText(currentJobInfo.getContact());
        currentJobInfo.setBookingStatus("1");
        int progress = AppConstants.getProgress(currentJobInfo.getBookingStatus());
        holder.jobStatusTV.setText(AppConstants.getProgressMessage(currentJobInfo.getBookingStatus()));
        holder.statusProgressPB.setProgress(progress);
        System.out.println(currentJobInfo.getBookingStatus());
    }

    @Override
    public int getItemCount() {
        return currentJobList.getSize();
    }

    class Holder extends RecyclerView.ViewHolder{

        private TextView jobCardTV, vehicleRegNoTV, makeModelTV, jobStatusTV, customerNameTV, customerContactTV;
        private ProgressBar statusProgressPB;
        private Button viewMoreButton;
        Holder(@NonNull View itemView) {
            super(itemView);
            jobCardTV = itemView.findViewById(R.id.jobCardTV);
            vehicleRegNoTV = itemView.findViewById(R.id.vehicleRegNoTV);
            makeModelTV = itemView.findViewById(R.id.makeModelTV);
            statusProgressPB = itemView.findViewById(R.id.statusprogressPB);
            jobStatusTV = itemView.findViewById(R.id.jobStatusTV);
            customerNameTV = itemView.findViewById(R.id.customerNameTV);
            customerContactTV = itemView.findViewById(R.id.customerContactNoTV);
            viewMoreButton = itemView.findViewById(R.id.viewmoreBTN);
        }
    }
}
