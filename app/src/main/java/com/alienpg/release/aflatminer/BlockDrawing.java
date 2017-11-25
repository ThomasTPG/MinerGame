package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Thomas on 11/09/2017.
 */

public class BlockDrawing {

    BlockManager blocksOnScreen;
    BlockArray blockArray;
    private Context context;
    int blockSize;
    int borderSize;
    Camera mCamera;
    int mGameWidth;
    int mGameHeight;
    int blocksHorizontalScreen;
    int blocksVerticalScreen;
    private EncyclopediaMemory encyclopediaMemory;
    int crystalCount = 0;

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
    private Bitmap explodiumBitmap;
    private Bitmap marbleBitmap;
    private Bitmap springBitmap;
    private Bitmap waterBitmap;
    private Bitmap gasBitmap;
    private Bitmap gasWaterBitmap;
    private Bitmap life1;
    private Bitmap life2;
    private Bitmap life3;
    private Bitmap life4;
    private Bitmap life5;
    private Bitmap life6;
    private Bitmap iceBitmap;
    private Bitmap goldBitmap;
    private Bitmap crystalBase;
    private Bitmap gasRockBitmap;
    private Bitmap costumeGemBitmap;
    private Bitmap tinBitmap;
    private Bitmap crystal1;
    private Bitmap crystal2;
    private Bitmap crystal3;
    private Bitmap crystal4;
    private Bitmap crystal5;
    private Bitmap dynamite;
    private Bitmap iceBomb;
    private Bitmap background1;
    BorderBitmapManager borderBitmapManager;
    private InGameNotifications inGameNotifications;
    private Achievements achievementManager;

    public BlockDrawing(BlockManager blockManager, Context context, int blockDimensions, Camera camera, int gameWidth, int gameHeight, EncyclopediaMemory encyclopediaMemory, InGameNotifications inGameNotifications, Achievements achievements)
    {
        blocksOnScreen = blockManager;
        blocksHorizontalScreen = blocksOnScreen.getBlocksHorizontalScreen();
        blocksVerticalScreen = blocksOnScreen.getBlocksVerticalScreen();
        this.context = context;
        this.encyclopediaMemory = encyclopediaMemory;
        blockSize = blockDimensions;
        borderSize = blocksOnScreen.getBorderSize();
        mCamera = camera;
        mGameHeight = gameHeight;
        mGameWidth = gameWidth;
        this.inGameNotifications = inGameNotifications;
        achievementManager = achievements;
        achievementManager.checkEncyclopediaUnlock(encyclopediaMemory.getNumberUnlocked());
        borderBitmapManager = new BorderBitmapManager(context, borderSize);
        loadBitmaps();
    }


