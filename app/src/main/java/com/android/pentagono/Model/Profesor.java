package com.android.pentagono.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Profesor implements Parcelable {

    private String name,email, password, barberId;
    private Long rating;

    public Profesor() {

    }

    protected Profesor(Parcel in) {
        name = in.readString();
        email = in.readString();
        password = in.readString();
        barberId = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readLong();
        }
    }

    public static final Creator<Profesor> CREATOR = new Creator<Profesor>() {
        @Override
        public Profesor createFromParcel(Parcel in) {
            return new Profesor(in);
        }

        @Override
        public Profesor[] newArray(int size) {
            return new Profesor[size];
        }
    };

    public String getBarberId() {
        return barberId;
    }

    public void setBarberId(String barberId) {
        this.barberId = barberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(barberId);
        if (rating == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(rating);
        }
    }
}
