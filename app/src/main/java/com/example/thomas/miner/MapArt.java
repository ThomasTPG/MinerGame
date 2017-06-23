
package com.example.thomas.miner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.thomas.miner.R;

/**
 * Created by Thomas on 21/06/2017.
 */

public class MapArt {

    private int screenHeight;
    private int screenWidth;
    private Context context;
    private Bitmap gardenBmp;
    private Bitmap houseBmp;
    private int blockDimensions;
    private int blocksAcross;
    private int blocksPerScreen;

    private int LHSGarden;
    private int RHSGarden;
    private int bottomGarden;
    private int topGarden;

    private int LHSHouse;
    private int RHSHouse;
    private int bottomHouse;
    private int topHouse;

    private ShopMemory shopMemory;


    public MapArt(Canvas c, Context context, int blockSize, ShopMemory shopMemory)
    {
        screenWidth = c.getWidth();
        screenHeight = c.getHeight();
        this.context = context;
        this.shopMemory = shopMemory;
        blocksAcross = context.getResources().getInteger(R.integer.blocks_across);
        blocksPerScreen = context.getResources().getInteger(R.integer.blocks_per_screen_width);
        blockDimensions = blockSize;
        setGardenDimensions();
        setHouseDimensions();
        loadGardenBitmap();
        loadHouseBitmap();
    }

    private void setGardenDimensions()
    {
        LHSGarden = blockDimensions * (blocksAcross - blocksPerScreen + context.getResources().getInteger(R.integer.left_hand_side_wiggle_room) + 1);
        RHSGarden = blockDimensions * (blocksAcross + context.getResources().getInteger(R.integer.left_hand_side_wiggle_room) + 1);
        bottomGarden = 0;
        topGarden = -(blockDimensions * blocksPerScreen);
    }

    private void setHouseDimensions()
    {
        LHSHouse = blockDimensions * (blocksAcross - blocksPerScreen/2 + context.getResources().getInteger(R.integer.left_hand_side_wiggle_room) + 1);
        RHSHouse = blockDimensions * (blocksAcross + context.getResources().getInteger(R.integer.left_hand_side_wiggle_room));
        bottomHouse = -blockDimensions;
        topHouse = -(blockDimensions * ((blocksPerScreen-1)/2));
    }

    private void loadGardenBitmap()
    {
        int gardenLvl = shopMemory.getItem(GlobalConstants.GARDENUPGRADE);
        switch (gardenLvl)
        {
            case (0):
                gardenBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.garden1);
                break;
            case(1):
                break;
            default:
                gardenBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.garden1);
                break;
        }
    }

    private void loadHouseBitmap()
    {
        int houseLvl = shopMemory.getItem(GlobalConstants.HOUSEUPGRADE);
        switch (houseLvl)
        {
            case (0):
                houseBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.house1);
                break;
            case(1):
                break;
            default:
                houseBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.house1);
                break;
        }
    }

    public void drawArt(Camera camera, Canvas canvas)
    {

        Rect rGarden = new Rect(LHSGarden - camera.getCameraX(), topGarden - camera.getCameraY()+canvas.getHeight()/2, RHSGarden - camera.getCameraX(), bottomGarden - camera.getCameraY()+canvas.getHeight()/2);
        canvas.drawBitmap(gardenBmp, null,rGarden,null);
        Rect rHouse = new Rect(LHSHouse - camera.getCameraX(), topHouse - camera.getCameraY()+canvas.getHeight()/2, RHSHouse - camera.getCameraX(), bottomHouse - camera.getCameraY()+canvas.getHeight()/2);
        canvas.drawBitmap(houseBmp, null,rHouse,null);
    }
}
