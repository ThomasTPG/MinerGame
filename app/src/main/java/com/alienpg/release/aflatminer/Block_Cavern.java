package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Thomas on 15/11/2017.
 */

public class Block_Cavern extends Block{

    public Block_Cavern(Coordinates coordinates, BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(coordinates, blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_Cavern(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        setBitmap(blockBitmapManager.getBackground1());
        setSoftness(PickaxeTypes.CANNOT_BE_MINED);
        setType(GlobalConstants.CAVERN);
    }

    @Override
    public void draw(Canvas c, Rect src, Rect loc, int blockSize, int gameHeight) {
        c.drawBitmap(blockBitmapManager.getBackground1(), src, loc, null);
        drawFluidBlock(c, src, loc, blockSize, gameHeight);
    }

    private void drawFluidBlock(Canvas canvas, Rect source, Rect location, int blockSize, int mGameHeight)
    {
        int totalPercent = blockLiquidData.getGasPercentage() + blockLiquidData.getWaterPercentage();
        int overlay = 0;
        if (totalPercent > 100)
        {
            overlay = totalPercent - 100;
        }
        int gasHeight = (int) ((double)(Math.min(100 - blockLiquidData.getWaterPercentage(), blockLiquidData.getGasPercentage()) * blockSize)/(double)100);
        double waterPercentageDown = ((double) (100 - (blockLiquidData.getWaterPercentage() - overlay)) /(double) 100);
        int waterHeight = (int) Math.ceil(waterPercentageDown * blockSize);

        if (gasHeight > 0)
        {
            int bottomBmp = (int) ((double)(Math.min(100 - blockLiquidData.getWaterPercentage(), blockLiquidData.getGasPercentage()) * blockBitmapManager.getGasBitmap().getHeight())/(double)100);

            Rect whichBmp = source;
            whichBmp.bottom = Math.min(bottomBmp, whichBmp.bottom);
            Rect scaledLocation;
            if (location.top  == 0)
            {
                scaledLocation = new Rect(location.left, location.top, location.right, location.bottom - blockSize + gasHeight);
            }
            else if (location.height() < gasHeight)
            {
                scaledLocation = new Rect(location.left, location.top, location.right, mGameHeight);
            }
            else
            {
                scaledLocation = new Rect(location.left, location.top, location.right, location.top + gasHeight);
            }
            canvas.drawBitmap(blockBitmapManager.getGasBitmap(),whichBmp,scaledLocation,null);
        }
        if (overlay > 0)
        {
            Rect scaledLocation = new Rect(location.left,location.top + gasHeight,location.right,location.top + waterHeight);
            canvas.drawBitmap(blockBitmapManager.getGasWaterBitmap(),null,scaledLocation,null);
        }
        if (waterHeight < 100)
        {
            int topBmp = (int) ((double)((100 - blockLiquidData.getWaterPercentage() - overlay) * blockBitmapManager.getWaterBitmap().getHeight())/(double)100);
            Rect scaledLocation;
            Rect whichBmp = source;
            whichBmp.top = Math.max(topBmp,whichBmp.top);
            if (location.top  == 0)
            {
                if (location.height() < blockSize - waterHeight)
                {
                    scaledLocation = new Rect(location.left, 0, location.right, location.bottom);
                }
                else
                {
                    scaledLocation = new Rect(location.left, location.bottom - blockSize + waterHeight, location.right, location.bottom);
                }
            }
            else
            {
                scaledLocation = new Rect(location.left, location.top + waterHeight, location.right, location.bottom);
            }
            canvas.drawBitmap(blockBitmapManager.getWaterBitmap(),whichBmp,scaledLocation,null);
        }
    }
}
