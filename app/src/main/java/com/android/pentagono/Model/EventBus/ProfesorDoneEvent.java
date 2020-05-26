package com.android.pentagono.Model.EventBus;

import com.android.pentagono.Model.Profesor;

import java.util.List;

public class ProfesorDoneEvent {

    private List<Profesor> profesorList;

    public ProfesorDoneEvent(List<Profesor> profesorList) {
        this.profesorList = profesorList;
    }

    public List<Profesor> getProfesorList() {
        return profesorList;
    }

    public void setProfesorList(List<Profesor> profesorList) {
        this.profesorList = profesorList;
    }
}
