package com.example.thomas.miner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.Random;

/**
 * Created by Thomas on 13/02/2017.
 */

public class ArrayOfBlocksOnScreen {

    private Block[][] blockArray;
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
    private MinedLocations minedLocations;
    private Context context;
    //Bitmaps
    private Bitmap mining1;
    private Bitmap mining2;
    private Bitmap miningborder;
    private Bitmap soil1Bitmap;
    private Bitmap soil2Bitmap;
    private Bitmap boulderBitmap;
    private Bitmap hardBoulderBitmap;
    private Bitmap fireBallBitmap;
    private Bitmap copperBitmap;
    private Bitmap ironBitmap;
    private Bitmap alieniteBitmap;
    private Bitmap marbleBitmap;
    private Bitmap springBitmap;
    private Bitmap waterBitmap;
    private Bitmap gasBitmap;
    private Bitmap gasWaterBitmap;
    private Bitmap lifeBitmap;
    private Bitmap lifeGrown;
    private Bitmap iceBitmap;
    private Bitmap goldBitmap;
    private Bitmap crystalBase;
    private Bitmap gasRockBitmap;
    private Bitmap costumeGemBitmap;
    private Bitmap crystal1;
    private Bitmap crystal2;
    private Bitmap crystal3;
    private Bitmap dynamite;
    private Bitmap iceBomb;
    // Bitmaps for borders
    private Bitmap leftBorder;
    private Bitmap bottomBorder;
    private Bitmap topBorder;
    private Bitmap rightBorder;
    private Bitmap topLeftBorder;
    private Bitmap topRightBorder;
    private Bitmap bottomRightBorder;
    private Bitmap bottomLeftBorder;
    private Bitmap bottomSurroundBorder;
    private Bitmap allBorders;

    //
    private Camera mCamera;
    private int borderSize = 8;
    private int blocksHorizontalScreen;
    private int blocksVerticalScreen;
    private int waterDelay = 0;

    public ArrayOfBlocksOnScreen(int gameWidth, int gameHeight, int blockSize, Context context, int seed, Camera camera, MinedLocations minedLocations)
    {
        this.minedLocations = minedLocations;
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
        blockArray = new Block[horizontalBlockLimit][verticalBlockLimit];
        loadBitmaps();
        createInitialBlockArray();
    }