    private void loadBitmaps()
    {
        mining1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.miningprogress1);
        mining2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.miningprogress2);
        miningborder = BitmapFactory.decodeResource(context.getResources(), R.drawable.whichmined);
        waterBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.water_test);
        gasBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gas_background);
        gasWaterBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gaswater);
        soil1Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil);
        soil2Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil2);
        boulderBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rock);
        hardBoulderBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hardrock);
        fireBallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fireball);
        copperBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.copper);
        ironBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.iron);
        explodiumBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.explodium);
        marbleBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marble);
        springBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.spring);
        tinBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tin);
        life1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.life_1);
        life2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.life_2);
        life3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.life_3);
        life4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.life_4);
        life5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.life_5);
        life6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.life_6);
        iceBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ice_test);
        goldBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gold);
        crystalBase = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystalbase);
        gasRockBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gasrock);
        costumeGemBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.costumegem);
        crystal1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystal1);
        crystal2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystal2);
        crystal3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystal3);
        crystal4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystal4);
        crystal5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystal5);
        dynamite = BitmapFactory.decodeResource(context.getResources(), R.drawable.dynamitesquare);
        iceBomb = BitmapFactory.decodeResource(context.getResources(), R.drawable.icedynamitesquare);
        background1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_cave_1);
    }

    private void checkEncyclopedia(Block block)
    {
        boolean newUnlock = false;
        int type = block.getType();
        if (!encyclopediaMemory.isOreUnlocked(GlobalConstants.WATER))
        {
            if (type == GlobalConstants.CAVERN)
            {
                if (block.getWaterPercentage() > 0)
                {
                    encyclopediaMemory.writeFile(GlobalConstants.WATER);
                    inGameNotifications.setCurrentNotification(InGameNotifications.NEW_ENCYCLOPEDIA_ENTRY);
                    inGameNotifications.setBitmap(waterBitmap);
                    newUnlock = true;
                }
            }
        }
        if (!encyclopediaMemory.isOreUnlocked(GlobalConstants.GAS))
        {
            if (type == GlobalConstants.CAVERN)
            {
                if (block.getGasPercentage() > 0)
                {
                    encyclopediaMemory.writeFile(GlobalConstants.GAS);
                    inGameNotifications.setCurrentNotification(InGameNotifications.NEW_ENCYCLOPEDIA_ENTRY);
                    inGameNotifications.setBitmap(gasBitmap);
                    newUnlock = true;
                }
            }
        }
        if (!encyclopediaMemory.isOreUnlocked(type))
        {
            encyclopediaMemory.writeFile(type);
            Bitmap b = workOutBitmap(block);
            if ((b != null && block.getType() != GlobalConstants.FIREBALL))
            {
                inGameNotifications.setCurrentNotification(InGameNotifications.NEW_ENCYCLOPEDIA_ENTRY);
                inGameNotifications.setBitmap(b);
                newUnlock = true;
            }
        }
        if (newUnlock)
        {
            achievementManager.checkEncyclopediaUnlock(encyclopediaMemory.getNumberUnlocked());
        }
    }

    public void drawBlocks(Canvas c)
    {
        blockArray = blocksOnScreen.getBlockArray();
        for (int ii = 0; ii < blocksHorizontalScreen; ii ++)
        {
            for (int jj = 0; jj < blocksVerticalScreen; jj++)
            {
                Block currentBlock = blockArray.getBlock(ii + borderSize, jj + borderSize);
                Bitmap blockBitmap = workOutBitmap(currentBlock);

                checkEncyclopedia(currentBlock);

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

                    if (currentBlock.getType() == GlobalConstants.ICE)
                    {
                        c.drawBitmap(blockBitmap,source,location,null);
                        borderBitmapManager.drawBorders(blockArray, ii, jj, c,source,location);
                    }
                    else
                    {
                        c.drawBitmap(blockBitmap,source,location,null);
                    }


                    //Finally overlay any mining
                    if (currentBlock.isCurrentlyBeingMined())
                    {
                        c.drawBitmap(miningborder,source,location,null);
                    }
                    if (currentBlock.getMiningProgress() >= 70)
                    {
                        c.drawBitmap(mining2,source,location,null);
                    }
                    else if (currentBlock.getMiningProgress() >= 40)
                    {
                        c.drawBitmap(mining1,source,location,null);
                    }
                }
                else
                {
                    Rect source = getScaledRectangle(background1,location,ii,jj);
                    c.drawBitmap(background1,source,location,null);
                    //Draw fluids
                    if (currentBlock.blockHasLiquid())
                    {
                        drawFluidBlock(currentBlock.getBlockLiquidData(),c,location, ii ,jj);
                    }
                    //Draw borders
                    borderBitmapManager.drawBorders(blockArray, ii, jj, c,source,location);
                }
                //Overlay any bombs
                if (currentBlock.getBomb() != ActiveBombs.NO_BOMB)
                {
                    switch (currentBlock.getBomb())
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

    private void drawFluidBlock(NonSolidBlocks blockLiquidData, Canvas canvas, Rect location, int ii, int jj)
    {
        int totalPercent = blockLiquidData.getGasPercentage() + blockLiquidData.getWaterPercentage();
        int overlay = 0;
        if (totalPercent > 100)
        {
            overlay = totalPercent - 100;
        }
        int gasHeight = (int) ((double)(Math.min(100 - blockLiquidData.getWaterPercentage(), blockLiquidData.getGasPercentage()) * blockSize)/(double)100);
        double waterPercentageDown = ((double) (100 - (blockLiquidData.getWaterPercentage() - overlay)) /(double) 100);
        int waterHeight = (int) Math.ceil(waterPercentageDown * blockSize);

        if (gasHeight > 0)
        {
            int bottomBmp = (int) ((double)(Math.min(100 - blockLiquidData.getWaterPercentage(), blockLiquidData.getGasPercentage()) * gasBitmap.getHeight())/(double)100);

            Rect whichBmp = getScaledRectangle(gasBitmap,location,ii,jj);
            whichBmp.bottom = Math.min(bottomBmp, whichBmp.bottom);
            Rect scaledLocation;
            if (location.top  == 0)
            {
                scaledLocation = new Rect(location.left, location.top, location.right, location.bottom - blockSize + gasHeight);
            }
            else if (location.height() < gasHeight)
            {
                scaledLocation = new Rect(location.left, location.top, location.right, mGameHeight);
            }
            else
            {
                scaledLocation = new Rect(location.left, location.top, location.right, location.top + gasHeight);
            }
            canvas.drawBitmap(gasBitmap,whichBmp,scaledLocation,null);
        }
        if (overlay > 0)
        {
            Rect scaledLocation = new Rect(location.left,location.top + gasHeight,location.right,location.top + waterHeight);
            canvas.drawBitmap(gasWaterBitmap,null,scaledLocation,null);
        }
        if (waterHeight < 100)
        {
            int topBmp = (int) ((double)((100 - blockLiquidData.getWaterPercentage() - overlay) * waterBitmap.getHeight())/(double)100);
            Rect scaledLocation;
            int top = Math.max(topBmp,getTopScaling(waterBitmap,location,jj));
            Rect whichBmp = new Rect(getLeftScaling(waterBitmap,location,ii), top,getRightScaling(waterBitmap,location,ii),getBottomScaling(waterBitmap, location,jj));//getScaledRectangleWater(location,ii,jj,waterPercentageDown);
            if (location.top  == 0)
            {
                if (location.height() < blockSize - waterHeight)
                {
                    scaledLocation = new Rect(location.left, 0, location.right, location.bottom);
                }
                else
                {
                    scaledLocation = new Rect(location.left, location.bottom - blockSize + waterHeight, location.right, location.bottom);
                }
            }
            else
            {
                scaledLocation = new Rect(location.left, location.top + waterHeight, location.right, location.bottom);
            }
            canvas.drawBitmap(waterBitmap,whichBmp,scaledLocation,null);
        }
    }

    private int getLeftScaling(Bitmap bitmap, Rect location, int ii)
    {
        int left = 0;
        if (location.width() < blockSize)
        {
            if (ii == 0)
            {
                double scale = 1 - location.width()/(double)blockSize;
                left = (int) (bitmap.getWidth() * scale);
            }
        }
        return left;
    }

    private int getRightScaling(Bitmap bitmap, Rect location, int ii)
    {
        int right = bitmap.getWidth();
        if (location.width() < blockSize)
        {
            if (ii == blocksHorizontalScreen-1)
            {
                double scale = location.width()/(double)blockSize;
                right = (int) (bitmap.getWidth() * scale);
            }
        }
        return right;
    }

    private int getTopScaling(Bitmap bitmap, Rect location, int jj)
    {
        int top = 0;
        if (location.height() < blockSize) {
            if (jj == 0) {
                double scaleDueToScreen = 1 - location.height() / (double) blockSize;
                top = (int) Math.floor(bitmap.getHeight() * scaleDueToScreen);
            }
        }
        return top;
    }

    private int getBottomScaling(Bitmap bitmap, Rect location ,int jj)
    {
        int bottom = bitmap.getHeight();
        if (location.height() < blockSize)
        {
            if (jj >= blocksVerticalScreen - 2)
            {
                double scale = location.height()/(double)blockSize;
                bottom = (int) (bitmap.getHeight() * scale);
            }
        }
        return bottom;

    }

    private Rect getScaledRectangle(Bitmap blockBmp, Rect location, int ii, int jj)
    {
        return new Rect(getLeftScaling(blockBmp,location,ii), getTopScaling(blockBmp,location,jj), getRightScaling(blockBmp,location,ii), getBottomScaling(blockBmp,location,jj));
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
            case (GlobalConstants.EXPLODIUM):
                blockBitmap = explodiumBitmap;
                break;
            case (GlobalConstants.MARBLE):
                blockBitmap = marbleBitmap;
                break;
            case (GlobalConstants.SPRING):
                blockBitmap = springBitmap;
                break;
            case (GlobalConstants.LIFE):
                if (currentBlock.getWaterPercentage() >= 100)
                {
                    blockBitmap = life6;
                }
                else if (currentBlock.getWaterPercentage() > 80)
                {
                    blockBitmap = life5;
                }
                else if (currentBlock.getWaterPercentage() > 60)
                {
                    blockBitmap = life4;
                }
                else if (currentBlock.getWaterPercentage() > 40)
                {
                    blockBitmap = life3;
                }
                else
                {
                    blockBitmap = life2;
                }
                break;
            case (GlobalConstants.ICE):
                blockBitmap = iceBitmap;
                break;
            case (GlobalConstants.GOLD):
                blockBitmap = goldBitmap;
                break;
            case (GlobalConstants.CRYSTAL):
                int surroundingIce = currentBlock.getSurroundingIce();
                if (surroundingIce == 0)
                {
                    blockBitmap = crystal1;
                }
                else if (surroundingIce < 3)
                {
                    blockBitmap = crystal2;
                }
                else if (surroundingIce < GlobalConstants.CRYSTALFREEZEAMOUNT)
                {
                    blockBitmap = crystal3;
                }
                else
                {
                    if (crystalCount < 10)
                    {
                        blockBitmap = crystal4;
                    }
                    else
                    {
                        blockBitmap = crystal5;
                    }
                    crystalCount ++;
                    if (crystalCount == 20)
                    {
                        crystalCount = 0;
                    }
                }
                break;
            case (GlobalConstants.GASROCK):
                blockBitmap = gasRockBitmap;
                break;
            case (GlobalConstants.COSTUMEGEM):
                blockBitmap = costumeGemBitmap;
                break;
            case (GlobalConstants.TIN):
                blockBitmap = tinBitmap;
                break;
            default:
                blockBitmap = null;
                break;
        }
        return blockBitmap;
    }
}
