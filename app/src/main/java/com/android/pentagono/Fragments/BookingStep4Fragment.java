package com.android.pentagono.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.pentagono.Common.Common;
import com.android.pentagono.Model.BookingInformation;
import com.android.pentagono.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BookingStep4Fragment extends Fragment {


    SimpleDateFormat simpleDateFormat;
    LocalBroadcastManager localBroadcastManager;

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

        BookingInformation bookingInformation = new BookingInformation();
        bookingInformation.setBarberid(Common.currentProfesor.getBarberId());
        bookingInformation.setBarberName(Common.currentProfesor.getName());

        //bookingInformation.setCustomerName(Common.currentUser.getName());
        //bookingInformation.setCustomerPhone(Common.currentUser.getPhoneNumber());
        bookingInformation.setCustomerName("Student");
        bookingInformation.setCustomerPhone("+57 3008494164");
        bookingInformation.setSalonId(Common.currentCourse.getCourse_id());
        bookingInformation.setSalonAddress(Common.currentCourse.getAddress());
        bookingInformation.setSalonName(Common.currentCourse.getName());
        bookingInformation.setTime(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
                .append(" at ").append(simpleDateFormat.format(Common.currentDate.getTime())).toString());
        bookingInformation.setSlot(Long.valueOf(Common.currentTimeSlot));

        DocumentReference bookingDate = FirebaseFirestore.getInstance()
                .collection("courses")
                .document(Common.subject)
                .collection("branch")
                .document(Common.currentCourse.getCourse_id())
                .collection("profesores")
                .document(Common.currentProfesor.getBarberId())
                .collection(Common.simpleDateFormat.format(Common.currentDate.getTime()))
                .document(String.valueOf(Common.currentTimeSlot));

        bookingDate.set(bookingInformation)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        resetStaticData();
                        getActivity().finish();
                        Toast.makeText(getContext(),"Thank you for using our services",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void resetStaticData() {
        Common.step = 0;
        Common.currentTimeSlot= -1;
        Common.currentCourse = null;
        Common.currentProfesor = null;
        Common.currentDate.add(Calendar.DATE,0);
    }


    Unbinder unbinder;

    BroadcastReceiver confirmBookingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setData();
        }
    };

    private void setData() {
        txt_booking_profesor_text.setText((Common.currentProfesor.getName()));
        txt_booking_time_text.setText(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
        .append(" at ").append(simpleDateFormat.format(Common.currentDate.getTime()))) ;
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

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(confirmBookingReceiver,
                new IntentFilter(Common.KEY_CONFIRM_BOOKING));
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(confirmBookingReceiver);
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
