package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 25/11/2017.
 */

public class BlockArray {

    private int verticalBlockLimit;
    private int horizontalBlockLimit;
    private Block[][] blockArray;
    private int borderSize = 8;

    public BlockArray(int gameWidth, int gameHeight, int blockSize)
    {
        int blocksHorizontalScreen = (int) Math.ceil(gameWidth / blockSize + 1);
        int blocksVerticalScreen = (int) Math.ceil(gameHeight / blockSize + 2);
        verticalBlockLimit = blocksVerticalScreen + 2*borderSize;
        horizontalBlockLimit = blocksHorizontalScreen + 2*borderSize;
        blockArray = new Block[horizontalBlockLimit][verticalBlockLimit];
    }

    //Returns the horizontal size of the array of blocks.
    public int getHorizontalBlockLimit()
    {
        return horizontalBlockLimit;
    }

    //Returns the vertical size of the array of blocks.
    public int getVerticalBlockLimit()
    {
        return verticalBlockLimit;
    }

    public void setBlock(int ii, int jj, Block b)
    {
        blockArray[ii][jj] = b;
    }

    public Block getBlock(int ii, int jj)
    {
        return blockArray[ii][jj];
    }


}
