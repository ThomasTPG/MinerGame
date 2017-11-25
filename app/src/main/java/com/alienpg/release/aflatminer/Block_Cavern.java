package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by Thomas on 15/11/2017.
 */

public class Block_Cavern extends Block{

    public Block_Cavern(Coordinates coords, int seed, Context context, int blocksAcross, MinedLocations minedLocations, BitmapStore bitmapStore)
    {
        super(coords,seed,context, blocksAcross, minedLocations, bitmapStore);
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
