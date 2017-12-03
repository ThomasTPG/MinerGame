package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 03/12/2017.
 */

public class Block_Marble extends  Block{

    public Block_Marble(Coordinates coordinates, BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(coordinates, blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_Marble(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        setBitmap(blockBitmapManager.getMarbleBitmap());
        setSoftness(PickaxeTypes.COPPER_PICKAXE);
        setType(GlobalConstants.MARBLE);
    }
}
