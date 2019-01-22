package com.tantransh.workshopapp.listadapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.appdata.JobConcernItem;
import com.tantransh.workshopapp.jobbooking.JobInformationActivity;

public class UserItemsListAdapter extends RecyclerView.Adapter<UserItemsListAdapter.Holder> {


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_user_items_listitem,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") final int i) {
        JobConcernItem jobConcernItem = JobInformationActivity.userItems.getItem(i);
        holder.srNoTV.setText(String.valueOf(i+1));
        holder.itemNameTV.setText(jobConcernItem.itemName);
        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobInformationActivity.userItems.removeItem(i);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return JobInformationActivity.userItems.getSize();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView srNoTV, itemNameTV;
        ImageView deleteIV;
        Holder(@NonNull View itemView) {
            super(itemView);
            srNoTV = itemView.findViewById(R.id.srnotv);
            itemNameTV = itemView.findViewById(R.id.item_name_tv);
            deleteIV = itemView.findViewById(R.id.delete_iv);
        }
    }
}
