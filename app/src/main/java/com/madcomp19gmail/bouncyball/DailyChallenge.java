package com.madcomp19gmail.bouncyball;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
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
    private int height, width;
    private long degrees = 0;
    private int n_prizes = 12;
    private ImageView roulette;
    private Button spin_button;

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
        mediaPlayerManager.changeVolume(0.3f);
        consecutive_days = prefs.getConsecutiveDays();
        setConsecutiveImages(consecutive_days);

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
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;


        //REMOVE THIS LATER*********************************************
        //testing roulette mini game
        ImageView title = (ImageView) findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFinalPrizeDialog();
            }
        });
        //REMOVE THIS LATER*********************************************
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
    private void setConsecutiveImages(int limit) {
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
        setConsecutiveImages(++consecutive_days);

        String todays_date = prefs.getTodayDateString();
        prefs.setLastDailyChallengeDate(todays_date);

        if (consecutive_days == 7) {
            prefs.setConsecutiveDays(0);
            showFinalPrizeDialog();
        } else {
            prefs.setConsecutiveDays(consecutive_days);
            getPrize(consecutive_days, false);
            showSuccessDialog();
        }
    }

    private void wrongAnswer() {
        //update the number of consecutive days in shared prefs
        consecutive_days = 0;
        clearConsecutiveDays();

        String todays_date = prefs.getTodayDateString();

        prefs.setLastDailyChallengeDate(todays_date);
        prefs.setConsecutiveDays(0);

        showErrorDialog();
    }

    private void clearConsecutiveDays() {
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
        text.setMaxWidth((int) (0.6 * width));
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
        text.setMaxWidth((int) (0.6 * width));
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

    //region - roulette mini game
    private void showFinalPrizeDialog() {
        customDialog.setContentView(R.layout.dialog_roulette_daily_challenge);

        roulette = (ImageView) customDialog.findViewById(R.id.roulette);

        //this makes the image get loaded 30 degrees off
        //Glide.with(this).load(R.drawable.roulette_wheel).into(roulette);

        ImageView indicator = (ImageView) customDialog.findViewById(R.id.roulette_indicator);
        spin_button = (Button) customDialog.findViewById(R.id.spin_button);

        //set roulette and indicator sizes
        roulette.getLayoutParams().height = (int) (0.6 * width);
        roulette.getLayoutParams().width = (int) (0.6 * width);
        indicator.getLayoutParams().height = (int) (0.1 * width);
        indicator.getLayoutParams().width = (int) (0.1 * width);

        spin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSpin();
            }
        });

        customDialog.getWindow().setLayout(width, height);
        customDialog.setCancelable(false);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    private void onClickSpin() {
        int rand = new Random().nextInt(360) + 3600;
        RotateAnimation rotateAnimation = new RotateAnimation((float) degrees, (float)
                (degrees + ((long) rand)), 1, 0.5f, 1, 0.5f);

        degrees += ((long) rand) % 360;
        rotateAnimation.setDuration((long) rand);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //disable the spin button
                spin_button.setEnabled(false);
                spin_button.setBackgroundResource(R.drawable.rounded_button6_vector);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //calculate the final prize on the wheel
                int prize = (int) (((double) n_prizes) - Math.floor(((double) degrees % 360)
                        / (360.0d / (double) n_prizes)));
                getPrize(prize, true);

                //enable the spin button and change it to finish activity
                spin_button.setEnabled(true);
                spin_button.setBackgroundResource(R.drawable.rounded_button_vector);
                spin_button.setText(R.string.ok);
                spin_button.setOnClickListener(null);
                spin_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        roulette.setAnimation(rotateAnimation);
        roulette.startAnimation(rotateAnimation);
    }

    //days 1 to 6: 5 gems per daily challenge
    //day 7: roulette - 10, 15, 20, 50 gems, 1000, 2000, 5000, 10000 coins,
    //2x, 5x, 10x, 50x 10min boosts
    private void getPrize(int prize_id, boolean isFinalPrize) {

        if (!isFinalPrize) {
            //daily prize
            prefs.setTotalGems(prefs.getTotalGems() + 5);
            Toast.makeText(this, "You have won 5 gems!", Toast.LENGTH_LONG).show();
        } else {
            //roulette prizes
            switch (prize_id) {
                case 1: //5000 coins
                    setCoins(5000);
                    break;
                case 2: //25 gems
                    setGems(25);
                    break;
                case 3: //50x boost
                    setBoost(50);
                    break;
                case 4: //10000 coins
                    setCoins(10000);
                    break;
                case 5: //15 gems
                    setGems(15);
                    break;
                case 6: //2x boost
                    setBoost(2);
                    break;
                case 7: //20000 coins
                    setCoins(20000);
                    break;
                case 8: //10 gems
                    setGems(10);
                    break;
                case 9: //5x boost
                    setBoost(5);
                    break;
                case 10: //1000 coins
                    setCoins(1000);
                    break;
                case 11: //50 gems
                    setGems(50);
                    break;
                case 12: //10x boost
                    setBoost(10);
                    break;
                default: //1000 coins
                    setCoins(1000);
                    break;
            }
        }
    }

    private void setBoost(int boost) {
        //If there is a boost already active, give gems instead
        if (Calendar.getInstance().getTimeInMillis() < prefs.getActiveBoostTime()) {
            int gems;

            switch (boost) {
                case 2:
                    gems = 15;
                    break;
                case 5:
                    gems = 30;
                    break;
                case 10:
                    gems = 50;
                    break;
                case 50:
                    gems = 100;
                    break;
                default:
                    gems = 15;
                    break;
            }

            prefs.setTotalGems(prefs.getTotalGems() + gems);
            Toast.makeText(this, "There is an active boost. You have received " + gems + " gems instead.", Toast.LENGTH_LONG).show();
        } else { //activate the boost

            int time = 10;
            int seconds = 0;
            String label = "x" + boost + "_10m" + "_Label"; //e.g. x50_10min_Label

            int label_id = this.getResources().getIdentifier(label, "id", getPackageName());

            prefs.setActiveBoost(boost);
            prefs.setActiveBoostLabel(label_id);
            prefs.setActiveBoostTime(Calendar.getInstance().getTimeInMillis() + time * 60000);

            Toast.makeText(this, "Boost is now active", Toast.LENGTH_LONG).show();
        }
    }

    private void setCoins(int coins) {
        prefs.setTotalBounces(prefs.getTotalBounces() + coins);
        Toast.makeText(this, "You have won " + coins + " coins", Toast.LENGTH_LONG).show();
    }

    private void setGems(int gems) {
        prefs.setTotalGems(prefs.getTotalGems() + gems);
        Toast.makeText(this, "You have won " + gems + " gems", Toast.LENGTH_LONG).show();
    }
    //endregion

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
