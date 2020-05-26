package com.android.pentagono.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pentagono.Adapter.MyProfesorAdapter;
import com.android.pentagono.Common.SpacesItemDecoration;
import com.android.pentagono.Model.EventBus.ProfesorDoneEvent;
import com.android.pentagono.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookingStep2Fragment extends Fragment {

    static BookingStep2Fragment instance;
    Unbinder unbinder;

    @BindView(R.id.recycler_profesor)
    RecyclerView recyclerProfesor;


    @Override
    public void onStart() {
       super.onStart();
       EventBus.getDefault().register(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void setProfesorAdapter(ProfesorDoneEvent event)
    {
        MyProfesorAdapter adapter =  new MyProfesorAdapter(getContext(),event.getProfesorList());
        recyclerProfesor.setAdapter(adapter);
    }



    public static BookingStep2Fragment getInstance() {
        if(instance == null)
            instance = new BookingStep2Fragment();
        return instance;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
