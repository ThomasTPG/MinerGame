package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Thomas on 25/11/2017.
 */

public class BitmapFlyWeight {

    private BlockBitmapManager blockBitmapManager;
    private MiningBitmapManager miningBitmapManager;

    public BitmapFlyWeight(Context context)
    {
        blockBitmapManager = new BlockBitmapManager(context);
        miningBitmapManager = new MiningBitmapManager(context);
    }

    public BlockBitmapManager getBlockBitmapManager()
    {
        return blockBitmapManager;
    }

    public MiningBitmapManager getMiningBitmapManager()
    {
        return miningBitmapManager;
    }
}
