package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 03/12/2017.
 */


public class Block_Tin extends  Block{

    public Block_Tin(BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_Tin(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        setBitmap(blockBitmapManager.getTinBitmap());
        setSoftness(PickaxeTypes.WOOD_PICKAXE);
        setType(GlobalConstants.TIN);
    }
}

