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

    private StorageManager(Context context) {
        this.context = context;
        String file_name = context.getString(R.string.shared_prefs_filename);
        prefs = context.getSharedPreferences(file_name, Context.MODE_PRIVATE);
    }

    //call this method in MainMenu class
    public static void initialize(Context context) {
        if (instance == null)
            instance = new StorageManager(context);
    }

    public static StorageManager getInstance() {
        if (instance == null) return null;
        return instance;
    }

    //clears the sharedPreferences file
    public void resetStorage() {
        prefs.edit().clear().commit();
    }

    public String getLastDailyChallengeDateString() {
        return prefs.getString(context.getString(R.string.last_daily_challenge_date), "");
    }

    public int getConsecutiveDays() {
        return prefs.getInt(context.getString(R.string.consecutive_days_daily_challenge), 0);
    }

    public String getTodayDateString() {
        Calendar date = Calendar.getInstance();
        String year = Integer.toString(date.get(Calendar.YEAR));
        String month = Integer.toString(date.get(Calendar.MONTH));
        String day = Integer.toString(date.get(Calendar.DAY_OF_MONTH));
        return day + month + year;
    }

    public void setLastDailyChallengeDate(String day) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(context.getString(R.string.last_daily_challenge_date), day);
        editor.commit();
    }

    public void setConsecutiveDays(int days) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.consecutive_days_daily_challenge), days);
        editor.commit();
    }

    public int getTotalBounces() {
        return prefs.getInt(context.getString(R.string.total_touches), 0);
    }

    public void setTotalBounces(int touches) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.total_touches), touches);
        editor.commit();
    }

    public int getTotalGems() {
        return prefs.getInt(context.getString(R.string.total_gems), 0);
    }

    public void addGems(int gems) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.total_gems), getTotalGems() + gems);
        editor.commit();
    }

    public void takeGems(int gems) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.total_gems), getTotalGems() - gems);
        editor.commit();
    }

    public void setTotalGems(int gems) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.total_gems), gems);
        editor.commit();
    }

    public int getTotalBouncesEver() {
        return prefs.getInt(context.getString(R.string.total_bounces_ever), 0);
    }

    public void setTotalBouncesEver(int touches) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.total_bounces_ever), touches);
        editor.commit();
    }

    //Achievements
    //region
    public void addCollectedAchievement(int achievement) {
        Set<String> set = prefs.getStringSet(context.getString(R.string.collected_achievements), new HashSet<String>());

        if (set.contains(Integer.toString(achievement)))
            return;

        set.add(achievement + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.collected_achievements), set);
        editor.commit();
    }

    public ArrayList<Integer> getCollectedAchievements() {
        Set<String> set = prefs.getStringSet(context.getString(R.string.collected_achievements), new HashSet<String>());

        ArrayList<Integer> collected_achievements = new ArrayList<>();

        for (String s : set)
            collected_achievements.add(Integer.parseInt(s));

        return collected_achievements;
    }

    public int getNumberOfCollectedAchievements() {
        Set<String> set = prefs.getStringSet(context.getString(R.string.collected_achievements), new HashSet<String>());

        return set.size();
    }
    //endregion

    public ArrayList<Integer> getOwnedSkins() {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_skins), new HashSet<String>());

        ArrayList<Integer> owned_skins = new ArrayList<>();

        for (String s : set)
            owned_skins.add(Integer.parseInt(s));

        return owned_skins;
    }

    public int getNumberOfOwnedSkins() {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_skins), new HashSet<String>());

        return set.size();
    }

    public void addOwnedSkin(int skin) {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_skins), new HashSet<String>());

        if (set.contains(Integer.toString(skin)))
            return;

        set.add(skin + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.owned_skins), set);
        editor.commit();
    }

    public ArrayList<Integer> getOwnedSkinsLabels() {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_skins_labels), new HashSet<String>());

        ArrayList<Integer> owned_skins_labels = new ArrayList<>();

        for (String s : set)
            owned_skins_labels.add(Integer.parseInt(s));

        return owned_skins_labels;
    }

    public void addOwnedSkinLabel(int label) {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_skins_labels), new HashSet<String>());

        if (set.contains(Integer.toString(label)))
            return;

        set.add(label + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.owned_skins_labels), set);
        editor.commit();
    }

    public int getSelectedSkinLabel() {
        return prefs.getInt(context.getString(R.string.selected_skin_label), 0);
    }

    public void setSelectedSkinLabel(int label) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.selected_skin_label), label);
        editor.commit();
    }

    public int getSelectedSkin() {
        return prefs.getInt(context.getString(R.string.selected_skin), 0);
    }

    public void setSelectedSkin(int skin) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.selected_skin), skin);
        editor.commit();
    }

    public ArrayList<Integer> getOwnedTrails() {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_trails), new HashSet<String>());

        ArrayList<Integer> owned_trails = new ArrayList<>();

        for (String s : set)
            owned_trails.add(Integer.parseInt(s));

        return owned_trails;
    }

    public int getNumberOfOwnedTrails() {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_trails), new HashSet<String>());

        return set.size();
    }

    public ArrayList<Integer> getOwnedSounds() {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_sounds), new HashSet<String>());

        ArrayList<Integer> owned_sounds = new ArrayList<>();

        for (String s : set)
            owned_sounds.add(Integer.parseInt(s));

        return owned_sounds;
    }

    public int getNumberOfOwnedSounds() {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_sounds), new HashSet<String>());

        return set.size();
    }

    public ArrayList<Integer> getOwnedSoundsLabels() {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_sounds_labels), new HashSet<String>());

        ArrayList<Integer> owned_sounds_labels = new ArrayList<>();

        for (String s : set)
            owned_sounds_labels.add(Integer.parseInt(s));

        return owned_sounds_labels;
    }

    public void addOwnedTrail(int trail) {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_trails), new HashSet<String>());

        if (set.contains(Integer.toString(trail)))
            return;

        set.add(trail + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.owned_trails), set);
        editor.commit();
    }

    public void addOwnedSound(int sound) {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_sounds), new HashSet<String>());

        if (set.contains(Integer.toString(sound)))
            return;

        set.add(sound + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.owned_sounds), set);
        editor.commit();
    }

    public int getSelectedTrail() {
        return prefs.getInt(context.getString(R.string.selected_trail), -10);
    }

    public int getSelectedSound() {
        return prefs.getInt(context.getString(R.string.selected_sound), 0);
    }

    public void setSelectedTrail(int trail) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.selected_trail), trail);
        editor.commit();
    }

    public void setSelectedSound(int sound) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.selected_sound), sound);
        editor.commit();
    }

    public ArrayList<Integer> getOwnedTrailsLabels() {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_trails_labels), new HashSet<String>());

        ArrayList<Integer> owned_trails_labels = new ArrayList<>();

        for (String s : set)
            owned_trails_labels.add(Integer.parseInt(s));

        return owned_trails_labels;
    }

    public void addOwnedTrailLabel(int trail_label) {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_trails_labels), new HashSet<String>());

        if (set.contains(Integer.toString(trail_label)))
            return;

        set.add(trail_label + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.owned_trails_labels), set);
        editor.commit();
    }

    public void addOwnedSoundLabel(int sound_label) {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_sounds_labels), new HashSet<String>());

        if (set.contains(Integer.toString(sound_label)))
            return;

        set.add(sound_label + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.owned_sounds_labels), set);
        editor.commit();
    }

    public int getSelectedTrailLabel() {
        return prefs.getInt(context.getString(R.string.selected_trail_label), 0);
    }

    public int getSelectedSoundLabel() {
        return prefs.getInt(context.getString(R.string.selected_sound_label), 0);
    }

    public void setSelectedTrailLabel(int trail) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.selected_trail_label), trail);
        editor.commit();
    }

    public void setSelectedSoundLabel(int sound) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.selected_sound_label), sound);
        editor.commit();
    }

    //Backgrounds
    //region

    public void addOwnedBackground(int background) {
        Set<String> set = prefs.getStringSet("OwnedBackgrounds", new HashSet<String>());

        if (set.contains(Integer.toString(background)))
            return;

        set.add(background + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet("OwnedBackgrounds", set);
        editor.commit();
    }

    public ArrayList<Integer> getOwnedBackgrounds() {
        Set<String> set = prefs.getStringSet("OwnedBackgrounds", new HashSet<String>());

        ArrayList<Integer> owned_backgrounds = new ArrayList<>();

        for (String s : set)
            owned_backgrounds.add(Integer.parseInt(s));

        return owned_backgrounds;
    }

    public int getNumberOfOwnedBackgrounds() {
        Set<String> set = prefs.getStringSet("OwnedBackgrounds", new HashSet<String>());

        return set.size();
    }

    public int getSelectedBackground() {
        return prefs.getInt("SelectedBackground", 0);
    }

    public void setSelectedBackground(int background) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("SelectedBackground", background);
        editor.commit();
    }

    public ArrayList<Integer> getOwnedBackgroundsLabels() {
        Set<String> set = prefs.getStringSet("OwnedBackgroundsLabels", new HashSet<String>());

        ArrayList<Integer> owned_backgrounds_labels = new ArrayList<>();

        for (String s : set)
            owned_backgrounds_labels.add(Integer.parseInt(s));

        return owned_backgrounds_labels;
    }

    public void addOwnedBackgroundLabel(int background_label) {
        Set<String> set = prefs.getStringSet("OwnedBackgroundsLabels", new HashSet<String>());

        if (set.contains(Integer.toString(background_label)))
            return;

        set.add(background_label + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet("OwnedBackgroundsLabels", set);
        editor.commit();
    }

    public int getSelectedBackgroundLabel() {
        return prefs.getInt("SelectedBackgroundLabel", 0);
    }

    public void setSelectedBackgroundLabel(int background) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("SelectedBackgroundLabel", background);
        editor.commit();
    }

    //endregion


    public boolean getMenuMusicSetting() {
        return prefs.getBoolean(context.getString(R.string.menuMusicSetting), true);
    }

    public void setMenuMusicSetting(boolean setting) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(context.getString(R.string.menuMusicSetting), setting);
        editor.commit();
    }

    public boolean getShopMusicSetting() {
        return prefs.getBoolean(context.getString(R.string.shopMusicSetting), true);
    }

    public void setShopMusicSetting(boolean setting) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(context.getString(R.string.shopMusicSetting), setting);
        editor.commit();
    }

    public int getGameMusicSetting() {
        return prefs.getInt(context.getString(R.string.gameMusicSetting), 5);
    }

    public void setGameMusicSetting(int setting) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.gameMusicSetting), setting);
        editor.commit();
    }

    public int getAdsAvailableToday() {
        return prefs.getInt("NumberAdsAvailableToday", 10);
    }

    public void removeAdsAvailableToday() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("NumberAdsAvailableToday", getAdsAvailableToday() - 1);
        editor.commit();
    }

    public String getLastAdDateString() {
        return prefs.getString("LastAdDate", "");
    }

    public void setLastAdDate(String day) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LastAdDate", day);
        editor.commit();
    }

    public void resetAdAvailableToday() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("NumberAdsAvailableToday", 10);
        editor.commit();
    }

    public int getBallPageNumber() {
        return prefs.getInt("BallPageNumber", 0);
    }

    public void setBallPageNumber(int n) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("BallPageNumber", n);
        editor.commit();
    }

    public int getSkinPageNumber() {
        return prefs.getInt("SkinPageNumber", 0);
    }

    public void setSkinPageNumber(int n) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("SkinPageNumber", n);
        editor.commit();
    }

    public void setActiveBoost(int boost) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.selected_boost), boost);
        editor.commit();
    }

    public int getActiveBoost() {
        return prefs.getInt(context.getString(R.string.selected_boost), 1);
    }

    public void setActiveBoostTime(long time) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(context.getString(R.string.selected_boost_time), time);
        editor.commit();
    }

    public long getActiveBoostTime() {
        return prefs.getLong(context.getString(R.string.selected_boost_time), 0);
    }

    public void setActiveBoostLabel(int boost) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.selected_boost_label), boost);
        editor.commit();
    }

    public int getActiveBoostLabel() {
        return prefs.getInt(context.getString(R.string.selected_boost_label), 0);
    }

    //Balls
    //region
    public void setSelectedBall(String ball) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(context.getString(R.string.selected_ball), ball);
        editor.commit();
    }

    public void setSelectedBallLabel(int label) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.selected_ball_label), label);
        editor.commit();
    }

    public String getSelectedBall() {
        return prefs.getString(context.getString(R.string.selected_ball), "0_0_0_0");
    }

    public int getSelectedBallLabel() {
        return prefs.getInt(context.getString(R.string.selected_ball_label), 0);
    }

    public void addOwnedBall(String ball) {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_balls), new HashSet<String>());

        if (set.contains(ball))
            return;

        set.add(ball);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.owned_balls), set);
        editor.commit();
    }

    public void addOwnedBallLabel(int label) {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_balls_labels), new HashSet<String>());

        if (set.contains(Integer.toString(label)))
            return;

        set.add(label + "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(context.getString(R.string.owned_balls_labels), set);
        editor.commit();
    }

    public ArrayList<String> getOwnedBalls() {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_balls), new HashSet<String>());

        ArrayList<String> owned_balls = new ArrayList<>();

        for (String s : set)
            owned_balls.add(s);

        return owned_balls;
    }

    public ArrayList<Integer> getOwnedBallsLabels() {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_balls_labels), new HashSet<String>());

        ArrayList<Integer> owned_balls_labels = new ArrayList<>();

        for (String s : set)
            owned_balls_labels.add(Integer.parseInt(s));

        return owned_balls_labels;
    }

    public int getNumberOfOwnedBalls() {
        Set<String> set = prefs.getStringSet(context.getString(R.string.owned_balls), new HashSet<String>());

        return set.size();
    }
    //endregion

    //No Ads
    //region
    public void setNoAds(boolean bool) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("NoAds", bool);
        editor.commit();
    }

    public boolean getNoAds() {
        return prefs.getBoolean("NoAds", false);
    }
    //endregion
}
