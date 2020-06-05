package com.android.pentagono.Fragments;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.pentagono.Common.Common;
import com.android.pentagono.Model.BookingInformation;
import com.android.pentagono.Model.EventBus.ConfirmBookingEvent;
import com.android.pentagono.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;

public class BookingStep4Fragment extends Fragment {


    SimpleDateFormat simpleDateFormat;
    FirebaseAuth mauth;

    AlertDialog dialog;

    @BindView(R.id.txt_booking_profesor_text)
    TextView txt_booking_profesor_text;
    @BindView(R.id.txt_booking_time_text)
    TextView txt_booking_time_text;
    @BindView(R.id.txt_course_name)
    TextView txt_course_name;
    @BindView(R.id.txt_location)
    TextView txt_location;
    @BindView(R.id.txt_open_hours)
    TextView txt_open_hours;
    @BindView(R.id.txt_course_phone)
    TextView txt_phone;
    @BindView(R.id.txt_course_web)
    TextView txt_course_web;

    @OnClick(R.id.btn_confirm)
            void confirmBooking() {
        dialog.show();
        String startTime = Common.convertTimeSlotToString(Common.currentTimeSlot);
        String[] convertTime = startTime.split("-");
        String[] startTimeConvert = convertTime[0].split(":");
        int startHourInt = Integer.parseInt(startTimeConvert[0].trim());
        int startMinInt = Integer.parseInt(startTimeConvert[1].trim());

        Calendar bookingDateWithourHouse = Calendar.getInstance();
        bookingDateWithourHouse.setTimeInMillis(Common.bookingDate.getTimeInMillis());
        bookingDateWithourHouse.set(Calendar.HOUR_OF_DAY,startHourInt);
        bookingDateWithourHouse.set(Calendar.MINUTE,startMinInt);

        Timestamp timestamp = new Timestamp(bookingDateWithourHouse.getTime());


        final BookingInformation bookingInformation = new BookingInformation();
        bookingInformation.setCourse(Common.subject);
        bookingInformation.setTimestamp(timestamp);
        bookingInformation.setBarberid(Common.currentProfesor.getBarberId());
        bookingInformation.setBarberName(Common.currentProfesor.getName());

        //bookingInformation.setCustomerName(Common.currentUser.getName());
        //bookingInformation.setCustomerPhone(Common.currentUser.getPhoneNumber());
        bookingInformation.setDone(false);

        bookingInformation.setCustomerName("Student");
        bookingInformation.setCustomerPhone("+57 3008494164");
        bookingInformation.setSalonId(Common.currentCourse.getCourse_id());
        bookingInformation.setSalonAddress(Common.currentCourse.getAddress());
        bookingInformation.setSalonName(Common.currentCourse.getName());
        bookingInformation.setTime(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
                .append(" at ").append(simpleDateFormat.format(bookingDateWithourHouse.getTime())).toString());
        bookingInformation.setSlot(Long.valueOf(Common.currentTimeSlot));

        DocumentReference bookingDate = FirebaseFirestore.getInstance()
                .collection("courses")
                .document(Common.subject)
                .collection("branch")
                .document(Common.currentCourse.getCourse_id())
                .collection("profesores")
                .document(Common.currentProfesor.getBarberId())
                .collection(Common.simpleDateFormat.format(Common.bookingDate.getTime()))
                .document(String.valueOf(Common.currentTimeSlot));

        bookingDate.set(bookingInformation)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addToUserBooking(bookingInformation);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addToUserBooking(BookingInformation bookingInformation) {

        CollectionReference userbooking = FirebaseFirestore.getInstance()
                .collection("estudiantes")
                .document(mauth.getCurrentUser().getEmail())
                .collection("bookings");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);

        Timestamp toDayTimeStamp = new Timestamp(calendar.getTime());

        userbooking.whereGreaterThanOrEqualTo("timestamp",toDayTimeStamp)
                .whereEqualTo("done", true)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.getResult().isEmpty())
                        {
                            userbooking.document()
                                    .set(bookingInformation)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            if(dialog.isShowing())
                                            {
                                                dialog.dismiss();
                                            }

                                            addToCalendar(Common.bookingDate,
                                               Common.convertTimeSlotToString(Common.currentTimeSlot));

                                            getActivity().finish();
                                            Toast.makeText(getContext(),"Thank you for using our services",Toast.LENGTH_SHORT).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if(dialog.isShowing())
                                    {
                                        dialog.dismiss();
                                    }
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else
                        {
                            if(dialog.isShowing())
                            {
                                dialog.dismiss();
                            }
                            getActivity().finish();
                            Toast.makeText(getContext(),"Thank you for using our services",Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

    private void addToCalendar(Calendar bookingDate, String startDate) {

        String startTime = Common.convertTimeSlotToString(Common.currentTimeSlot);
        String[] convertTime = startTime.split("-");
        String[] startTimeConvert = convertTime[0].split(":");
        int startHourInt = Integer.parseInt(startTimeConvert[0].trim());
        int startMinInt = Integer.parseInt(startTimeConvert[1].trim());

        String[] endTimeConvert = convertTime[1].split(":");
        int endHourInt = Integer.parseInt(endTimeConvert[0].trim());
        int endMinInt = Integer.parseInt(endTimeConvert[1].trim());

        Calendar startEvent = Calendar.getInstance();
        startEvent.setTimeInMillis(bookingDate.getTimeInMillis());
        startEvent.set(Calendar.HOUR_OF_DAY,startHourInt);
        startEvent.set(Calendar.MINUTE,startMinInt);

        Calendar endEvent = Calendar.getInstance();
        endEvent.setTimeInMillis(bookingDate.getTimeInMillis());
        endEvent.set(Calendar.HOUR_OF_DAY,endHourInt);
        endEvent.set(Calendar.MINUTE,endMinInt);

        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String startEventTime = calendarDateFormat.format(startEvent.getTime());
        String endEventTime = calendarDateFormat.format(endEvent.getTime());

        addToDeviceCalendar(startEventTime, endEventTime, "Pentagono Online Appointment",
                new StringBuilder("Appointment from ")
        .append(startTime)
        .append( " with ")
        .append(Common.currentProfesor.getName())                
        .append(" Topic: " )
        .append(Common.currentCourse.getName()).toString());




    }

    private void addToDeviceCalendar(String startEventTime, String endEventTime, String pentagono_online_appointment, String appointment_from_) {
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            Date start = calendarDateFormat.parse(startEventTime);
            Date end = calendarDateFormat.parse(endEventTime);

            ContentValues event = new ContentValues();

            event.put(CalendarContract.Events.CALENDAR_ID,getCalendar(getContext()));
            event.put(CalendarContract.Events.TITLE,pentagono_online_appointment);
            event.put(CalendarContract.Events.DESCRIPTION,appointment_from_);

            event.put(CalendarContract.Events.DTSTART,start.getTime());
            event.put(CalendarContract.Events.DTEND, end.getTime());
            event.put(CalendarContract.Events.ALL_DAY,0);
            event.put(CalendarContract.Events.HAS_ALARM,1);

            String timeZone = TimeZone.getDefault().getID();
            event.put(CalendarContract.Events.EVENT_TIMEZONE,timeZone);


            Uri calendars;
            if(Build.VERSION.SDK_INT >= 8)
                calendars = Uri.parse("content://com.android.calendar/events");
            else
                 calendars = Uri.parse("content://calendar/events");

           Uri uri_save = getActivity().getContentResolver().insert(calendars,event);
           Paper.init(getActivity());
           Paper.book().write(Common.EVENT_URI_CACHE,uri_save.toString());



        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private String getCalendar(Context context) {
        String gmailCalendar = "";
        String projection[] = {"_id","calendar_displayName"};
        Uri calendars = Uri.parse("content://com.android.calendar/calendars");
        ContentResolver contentResolver = context.getContentResolver();
        Cursor managedCursor = contentResolver.query(calendars,projection,null,null,null );
        if(managedCursor.moveToFirst())
        {
            String calName;
            int nameCol = managedCursor.getColumnIndex(projection[1]);
            int idCol = managedCursor.getColumnIndex(projection[0]);
            do {
                calName = managedCursor.getString(nameCol);
                if(calName.contains("@gmail.com"))
                {
                    gmailCalendar = managedCursor.getString(idCol);
                    break;
                }
            } while (managedCursor.moveToNext());
            managedCursor.close();
        }
        Log.d("calendarName",gmailCalendar);
        return gmailCalendar;
    }

    private void resetStaticData() {
        Common.step = 0;
        Common.currentTimeSlot= -1;
        Common.currentCourse = null;
        Common.currentProfesor = null;
        Common.bookingDate.add(Calendar.DATE,0);
    }


    Unbinder unbinder;



    @Override
    public void onStart() {
        super.onStart();
        mauth = FirebaseAuth.getInstance();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void setDataBooking(ConfirmBookingEvent event)
    {
        if(event.isConfirm())
        {
            setData();
        }
    }



    private void setData() {
        txt_booking_profesor_text.setText((Common.currentProfesor.getName()));
        txt_booking_time_text.setText(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
        .append(" at ").append(simpleDateFormat.format(Common.bookingDate.getTime()))) ;
        txt_course_name.setText(Common.currentCourse.getName());
        txt_location.setText(Common.currentCourse.getAddress());
        txt_phone.setText(Common.currentCourse.getPhone());
        txt_course_web.setText(Common.currentCourse.getWebsite());
        txt_open_hours.setText(Common.currentCourse.getOpenHours());

    }


    static BookingStep4Fragment instance;

    public static BookingStep4Fragment getInstance() {
        if(instance == null)
            instance = new BookingStep4Fragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false)
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_booking_step_four, container, false);
        unbinder = ButterKnife.bind(this,itemView);
            return itemView;
    }
}
