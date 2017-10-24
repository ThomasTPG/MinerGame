package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Thomas on 26/01/2017.
 */

public class Sprite {

    private Context mContext;
    private Canvas mCanvas;
    private int dimensions = 0;
    private Bitmap sprite;
    private int canvasWidth;
    private int canvasHeight;
    boolean jumping = false;
    private int heightLeftToJump = 0;
    private int heightToFall = 0;
    private int baseJumpSpeed;
    private int baseGravitySpeed;
    private int baseRunningSpeed;
    private boolean falling;
    private boolean isAir = false;
    private int mBlockSize;
    private boolean isClamberingLeft = false;
    private boolean isClamberingRight = false;
    private int heightToClamber;
    private int widthToClamber;
    private ArrayOfBlocksOnScreen blocksOnScreen;
    private int maxOxygenAmount;
    private int oxygenAmount;
    private boolean dead = false;
    int deathReason = GlobalConstants.ESCAPE;
    private int direction = GlobalConstants.LEFT;
    private Bitmap airOuter;
    private Bitmap airInner;
    private ShopMemory shopMemory;


    public Sprite(Context context, Canvas canvas, int dimension, int blockSize)
    {
        mBlockSize = blockSize;
        mCanvas = canvas;
        mContext = context;
        dimensions = dimension;
        baseJumpSpeed = (int) (dimensions/8.0);
        baseGravitySpeed = (int) (dimension/8.0);
        baseRunningSpeed = (int) (dimension/8.0);
        sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.miner);
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();
        airInner = BitmapFactory.decodeResource(context.getResources(), R.drawable.airinner);
        airOuter = BitmapFactory.decodeResource(context.getResources(), R.drawable.airouter);
        shopMemory = new ShopMemory(context);
        setMaxOxygenAmount();
        oxygenAmount = maxOxygenAmount;
    }

    private void setMaxOxygenAmount()
    {
        int level = shopMemory.getItem(GlobalConstants.AIRTANKUPGRADE);
        switch (level)
        {
            case(0):
                maxOxygenAmount = 250;
                break;
            case (1):
                maxOxygenAmount = 500;
                break;
        }
    }

    public void setBlocksOnScreen(ArrayOfBlocksOnScreen blocks)
    {
        blocksOnScreen = blocks;
    }

    public boolean isClambering()
    {
        return (isClamberingLeft || isClamberingRight);
    }

    public void clamberLeft(int height)
    {
        isClamberingLeft = true;
        heightToClamber = height;
        widthToClamber = mBlockSize/2;
    }

    public void clamberRight(int height)
    {
        isClamberingRight = true;
        heightToClamber = height;
        widthToClamber = mBlockSize/2;
    }


    public int clamberY()
    {
        int yMovement = 0;
        if (heightToClamber > baseRunningSpeed)
        {
            heightToClamber = heightToClamber - baseRunningSpeed;
            yMovement = -baseRunningSpeed;
        }
        else
        {
            yMovement = -heightToClamber;
            setInAir(false);
            heightToClamber = 0;
        }
        return yMovement;
    }

    public void setDirection(int d)
    {
        direction = d;
    }

    public int getDirection()
    {
        return direction;
    }

    public int clamberX()
    {
        int xMovement = 0;
        if (heightToClamber == 0)
        {
            if (widthToClamber > baseRunningSpeed)
            {
                widthToClamber = widthToClamber - baseRunningSpeed;
                xMovement = (isClamberingLeft)?(-baseRunningSpeed):baseRunningSpeed;
            }
            else {
                xMovement = (isClamberingLeft) ? (-widthToClamber) : widthToClamber;
                widthToClamber = 0;
                isClamberingLeft = false;
                isClamberingRight = false;
            }
        }
        return xMovement;
    }

    public void draw()
    {
        Rect position = new Rect(((canvasWidth - dimensions) / 2), ((canvasHeight - dimensions)/2), ((canvasWidth + dimensions) / 2), ((canvasHeight + dimensions)/2));
        mCanvas.drawBitmap(sprite, null, position, null);
    }

    public int getBaseGravitySpeed()
    {
        return baseGravitySpeed;
    }

    public int getGravitySpeed(int gap)
    {
        setInAir(!(gap % mBlockSize == 0));
        if (gap == baseGravitySpeed)
        {
            return baseGravitySpeed;
        }
        if (gap <= baseGravitySpeed && gap > 0)
        {
            return gap;
        }
        else if (mBlockSize - gap <= baseGravitySpeed)
        {
            return gap - mBlockSize;
        }
        return 0;
    }

    public boolean isJumping()
    {
        return jumping;
    }

    public void setInAir(boolean air)
    {
        isAir = air;
    }

    /*
    Starts the jump, with a predefined jump height
     */
    public void startJumping(int heightToJump)
    {
        heightLeftToJump = heightToJump;
        jumping = true;
        setInAir(true);
    }

    public boolean isInAir()
    {
        return isAir;
    }

    public int getJumpSpeed()
    {
        if (heightLeftToJump > baseJumpSpeed)
        {
            heightLeftToJump = heightLeftToJump - baseJumpSpeed;
            return baseJumpSpeed;
        }
        else
        {
            setInAir(false);
            jumping = false;
            return heightLeftToJump;
        }
    }

    public boolean isInWater()
    {
        return (blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(canvasWidth/2, canvasHeight/2).getWaterPercentage() > 50);
    }

    public boolean isDead()
    {
        if (oxygenAmount <=0)
        {
            dead = true;
            deathReason = GlobalConstants.SUFFOCATED;
        }
        else if (blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(canvasWidth/2, canvasHeight/2).isIce())
        {
            dead = true;
            deathReason = GlobalConstants.FROZEN;
        }
        else if (blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(canvasWidth/2, canvasHeight/2).isFire())
        {
            dead = true;
            deathReason = GlobalConstants.EXPLOSION;
        }
        return (dead);
    }

    public int getDeath()
    {
        return deathReason;
    }

    public int getRunningSpeed()
    {
        int speed = baseRunningSpeed;
        if (isInWater())
        {
            speed = (int) (3*baseRunningSpeed/4.0);
        }
        return speed;
    }

    public void setOxygen(int o)
    {
        oxygenAmount = o;
    }

    public int getOxygenAmount()
    {
        return oxygenAmount;
    }

    public void drawOxygen(Canvas canvas)
    {
        if (isInWater())
        {
            oxygenAmount --;
            int width = (int) Math.floor(mBlockSize * ((double) 1/ (double) 8));
            Rect locationOuter = new Rect(canvasWidth/2 - mBlockSize, canvasHeight/2 - mBlockSize, canvasWidth/2 - mBlockSize + width, canvasHeight/2);
            canvas.drawBitmap(airOuter,null,locationOuter,null);
            double oxygenPct = (double)  oxygenAmount / (double )maxOxygenAmount;
            int displacedHeightCanvas = (int) Math.floor(mBlockSize * oxygenPct);
            Rect locationInner = new Rect(canvasWidth/2 - mBlockSize,canvasHeight/2 - displacedHeightCanvas,canvasWidth/2 - mBlockSize + width, canvasHeight/2);
            int displacedHeightBitmap = (int) Math.floor(airInner.getHeight() * (1-oxygenPct));
            Rect whichInner = new Rect(0,displacedHeightBitmap , airInner.getWidth(),airInner.getHeight());
            canvas.drawBitmap(airInner,whichInner,locationInner,null);
        }
        else
        {
            oxygenAmount = maxOxygenAmount;
        }
    }
}
