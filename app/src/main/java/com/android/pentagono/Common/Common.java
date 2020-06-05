package com.android.pentagono.Common;

import com.android.pentagono.Model.BookingInformation;
import com.android.pentagono.Model.Course;
import com.android.pentagono.Model.Profesor;
import com.android.pentagono.Model.User;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Common {

    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_SALON_STORE = "SALON_SAVE";
    public static final String KEY_BARBER_LOAD_DONE = "BARBER_LOAD_DONE" ;
    public static final String KEY_DISPLAY_TIME_SLOT =  "TIME_SLOT";
    public static final String KEY_STEP = "STEP";
    public static final String KEY_BARBER_SELECTED = "PROFESOR_SELECTED";
    public static final String DISABLE_TAG = "DISABLE";
    public static final String KEY_TIME_SLOT = "TIME_SLOT";
    public static final String KEY_CONFIRM_BOOKING = "CONFIRM_BOOKING";
    public static final String EVENT_URI_CACHE = "URI_EVENT_SAVE";
    public static Course currentCourse;
    public static int step = 0;
    public static String subject = "";
    public static Profesor currentProfesor;
    public static final int TIME_SLOT_TOTAL = 8;
    public static int currentTimeSlot = -1;
    public static Calendar bookingDate = Calendar.getInstance();
    public static User currentUser;
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");
    public static BookingInformation currentBooking;
    public static String currentBookingId = "";
    public static  int screen_step = -1;
    public static List<BookingInformation> user_bookings = new ArrayList<>();

    public static String convertTimeSlotToString(int i) {
        switch (i)
        {
            case 0:
                return "8:00 - 9:00";
            case 1:
                return "9:00 - 10:00";
            case 2:
                return "10:00 - 11:00";
            case 3:
                return "11:00 - 12:00";
            case 5:
                return "12:00 - 13:00";
            case 6:
                return "13:00 - 14:00";
            case 7:
                return "14:00 - 15:00";
            default:
                return "Not working";



        }
    }

    public static String convertTimestampToStringKey(Timestamp timestamp) {
        Date date = timestamp.toDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(("dd_MM_yyyy"));
        return simpleDateFormat.format(date);

    }

    public static void setScreen_step(int step)
    {
        screen_step = step;
    }

    public static void setArrayHistoric(List<BookingInformation> bookingInformations) {
        user_bookings = bookingInformations;
    }


}
