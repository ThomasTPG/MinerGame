package com.example.thomas.miner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Thomas on 11/09/2017.
 */

public class BlockDrawing {

    ArrayOfBlocksOnScreen blocksOnScreen;
    Block[][] blockArray;
    private Context context;
    int blockSize;
    int borderSize;
    Camera mCamera;
    int mGameWidth;
    int mGameHeight;
    int blocksHorizontalScreen;
    int blocksVerticalScreen;

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

    public BlockDrawing(ArrayOfBlocksOnScreen arrayOfBlocksOnScreen, Context context, int blockDimensions, Camera camera, int gameWidth, int gameHeight)
    {
        blocksOnScreen = arrayOfBlocksOnScreen;
        blocksHorizontalScreen = blocksOnScreen.getBlocksHorizontalScreen();
        blocksVerticalScreen = blocksOnScreen.getBlocksVerticalScreen();
        this.context = context;
        blockSize = blockDimensions;
        borderSize = blocksOnScreen.getBorderSize();
        mCamera = camera;
        mGameHeight = gameHeight;
        mGameWidth = gameWidth;
        loadBitmaps();
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

    public void drawBlocks(Canvas c)
    {
        blockArray = blocksOnScreen.getBlockArray();
        for (int ii = 0; ii < blocksHorizontalScreen; ii ++)
        {
            for (int jj = 0; jj < blocksVerticalScreen; jj++)
            {
                Bitmap blockBitmap = workOutBitmap(blockArray[ii + borderSize][jj + borderSize]);

                int screenTopLeftX = mCamera.getCameraX() - mGameWidth/2;
                int screenTopLeftY = mCamera.getCameraY() - mGameHeight/2;
                int topLeftX = (int) Math.max(screenTopLeftX, blockSize * Math.floor(screenTopLeftX / (double)blockSize + ii));
                int topLeftY = (int) Math.max(screenTopLeftY,  blockSize * Math.floor(screenTopLeftY / (double)blockSize + jj));
                int topRightX = (int) Math.min(blockSize * Math.floor(screenTopLeftX / (double)blockSize + ii+1), screenTopLeftX + mGameWidth);
                int bottomLeftY = (int) Math.min(blockSize * Math.floor(screenTopLeftY / (double)blockSize + jj+1), screenTopLeftY + mGameHeight);
                Rect location = new Rect(topLeftX - mCamera.getCameraX() + mGameWidth/2, topLeftY -mCamera.getCameraY() + mGameHeight/2, topRightX - mCamera.getCameraX() + mGameWidth/2, bottomLeftY - mCamera.getCameraY() + mGameHeight/2);

                if (blockBitmap != null)
                {
                    //Now work out the scalings for the blocks
                    Rect source = getScaledRectangle(blockBitmap,location,ii,jj);

                    if (blockArray[ii +borderSize][jj+ borderSize].getType() == GlobalConstants.LIFE)
                    {
                        if (blockArray[ii +borderSize][jj+ borderSize].getWaterPercentage() < 100)
                        {
                            c.drawBitmap(lifeBitmap,source,location,null);
                        }
                        else
                        {
                            c.drawBitmap(lifeGrown,source,location,null);
                        }
                    }
                    else if (blockArray[ii +borderSize][jj+ borderSize].getType() == GlobalConstants.CRYSTAL)
                    {
                        if (!blockArray[ii +borderSize][jj+ borderSize].getFrozen())
                        {
                            c.drawBitmap(crystalBase,source,location,null);
                        }
                        else
                        {
                            c.drawBitmap(crystal3,source,location,null);
                        }
                    }
                    else
                    {
                        c.drawBitmap(blockBitmap,source,location,null);
                    }


                    //Finally overlay any mining
                    if (blockArray[ii + borderSize][jj + borderSize].isCurrentlyBeingMined())
                    {
                        c.drawBitmap(miningborder,source,location,null);
                    }
                    if (blockArray[ii + borderSize][jj + borderSize].getMiningProgress() >= 70)
                    {
                        c.drawBitmap(mining2,source,location,null);
                    }
                    else if (blockArray[ii +borderSize][jj+ borderSize].getMiningProgress() >= 40)
                    {
                        c.drawBitmap(mining1,source,location,null);
                    }
                }
                else
                {
                    //Draw fluids
                    if (blockArray[ii + borderSize][jj + borderSize].blockHasLiquid())
                    {
                        drawFluidBlock(blockArray[ii+borderSize][jj + borderSize].getBlockLiquidData(),c,location);
                    }
                    //Draw borders
                    Rect source = getScaledRectangle(bottomBorder,location,ii,jj);
                    drawBorders(ii,jj,source,location,c);
                }
                //Overlay any bombs
                if (blockArray[ii + borderSize][jj + borderSize].getBomb() != ActiveBombs.NO_BOMB)
                {
                    switch (blockArray[ii + borderSize][jj + borderSize].getBomb())
                    {
                        case(ActiveBombs.DYNAMITE):
                            c.drawBitmap(dynamite,null,location,null);
                            break;
                        case(ActiveBombs.ICEBOMB):
                            c.drawBitmap(iceBomb,null,location,null);
                            break;
                    }
                }
            }
        }
    }


    private void drawBorders(int ii, int jj, Rect source, Rect location, Canvas canvas)
    {
        int whichBitmapCode = 1;
        //Draw any borders
        if (blockArray[ii +borderSize - 1][jj+ borderSize].isSolid())
        {
            whichBitmapCode *=7;
        }
        if (blockArray[ii +borderSize + 1][jj+ borderSize].isSolid())
        {
            whichBitmapCode *=3;
        }
        if (blockArray[ii +borderSize][jj+ borderSize - 1].isSolid())
        {
            whichBitmapCode *=2;
        }
        if (blockArray[ii +borderSize][jj+ borderSize + 1].isSolid())
        {
            whichBitmapCode *=5;
        }
        //Code is given by
        //   2
        //  7 3
        //   5
        switch (whichBitmapCode)
        {
            case (2):
                canvas.drawBitmap(topBorder,source,location,null);
                break;
            case (3):
                canvas.drawBitmap(rightBorder,source,location,null);
                break;
            case (5):
                canvas.drawBitmap(bottomBorder,source,location,null);
                break;
            case (7):
                canvas.drawBitmap(leftBorder,source,location,null);
                break;
            case (6):
                canvas.drawBitmap(topRightBorder,source,location,null);
                break;
            case (14):
                canvas.drawBitmap(topLeftBorder,source,location,null);
                break;
            case (15):
                canvas.drawBitmap(bottomRightBorder,source,location,null);
                break;
            case (35):
                canvas.drawBitmap(bottomLeftBorder,source,location,null);
                break;
            case (21):
                canvas.drawBitmap(leftBorder,source,location,null);
                canvas.drawBitmap(rightBorder,source,location,null);
                break;
            case (10):
                canvas.drawBitmap(topBorder,source,location,null);
                canvas.drawBitmap(bottomBorder,source,location,null);
                break;
            case (105):
                canvas.drawBitmap(bottomSurroundBorder,source,location,null);
                break;
            case (210):
                canvas.drawBitmap(allBorders,source,location,null);
                break;
        }
    }

    private void drawFluidBlock(NonSolidBlocks blockLiquidData, Canvas canvas, Rect location)
    {
        int totalPercent = blockLiquidData.getGasPercentage() + blockLiquidData.getWaterPercentage();
        int overlay = 0;
        if (totalPercent > 100)
        {
            overlay = totalPercent - 100;
        }
        int gasHeight = (int) ((double)(Math.min(100 - blockLiquidData.getWaterPercentage(), blockLiquidData.getGasPercentage()) * blockSize)/(double)100);
        int waterHeight = (int) ((double) ((100 - (blockLiquidData.getWaterPercentage() - overlay)) * blockSize) /(double) 100);

        if (gasHeight > 0)
        {
            Rect scaledLocation = new Rect(location.left, location.top, location.right, location.top + gasHeight);
            canvas.drawBitmap(gasBitmap,null,scaledLocation,null);
        }
        if (overlay > 0)
        {
            Rect scaledLocation = new Rect(location.left,location.top + gasHeight,location.right,location.top + waterHeight);
            canvas.drawBitmap(gasWaterBitmap,null,scaledLocation,null);
        }
        if (waterHeight < 100)
        {
            Rect scaledLocation = new Rect(location.left, location.top + waterHeight, location.right, location.bottom);
            canvas.drawBitmap(waterBitmap,null,scaledLocation,null);
        }
    }

    private Rect getScaledRectangle(Bitmap blockBmp, Rect location, int ii, int jj)
    {
        int left = 0;
        int right = blockBmp.getWidth();
        int top = 0;
        int bottom = blockBmp.getHeight();
        if (location.width() < blockSize)
        {
            if (ii == 0)
            {
                double scale = 1 - location.width()/(double)blockSize;
                left = (int) (blockBmp.getWidth() * scale);
            }
            else if (ii == blocksHorizontalScreen-1)
            {
                double scale = location.width()/(double)blockSize;
                right = (int) (blockBmp.getWidth() * scale);
            }

        }
        if (location.height() < blockSize)
        {
            if (jj == 0)
            {
                double scale = 1 - location.height()/(double)blockSize;
                top = (int) (blockBmp.getHeight() * scale);
            }
            else if (jj >= blocksVerticalScreen - 2)
            {
                double scale = location.height()/(double)blockSize;
                bottom = (int) (blockBmp.getHeight() * scale);
            }
        }

        Rect source = new Rect(left, top, right, bottom);
        return source;
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
}