    private void loadBitmaps()
    {
        mining1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.miningprogress1);
        mining2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.miningprogress2);
        miningborder = BitmapFactory.decodeResource(context.getResources(), R.drawable.whichmined);
        waterBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.water);
        gasBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gas);
        gasWaterBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gaswater);
        soil1Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil);
        soil2Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil2);
        boulderBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rock);
        hardBoulderBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hardrock);
        fireBallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fireball);
        copperBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.copper);
        ironBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.iron);
        alieniteBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.alienite);
        marbleBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marble);
        springBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.spring);
        lifeBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);
        lifeGrown = BitmapFactory.decodeResource(context.getResources(), R.drawable.lifegrown);
        iceBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ice);
        goldBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gold);
        crystalBase = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystalbase);
        gasRockBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gasrock);
        costumeGemBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.costumegem);
        crystal1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystal1);
        crystal2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystal2);
        crystal3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystal3);
        dynamite = BitmapFactory.decodeResource(context.getResources(), R.drawable.dynamitebutton);
        iceBomb = BitmapFactory.decodeResource(context.getResources(), R.drawable.icebombbutton);

        leftBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_left);
        rightBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_right);
        bottomBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_bottom);
        topBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_top);
        topLeftBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_top_left);
        topRightBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_top_right);
        bottomRightBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_bottom_right);
        bottomLeftBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.bottom_left);
        bottomSurroundBorder = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_bottom_surround);
        allBorders = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil_all_borders);


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
                blockArray[ii][jj] = new Block(seed, xCoord, yCoord, context, blocksAcross, minedLocations);
            }
        }
    }

    public int[] getBlockArrayIndicesFromScreenCoordinates(int x, int y)
    {
        int screenTopLeftX = (int)Math.floor((mCamera.getCameraX() - mGameWidth/2));
        int screenTopLeftY = (int)Math.floor((mCamera.getCameraY() - mGameHeight/2));
        int[] arrayIndices = new int[2];
        arrayIndices[0] = borderSize + (int) (Math.floor((screenTopLeftX + x)/blockSize) - Math.floor(screenTopLeftX/blockSize));
        arrayIndices[1] = borderSize + (int) (Math.floor((double)(screenTopLeftY + y)/(double)blockSize) - Math.floor((double)screenTopLeftY/(double)blockSize));

        return arrayIndices;
    }

    public Block getBlockFromArrayUsingScreenCoordinates(int x, int y)
    {
        int[] coords = getBlockArrayIndicesFromScreenCoordinates(x, y);
        return blockArray[coords[0]][coords[1]];
    }

    public void calculateCurrentBlocks()
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
                    blockArray[ii][jj] = blockArray[ii][jj-1];
                }
                int xCoord = (int) (Math.floor(screenTopLeftX / (double)blockSize) + ii - borderSize);
                int yCoord = (int) (Math.floor(screenTopLeftY / (double)blockSize) - borderSize);

                blockArray[ii][0] = new Block(seed, xCoord, yCoord, context, blocksAcross,minedLocations);
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
                    blockArray[ii][jj] = blockArray[ii][jj+1];
                }
                int xCoord = (int) (Math.floor(screenTopLeftX / (double)blockSize + ii) - borderSize);
                int yCoord = (int) (Math.floor(screenTopLeftY / (double)blockSize + verticalBlockLimit - 1 - borderSize));
                blockArray[ii][verticalBlockLimit-1] = new Block(seed, xCoord, yCoord, context, blocksAcross, minedLocations);
            }
        }
        if (Math.floor(currentScreenDimensions[3]) > Math.floor(previousScreenDimensions[3]))
        {
            // The screen has moved east
            for (int jj = 0; jj < verticalBlockLimit; jj++)
            {
                for (int ii = 0; ii < horizontalBlockLimit - 1; ii++)
                {
                    blockArray[ii][jj] = blockArray[ii+1][jj];
                }
                int xCoord = (int) (Math.floor(screenTopLeftX / (double)blockSize) + horizontalBlockLimit - 1 - borderSize);
                int yCoord = (int) (Math.floor(screenTopLeftY / (double)blockSize) + jj -borderSize);
                blockArray[horizontalBlockLimit-1][jj] = new Block(seed, xCoord, yCoord, context, blocksAcross, minedLocations);
            }
        }
        else if (Math.floor(currentScreenDimensions[1]) < Math.floor(previousScreenDimensions[1]))
        {
            // The screen has moved west
            for (int jj = 0; jj < verticalBlockLimit; jj++)
            {
                for (int ii = horizontalBlockLimit - 1; ii > 0; ii--)
                {
                    blockArray[ii][jj] = blockArray[ii-1][jj];
                }
                int xCoord = (int) (Math.floor(screenTopLeftX / (double)blockSize) - borderSize);
                int yCoord = (int) (Math.floor(screenTopLeftY / (double)blockSize) + jj - borderSize);
                blockArray[0][jj] = new Block(seed, xCoord, yCoord, context, blocksAcross, minedLocations);
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
    
    public void explodeBomb(Block bomb)
    {
        int index = bomb.getIndex();
        int x = 0;
        int y = 0;
        boolean foundBomb = false;
        for (int ii = horizontalBlockLimit-2; ii >=0; ii --)
        {
            for (int jj = verticalBlockLimit - 2; jj >= 0; jj--)
            {
                if (blockArray[ii][jj].getIndex() == index)
                {
                    x = ii;
                    y = jj;
                    foundBomb = true;
                }
            }
        }
        if (foundBomb)
        {
            switch(bomb.getBomb())
            {
                case(ActiveBombs.DYNAMITE):
                    for (int aa = -1; aa <=1; aa++)
                    {
                        for (int bb = -1; bb<=1; bb++)
                        {
                            blockArray[x + aa][y + bb].blowUp();
                        }
                    }
                    break;
                case(ActiveBombs.ICEBOMB):
                    for (int aa = -1; aa <=1; aa++)
                    {
                        for (int bb = -1; bb<=1; bb++)
                        {
                            blockArray[x + aa][y + bb].detonateIceBomb();
                        }
                    }
                    break;
            }

        }
    }

    private Bitmap workOutBitmap(Block currentBlock)
    {
        int type = currentBlock.getType();
        Bitmap blockBitmap ;
        switch (type)
        {
            case(GlobalConstants.CAVERN):
                blockBitmap = null;
                break;
            case(GlobalConstants.SOIL):
                if (currentBlock.getIndex() % 2 == 0)
                {
                    blockBitmap = soil1Bitmap;
                }
                else
                {
                    blockBitmap = soil2Bitmap;
                }
                break;
            case (GlobalConstants.BOULDER):
                blockBitmap = boulderBitmap;
                break;
            case (GlobalConstants.HARD_BOULDER):
                blockBitmap = hardBoulderBitmap;
                break;
            case (GlobalConstants.FIREBALL):
                blockBitmap = fireBallBitmap;
                break;
            case (GlobalConstants.COPPER):
                blockBitmap = copperBitmap;
                break;
            case (GlobalConstants.IRON):
                blockBitmap = ironBitmap;
                break;
            case (GlobalConstants.ALIENITE):
                blockBitmap = alieniteBitmap;
                break;
            case (GlobalConstants.MARBLE):
                blockBitmap = marbleBitmap;
                break;
            case (GlobalConstants.SPRING):
                blockBitmap = springBitmap;
                break;
            case (GlobalConstants.LIFE):
                blockBitmap = lifeBitmap;
                break;
            case (GlobalConstants.ICE):
                blockBitmap = iceBitmap;
                break;
            case (GlobalConstants.GOLD):
                blockBitmap = goldBitmap;
                break;
            case (GlobalConstants.CRYSTAL):
                blockBitmap = crystalBase;
                break;
            case (GlobalConstants.GASROCK):
                blockBitmap = gasRockBitmap;
                break;
            case (GlobalConstants.COSTUMEGEM):
                blockBitmap = costumeGemBitmap;
                break;
            default:
                blockBitmap = null;
                break;
        }
        return blockBitmap;
    }

    public void moveWaterRight()
    {
        Block aboveBlock = getBlockFromArrayUsingScreenCoordinates(mGameWidth/2, mGameHeight/2 - blockSize);
        if (aboveBlock.getWaterPercentage() == 0 && waterDelay % 3 == 0)
        {
            Block currentBlock = getBlockFromArrayUsingScreenCoordinates(mGameWidth/2, mGameHeight/2);
            Block rightBlock = getBlockFromArrayUsingScreenCoordinates(mGameWidth/2 + blockSize, mGameHeight/2);
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
        Block aboveBlock = getBlockFromArrayUsingScreenCoordinates(mGameWidth/2, mGameHeight/2 - blockSize);
        if (!aboveBlock.hasWater() && waterDelay % 3 == 0)
        {
            Block currentBlock = getBlockFromArrayUsingScreenCoordinates(mGameWidth/2, mGameHeight/2);
            Block leftBlock = getBlockFromArrayUsingScreenCoordinates(mGameWidth/2 - blockSize, mGameHeight/2);
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
        Block aboveBlock = getBlockFromArrayUsingScreenCoordinates(mGameWidth/2, mGameHeight/2 - blockSize);
        if (aboveBlock.getWaterPercentage() < 50 && waterDelay % 3 == 0)
        {
            Block currentBlock = getBlockFromArrayUsingScreenCoordinates(mGameWidth/2, mGameHeight/2);
            Block topLeftBlock = getBlockFromArrayUsingScreenCoordinates(mGameWidth/2 - blockSize, mGameHeight/2 - blockSize);
            Block topRightBlock = getBlockFromArrayUsingScreenCoordinates(mGameWidth/2 + blockSize, mGameHeight/2 - blockSize);
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

    public Block[][] getBlockArray()
    {
        return blockArray;
    }

    public void setBlockArray(Block[][] newBlockArray)
    {
        blockArray = newBlockArray;
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
