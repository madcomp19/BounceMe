package com.madcomp19gmail.bouncyball;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

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

    public int getTotalGems(){
        return prefs.getInt(context.getString(R.string.total_gems), 0);
    }

    public void addGems(int gems){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.total_gems), getTotalGems() + gems);
        editor.apply();
    }

    public void takeGems(int gems){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.total_gems), getTotalGems() - gems);
        editor.apply();
    }

    public int getTotalBouncesEver(){
        return prefs.getInt(context.getString(R.string.total_bounces_ever), 0);
    }

    public void setTotalBouncesEver(int touches){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.total_bounces_ever), touches);
        editor.apply();
    }

    public void addCollectedAchievement(int achievement)
    {
        Set<String> set = prefs.getStringSet(context.getString(R.string.collected_achievements), new HashSet<String>());

        if(set.contains(achievement))
            return;

        set.add(achievement + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.owned_skins), set);
        editor.apply();
    }

    public ArrayList<Integer> getCollectedAchievements()
    {
        Set<String> set = prefs.getStringSet(context.getString(R.string.collected_achievements), new HashSet<String>());

        ArrayList<Integer> collected_achievements = new ArrayList<>();

        for(String s : set)
            collected_achievements.add(Integer.parseInt(s));

        return collected_achievements;
    }

    public ArrayList<Integer> getOwnedSkins()
    {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_skins), new HashSet<String>());

        ArrayList<Integer> owned_skins = new ArrayList<>();

        for(String s : set)
            owned_skins.add(Integer.parseInt(s));

        return owned_skins;
    }

    public int getNumberOfOwnedSkins()
    {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_skins), new HashSet<String>());

        return set.size();
    }

    public void addOwnedSkin(int skin)
    {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_skins), new HashSet<String>());

        if(set.contains(skin))
            return;

        set.add(skin + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.owned_skins), set);
        editor.apply();
    }

    public ArrayList<Integer> getOwnedSkinsLabels()
    {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_skins_labels), new HashSet<String>());

        ArrayList<Integer> owned_skins_labels = new ArrayList<>();

        for(String s : set)
            owned_skins_labels.add(Integer.parseInt(s));

        return owned_skins_labels;
    }

    public void addOwnedSkinLabel(int label)
    {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_skins_labels), new HashSet<String>());

        if(set.contains(label))
            return;

        set.add(label + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.owned_skins_labels), set);
        editor.apply();
    }

    public int getSelectedSkinLabel()
    {
        return prefs.getInt(context.getString(R.string.selected_skin_label), 0);
    }

    public void setSelectedSkinLabel(int label)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.selected_skin_label), label);
        editor.apply();
    }

    public int getSelectedSkin()
    {
        return prefs.getInt(context.getString(R.string.selected_skin), 0);
    }

    public void setSelectedSkin(int skin)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.selected_skin), skin);
        editor.apply();
    }

    public ArrayList<Integer> getOwnedTrails()
    {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_trails), new HashSet<String>());

        ArrayList<Integer> owned_trails = new ArrayList<>();

        for(String s : set)
            owned_trails.add(Integer.parseInt(s));

        return owned_trails;
    }

    public ArrayList<Integer> getOwnedSounds()
    {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_sounds), new HashSet<String>());

        ArrayList<Integer> owned_sounds = new ArrayList<>();

        for(String s : set)
            owned_sounds.add(Integer.parseInt(s));

        return owned_sounds;
    }

    public ArrayList<Integer> getOwnedSoundsLabels()
    {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_sounds_labels), new HashSet<String>());

        ArrayList<Integer> owned_sounds_labels = new ArrayList<>();

        for(String s : set)
            owned_sounds_labels.add(Integer.parseInt(s));

        return owned_sounds_labels;
    }


    public void addOwnedTrail(int trail)
    {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_trails), new HashSet<String>());

        if(set.contains(trail))
            return;

        set.add(trail + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.owned_trails), set);
        editor.apply();
    }

    public void addOwnedSound(int sound)
    {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_sounds), new HashSet<String>());

        if(set.contains(sound))
            return;

        set.add(sound + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.owned_sounds), set);
        editor.apply();
    }

    public int getSelectedTrail()
    {
        return prefs.getInt(context.getString(R.string.selected_trail), -10);
    }

    public int getSelectedSound()
    {
        return prefs.getInt(context.getString(R.string.selected_sound), 0);
    }

    public void setSelectedTrail(int trail)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.selected_trail), trail);
        editor.apply();
    }

    public void setSelectedSound(int sound)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.selected_sound), sound);
        editor.apply();
    }

    public ArrayList<Integer> getOwnedTrailsLabels()
    {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_trails_labels), new HashSet<String>());

        ArrayList<Integer> owned_trails_labels = new ArrayList<>();

        for(String s : set)
            owned_trails_labels.add(Integer.parseInt(s));

        return owned_trails_labels;
    }

    public void addOwnedTrailLabel(int trail_label)
    {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_trails_labels), new HashSet<String>());

        if(set.contains(trail_label))
            return;

        set.add(trail_label + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.owned_trails_labels), set);
        editor.apply();
    }

    public void addOwnedSoundLabel(int sound_label)
    {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_sounds_labels), new HashSet<String>());

        if(set.contains(sound_label))
            return;

        set.add(sound_label + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.owned_sounds_labels), set);
        editor.apply();
    }

    public int getSelectedTrailLabel()
    {
        return prefs.getInt(context.getString(R.string.selected_trail_label), 0);
    }

    public int getSelectedSoundLabel()
    {
        return prefs.getInt(context.getString(R.string.selected_sound_label), 0);
    }

    public void setSelectedTrailLabel(int trail)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.selected_trail_label), trail);
        editor.apply();
    }

    public void setSelectedSoundLabel(int sound)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.selected_sound_label), sound);
        editor.apply();
    }

    public boolean getMusicSetting()
    {
        return prefs.getBoolean(context.getString(R.string.musicSetting), true);
    }

    public void setMusicSetting(boolean setting)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(context.getString(R.string.musicSetting), setting);
        editor.apply();
    }

    public int getAdsAvailableToday()
    {
        return prefs.getInt("NumberAdsAvailableToday", 10);
    }

    public void removeAdsAvailableToday()
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("NumberAdsAvailableToday", getAdsAvailableToday() - 1);
        editor.apply();
    }

    public void resetAdAvailableToday()
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("NumberAdsAvailableToday", 10);
        editor.apply();
    }
}
