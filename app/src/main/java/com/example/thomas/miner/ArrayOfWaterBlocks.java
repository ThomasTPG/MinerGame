package com.example.thomas.miner;

import android.content.Context;

/**
 * Created by Thomas on 25/02/2017.
 */

public class ArrayOfWaterBlocks {

    private int areaMultiplier = 3;
    int[][] waterArray;
    private Camera camera;
    private int gameWidth;
    private int gameHeight;
    private int horizontalDimension;
    private int vertialDimension;
    private int blockSize;
    private int blocksAcross;
    private int seed;
    private MinedLocations minedLocations;

    public ArrayOfWaterBlocks(int seed,Context context, int verticalBlockLimit, int horizontalBlockLimit, int gameWidth, int gameHeight, Camera c, int blockSize, MinedLocations minedLocations)
    {
        horizontalDimension = areaMultiplier*horizontalBlockLimit;
        vertialDimension = areaMultiplier*verticalBlockLimit;
        waterArray = new int[horizontalDimension][vertialDimension];
        camera = c;
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
        this.blockSize = blockSize;
        this.blocksAcross = context.getResources().getInteger(R.integer.blocks_across);
        this.seed = seed;
        this.minedLocations = minedLocations;
    }

}
