package com.sikaseal.sika_engine.common;

public class SwimCoach implements Coach {

    public SwimCoach(){
        System.out.println("In constructor:  + " + getClass().getSimpleName());
    }
    @Override
    public String getDailyWorkout() {
        return "Swim 1000 meters as a warp up";
    }
}
