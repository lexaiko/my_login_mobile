package com.example.lesson3.data;

import android.content.Context;

import androidx.room.Room;


public class AppDbProvider {

    private static  DTSAppDatabase instance;
    public  static  DTSAppDatabase getInstance (Context context)
    {
        if(AppDbProvider.instance == null)
        {
            AppDbProvider.instance = Room.databaseBuilder(
                    context, DTSAppDatabase.class, "dtsapp.db"
            ).allowMainThreadQueries().build();
        }
        return AppDbProvider.instance;
    }
}