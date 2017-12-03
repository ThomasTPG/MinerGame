package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 03/12/2017.
 */

public class Block_Iron extends  Block{

    public Block_Iron(Coordinates coordinates, BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(coordinates, blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_Iron(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        setBitmap(blockBitmapManager.getIronBitmap());
        setSoftness(PickaxeTypes.COPPER_PICKAXE);
        setType(GlobalConstants.IRON);
    }
}