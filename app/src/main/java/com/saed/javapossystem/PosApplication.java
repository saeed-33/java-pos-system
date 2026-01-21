package com.saed.javapossystem;

import android.app.Application;

import com.saed.javapossystem.di.AppContainer;

public class PosApplication extends Application {

    // This is the single instance of your Dependency Container
    public AppContainer appContainer;

    @Override
    public void onCreate() {
        super.onCreate();
        appContainer = new AppContainer(this);
    }
}