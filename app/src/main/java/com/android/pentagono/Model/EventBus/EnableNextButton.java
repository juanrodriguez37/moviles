package com.android.pentagono.Model.EventBus;

import com.android.pentagono.Model.Course;
import com.android.pentagono.Model.Profesor;
import com.android.pentagono.Model.TimeSlot;

public class EnableNextButton {

    private int step;
    private Profesor profesor;
    private Course course;
    private int timeslot;

    public EnableNextButton(int step, Profesor profesor) {
        this.step = step;
        this.profesor = profesor;
    }

    public EnableNextButton(int step, int timeslot) {
        this.step = step;
        this.timeslot = timeslot;
    }

    public EnableNextButton(int step, Course course) {
        this.step = step;
        this.course = course;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(int timeslot) {
        this.timeslot = timeslot;
    }
}
