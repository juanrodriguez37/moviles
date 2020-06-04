package com.android.pentagono;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.pentagono.Common.AppStatus;
import com.android.pentagono.Common.Common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OfflineActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    @BindView(R.id.button_try_again)
    Button again;

    public void checkConnection() {

        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

            Toast.makeText(getApplicationContext(), "You are back online", Toast.LENGTH_LONG).show();

            if (Common.screen_step != 0) {

                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

            } else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }

        } else {

            Toast.makeText(getApplicationContext(), "You are still offline, please check your connection", Toast.LENGTH_LONG).show();
            Log.v("Offline", "############################You are not online!!!!");
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        ButterKnife.bind(OfflineActivity.this);
        mAuth = FirebaseAuth.getInstance();
        again.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkConnection();

            }
        });
    }

}
