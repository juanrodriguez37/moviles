package com.android.pentagono.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pentagono.Common.Common;
import com.android.pentagono.Interface.IRecyclerItemSelectedListener;
import com.android.pentagono.Model.Profesor;
import com.android.pentagono.R;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class MyProfesorAdapter extends RecyclerView.Adapter<MyProfesorAdapter.MyViewHolder> {

    Context context;
    List<Profesor> profesorList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public MyProfesorAdapter(Context context, List<Profesor> profesorList){
        this.context = context;
        this.profesorList = profesorList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);

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
    if(!cardViewList.contains(holder.card_profesor))
    {
        cardViewList.add(holder.card_profesor);
    }

    holder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
        @Override
        public void onItemSelectedListener(View view, int pos) {

            for(CardView cardView: cardViewList)
            {
                cardView.setCardBackgroundColor(context.getResources()
                        .getColor(android.R.color.white));
            }
            holder.card_profesor.setCardBackgroundColor(
                    context.getResources()
                    .getColor(android.R.color.holo_orange_dark)
            );

            Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
            intent.putExtra(Common.KEY_BARBER_SELECTED,profesorList.get(pos));
            intent.putExtra(Common.KEY_STEP, 2);
            localBroadcastManager.sendBroadcast(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return profesorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_profesor_name;
        RatingBar ratingBar;
        CardView card_profesor;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_profesor_name = (TextView) itemView.findViewById(R.id.txt_profesor_name);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rtb_profesor);
            card_profesor = (CardView) itemView.findViewById(R.id.card_profesor);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
           iRecyclerItemSelectedListener.onItemSelectedListener(view,getAdapterPosition());
        }

    }
}
