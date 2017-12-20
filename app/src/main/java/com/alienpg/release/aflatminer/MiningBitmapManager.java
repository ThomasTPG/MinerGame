package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Thomas on 25/11/2017.
 */

public class MiningBitmapManager {

    private Bitmap mining1;
    private Bitmap mining2;
    private Bitmap miningborder;

    private Context context;

    public MiningBitmapManager(Context c)
    {
        context = c;
        loadBitmaps();
    }

    //Load the bitmaps into memory
    private void loadBitmaps() {
        mining1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.miningprogress1);
        mining2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.miningprogress2);
        miningborder = BitmapFactory.decodeResource(context.getResources(), R.drawable.whichmined);
    }

    public Bitmap getMiningborder()
    {
        return miningborder;
    }

    public Bitmap getMining1() {
        return mining1;
    }

    public Bitmap getMining2() {
        return mining2;
    }
}
