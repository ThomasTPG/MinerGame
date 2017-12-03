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

    private void setData()
    {
        setBitmap(blockBitmapManager.getLife1());
        setSoftness(PickaxeTypes.COPPER_PICKAXE);
        setType(GlobalConstants.LIFE);
    }
}
