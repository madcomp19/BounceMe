package com.madcomp19gmail.bouncyball;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPoolManager
{
    private static SoundPoolManager instance = null;
    private static Context context;

    private SoundPool soundPool;
    private int sound;

    private SoundPoolManager()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        else
        {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
    }

    /* Ideally we would pass as a parameter the sound id that we want to play
       Tried it but it didn't play any sound, needs debugging

    public void loadSound(int id)
    {
        sound = soundPool.load(context, id, 1);
    }

    public void playSound(int id)
    {
        loadSound(id);
        soundPool.play(sound, 1, 1, 0, 0, 1);
    }
    */


    private void loadSound()
    {
        sound = soundPool.load(context, R.raw.bubble, 1);
    }

    public static synchronized SoundPoolManager getInstance()
    {
        if(instance == null)
            instance = new SoundPoolManager();

        return instance;
    }

    public static void initialize(Context aContext)
    {
        SoundPoolManager soundPoolManager = getInstance();
        context = aContext;
        soundPoolManager.loadSound();
    }

    public void playSound()
    {
        soundPool.play(sound, 1, 1, 0, 0, 1);
    }
}
