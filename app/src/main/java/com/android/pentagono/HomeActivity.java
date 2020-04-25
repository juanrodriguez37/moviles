package com.android.pentagono;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pentagono.Adapter.HomeSliderAdapter;
import com.android.pentagono.Interface.IBannerLoadListener;
import com.android.pentagono.Interface.ILookBookLoadListener;
import com.android.pentagono.Model.Banner;
import com.android.pentagono.Service.PiccassoImageLoadingService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Distribution;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ss.com.bannerslider.Slider;

public class HomeActivity extends AppCompatActivity implements IBannerLoadListener, ILookBookLoadListener {

    private Unbinder unbinder;
    @BindView(R.id.layout_user_information)
    LinearLayout layout_user_information;
    @BindView(R.id.txt_user_name)
    LinearLayout txt_user_name;
    @BindView(R.id.banner_slider)
    Slider banner_slider;
    @BindView(R.id.recycler_look_book)
    RecyclerView recycler_look_book;

    //Firestore
    CollectionReference bannerRef,lookbookRef;

    //Interface
    IBannerLoadListener iBannerLoadListener;
    ILookBookLoadListener iLookBookLoadListener;


    public HomeActivity() {
        bannerRef = FirebaseFirestore.getInstance().collection("/banner");
        lookbookRef = FirebaseFirestore.getInstance().collection("/books");


    }





                             @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.home);

    //Init
        Slider.init(new PiccassoImageLoadingService());
        iBannerLoadListener = this;
        iLookBookLoadListener = this;
        loadBanner();




    }

    private void loadBanner() {
        bannerRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       List<Banner> banners = new ArrayList<>();

                       if(task.isSuccessful()) {
                           for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                                Banner banner = bannerSnapshot.toObject(Banner.class);
                                banners.add(banner);
                           }
                           iBannerLoadListener.onBannerLoadSuccess(banners);
                       }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBannerLoadListener.onBannerLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onBannerLoadSuccess(List<Banner> banners) {
        banner_slider.setAdapter(new HomeSliderAdapter(banners));
    }

    @Override
    public void onBannerLoadFailed(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLookBookLoadSuccess(List<Banner> banners) {

    }

    @Override
    public void onLookBookLoadFailed(String message) {

    }
}
