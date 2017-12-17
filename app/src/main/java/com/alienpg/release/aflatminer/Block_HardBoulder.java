package com.alienpg.release.aflatminer;

import android.content.Context;

/**
 * Created by Thomas on 26/11/2017.
 */

public class Block_HardBoulder extends Block {

    public Block_HardBoulder(BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_HardBoulder(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        setBitmap(blockBitmapManager.getHardBoulderBitmap());
        setSoftness(PickaxeTypes.CANNOT_BE_MINED);
        setType(GlobalConstants.HARD_BOULDER);
    }
}
