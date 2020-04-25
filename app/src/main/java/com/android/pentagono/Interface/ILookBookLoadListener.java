package com.android.pentagono.Interface;

import com.android.pentagono.Model.Banner;

import java.util.List;

public interface ILookBookLoadListener {

    void onLookBookLoadSuccess(List<Banner> banners);

    void onLookBookLoadFailed(String message);

}
