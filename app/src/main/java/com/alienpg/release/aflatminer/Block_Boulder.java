package com.alienpg.release.aflatminer;

import android.content.Context;

/**
 * Created by Thomas on 25/11/2017.
 */

public class Block_Boulder extends Block {

    public Block_Boulder(BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_Boulder(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        setBitmap(blockBitmapManager.getBoulderBitmap());
        setSoftness(PickaxeTypes.CANNOT_BE_MINED);

        setType(GlobalConstants.BOULDER);
    }
}
