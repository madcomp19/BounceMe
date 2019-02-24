package com.madcomp19gmail.bouncyball;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

public class StorageManager {

    private static StorageManager instance = null;
    private static Context context;
    private static SharedPreferences prefs;

    private StorageManager(Context context){
        this.context = context;
        String file_name = context.getString(R.string.shared_prefs_filename);
        prefs = context.getSharedPreferences(file_name, Context.MODE_PRIVATE);
    }

    //call this method in MainMenu class
    public static void initialize(Context context){
        if(instance == null)
            instance = new StorageManager(context);
    }

    public static StorageManager getInstance(){
        if(instance == null) return null;
        return instance;
    }

    //clears the sharedPreferences file
    public void resetStorage(){
        prefs.edit().clear().apply();
    }

    public String getLastDailyChallengeDateString(){
        return prefs.getString(context.getString(R.string.last_daily_challenge_date), "");
    }

    public int getConsecutiveDays(){
        return prefs.getInt(context.getString(R.string.consecutive_days_daily_challenge), 0);
    }

    public String getTodayDateString(){
        Calendar date = Calendar.getInstance();
        String year = Integer.toString(date.get(Calendar.YEAR));
        String month = Integer.toString(date.get(Calendar.MONTH));
        String day = Integer.toString(date.get(Calendar.DAY_OF_MONTH));
        return day + month + year;
    }

    public void setLastDailyChallengeDate(String day){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(context.getString(R.string.last_daily_challenge_date), day);
        editor.apply();
    }

    public void setConsecutiveDays(int days){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.consecutive_days_daily_challenge), days);
        editor.apply();
    }

    public int getTotalTouches(){
        return prefs.getInt(context.getString(R.string.total_touches), 0);
    }

    public void setTotalTouches(int touches){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.total_touches), touches);
        editor.apply();
    }
}
