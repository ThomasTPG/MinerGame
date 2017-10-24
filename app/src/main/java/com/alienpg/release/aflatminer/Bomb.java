package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 27/07/2017.
 */

public class Bomb {

    private boolean exploded = false;
    private Thread bombTimer;

    public Bomb() {

        bombTimer = new Thread()
        {
            @Override
            public void run() {
                try {
                    synchronized (this)
                    {
                        wait(3000);
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                exploded = true;
            }
        };
    }

    public void startTimer()
    {
        bombTimer.start();
    }

    public boolean isExploded()
    {
        return exploded;
    }

}
