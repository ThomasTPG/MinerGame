package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 03/12/2017.
 */

public class Block_GasRock extends  Block{

    public Block_GasRock(BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_GasRock(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        setBitmap(blockBitmapManager.getGasRockBitmap());
        setSoftness(PickaxeTypes.COPPER_PICKAXE);
        setType(GlobalConstants.GASROCK);
    }

    @Override
    public void tryDecaying() {

    }
}