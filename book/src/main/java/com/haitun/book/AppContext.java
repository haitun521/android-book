package com.haitun.book;

import android.app.Application;
import android.content.Context;

/**
 * Created by 笨笨 on 2016/4/27.
 */
public class AppContext extends Application{
    private static Context ourInstance ;

    public static Context getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        ourInstance=getApplicationContext();
        super.onCreate();

    }
}
