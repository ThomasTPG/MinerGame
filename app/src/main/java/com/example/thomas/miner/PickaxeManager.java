package com.example.thomas.miner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

/**
 * Created by Thomas on 16/10/2017.
 */

public class PickaxeManager {

    private Mining miningClass;
    private Sprite character;
    private int screenWidth;
    private int screenHeight;
    private Bitmap bitmap;
    private Context context;
    private int blockSize;
    private int restLength;
    private int restHeight;
    private int angle = 0;
    private int angleOscillation = 0;
    private boolean oscillationUp = false;
    private int pickaxeSpeed = 3;
    private int pickaxeAmp = 40;

    public PickaxeManager(Mining mining, Canvas c, Context context, int blockSize, Sprite sprite)
    {
        miningClass = mining;
        character = sprite;
        screenWidth = c.getWidth();
        screenHeight = c.getHeight();
        this.context = context;
        this.blockSize = blockSize;
        restHeight = blockSize;
        restLength = blockSize;
        getBitmap();
    }

    private void getBitmap()
    {
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.pickaxe_copper);
    }

    private void getAngle()
    {
        if (oscillationUp)
        {
            if (angleOscillation < pickaxeAmp)
            {
                angleOscillation += pickaxeSpeed;
            }
            else
            {
                oscillationUp = false;
            }
        }
        else
        {
            if (angleOscillation > -pickaxeAmp)
            {
                angleOscillation -= pickaxeSpeed;
            }
            else
            {
                oscillationUp = true;
            }
        }
        //146
        //207
        //358
        switch (miningClass.getMiningOctant())
        {
            case (0):
                if (character.getDirection() == GlobalConstants.LEFT)
                {
                    angle = 180;
                }
                else
                {
                    angle = 0;
                }
                break;
            case (1):
                angle = 225;
                break;
            case (2):
                angle = 180;
                break;
            case (3):
                angle = 135;
                break;
            case (4):
                angle = 270;
                break;
            case (5):
                angle = 90;
                break;
            case (6):
                angle = 315;
                break;
            case (7):
                angle = 0;
                break;
            case (8):
                angle = 45;
                break;
        }
        if (miningClass.getMiningOctant() != 0 && miningClass.isCurrentlyMining())
        {
            angle = angle + angleOscillation;
        }
    }

    public void onDraw(Canvas canvas)
    {
        getAngle();
        Matrix matrix = new Matrix();
        matrix.setRotate(angle);
        int height = (int) Math.floor(Math.abs(Math.sin(Math.toRadians(angle)) * restLength) + Math.abs(Math.cos(Math.toRadians(angle)) * restHeight));
        int length = (int) Math.floor(Math.abs(Math.cos(Math.toRadians(angle)) * restLength) + Math.abs(Math.sin(Math.toRadians(angle)) * restHeight));
        int centrex = screenWidth/2 + (int) Math.floor(blockSize/2 * Math.cos(Math.toRadians(angle)));
        int centrey = screenHeight/2 + (int) Math.floor(blockSize/2 * Math.sin(Math.toRadians(angle)));
        Bitmap bmpRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, false);
        bmpRotated.setHasAlpha(true);
        Rect where = new Rect(centrex -length/2, centrey - height/2, centrex + length/2, centrey + height/2);
        canvas.drawBitmap(bmpRotated,null, where,null);
    }

}
