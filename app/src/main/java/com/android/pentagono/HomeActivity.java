package com.android.pentagono;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pentagono.Adapter.HomeSliderAdapter;
import com.android.pentagono.Fragments.ShoppingFragment;
import com.android.pentagono.Interface.IBannerLoadListener;
import com.android.pentagono.Interface.ILookBookLoadListener;
import com.android.pentagono.Model.Banner;
import com.android.pentagono.Model.User;
import com.android.pentagono.Service.PiccassoImageLoadingService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ss.com.bannerslider.Slider;

public class HomeActivity extends AppCompatActivity  {

    private Unbinder unbinder;


    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;



    BottomSheetDialog bottomSheetDialog;

    CollectionReference userRef;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;



    public HomeActivity() {


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.home);
    ButterKnife.bind(HomeActivity.this);
    mAuth = FirebaseAuth.getInstance();
    userRef = FirebaseFirestore.getInstance().collection("User");
    db = FirebaseFirestore.getInstance();


     //View
     bottomNavigationView.setOnNavigationItemSelectedListener(
             new BottomNavigationView.OnNavigationItemSelectedListener() {
                 Fragment fragment = null;
                 @Override
                 public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    if(menuItem.getItemId() == R.id.action_home)
                        fragment = new HomeFragment();
                    else if(menuItem.getItemId() == R.id.action_shopping)
                        fragment = new ShoppingFragment();
                     return loadFragment(fragment);
                 }
             }
     );

        bottomNavigationView.setSelectedItemId(R.id.action_home);

   }


    private void checkUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
        {

            Log.d("settings", currentUser.getUid());
            Log.d("settings", currentUser.getEmail());

            Map<String,Object> data = new HashMap<>();
            data.put("address","KR 70G 99A29");
            data.put("name","JM");
            data.put("email",currentUser.getEmail());


            db.collection("User")
                    .document(currentUser.getUid())
                    .set(data)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("Settings", "document succesfully written");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Settings",  "error writting document", e);
                }
            });

        }



    }


    private boolean loadFragment(Fragment fragment) {
        if(fragment != null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).commit();
            return true;

        }
        return false;
    }

    private void showUpdateDialog(String phoneNumber) {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setCancelable(false);
        View sheetView = getLayoutInflater().inflate(R.layout.layout_update_information, null);

        Button btn_update = (Button) sheetView.findViewById(R.id.btn_update);
        TextInputEditText edt_name = sheetView.findViewById(R.id.edt_name);
        TextInputEditText edt_address = sheetView.findViewById(R.id.edt_address);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();

    }


}
