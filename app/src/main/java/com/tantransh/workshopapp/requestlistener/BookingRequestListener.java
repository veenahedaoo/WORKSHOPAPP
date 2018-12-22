package com.tantransh.workshopapp.requestlistener;

import com.tantransh.workshopapp.appdata.BookingDetails;
import com.tantransh.workshopapp.jobbooking.data.ImageList;

public interface BookingRequestListener {

    void bookJob(BookingDetails bookingDetails);
    void uploadPictures(ImageList imageList);
}
