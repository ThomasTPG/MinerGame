package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 27/07/2017.
 */

public class Timer {

    private Thread timer;

    public Timer(int inputTime)
    {
        final int time = inputTime;
        timer = new Thread()
        {
            @Override
            public void run() {
                try {
                    synchronized (this)
                    {
                        wait(time);
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
    }

    public void start()
    {
        timer.start();
    }


}
