package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 03/12/2017.
 */


public class Block_Life extends  Block{

    public Block_Life(Coordinates coordinates, BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(coordinates, blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_Life(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    @Override
    public void setWaterPercentage(int pct) {
        if (pct < 0) {
            pct = 0;
        }
        blockLiquidData.setWaterPercentage(pct);

        if (yCoord >= 0) {
            saveToMemory();
        }
    }



    private void setLifeBitmap()
    {
        if (getWaterPercentage() >= 100)
        {
            setBitmap(blockBitmapManager.getLife6());

        }
        else if (getWaterPercentage() > 80)
        {
            setBitmap(blockBitmapManager.getLife5());

        }
        else if (getWaterPercentage() > 60)
            {
            setBitmap(blockBitmapManager.getLife4());

        }
        else if (getWaterPercentage() > 40)
        {
            setBitmap(blockBitmapManager.getLife3());

        }
        else
        {
            setBitmap(blockBitmapManager.getLife2());
        }
    }

    private void setData()
    {
        setLifeBitmap();
        setSoftness(PickaxeTypes.COPPER_PICKAXE);
        setType(GlobalConstants.LIFE);
    }
}