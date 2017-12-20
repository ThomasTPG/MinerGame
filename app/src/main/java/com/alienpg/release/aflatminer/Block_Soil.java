package com.alienpg.release.aflatminer;

import android.content.Context;

/**
 * Created by Thomas on 26/11/2017.
 */

public class Block_Soil extends Block{

    public Block_Soil(BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_Soil(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        if (index % 2 == 0)
        {
            setBitmap(blockBitmapManager.getSoil1Bitmap());
        }
        else
        {
            setBitmap(blockBitmapManager.getSoil2Bitmap());
        }
        setSoftness(PickaxeTypes.WOOD_PICKAXE);
        setType(GlobalConstants.SOIL);
    }
}
