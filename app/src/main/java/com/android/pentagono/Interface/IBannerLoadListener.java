package com.android.pentagono.Interface;

import com.android.pentagono.Model.Banner;

import java.util.List;

public interface  IBannerLoadListener {


    void onBannerLoadSuccess(List<Banner> banners);

    void onBannerLoadFailed(String message);


}
