package com.android.pentagono.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pentagono.Model.Course;
import com.android.pentagono.R;

import java.util.List;

public class MySalonAdapter extends RecyclerView.Adapter<MySalonAdapter.MyViewHolder> {

    Context context;
    List<Course> courseList;

    public MySalonAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_salon, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
     holder.txt_salon_name.setText(courseList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_salon_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_salon_name = (TextView) itemView.findViewById(R.id.txt_salon_name);
        }
    }
}
