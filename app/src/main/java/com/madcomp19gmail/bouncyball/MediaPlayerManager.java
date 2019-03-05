package com.madcomp19gmail.bouncyball;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerManager
{
    private static MediaPlayerManager instance = null;
    private static MediaPlayer mediaPlayer;
    private static int sound_id;
    private static Context context;

    private MediaPlayerManager()
    {
        mediaPlayer = MediaPlayer.create(context, sound_id);
        mediaPlayer.setLooping(true);
    }

    public static void initialize(Context aContext, int aSound)
    {
        context = aContext;
        sound_id = aSound;
    }

    public static MediaPlayerManager getInstance()
    {
        if(instance == null)
        {
            instance = new MediaPlayerManager();
            return instance;
        }
        else
            return instance;
    }

    public static void loadSound(int id)
    {
        if(sound_id != id)
        {
            sound_id = id;
            mediaPlayer = MediaPlayer.create(context, sound_id);
        }
    }

    public static void play()
    {
        mediaPlayer.start();
    }

    public static void pause()
    {
        mediaPlayer.pause();
    }

    public static void stop()
    {
        mediaPlayer.stop();
    }

    public static void changeVolume(float volume)
    {
        mediaPlayer.setVolume(volume, volume);
    }
}
