package com.example.thomas.miner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Thomas on 25/01/2017.
 */

public class GameMain extends Fragment implements View.OnTouchListener{

    GameView gameView;
    Thread scoreKeeper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gameview_content, container, false);
        super.onCreate(savedInstanceState);
        gameView = (GameView) view.findViewById(R.id.gameviewsurface);
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
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                gameExit();
                            }
                        });
                    }
                }
            }
        };
        scoreKeeper.start();
        return view;

    }

    private void gameExit()
    {
        final Intent menuIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(menuIntent);
    }

    @Override
    public void onPause() {
        super.onPause();
        gameView.onPause();
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
