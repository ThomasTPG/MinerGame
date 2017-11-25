package com.alienpg.release.aflatminer;

import android.content.Context;

import java.util.Random;

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
    private BitmapStore bitmapStore;

    public BlockCreator(int seed, Context context, int blocksAcross, MinedLocations minedLocations, BlockArray blockArray)
    {
        mSeed = seed;
        mContext = context;
        this.blocksAcross = blocksAcross;
        this.minedLocations = minedLocations;
        this.blockArray = blockArray;
        bitmapStore = new BitmapStore(context);
    }

    public void setNewBlock(Coordinates coordinates, int ii, int jj)
    {
        Block newBlock = new Block(coordinates, mSeed, mContext, blocksAcross,minedLocations, bitmapStore);
        blockArray.setBlock(ii,jj,newBlock);
    }


}
