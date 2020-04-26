package com.android.pentagono.Interface;

import com.android.pentagono.Model.Course;

import java.util.List;

public interface IBranchLoadListener {


    void onBranchLoadSuccess(List<Course> coursesList);
    void onBranchLoadFailed(String message);
}
