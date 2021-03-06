package com.alienpg.release.aflatminer;

import android.content.Context;

/**
 * Created by Thomas on 13/02/2017.
 */

public class BlockManager {

    private BlockArray blockArray;
    private int mGameWidth;
    private int mGameHeight;
    private int verticalBlockLimit;
    private int horizontalBlockLimit;
    private int blocksAcross;
    private int seed;
    private int blockSize;
    //Screen dimensions measured in NESW order
    private int[] currentScreenDimensions = new int[4];
    private int[] previousScreenDimensions = new int[4];
    private Context context;

    //
    private Camera mCamera;
    private int borderSize = 8;
    private int blocksHorizontalScreen;
    private int blocksVerticalScreen;
    private int waterDelay = 0;
    private Achievements achievementManager;
    private BlockCreator blockCreator;

    public BlockManager(int gameWidth, int gameHeight, int blockSize, Context context, int seed, Camera camera, MinedLocations minedLocations, Achievements achievements)
    {
        blocksAcross = context.getResources().getInteger(R.integer.blocks_across);
        this.seed = seed;
        this.blockSize = blockSize;
        mCamera = camera;
        this.context = context;
        mGameHeight = gameHeight;
        mGameWidth = gameWidth;
        blocksHorizontalScreen = (int) Math.ceil(gameWidth / blockSize + 1);
        blocksVerticalScreen = (int) Math.ceil(gameHeight / blockSize + 2);
        verticalBlockLimit = blocksVerticalScreen + 2*borderSize;
        horizontalBlockLimit = blocksHorizontalScreen + 2*borderSize;
        blockArray = new BlockArray(gameWidth,gameHeight,blockSize);
        achievementManager = achievements;
        blockCreator = new BlockCreator(seed,context,blocksAcross,minedLocations, blockArray);
        createInitialBlockArray();
    }

    public void createInitialBlockArray()
    {
        int screenTopLeftX = (int)Math.floor((mCamera.getCameraX() - mGameWidth/2));
        int screenTopLeftY = (int)Math.floor((mCamera.getCameraY() - mGameHeight/2));

        for (int ii = 0; ii < horizontalBlockLimit; ii ++)
        {
            for (int jj = 0; jj < verticalBlockLimit; jj++)
            {
                int xCoord = (int) (Math.floor(screenTopLeftX / (double)blockSize) + ii - borderSize);
                int yCoord = (int) (Math.floor(screenTopLeftY / (double)blockSize) + jj - borderSize);
                blockCreator.setNewBlock(new Coordinates(xCoord,yCoord),ii ,jj);
            }
        }
    }

    public int[] getBlockArrayIndicesFromScreenCoordinates(Coordinates coordinates)
    {
        int screenTopLeftX = (int)Math.floor((mCamera.getCameraX() - mGameWidth/2));
        int screenTopLeftY = (int)Math.floor((mCamera.getCameraY() - mGameHeight/2));
        int[] arrayIndices = new int[2];
        arrayIndices[0] = borderSize + (int) (Math.floor((screenTopLeftX + coordinates.getX())/blockSize) - Math.floor(screenTopLeftX/blockSize));
        arrayIndices[1] = borderSize + (int) (Math.floor((double)(screenTopLeftY + coordinates.getY())/(double)blockSize) - Math.floor((double)screenTopLeftY/(double)blockSize));

        return arrayIndices;
    }

    public Block getBlockFromArrayUsingScreenCoordinates(Coordinates coordinates)
    {
        int[] coords = getBlockArrayIndicesFromScreenCoordinates(coordinates);
        return blockArray.getBlock(coords[0], coords[1]);
    }

    public void setBlockUsingScreenCoordinates(Coordinates coordinates, Block b)
    {
        int[] coords = getBlockArrayIndicesFromScreenCoordinates(coordinates);
        blockArray.setBlock(coords[0], coords[1], b);
    }

