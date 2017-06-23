package com.example.thomas.miner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Thomas on 25/01/2017.
 */

public class GameMain extends Activity implements View.OnTouchListener{

    GameView gameView;
    Thread scoreKeeper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameview_content);
        gameView = (GameView) findViewById(R.id.gameviewsurface);
        gameView.setOnTouchListener(this);

        scoreKeeper = new Thread() {
            @Override
            public void run() {
                while (gameView.isRunning()) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    /*
                    runOnUiThread(new Runnable() {
                        public void run() {
                            updateScore();
                        }
                    });
                    */
                    if (gameView.isGameOver()) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                gameExit();
                            }
                        });
                    }
                }
            }
        };
        scoreKeeper.start();


    }

    private void gameExit()
    {
        final Intent menuIntent = new Intent(this, MainActivity.class);
        startActivity(menuIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.onCreate(null);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(v.getId()){
            case (R.id.gameviewsurface):
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    gameView.requestAction(event);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    gameView.stopMotion(event);
                }
                else
                {
                    gameView.motionEvent(event);
                }
                break;
        }
        return true;
    }
}
