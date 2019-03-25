package com.spencerstudios.simplenote;

import android.app.Application;

import com.spencerstudios.simplenote.ObjectBox;

/**
 * Created by User on 19/03/2019.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
    }
}
