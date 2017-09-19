package com.example.thomas.miner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Thomas on 18/09/2017.
 */

public class SkyObject {

    Bitmap objectBitmap;
    int height;
    int speed = 3;
    int LHSObject;
    int RHSObject;
    int topObject;
    int bottomObject;
    int objectWidth;
    int objectHeight;
    int LHSSky;
    int RHSSky;
    int topSky;
    int bottomSky;
    int screenHeight;
    int screenWidth;
    int blockSize;

    public SkyObject(Rect skyBoundaries, int screenHeight, int screenWidth, Context context, int blockSize)
    {
        LHSSky = skyBoundaries.left;
        RHSSky = skyBoundaries.right;
        topSky = skyBoundaries.top;
        bottomSky = skyBoundaries.bottom;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.blockSize = blockSize;
        getRandomHeight();
        objectBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud1);
    }

    public void getRandomHeight()
    {
        LHSObject = RHSSky;
        RHSObject = RHSSky + (int) Math.ceil(((0.1 +Math.random()) * (RHSSky - LHSSky))/2);
        objectWidth = RHSObject - LHSObject;
        bottomObject =   (int) Math.ceil((Math.random() * topSky * 0.5) - blockSize);
        objectHeight = (int) Math.ceil((Math.random() + 0.5) * 2 * blockSize);
        topObject = bottomObject - objectHeight;
    }

    public void setInitialLocation()
    {
        LHSObject = LHSSky + (int) Math.ceil(Math.random() * (RHSSky - LHSSky));
        RHSObject = LHSObject + objectWidth;
    }


    public void getBitmap()
    {

    }

    public void drawArt(Canvas canvas, Camera camera)
    {
        LHSObject -= speed;
        RHSObject -= speed;
        if (RHSObject < LHSSky)
        {
            LHSObject = RHSSky;
            RHSObject = LHSObject + objectWidth;
        }

        if (camera.getCameraY() - screenHeight/2 <= bottomObject)
        {
            if (camera.getCameraX() -screenWidth/2 <= RHSObject && camera.getCameraX() + screenWidth/2 >= LHSObject)
            {
                int LHS = Math.max(0, -camera.getCameraX() + screenWidth/2 + LHSObject);
                int RHS = Math.min(screenWidth, -camera.getCameraX() + screenWidth/2 + RHSObject);
                int bottom = -camera.getCameraY() + screenHeight/2 + bottomObject;
                int top = Math.max(0, -camera.getCameraY() + screenHeight/2 + topObject);

                Rect loc = new Rect(LHS,top,RHS,bottom);
                Rect whichBmp = new Rect(0,0,objectBitmap.getWidth(),objectBitmap.getHeight());

                if (LHS == 0)
                {
                    int distance = camera.getCameraX() - screenWidth/2 - LHSObject;
                    double LHSPerc = (double) distance /(double) objectWidth;
                    whichBmp.left = (int) Math.ceil(LHSPerc * objectBitmap.getWidth());
                }
                if (RHS == screenWidth)
                {
                    int distance = -camera.getCameraX() - screenWidth/2 + RHSObject;
                    double RHSPerc = (double) distance / (double) objectWidth;
                    whichBmp.right = (int) Math.ceil((1-RHSPerc) * objectBitmap.getWidth());
                }
                if (top == 0)
                {
                    int distance = camera.getCameraY() - screenHeight/2 - topObject;
                    double topPerc = (double) distance / (double) objectHeight;
                    whichBmp.top = (int) Math.ceil(topPerc * objectBitmap.getHeight());
                }

                canvas.drawBitmap(objectBitmap, whichBmp, loc, null);
            }
        }
    }

}
