package com.madcomp19gmail.bouncyball;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class DailyChallenge extends AppCompatActivity {

    private final int background_music_id = R.raw.background_music_2;

    private Dialog customDialog;
    private ImageView day_counter;
    private int id;
    private int consecutive_days;
    private StorageManager prefs;
    private MediaPlayerManager mediaPlayerManager;
    private SoundPoolManager soundPoolManager;
    private Random rand;
    private int soundId;
    private ArrayList<RadioButton> radioButtons;
    private DisplayMetrics metrics;
    private int height, width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_challenge);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SoundPoolManager.initialize(this);
        soundPoolManager = SoundPoolManager.getInstance();
        prefs = StorageManager.getInstance();
        mediaPlayerManager = MediaPlayerManager.getInstance();
        consecutive_days = prefs.getConsecutiveDays();
        setStarImages(consecutive_days);

        /*if (prefs.getMenuMusicSetting()) {
            mediaPlayerManager.loadSound(background_music_id, "Menu");
            mediaPlayerManager.play();
        }*/

        ArrayList<String> sounds = getSoundsNames();
        rand = new Random();
        int chosenSoundIndex = rand.nextInt((sounds.size()));

        radioButtons = new ArrayList<>();
        initializeButtons();
        setupSound(sounds, chosenSoundIndex);
        setupChoices(sounds, chosenSoundIndex);
        customDialog = new Dialog(this);
        metrics = getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    //return a list with every sound file name
    private ArrayList<String> getSoundsNames() {
        ArrayList<String> sounds = new ArrayList<>();
        Field[] fields = R.raw.class.getFields();
        for (Field f : fields) {
            sounds.add(f.getName());
        }

        //remove unwanted files from the list
        //update this if more sounds are added
        sounds.remove("background_music_1");
        sounds.remove("background_music_2");

        return sounds;
    }

    public void onClickPlaySound(View view) {
        soundPoolManager.playSound();
    }

    public void onClickSubmit(View view) {
        int btnId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        String todays_date = prefs.getTodayDateString();
        String last_daily = prefs.getLastDailyChallengeDateString();

        //if the player has played the daily challenge today
        if (todays_date.equals(last_daily)) {
            Toast toast = Toast.makeText(getApplicationContext(), "Come back tomorrow!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (btnId == -1) {
            Toast.makeText(this, "Please select an answer.", Toast.LENGTH_SHORT).show();
        } else {
            //disable all buttons
            ((RadioGroup) findViewById(R.id.radioGroup)).setEnabled(false);
            ((Button) findViewById(R.id.submitButton)).setEnabled(false);

            RadioButton btn = findViewById(btnId);

            if ((int) btn.getTag() == soundId) {
                rightAnswer();
            } else {
                wrongAnswer();
            }
        }
    }

    private void initializeButtons() {
        radioButtons.add((RadioButton) findViewById(R.id.radioButton));
        radioButtons.add((RadioButton) findViewById(R.id.radioButton2));
        radioButtons.add((RadioButton) findViewById(R.id.radioButton3));
        radioButtons.add((RadioButton) findViewById(R.id.radioButton4));
    }

    private void setupChoices(ArrayList<String> list, int index) {
        ArrayList<String> selectedNames = new ArrayList<>();
        String aux = "";

        selectedNames.add(formatStringUnderscore(list.get(index))); //add the selected sound for the challenge

        for (RadioButton btn : radioButtons) {
            btn.setTag(0);
            aux = formatStringUnderscore(list.get(rand.nextInt(list.size())));

            while (selectedNames.contains(aux)) {
                aux = formatStringUnderscore(list.get(rand.nextInt(list.size())));
            }

            selectedNames.add(aux);
            btn.setText(aux);
        }

        int ind = rand.nextInt(4);
        radioButtons.get(ind).setTag(soundId);
        radioButtons.get(ind).setText(formatStringUnderscore(list.get(index)));
    }

    private void setupSound(ArrayList<String> list, int index) {
        int soundId_temp = getResources().getIdentifier(list.get(index),
                "raw", getPackageName());

        soundPoolManager.loadSound(soundId_temp);
        soundId = soundId_temp;
    }

    //turns a string from "sound_name.wav" to "Sound Name"
    private String formatStringUnderscore(String str) {
        String[] temp_1 = str.split("\\.")[0].split("_");
        String temp_2 = "";

        for (String s : temp_1) {
            if (s.length() > 0) {
                temp_2 += s.substring(0, 1).toUpperCase() + s.substring(1) + " ";
            }
        }

        return temp_2.trim();
    }

    //sets the stars to the number of consecutive days
    private void setStarImages(int limit) {
        if (limit > 7 || limit < 1) return;
        for (int i = 1; i <= limit; i++) {
            //change the corresponding images
            id = getResources().getIdentifier("consecutive_day_" + i, "id", getPackageName());
            day_counter = findViewById(id);
            day_counter.setImageResource(R.drawable.selected_icon_vector);
        }
    }

    private void rightAnswer() {
        //update the number of consecutive days in shared prefs
        setStarImages(++consecutive_days);

        String todays_date = prefs.getTodayDateString();
        prefs.setLastDailyChallengeDate(todays_date);

        if (consecutive_days == 7) {
            prefs.setConsecutiveDays(0);
            showFinalPrizeDialog();
        } else {
            prefs.setConsecutiveDays(consecutive_days);
            showSuccessDialog();
        }
    }

    private void wrongAnswer() {
        //update the number of consecutive days in shared prefs
        consecutive_days = 0;
        clearStars();

        String todays_date = prefs.getTodayDateString();

        prefs.setLastDailyChallengeDate(todays_date);
        prefs.setConsecutiveDays(0);

        showErrorDialog();
    }

    private void clearStars() {
        for (int i = 1; i <= 7; i++) {
            //change the corresponding images
            id = getResources().getIdentifier("consecutive_day_" + i, "id", getPackageName());
            day_counter = findViewById(id);
            day_counter.setImageResource(R.drawable.cross_icon_vector);
        }
    }

    private void showSuccessDialog() {
        customDialog.setContentView(R.layout.daily_challenge_custom_dialog);
        Button closeBtn = customDialog.findViewById(R.id.closeRouletteButton2);
        TextView title = customDialog.findViewById(R.id.customDialogTitle);
        TextView text = customDialog.findViewById(R.id.customDialogText);

        text.setText(R.string.success_dialog_message);
        title.setText(R.string.success_dialog_title);
        title.setTextColor(getResources().getColor(android.R.color.holo_green_light));

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        customDialog.getWindow().setLayout(width, height);
        customDialog.setCancelable(false);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    private void showErrorDialog() {
        customDialog.setContentView(R.layout.daily_challenge_custom_dialog);
        Button closeBtn = customDialog.findViewById(R.id.closeRouletteButton2);
        TextView title = customDialog.findViewById(R.id.customDialogTitle);
        TextView text = customDialog.findViewById(R.id.customDialogText);

        text.setText(R.string.error_dialog_message);
        title.setText(R.string.error_dialog_title);
        title.setTextColor(getResources().getColor(android.R.color.holo_red_light));

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        customDialog.getWindow().setLayout(width, height);
        customDialog.setCancelable(false);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    private void showFinalPrizeDialog() {
        customDialog.setContentView(R.layout.dialog_roulette_daily_challenge);
        Button closeBtn = customDialog.findViewById(R.id.closeRouletteButton);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        customDialog.getWindow().setLayout(width, height);
        customDialog.setCancelable(false);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getMenuMusicSetting()) {
            mediaPlayerManager.loadSound(background_music_id, "Menu");
            mediaPlayerManager.play();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!this.isFinishing() && prefs.getMenuMusicSetting())
            mediaPlayerManager.pause();
    }
}
