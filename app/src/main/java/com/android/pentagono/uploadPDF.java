package com.android.pentagono;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class uploadPDF extends AppCompatActivity {

    public String name;
    public String url;

    public uploadPDF() {
    }

    public uploadPDF( String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName()
    {
        return name;
    }

    public String getUrl()
    {
        return url;
    }
}