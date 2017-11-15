package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by Thomas on 15/11/2017.
 */

public class CopperBlock extends Block {

    public CopperBlock (int seed, int xCoord, int yCoord, Context context, int blocksAcross, MinedLocations minedLocations)
    {
        super(seed, xCoord, yCoord, context, blocksAcross, minedLocations);
        super.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.copper));
    }
}
