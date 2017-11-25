package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Thomas on 25/11/2017.
 */

public class BorderBitmapManager {

    // Bitmaps for borders
    private Bitmap leftBorder;
    private Bitmap bottomBorder;
    private Bitmap topBorder;
    private Bitmap rightBorder;
    private Bitmap topLeftBorder;
    private Bitmap topRightBorder;
    private Bitmap bottomRightBorder;
    private Bitmap bottomLeftBorder;
    private Bitmap bottomSurroundBorder;
    private Bitmap topSurroundBorder;
    private Bitmap rightSurroundBorder;
    private Bitmap leftSurroundBorder;
    private Bitmap allBorders;

    private Context context;
    private int borderSize;

    public BorderBitmapManager(Context c, int borderSize)
    {
        context = c;
        loadBitmaps();
        this.borderSize = borderSize;
    }

    //Load the bitmaps into memory
    private void loadBitmaps()
    {
        leftBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_left);
        rightBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_right);
        bottomBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_bottom);
        topBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_top);
        topLeftBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_top_left);
        topRightBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_top_right);
        bottomRightBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_bottom_right);
        bottomLeftBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_bottom_left);
        bottomSurroundBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_bottom_surround);
        topSurroundBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_top_surround);
        leftSurroundBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_left_surround);
        rightSurroundBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_right_surround);
        allBorders = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_all_borders);
    }

    //Works out an integer representation of the borders required. If the block above is not empty,
    //multiply by 2, block to the right 3, block below 5 and block to the left 7.
    private int getCode(BlockArray blockArray, int ii, int jj, int borderSize)
    {
        //Code is given by
        //   2
        //  7 3
        //   5
        int whichBitmapCode = 1;
        //Draw any borders
        if (blockArray.getBlock(ii +borderSize - 1,jj+ borderSize).canNeedBorder())
        {
            whichBitmapCode *=7;
        }
        if (blockArray.getBlock(ii +borderSize + 1,jj+ borderSize).canNeedBorder())
        {
            whichBitmapCode *=3;
        }
        if (blockArray.getBlock(ii +borderSize,jj+ borderSize - 1).canNeedBorder())
        {
            whichBitmapCode *=2;
        }
        if (blockArray.getBlock(ii +borderSize,jj+ borderSize + 1).canNeedBorder())
        {
            whichBitmapCode *=5;
        }
        return whichBitmapCode;
    }

    //Using the code, draw the correct borders onto the location rectanalge given.
    private void drawOnCanvas(int whichBitmapCode, Canvas canvas, Rect source, Rect location)
    {
        switch (whichBitmapCode)
        {
            case (2):
                canvas.drawBitmap(topBorder,source,location,null);
                break;
            case (3):
                canvas.drawBitmap(rightBorder,source,location,null);
                break;
            case (5):
                canvas.drawBitmap(bottomBorder,source,location,null);
                break;
            case (7):
                canvas.drawBitmap(leftBorder,source,location,null);
                break;
            case (6):
                canvas.drawBitmap(topRightBorder,source,location,null);
                break;
            case (14):
                canvas.drawBitmap(topLeftBorder,source,location,null);
                break;
            case (15):
                canvas.drawBitmap(bottomRightBorder,source,location,null);
                break;
            case (35):
                canvas.drawBitmap(bottomLeftBorder,source,location,null);
                break;
            case (21):
                canvas.drawBitmap(leftBorder,source,location,null);
                canvas.drawBitmap(rightBorder,source,location,null);
                break;
            case (10):
                canvas.drawBitmap(topBorder,source,location,null);
                canvas.drawBitmap(bottomBorder,source,location,null);
                break;
            case (30):
                canvas.drawBitmap(rightSurroundBorder,source,location,null);
                break;
            case (42):
                canvas.drawBitmap(topSurroundBorder,source,location,null);
                break;
            case (70):
                canvas.drawBitmap(leftSurroundBorder,source,location,null);
                break;
            case (105):
                canvas.drawBitmap(bottomSurroundBorder,source,location,null);
                break;
            case (210):
                canvas.drawBitmap(allBorders,source,location,null);
                break;
        }
    }

    public void drawBorders(BlockArray blockArray, int ii, int jj, Canvas canvas, Rect source, Rect location)
    {
        int whichBitmapCode = getCode(blockArray, ii, jj ,borderSize);
        drawOnCanvas(whichBitmapCode, canvas, source, location);
    }

}
