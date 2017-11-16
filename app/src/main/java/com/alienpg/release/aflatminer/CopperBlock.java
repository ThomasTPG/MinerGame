package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by Thomas on 15/11/2017.
 */

public class CopperBlock extends Block {

    public CopperBlock (Coordinates coords, int seed, Context context, int blocksAcross, MinedLocations minedLocations)
    {
        super(coords,seed,context, blocksAcross, minedLocations);
        setData();
    }

    public CopperBlock(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        super.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.copper));
        super.setSoftness(PickaxeTypes.TIN_PICKAXE);
        super.setType(GlobalConstants.COPPER);
    }
}
