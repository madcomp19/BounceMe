package com.madcomp19gmail.bouncyball;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerManager
{
    private static MediaPlayerManager instance = null;
    private static MediaPlayer mediaPlayer;

    private MediaPlayerManager(Context context)
    {
        mediaPlayer = MediaPlayer.create(context, R.raw.background_music_2);
        mediaPlayer.setLooping(true);
    }

    public static MediaPlayerManager getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new MediaPlayerManager(context);
            return instance;
        }
        else
            return instance;
    }

    public static void loadSound(int id)
    {
        //
    }
}
