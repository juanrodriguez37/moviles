package com.android.pentagono;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import com.android.pentagono.Adapter.MyViewPagerAdapter;
import com.android.pentagono.Common.Common;
import com.android.pentagono.Common.NonSwipeViewPager;
import com.android.pentagono.Model.Profesor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class BookingActivity extends AppCompatActivity {

    LocalBroadcastManager localBroadcastManager;
    AlertDialog dialog;
    CollectionReference profesorRef;


    @BindView(R.id.step_view)
    StepView stepView;

    @BindView(R.id.view_pager)
    NonSwipeViewPager viewPager;

    @BindView(R.id.btn_previous_step)
    Button btn_previous_step;

    @BindView(R.id.btn_next_step)
    Button btn_next_step;

    @OnClick(R.id.btn_previous_step)
    void previousStep() {
        if(Common.step == 3 || Common.step > 0)
        {
            Common.step--;
            viewPager.setCurrentItem(Common.step);
        }
    }

    @OnClick(R.id.btn_next_step)
    void nextClick(){
       if(Common.step < 3 || Common.step == 0 )
       {
           Common.step++;
           if(Common.step == 1)
           {
               if(Common.currentCourse != null)
                   loadProfessorByCourse(Common.currentCourse.getCourse_id());
           }
           viewPager.setCurrentItem(Common.step);

       }


     }

    private void loadProfessorByCourse(String course_id) {
        ///courses/apo/branch/0SNfqvPbqbIDcqK5DDMx/profesores
         if(!TextUtils.isEmpty(Common.subject)) {
             profesorRef = FirebaseFirestore.getInstance()
                     .collection("courses")
                     .document(Common.subject)
                     .collection("branch")
                     .document(course_id)
                     .collection("profesores");

             profesorRef.get()
                     .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                         @Override
                         public void onComplete(@NonNull Task<QuerySnapshot> task) {

                             ArrayList<Profesor> profesores = new ArrayList<>();
                             for(QueryDocumentSnapshot profesoresSnapShot:task.getResult())
                             {
                                 Profesor profesor = profesoresSnapShot.toObject(Profesor.class);
                                 profesor.setBarberId(profesoresSnapShot.getId());

                                  profesores.add(profesor);
                             }
                             Intent intent = new Intent(Common.KEY_BARBER_LOAD_DONE);
                             intent.putParcelableArrayListExtra(Common.KEY_BARBER_LOAD_DONE,profesores);
                             localBroadcastManager.sendBroadcast(intent);

                             dialog.dismiss();

                         }

                     }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {

                     dialog.dismiss();

                 }
             });


         }



    }


    private BroadcastReceiver buttonNextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           Common.currentCourse = intent.getParcelableExtra(Common.KEY_SALON_STORE);
           btn_next_step.setEnabled(true);
           setColorButton();
        }
    };

    @Override
    protected void onDestroy() {
        localBroadcastManager.unregisterReceiver(buttonNextReceiver);
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        ButterKnife.bind(BookingActivity.this);

        dialog = new SpotsDialog.Builder().setContext(this).build();

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(buttonNextReceiver,new IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT));
        
        setUpStepView();
        setColorButton();

        //View

        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                stepView.go(position,true);

                 if( position == 0)
                 {
                     btn_previous_step.setEnabled(false);
                 }
                 else {
                     btn_previous_step.setEnabled(true);
                 }

                 setColorButton();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void setColorButton() {
        if(btn_next_step.isEnabled()) {
            btn_next_step.setBackgroundResource(R.color.black);
        }
        else {
            btn_next_step.setBackgroundResource(android.R.color.darker_gray);
        }

        if(btn_previous_step.isEnabled()) {
            btn_previous_step.setBackgroundResource(R.color.black);
        }
        else {
            btn_previous_step.setBackgroundResource(android.R.color.darker_gray);
        }

    }

    private void setUpStepView() {
        List<String> stepList = new ArrayList<>();
        stepList.add("Course");
        stepList.add("Assistant");
        stepList.add("Time");
        stepList.add("Confirm");
        stepView.setSteps(stepList);
    }
}
