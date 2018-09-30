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

    public void setBlock(Coordinates coords, Block b ){ blockArray[coords.getX()][ coords.getY()] = b;}

    public Block getBlock(int ii, int jj)
    {
        return blockArray[ii][jj];
    }

    public Block getBlock(Coordinates coods) { return blockArray[coods.getX()][coods.getY()];}

    public Coordinates getBlockCoordinatesByIndex(Block block)
    {
        int index = block.getIndex();
        int x = 0;
        int y = 0;
        boolean foundBlock = false;
        for (int ii = horizontalBlockLimit-2; ii >=0; ii --)
        {
            for (int jj = verticalBlockLimit - 2; jj >= 0; jj--)
            {
                if (getBlock(ii, jj).getIndex() == index)
                {
                    x = ii;
                    y = jj;
                    foundBlock = true;
                }
            }
        }
        if (foundBlock)
        {
            Coordinates c = new Coordinates(x,y);
            return c;
        }
        Coordinates defaultC = new Coordinates(0,0);
        return defaultC;
    }


}
