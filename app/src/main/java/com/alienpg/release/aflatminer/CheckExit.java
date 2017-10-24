package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Thomas on 24/02/2017.
 */

public class CheckExit {

    private Camera camera;
    private int blockSize;
    private int blocksAcross;
    private int blocksPerScreen;
    Bitmap exitBitmap;
    private int screenWidth;
    private int screenHeight;

    public CheckExit(Camera c, int blockSize, Context context, int gameHeight, int gameWidth)
    {
        camera = c;
        this.blockSize = blockSize;
        this.blocksAcross = context.getResources().getInteger(R.integer.blocks_across);
        this.blocksPerScreen = context.getResources().getInteger(R.integer.blocks_per_screen_width);
        exitBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.exitsign);
        screenHeight = gameHeight;
        screenWidth = gameWidth;
    }

    public boolean check(Canvas c)
    {
        boolean exited = false;
        if (camera.getCameraY() < 0 && camera.getCameraX()/(double)blockSize > blocksAcross - blocksPerScreen+1)
        {
            Rect exitRect = new Rect(0,0,screenWidth/4,screenHeight/10);
            c.drawBitmap(exitBitmap,null,exitRect,null);
            if (camera.getCameraY() < 0 && camera.getCameraX()/(double)blockSize > blocksAcross)
            {
                exited = true;
            }
        }
        return exited;
    }
}
