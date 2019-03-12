package com.madcomp19gmail.bouncyball;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class MediaPlayerManager
{
    private static AudioManager audioManager;
    private static AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;
    private static MediaPlayerManager instance = null;
    private static MediaPlayer mediaPlayer;
    private static int sound_id;
    private static Context context;

    private StorageManager storageManager;

    private MediaPlayerManager()
    {
        storageManager = StorageManager.getInstance();

        mediaPlayer = MediaPlayer.create(context, sound_id);
        mediaPlayer.setVolume(1, 1);

        audioManager = (AudioManager) context
                .getSystemService(Context.AUDIO_SERVICE);

        mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener()
        {

            @Override
            public void onAudioFocusChange ( int focusChange){
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        if(storageManager.getMusicSetting())
                            play();
                        break;
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                        // You have audio focus for a short time
                        if(storageManager.getMusicSetting())
                            play();
                        break;
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                        // Play over existing audio
                        if(storageManager.getMusicSetting())
                        {
                            changeVolume(1);
                            play();
                        }
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        if(storageManager.getMusicSetting())
                            pause();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        // Temporary loss of audio focus - expect to get it back - you can keep your resources around
                        if(storageManager.getMusicSetting())
                            pause();
                        break;
                }
            }
        };
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
        // Request audio focus for play back
        int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
        {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
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
