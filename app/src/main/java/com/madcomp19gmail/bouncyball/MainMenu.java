package com.madcomp19gmail.bouncyball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainMenu extends AppCompatActivity {

    private static int touches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        touches = 0;
    }

    public static void addTouch()
    {
        touches++;
    }

    public static int getTouches()
    {
        return touches;
    }

    public void playGame(View view)
    {
        Intent intent = new Intent(this, GameWorld.class);
        startActivity(intent);
    }
}
