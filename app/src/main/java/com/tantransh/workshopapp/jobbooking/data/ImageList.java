package com.tantransh.workshopapp.jobbooking.data;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class ImageList  {
    private List<Bitmap> imageList;

    private static ImageList instance;

    public static ImageList getInstance(){
        if(instance == null)
            instance = new ImageList();
        return instance;
    }

    private ImageList(){
        imageList = new ArrayList<>();
    }

    public Bitmap getImage(int pos){
        return imageList.get(pos);
    }

    public int getSize(){
        return imageList.size();
    }

    public void addImage(Bitmap bitmap){
        imageList.add(bitmap);
    }

    public void removeImage(int pos){
        imageList.remove(pos);
    }

    public void clear(){
        imageList.clear();
    }

}
