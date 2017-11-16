package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by Thomas on 15/11/2017.
 */

public class CavernBlock extends Block{

    public CavernBlock (Coordinates coords, int seed, Context context, int blocksAcross, MinedLocations minedLocations)
    {
        super(coords,seed,context, blocksAcross, minedLocations);
        setData();
    }

    public CavernBlock(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        super.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.background_cave_1));
        super.setSoftness(PickaxeTypes.CANNOT_BE_MINED);
        super.setType(GlobalConstants.CAVERN);
    }
}
