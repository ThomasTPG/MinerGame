package com.example.thomas.miner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Thomas on 24/02/2017.
 */

public class CheckExit {

    private Camera camera;
    private int blockSize;
    private int blocksAcross;
    private int blocksPerScreen;

    public CheckExit(Camera c, int blockSize, Context context)
    {
        camera = c;
        this.blockSize = blockSize;
        this.blocksAcross = context.getResources().getInteger(R.integer.blocks_across);
        this.blocksPerScreen = context.getResources().getInteger(R.integer.blocks_per_screen_width);
    }

    public boolean check(Canvas c)
    {
        boolean exited = false;
        if (camera.getCameraY() < 0 && camera.getCameraX()/(double)blockSize > blocksAcross - blocksPerScreen+1)
        {
            Paint p = new Paint(Color.RED);
            c.drawCircle(100,200,200,p);
            if (camera.getCameraX()/(double)blockSize > blocksAcross)
            {
                exited = true;
                c.drawCircle(200,200,200,p);
            }
        }
        return exited;
    }
}
