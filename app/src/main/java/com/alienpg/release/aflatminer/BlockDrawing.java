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
    private BorderBitmapManager borderBitmapManager;
    private InGameNotifications inGameNotifications;
    private Achievements achievementManager;
    private Bitmap background1;

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
                    inGameNotifications.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.water_test));
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
                    inGameNotifications.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.gas_background));
                    newUnlock = true;
                }
            }
        }
        if (!encyclopediaMemory.isOreUnlocked(type))
        {
            encyclopediaMemory.writeFile(type);
            Bitmap b = block.getBitmap();
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

    private Rect getLocationRectangle(int ii, int jj)
    {
        int screenTopLeftX = mCamera.getCameraX() - mGameWidth/2;
        int screenTopLeftY = mCamera.getCameraY() - mGameHeight/2;
        int topLeftX = (int) Math.max(screenTopLeftX, blockSize * Math.floor(screenTopLeftX / (double)blockSize + ii));
        int topLeftY = (int) Math.max(screenTopLeftY,  blockSize * Math.floor(screenTopLeftY / (double)blockSize + jj));
        int topRightX = (int) Math.min(blockSize * Math.floor(screenTopLeftX / (double)blockSize + ii+1), screenTopLeftX + mGameWidth);
        int bottomLeftY = (int) Math.min(blockSize * Math.floor(screenTopLeftY / (double)blockSize + jj+1), screenTopLeftY + mGameHeight);
        Rect location = new Rect(topLeftX - mCamera.getCameraX() + mGameWidth/2, topLeftY -mCamera.getCameraY() + mGameHeight/2, topRightX - mCamera.getCameraX() + mGameWidth/2, bottomLeftY - mCamera.getCameraY() + mGameHeight/2);
        return location;
    }

    public void drawBlocks(Canvas c)
    {
        blockArray = blocksOnScreen.getBlockArray();
        for (int ii = 0; ii < blocksHorizontalScreen; ii ++)
        {
            for (int jj = 0; jj < blocksVerticalScreen; jj++)
            {
                Block currentBlock = blockArray.getBlock(ii + borderSize, jj + borderSize);

                checkEncyclopedia(currentBlock);

                Rect location = getLocationRectangle(ii,jj);

                Rect source = getScaledRectangle(currentBlock.getBitmap(),location,ii,jj);
                currentBlock.draw(c, source, location, blockSize, mGameHeight);
                if (currentBlock.getType() == GlobalConstants.ICE || currentBlock.getType() == GlobalConstants.CAVERN)
                {
                    source = getScaledRectangle(currentBlock.getBitmap(),location,ii,jj);
                    borderBitmapManager.drawBorders(blockArray, ii, jj, c,source,location);
                }
            }
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
}
