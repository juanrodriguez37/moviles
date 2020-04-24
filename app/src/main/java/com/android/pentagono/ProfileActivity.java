package com.android.pentagono;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class ProfileActivity extends AppCompatActivity {

private static final String TAG = "ProfileActivity";
ImageView profileImageView;
TextInputEditText displayName;
Button updateProfile;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_profile);

        profileImageView = findViewById(R.id.imageView_profile);
        displayName = findViewById(R.id.display_name_text);
        updateProfile = findViewById(R.id.button_update);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            Log.d(TAG, "onCreate:" + user.getDisplayName());
            if(user.getDisplayName() != null)
            {
                displayName.setText(user.getDisplayName());
                displayName.setSelection(user.getDisplayName().length());
            }
        }


    }

    public void updateProfile(View view) {
        String display = displayName.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().
                setDisplayName(display).build() ;
        user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivity.this, "Succesfully updated profile", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            Log.e(TAG,"onFailure:", e.getCause());
            }
        });
    }
}