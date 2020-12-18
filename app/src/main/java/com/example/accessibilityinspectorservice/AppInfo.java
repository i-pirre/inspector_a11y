package com.example.accessibilityinspectorservice;

import android.graphics.drawable.Drawable;

public class AppInfo {
    public Drawable icon;
    public String applicationName;
    public String packageName;

    public AppInfo(){
        super();
    }

    public AppInfo(Drawable icon, String applicationName, String packageName){
        super();
        this.icon = icon;
        this.applicationName = applicationName;
        this.packageName = packageName;
    }

}
