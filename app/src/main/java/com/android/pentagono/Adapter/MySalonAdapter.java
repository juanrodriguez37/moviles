package com.android.pentagono.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pentagono.Common.Common;
import com.android.pentagono.Interface.IRecyclerItemSelectedListener;
import com.android.pentagono.Model.Course;
import com.android.pentagono.R;

import java.util.ArrayList;
import java.util.List;

public class MySalonAdapter extends RecyclerView.Adapter<MySalonAdapter.MyViewHolder> {

    Context context;
    List<Course> courseList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public MySalonAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
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

     if(!cardViewList.contains(holder.card_salon))
     {
         cardViewList.add(holder.card_salon);
     }

     holder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
         @Override
         public void onItemSelectedListener(View view, int pos) {
             for(CardView cardView:cardViewList)
             {
                 cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));


             }
             holder.card_salon.setCardBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_dark));

          Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
          intent.putExtra(Common.KEY_SALON_STORE, courseList.get(pos));
          localBroadcastManager.sendBroadcast(intent);

         }
     });


    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_salon_name;
        CardView card_salon;
        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_salon_name = (TextView) itemView.findViewById(R.id.txt_salon_name);
            card_salon = (CardView) itemView.findViewById(R.id.card_salon);

            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            iRecyclerItemSelectedListener.onItemSelectedListener(view, getAdapterPosition());

        }

    }
}