    public void calculateCurrentBlocks()
    {
        try {
            synchronized (blockArray)
            {
                calculateBlocks();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void calculateBlocks()
    {
        int screenTopLeftX = (int)Math.floor((mCamera.getCameraX() - mGameWidth/2));
        int screenTopLeftY = (int)Math.floor((mCamera.getCameraY() - mGameHeight/2));
        if (Math.floor(currentScreenDimensions[2]) < Math.floor(previousScreenDimensions[2]))
        {

            // The screen has moved up
            System.out.println("UP");
            for (int ii = 0; ii < horizontalBlockLimit; ii ++)
            {
                for (int jj = verticalBlockLimit - 1; jj > 0; jj--)
                {
                    blockArray.setBlock(ii, jj, blockArray.getBlock(ii, jj - 1));
                }
                int xCoord = (int) (Math.floor(screenTopLeftX / (double)blockSize) + ii - borderSize);
                int yCoord = (int) (Math.floor(screenTopLeftY / (double)blockSize) - borderSize);
                blockCreator.setNewBlock(new Coordinates(xCoord,yCoord), ii, 0);
            }
        }
        else if (Math.floor(currentScreenDimensions[0]) > Math.floor(previousScreenDimensions[0]))
        {
            System.out.println("DOWN");

            // The screen has moved down
            for (int ii = 0; ii < horizontalBlockLimit; ii ++)
            {
                for (int jj = 0; jj < verticalBlockLimit - 1; jj++)
                {
                    blockArray.setBlock(ii, jj, blockArray.getBlock(ii, jj + 1));
                }
                int xCoord = (int) (Math.floor(screenTopLeftX / (double)blockSize + ii) - borderSize);
                int yCoord = (int) (Math.floor(screenTopLeftY / (double)blockSize + verticalBlockLimit - 1 - borderSize));
                blockCreator.setNewBlock(new Coordinates(xCoord,yCoord), ii ,verticalBlockLimit-1);
            }
        }
        if (Math.floor(currentScreenDimensions[3]) > Math.floor(previousScreenDimensions[3]))
        {
            // The screen has moved east
            for (int jj = 0; jj < verticalBlockLimit; jj++)
            {
                for (int ii = 0; ii < horizontalBlockLimit - 1; ii++)
                {
                    blockArray.setBlock(ii, jj, blockArray.getBlock(ii+1, jj));
                }
                int xCoord = (int) (Math.floor(screenTopLeftX / (double)blockSize) + horizontalBlockLimit - 1 - borderSize);
                int yCoord = (int) (Math.floor(screenTopLeftY / (double)blockSize) + jj -borderSize);
                blockCreator.setNewBlock(new Coordinates(xCoord,yCoord), horizontalBlockLimit-1, jj);
            }
        }
        else if (Math.floor(currentScreenDimensions[1]) < Math.floor(previousScreenDimensions[1]))
        {
            // The screen has moved west
            for (int jj = 0; jj < verticalBlockLimit; jj++)
            {
                for (int ii = horizontalBlockLimit - 1; ii > 0; ii--)
                {
                    blockArray.setBlock(ii, jj, blockArray.getBlock(ii-1, jj));
                }
                int xCoord = (int) (Math.floor(screenTopLeftX / (double)blockSize) - borderSize);
                int yCoord = (int) (Math.floor(screenTopLeftY / (double)blockSize) + jj - borderSize);
                blockCreator.setNewBlock(new Coordinates(xCoord,yCoord), 0 ,jj);
            }

        }
    }

    public void updateCurrentScreenDimensions()
    {
        //Record the co-odinates of the blocks at the extremes of the screen
        currentScreenDimensions[0] = (int) (Math.floor((mCamera.getCameraY() - mGameHeight/2) / (double)blockSize));
        currentScreenDimensions[1] = (int) (Math.floor((mCamera.getCameraX() - mGameWidth/2) / (double)blockSize +blocksHorizontalScreen - 1 ));
        currentScreenDimensions[2] = (int) (Math.floor((mCamera.getCameraY() - mGameHeight/2) / (double)blockSize + blocksVerticalScreen - 1));
        currentScreenDimensions[3] = (int) (Math.floor((mCamera.getCameraX() - mGameWidth/2) / (double)blockSize));
    }

    public void updatePreviousScreenDimensions()
    {
        for (int kk = 0; kk<4;kk++)
        {
            previousScreenDimensions[kk]=currentScreenDimensions[kk];
        }
    }

    public Coordinates getBlockCoordinatesByIndex(Block block)
    {
        return blockArray.getBlockCoordinatesByIndex(block);
    }

    public void moveWaterRight()
    {
        Block aboveBlock = getBlockFromArrayUsingScreenCoordinates(new Coordinates(mGameWidth/2, mGameHeight/2 - blockSize));
        if (aboveBlock.getWaterPercentage() == 0 && waterDelay % 3 == 0)
        {
            Block currentBlock = getBlockFromArrayUsingScreenCoordinates(new Coordinates(mGameWidth/2, mGameHeight/2));
            Block rightBlock = getBlockFromArrayUsingScreenCoordinates(new Coordinates(mGameWidth/2 + blockSize, mGameHeight/2));
            if (!rightBlock.isSolid() && rightBlock.getWaterPercentage() < 100 && !currentBlock.isSolid() && currentBlock.getWaterPercentage() > 5)
            {
                int rightTotalVol = rightBlock.getWaterPercentage();
                int waterMoved = Math.min(100 - rightTotalVol, 5);
                currentBlock.setWaterPercentage(currentBlock.getWaterPercentage() - waterMoved);
                rightBlock.setWaterPercentage(rightBlock.getWaterPercentage() + waterMoved);
            }
        }
    }

    public void moveWaterLeft()
    {
        Block aboveBlock = getBlockFromArrayUsingScreenCoordinates(new Coordinates(mGameWidth/2, mGameHeight/2 - blockSize));
        if (!aboveBlock.hasWater() && waterDelay % 3 == 0)
        {
            Block currentBlock = getBlockFromArrayUsingScreenCoordinates(new Coordinates(mGameWidth/2, mGameHeight/2));
            Block leftBlock = getBlockFromArrayUsingScreenCoordinates(new Coordinates(mGameWidth/2 - blockSize, mGameHeight/2));
            if (!leftBlock.isSolid() && leftBlock.getWaterPercentage() < 100 && !currentBlock.isSolid() && currentBlock.getWaterPercentage() > 5)
            {
                int leftTotalVol = leftBlock.getWaterPercentage();
                int waterMoved = Math.min(100 - leftTotalVol, 5);
                currentBlock.setWaterPercentage(currentBlock.getWaterPercentage() - waterMoved);
                leftBlock.setWaterPercentage(leftBlock.getWaterPercentage() + waterMoved);
            }
        }
    }

    public void splash()
    {
        Block aboveBlock = getBlockFromArrayUsingScreenCoordinates(new Coordinates(mGameWidth/2, mGameHeight/2 - blockSize));
        if (aboveBlock.getWaterPercentage() < 50 && waterDelay % 3 == 0)
        {
            Block currentBlock = getBlockFromArrayUsingScreenCoordinates(new Coordinates(mGameWidth/2, mGameHeight/2));
            Block topLeftBlock = getBlockFromArrayUsingScreenCoordinates(new Coordinates(mGameWidth/2 - blockSize, mGameHeight/2 - blockSize));
            Block topRightBlock = getBlockFromArrayUsingScreenCoordinates(new Coordinates(mGameWidth/2 + blockSize, mGameHeight/2 - blockSize));
            if (topLeftBlock.getWaterPercentage() < 100 && (!topLeftBlock.isSolid()))
            {
                int waterMoved = Math.min(100 - topLeftBlock.getWaterPercentage(), 10);
                currentBlock.setWaterPercentage(currentBlock.getWaterPercentage() - waterMoved);
                topLeftBlock.setWaterPercentage(topLeftBlock.getWaterPercentage() + waterMoved);
            }
            if (topRightBlock.getWaterPercentage() < 100 && (!topRightBlock.isSolid()))
            {
                int waterMoved = Math.min(100 - topRightBlock.getWaterPercentage(), 10);
                currentBlock.setWaterPercentage(currentBlock.getWaterPercentage() - waterMoved);
                topRightBlock.setWaterPercentage(topRightBlock.getWaterPercentage() + waterMoved);
            }
        }
    }

    public int getVerticalBlockLimit()
    {
        return verticalBlockLimit;
    }

    public int getHorizontalBlockLimit()
    {
        return horizontalBlockLimit;
    }

    public BlockArray getBlockArray()
    {
        return blockArray;
    }

    public int getBorderSize()
    {
        return borderSize;
    }

    public int getBlocksHorizontalScreen()
    {
        return blocksHorizontalScreen;
    }

    public int getBlocksVerticalScreen()
    {
        return blocksVerticalScreen;
    }
}
