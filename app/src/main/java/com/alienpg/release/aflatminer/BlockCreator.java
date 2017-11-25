package com.alienpg.release.aflatminer;

import android.content.Context;

/**
 * Created by Thomas on 15/11/2017.
 */

public class BlockCreator {

    int mSeed;
    Context mContext;
    int blocksAcross;
    MinedLocations minedLocations;
    private BlockArray blockArray;
    private BlockBitmapManager blockBitmapManager;
    private MiningBitmapManager miningBitmapManager;
    private BitmapFlyWeight bitmapFlyWeight;

    public BlockCreator(int seed, Context context, int blocksAcross, MinedLocations minedLocations, BlockArray blockArray)
    {
        mSeed = seed;
        mContext = context;
        this.blocksAcross = blocksAcross;
        this.minedLocations = minedLocations;
        this.blockArray = blockArray;
        bitmapFlyWeight = new BitmapFlyWeight(context);
    }

    public void setNewBlock(Coordinates coordinates, int ii, int jj)
    {
        Block newBlock = new Block(coordinates, mSeed, mContext, blocksAcross,minedLocations, bitmapFlyWeight);
        blockArray.setBlock(ii,jj,newBlock);
    }


}
