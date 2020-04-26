package com.android.pentagono.Fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pentagono.Adapter.MyProfesorAdapter;
import com.android.pentagono.Common.Common;
import com.android.pentagono.Common.SpacesItemDecoration;
import com.android.pentagono.Model.Profesor;
import com.android.pentagono.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookingStep2Fragment extends Fragment {

    static BookingStep2Fragment instance;
    Unbinder unbinder;
    LocalBroadcastManager localBroadcastManager;

    @BindView(R.id.recycler_profesor)
    RecyclerView recyclerProfesor;

    private BroadcastReceiver profesorDoneReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Profesor> profesorArrayList = intent.getParcelableArrayListExtra(Common.KEY_BARBER_LOAD_DONE);
            MyProfesorAdapter adapter =  new MyProfesorAdapter(getContext(),profesorArrayList);
            recyclerProfesor.setAdapter(adapter);

        }
    };




    public static BookingStep2Fragment getInstance() {
        if(instance == null)
            instance = new BookingStep2Fragment();
        return instance;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(profesorDoneReceiver, new IntentFilter(Common.KEY_BARBER_LOAD_DONE));
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(profesorDoneReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_booking_step_two, container, false);

        unbinder = ButterKnife.bind(this,itemView);

        initView();
        return itemView;

    }

    private void initView() {

        recyclerProfesor.setHasFixedSize(true);
        recyclerProfesor.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerProfesor.addItemDecoration(new SpacesItemDecoration(4));


    }
}
