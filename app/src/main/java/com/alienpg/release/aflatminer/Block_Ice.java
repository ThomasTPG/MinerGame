package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 03/12/2017.
 */

public class Block_Ice extends  Block{

    /**
     * % of water to produce after an ice block is mined
     */
    public static int waterProducedFromIce = 12;

    public Block_Ice(BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_Ice(Block oldBlock)
    {
        super(oldBlock);
        blockLiquidData.setGasPercentage(0);
        blockLiquidData.setWaterPercentage(0);
        setData();
    }

    private void setData()
    {
        setBitmap(blockBitmapManager.getIceBitmap());
        setSoftness(PickaxeTypes.WOOD_PICKAXE);
        setType(GlobalConstants.ICE);
    }

    @Override
    public void resetWaterAfterMining()
    {
        blockLiquidData.setWaterPercentage(waterProducedFromIce);
    }
}
