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

    public BlockCreator(int seed, Context context, int blocksAcross, MinedLocations minedLocations)
    {
        mSeed = seed;
        mContext = context;
        this.blocksAcross = blocksAcross;
        this.minedLocations = minedLocations;
    }

    public Block getNewBlock(Coordinates coordinates)
    {
        Block newBlock = new Block(coordinates, mSeed, mContext, blocksAcross,minedLocations);
        return newBlock;
    }


}
