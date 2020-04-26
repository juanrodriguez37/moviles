package com.android.pentagono.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pentagono.Model.Profesor;
import com.android.pentagono.R;

import java.util.List;

public class MyProfesorAdapter extends RecyclerView.Adapter<MyProfesorAdapter.MyViewHolder> {

    Context context;
    List<Profesor> profesorList;

    public MyProfesorAdapter(Context context, List<Profesor> profesorList){
        this.context = context;
        this.profesorList = profesorList;
    }



    @NonNull
    @Override
    public MyProfesorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_profesor, parent, false);

        return new MyProfesorAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProfesorAdapter.MyViewHolder holder, int position) {
    holder.txt_profesor_name.setText(profesorList.get(position).getName());
    holder.ratingBar.setRating((float)profesorList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return profesorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_profesor_name;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_profesor_name = (TextView) itemView.findViewById(R.id.txt_profesor_name);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rtb_profesor);
        }
    }
}
