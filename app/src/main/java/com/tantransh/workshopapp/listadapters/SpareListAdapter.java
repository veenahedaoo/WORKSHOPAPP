package com.tantransh.workshopapp.listadapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.jobbooking.data.JobSpareList;
import com.tantransh.workshopapp.jobbooking.data.SpareInfo;

public class SpareListAdapter extends RecyclerView.Adapter<SpareListAdapter.Holder>{

    public SpareListAdapter(Context context, JobSpareList jobSpareList){
        this.jobSpareList = jobSpareList;
        this.context = context;
    }
    private JobSpareList jobSpareList;
    private Context context;
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_job_spare_list_item,viewGroup,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        final SpareInfo spInfo = jobSpareList.getSpare(i);
        holder.srNoTV.setText(String.valueOf(i+1));
        holder.spareNameTV.setText(spInfo.getSpareName());
        holder.qtyTV.setText(spInfo.getQty());
        holder.makeTV.setText(spInfo.getMake());
        holder.delIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobSpareList.removeSpare(i);
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return jobSpareList.getSize();
    }

    class Holder extends RecyclerView.ViewHolder{

        private TextView srNoTV, spareNameTV, makeTV, qtyTV;
        private ImageView delIcon;
        public Holder(@NonNull View itemView) {
            super(itemView);
            srNoTV = itemView.findViewById(R.id.srNOTV);
            spareNameTV = itemView.findViewById(R.id.spareNameTV);
            makeTV = itemView.findViewById(R.id.makeNameTV);
            qtyTV = itemView.findViewById(R.id.qtyTV);
            delIcon = itemView.findViewById(R.id.delIcon);
        }
    }
}
