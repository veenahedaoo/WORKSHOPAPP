package com.tantransh.workshopapp.listadapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.jobbooking.data.ImageList;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.Holder>{


    private ImageList imageList;
    private Context context;

    public ImageListAdapter(Context context, ImageList imageList){
        this.context = context;
        this.imageList = imageList;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_image_list_item,viewGroup,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        holder.image.setImageBitmap(imageList.getImage(i));
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageList.removeImage(i);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.getSize();
    }

    class Holder extends RecyclerView.ViewHolder{

        private ImageView image, close;
        public Holder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageview);
            close = itemView.findViewById(R.id.closeIV);
        }
    }
}
