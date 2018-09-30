package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 03/12/2017.
 */


public class Block_Life extends  Block{

    public Block_Life(BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_Life(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    @Override
    public void setWaterPercentage(int pct) {
        if (blockLiquidData.getWaterPercentage() == pct)
        {
            return;
        }
        if (pct < 0)
        {
            pct = 0;
        }
        blockLiquidData.setWaterPercentage(pct);
        setLifeBitmap();

        if (getBlockLocaleData().getCoordinates().getY() >= 0) {
            saveToMemory();
        }
    }

    @Override
    public boolean mineFurther(OreCounter oreCounter)
    {
        if (blockLiquidData.getWaterPercentage() < 100) {
            return false;
        }
        return super.mineFurther(oreCounter);
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
