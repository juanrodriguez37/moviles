package com.android.pentagono.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pentagono.Model.BookingInformation;
import com.android.pentagono.R;


import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyHistoryAdapter extends RecyclerView.Adapter<MyHistoryAdapter.MyViewHolder> {

    Context context;
    List<BookingInformation> bookingInformationList;

    public MyHistoryAdapter(Context context, List<BookingInformation> bookingInformationList) {
        this.context = context;
        this.bookingInformationList = bookingInformationList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_history,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_booking_profesor_text.setText(bookingInformationList.get(position).getBarberName());

        holder.txt_booking_time_text_history.setText(bookingInformationList.get(position).getTime());

        holder.txt_course_address.setText(bookingInformationList.get(position).getSalonAddress());

        holder.txt_course_name_history.setText(bookingInformationList.get(position).getSalonName());


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(("dd/MM/yyyy"));

        Date val = bookingInformationList.get(position).getTimestamp().toDate();



        holder.txt_booking_date.setText(simpleDateFormat.format(val));


    }

    @Override
    public int getItemCount() {
        return bookingInformationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Unbinder unbinder;
        @BindView(R.id.txt_history_course_name)
        TextView txt_course_name_history;
        @BindView(R.id.txt_course_address)
        TextView txt_course_address;
        @BindView(R.id.txt_booking_time_text_history)
        TextView txt_booking_time_text_history;
        @BindView(R.id.txt_profesor_name_history)
        TextView txt_booking_profesor_text;
        @BindView(R.id.txt_booking_date)
        TextView txt_booking_date;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
