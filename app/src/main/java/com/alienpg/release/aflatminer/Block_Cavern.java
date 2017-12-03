package com.alienpg.release.aflatminer;

import android.content.Context;

/**
 * Created by Thomas on 15/11/2017.
 */

public class Block_Cavern extends Block{

    public Block_Cavern(Coordinates coordinates, BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(coordinates, blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_Cavern(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        setBitmap(blockBitmapManager.getBackground1());
        setSoftness(PickaxeTypes.CANNOT_BE_MINED);
        setType(GlobalConstants.CAVERN);
    }
}
