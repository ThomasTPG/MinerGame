
package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Date;

/**
 * Created by Thomas on 21/06/2017.
 */

public class MapArt {

    private int screenHeight;
    private int screenWidth;
    private Context context;
    private Bitmap gardenBmp;
    private Bitmap gardenForeBmp;
    private Bitmap houseBmp;
    private Bitmap skyBmp;
    private Camera camera;
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

    private int LHSSky;
    private int RHSSky;
    private int bottomSky;
    private int topSky;

    private int setting;

    private ShopMemory shopMemory;

    private SkyObject[] skyObjects;


    public MapArt(Canvas c, Context context, int blockSize, ShopMemory shopMemory, Camera camera)
    {
        screenWidth = c.getWidth();
        screenHeight = c.getHeight();
        this.context = context;
        this.shopMemory = shopMemory;
        this.camera = camera;
        blocksAcross = context.getResources().getInteger(R.integer.blocks_across);
        blocksPerScreen = context.getResources().getInteger(R.integer.blocks_per_screen_width);
        blockDimensions = blockSize;
        setSetting();
        setSkyDimensions();
        setGardenDimensions();
        setHouseDimensions();
        loadGardenBitmap();
        loadHouseBitmap();
        loadSkyBitmap();
        skyObjects = new SkyObject[15];
        for (int ii = 0; ii < 15; ii++)
        {
            skyObjects[ii] = new SkyObject(new Rect(LHSSky, topSky, RHSSky,bottomSky),screenHeight,screenWidth,context,blockSize, setting,ii);
            skyObjects[ii].setInitialLocation();
        }
    }

    private void setSetting()
    {
        Date newDate  = new Date(System.currentTimeMillis());
        int hour = newDate.getHours();
        if (hour < 6 || hour > 21)
        {
            setting = GlobalConstants.NIGHT;
        }
        else if (hour < 15)
        {
            setting = GlobalConstants.DAY;
        }
        else
        {
            setting = GlobalConstants.SUNSET;
        }
    }

    public int getTime()
    {
        return setting;
    }

    private void setGardenDimensions()
    {
        LHSGarden = blockDimensions * (blocksAcross - blocksPerScreen + context.getResources().getInteger(R.integer.left_hand_side_wiggle_room) + 1);
        RHSGarden = blockDimensions * (blocksAcross + context.getResources().getInteger(R.integer.left_hand_side_wiggle_room) + 1);
        bottomGarden = 0;
        topGarden = -(blockDimensions * blocksPerScreen);
    }

    private void setSkyDimensions()
    {
        LHSSky = 0;
        RHSSky = blockDimensions * (blocksAcross + context.getResources().getInteger(R.integer.left_hand_side_wiggle_room) + blocksPerScreen/2);
        bottomSky = 0;
        topSky = -(blockDimensions * (blocksPerScreen + 1));
    }

    private void setHouseDimensions()
    {
        LHSHouse = blockDimensions * (blocksAcross - blocksPerScreen/2 + context.getResources().getInteger(R.integer.left_hand_side_wiggle_room) + 1);
        RHSHouse = blockDimensions * (blocksAcross + context.getResources().getInteger(R.integer.left_hand_side_wiggle_room));
        bottomHouse = 0;
        topHouse = -(blockDimensions * ((blocksPerScreen-1)/2));
    }

    private void loadGardenBitmap()
    {
        int gardenLvl = shopMemory.getItem(GlobalConstants.GARDENUPGRADE);
        switch (gardenLvl)
        {
            case (0):
                gardenBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.garden1);
                gardenForeBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.garden_foreground_1);
                break;
            case(1):
                gardenBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.garden1);
                gardenForeBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.gardenforeground2);
                break;
            case(2):
                gardenBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.garden1);
                gardenForeBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.gardenforeground3);
                break;
            default:
                gardenBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.garden1);
                gardenForeBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.garden_foreground_1);
                break;
        }
    }

    private void loadHouseBitmap()
    {
        int houseLvl = shopMemory.getItem(GlobalConstants.HOUSEUPGRADE);
        switch (houseLvl)
        {
            case (0):
                houseBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.house_tent);
                break;
            case(1):
                houseBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.house_shack);
                break;
            case(2):
                houseBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.house_caravan);
                break;
            default:
                houseBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.house_shack);
                break;
        }
        }

    private void loadSkyBitmap()
    {
        switch (setting)
        {
            case(GlobalConstants.SUNSET):
                skyBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.sunsetsky);
                break;
            case(GlobalConstants.DAY):
                skyBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.daysky);
                break;
            case (GlobalConstants.NIGHT):
                skyBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.nightsky2);
                break;
        }
    }

    public void drawArt(Canvas canvas)
    {
        if (camera.getCameraY() - screenHeight/2 <= 0)
        {
            double vertPercentSky = (double)(bottomSky - camera.getCameraY()+canvas.getHeight()/2) / (double)(bottomSky - topSky);
            double horizPercentSky = (double)(camera.getCameraX() + canvas.getWidth()/2 - LHSSky) / (double) (RHSSky - LHSSky);
            int topDrawLimit = Math.max(0,topSky - camera.getCameraY()+canvas.getHeight()/2);
            int bottomDrawLimit = Math.min(canvas.getHeight(), bottomSky - camera.getCameraY() + canvas.getHeight()/2);
            int leftDrawLimit = Math.max(0,LHSSky - camera.getCameraX() + canvas.getWidth()/2);
            int rightDrawLimit = Math.min(canvas.getWidth(), RHSSky - camera.getCameraX() + canvas.getWidth()/2);
            double screenToSkyRatio = (double) (canvas.getWidth())/(double)(RHSSky - LHSSky);
            Rect rSky = new Rect(leftDrawLimit, topDrawLimit, rightDrawLimit, bottomDrawLimit);
            Rect rWhichSky = new Rect((int) ((horizPercentSky-screenToSkyRatio) * skyBmp.getWidth()),(int) (skyBmp.getHeight() * (1-vertPercentSky)), (int) (horizPercentSky * skyBmp.getWidth()), skyBmp.getHeight());
            canvas.drawBitmap(skyBmp, rWhichSky,rSky,null);

            Rect rGarden = new Rect(LHSGarden - camera.getCameraX(), topGarden - camera.getCameraY()+canvas.getHeight()/2, RHSGarden - camera.getCameraX(), bottomGarden - camera.getCameraY()+canvas.getHeight()/2);
            canvas.drawBitmap(gardenBmp, null,rGarden,null);
            Rect rHouse = new Rect(LHSHouse - camera.getCameraX(), topHouse - camera.getCameraY()+canvas.getHeight()/2, RHSHouse - camera.getCameraX(), bottomHouse - camera.getCameraY()+canvas.getHeight()/2);
            canvas.drawBitmap(houseBmp, null,rHouse,null);
            for (int ii = 0; ii < 15; ii++)
            {
                skyObjects[ii].drawArt(canvas,camera);
            }
        }
    }

    public void drawForeGround(Canvas canvas)
    {
        Rect rGarden = new Rect(LHSGarden - camera.getCameraX(), topGarden - camera.getCameraY()+canvas.getHeight()/2, RHSGarden - camera.getCameraX(), bottomGarden - camera.getCameraY()+canvas.getHeight()/2);
        canvas.drawBitmap(gardenForeBmp, null,rGarden,null);
    }
}
