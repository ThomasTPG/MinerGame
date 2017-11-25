package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by Thomas on 15/11/2017.
 */

public class Block_Copper extends Block {

    public Block_Copper(Coordinates coords, int seed, Context context, int blocksAcross, MinedLocations minedLocations, BitmapStore bitmapStore)
    {
        super(coords,seed,context, blocksAcross, minedLocations, bitmapStore);
        setData();
    }

    public Block_Copper(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        setBitmap(blockBitmapManager.getCopperBitmap());
        setSoftness(PickaxeTypes.TIN_PICKAXE);
        setType(GlobalConstants.COPPER);
    }
}
