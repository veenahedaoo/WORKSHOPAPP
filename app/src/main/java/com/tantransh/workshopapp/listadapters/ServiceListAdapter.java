package com.tantransh.workshopapp.listadapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.jobbooking.data.JobServiceList;
import com.tantransh.workshopapp.jobbooking.data.ServiceInfo;

public class ServiceListAdapter extends  RecyclerView.Adapter<ServiceListAdapter.Holder>{

    private JobServiceList jobServiceList;
    public ServiceListAdapter(JobServiceList jobServiceList){
        this.jobServiceList = jobServiceList;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_service_list_item,viewGroup,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        ServiceInfo serviceInfo = jobServiceList.getService(i);
        holder.serviceNameTV.setText(serviceInfo.getServiceName());
        holder.srnoTV.setText(String.valueOf(i+1));
        holder.amtTV.setText(serviceInfo.getAmount());
        holder.delIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobServiceList.remove(i);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobServiceList.getSize();
    }

    class Holder extends RecyclerView.ViewHolder{

        private TextView srnoTV, serviceNameTV, amtTV;
        private ImageView delIV;
        public Holder(@NonNull View itemView) {
            super(itemView);
            srnoTV = itemView.findViewById(R.id.srnotv);
            serviceNameTV = itemView.findViewById(R.id.serviceNameTV);
            amtTV = itemView.findViewById(R.id.amtTV);
            delIV = itemView.findViewById(R.id.deleteIV);
        }
    }
}
