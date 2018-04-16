package com.dalimao.mytaxi;

import android.app.Activity;
import android.app.Application;

import java.util.Set;


public class App extends Application {
    private static App INSTANCE;
    private Set<Activity> mActivities;

    public static synchronized App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

}
