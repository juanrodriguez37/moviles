package com.android.pentagono.Interface;

import com.android.pentagono.Model.BookingInformation;

public interface IBookingInfoLoadListener {
    void onBookingInfoLoadEmpty();
    void OnBookingInfoLoadSuccess(BookingInformation bookingInformation, String documentId );
    void onBookingInfoLoadFailed(String message);
}
