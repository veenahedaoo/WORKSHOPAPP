package com.tantransh.workshopapp.listadapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.tantransh.workshopapp.jobcard.JobCardDetailsActivity;
import com.tantransh.workshopapp.openjobs.OpenJobInfo;
import com.tantransh.workshopapp.openjobs.OpenJoblist;

public class OpenJobListAdapter extends RecyclerView.Adapter<OpenJobListAdapter.Holder>{

    private OpenJoblist openJoblist;
    private Context context;

    @SuppressLint("StaticFieldLeak")
    private static OpenJobListAdapter instance;
    private OpenJobListAdapter(){

    }

    public static OpenJobListAdapter getInstance(Context context, OpenJoblist openJoblist){
        if(instance == null)
            instance = new OpenJobListAdapter();

        instance.context = context;
        instance.openJoblist = openJoblist;

        return instance;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_open_job_list_item,viewGroup,false);
        return new Holder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        final OpenJobInfo openJobInfo = openJoblist.getOpenJob(i);
        holder.jobcardNOTV.setText(openJobInfo.getBookingId());
        holder.vehicleNOTV.setText(openJobInfo.getVehicleNo());
        holder.makeModelTV.setText(openJobInfo.getMake()+" "+openJobInfo.getModel());
        holder.bookingStatusPB.setProgress(AppConstants.getProgress(openJobInfo.getBookingStatus()));
        holder.bookingStatusTV.setText(AppConstants.getProgressMessage(openJobInfo.getBookingStatus()));
        holder.bookingDateTV.setText(openJobInfo.getBookingDate());
        holder.attendedByTV.setText(openJobInfo.getUsername());
        holder.customerNameTV.setText(openJobInfo.getCustomerName());
        holder.customerContactTV.setText(openJobInfo.getCustomerContact());
        holder.repNameTV.setText(openJobInfo.getRepFirstName()+openJobInfo.getRepLastName());
        holder.repContactTV.setText(openJobInfo.getRepContact());
        if(openJobInfo.getRepFirstName().trim().equals("")){
            holder.repNameTV.setText("N/A");
            holder.repContactTV.setText("N/A");
        }
        System.out.println("\""+openJobInfo.getRepFirstName()+"\"");
        holder.jobCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jobInfoIntent = new Intent(context, JobCardDetailsActivity.class);
                jobInfoIntent.putExtra("booking_id",openJobInfo.getBookingId());
                context.startActivity(jobInfoIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return openJoblist.getSize();
    }

    class Holder extends RecyclerView.ViewHolder{

        private TextView jobcardNOTV, vehicleNOTV, makeModelTV, bookingStatusTV, bookingDateTV, attendedByTV, customerNameTV, customerContactTV, repNameTV, repContactTV;
        private Button jobInfoButton, jobCardButton;
        private ProgressBar bookingStatusPB;

        Holder(@NonNull View itemView) {
            super(itemView);
            jobcardNOTV = itemView.findViewById(R.id.jobcardNumTV);
            vehicleNOTV = itemView.findViewById(R.id.vehicleRegsNOTV);
            makeModelTV = itemView.findViewById(R.id.makemodelTV);
            bookingStatusPB = itemView.findViewById(R.id.progressBarPB);
            bookingStatusTV = itemView.findViewById(R.id.jobStatusTV);
            bookingDateTV = itemView.findViewById(R.id.bookedOnTV);
            attendedByTV = itemView.findViewById(R.id.attendedByTV);
            customerNameTV = itemView.findViewById(R.id.customernameTV);
            customerContactTV = itemView.findViewById(R.id.customercontactTV);
            repNameTV = itemView.findViewById(R.id.representativenameTV);
            repContactTV = itemView.findViewById(R.id.representativenumberTV);
            jobCardButton = itemView.findViewById(R.id.jobcardBTN);
            jobInfoButton = itemView.findViewById(R.id.jobInfoBTN);

        }
    }


}
