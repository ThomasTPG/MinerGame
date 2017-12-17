package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Thomas on 25/01/2017.
 */

public class Block {

    private int xCoord;
    protected int yCoord;
    protected NonSolidBlocks blockLiquidData;
    protected BlockStatusData blockStatusData;
    protected int index;
    private MinedLocations mMinedLocations;
    private int miningLimit = 100;
    private double pickaxeRequired;
    private int bombType = 0;
    private boolean currentlyBeingMined = false;
    private int surroundingIce = 0;
    private boolean achievementChainReactionII = false;
    private Bitmap mBitmap;
    private BitmapFlyWeight bitmapFlyWeight;
    protected BlockBitmapManager blockBitmapManager;
    protected MiningBitmapManager miningBitmapManager;
    private int MINING_STAGE_1 = 40;
    private int MINING_STAGE_2 = 70;
    public static int waterProducedFromIce = 12;


    public Block(Coordinates coordinates, BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight) {
        xCoord = coordinates.getX();
        yCoord = coordinates.getY();
        this.index = blockSavedData.getIndex();
        blockLiquidData = blockSavedData.getNonSolidBlocks();
        blockStatusData = blockSavedData.getBlockStatusData();
        mMinedLocations = minedLocations;
        this.bitmapFlyWeight = bitmapFlyWeight;
        miningBitmapManager = bitmapFlyWeight.getMiningBitmapManager();
        blockBitmapManager = bitmapFlyWeight.getBlockBitmapManager();
    }

    public Block(Block oldBlock) {
        index = oldBlock.getIndex();
        xCoord = oldBlock.getX();
        yCoord = oldBlock.getY();
        blockLiquidData = oldBlock.getBlockLiquidData();
        blockStatusData = oldBlock.getBlockStatusData();
        mMinedLocations = oldBlock.getmMinedLocations();
        bitmapFlyWeight = oldBlock.getBitmapFlyWeight();
        miningBitmapManager = bitmapFlyWeight.getMiningBitmapManager();
        blockBitmapManager = bitmapFlyWeight.getBlockBitmapManager();
        saveToMemory();
    }

    public MinedLocations getmMinedLocations() {
        return mMinedLocations;
    }

    public int getMinedStage() {
        if (blockStatusData.getMinedPercentage() >= GlobalConstants.ALMOST_PERCENTAGE) {
            return GlobalConstants.ALMOST_MINED;
        }
        if (blockStatusData.getMinedPercentage() >= GlobalConstants.SLIGHT_PERCENTAGE) {
            return GlobalConstants.SLIGHTLY_MINED;
        }
        return GlobalConstants.UNMINED;
    }

    public void resetMiningProgress() {
        if (getMinedStage() == GlobalConstants.ALMOST_MINED) {
            blockStatusData.setMinedPercentage(GlobalConstants.ALMOST_PERCENTAGE);
        } else if (getMinedStage() == GlobalConstants.SLIGHTLY_MINED) {
            blockStatusData.setMinedPercentage(GlobalConstants.SLIGHT_PERCENTAGE);
        } else {
            blockStatusData.setMinedPercentage(0);

        }
        saveToMemory();
        currentlyBeingMined = false;
    }

    public NonSolidBlocks getBlockLiquidData() {
        return blockLiquidData;
    }

    public void setBlockLiquidData(NonSolidBlocks blockLiquidData) {
        this.blockLiquidData = blockLiquidData;
    }

    public BlockStatusData getBlockStatusData() {
        return blockStatusData;
    }

    public void setBlockStatusData(BlockStatusData blockStatusData) {
        this.blockStatusData = blockStatusData;
    }

    public void saveToMemory() {
        mMinedLocations.addToMinedLocations(index, blockStatusData, blockLiquidData);
    }

    public int getX() {
        return xCoord;
    }

    public int getY() {
        return yCoord;
    }

    public boolean mineFurther(OreCounter oreCounter) {
        currentlyBeingMined = true;
        if (blockStatusData.getType() == GlobalConstants.LIFE && blockLiquidData.getWaterPercentage() < 100) {
            return false;
        }
        if (blockStatusData.getType() == GlobalConstants.CRYSTAL && getSurroundingIce() < GlobalConstants.CRYSTALFREEZEAMOUNT) {
            return false;
        }
        blockStatusData.incrementMinedPercentage();
        if (blockStatusData.getMinedPercentage() >= miningLimit) {
            oreCounter.incrementOre(blockStatusData.getType());
            if (blockStatusData.getType() == GlobalConstants.ICE) {
                blockLiquidData.setWaterPercentage(waterProducedFromIce);
            } else {
                blockLiquidData.setWaterPercentage(0);
            }
            //setType(GlobalConstants.CAVERN);
            saveToMemory();
            blockStatusData.setMinedPercentage(0);
            currentlyBeingMined = false;
            return true;
        }
        return false;
    }

    protected void setType(int newType) {
        blockStatusData.setType(newType);
    }

    public boolean hasWater() {
        return (blockLiquidData.getWaterPercentage() > 0);
    }

    public NonSolidBlocks getLiquidData() {
        return blockLiquidData;
    }

    public boolean isIce() {
        return (blockStatusData.getType() == GlobalConstants.ICE);
    }

    public boolean isFire() {
        return (blockStatusData.getType() == GlobalConstants.FIREBALL);
    }

    public boolean isSolid() {
        return (blockStatusData.getType() != GlobalConstants.CAVERN && blockStatusData.getType() != GlobalConstants.FIREBALL);
    }

    public boolean canNeedBorder() {
        return (isSolid() && !isIce());
    }


    public boolean isCavern() {
        return (blockStatusData.getType() == GlobalConstants.CAVERN);
    }

    public int getWaterPercentage() {
        return blockLiquidData.getWaterPercentage();
    }

    public int getGasPercentage() {
        return blockLiquidData.getGasPercentage();
    }

    public void setWaterPercentage(int pct) {

    }

    public void setGasPercentage(int pct) {}

    protected void setSoftness(int pickaxeType) {
        pickaxeRequired = pickaxeType;
    }

    public double getPickaxeRequired() {
        return pickaxeRequired;
    }

    public int getMiningProgress() {
        return blockStatusData.getMinedPercentage();
    }

    public int getType() {
        return blockStatusData.getType();
    }

    public boolean isCurrentlyBeingMined() {
        return currentlyBeingMined;
    }

    public boolean canFreeze() {
        return false;
    }

    public void tryDecaying() {
        if (blockStatusData.getType() != GlobalConstants.GASROCK &&
                blockStatusData.getType() != GlobalConstants.BOULDER &&
                blockStatusData.getType() != GlobalConstants.HARD_BOULDER) {
            Random decayRandom = new Random(System.nanoTime() * index);
            if (decayRandom.nextDouble() > 0.99992) {
                blockStatusData.setMinedPercentage(GlobalConstants.ALMOST_PERCENTAGE);
                saveToMemory();
            }
        }
    }

    protected void drawBombs(Canvas c,  Rect loc)
    {
        switch (bombType)
        {
            case(ActiveBombs.DYNAMITE):
                c.drawBitmap(bitmapFlyWeight.getBlockBitmapManager().getDynamite(),null,loc,null);
                break;
            case(ActiveBombs.ICEBOMB):
                c.drawBitmap(bitmapFlyWeight.getBlockBitmapManager().getIceBomb(),null,loc,null);
                break;
        }
    }

    public void draw(Canvas c, Rect src, Rect loc, int blockSize, int gameHeight)
    {
        c.drawBitmap(mBitmap,src,loc,null);
        drawMining(c, src, loc);
        drawBombs(c, loc);
    }
    
    public void setBomb(int type)
    {
        bombType = type;
    }

    public int getBomb()
    {
        return bombType;
    }

    public int getIndex()
    {
        return index;
    }

    public void incrementGas() {}

    public void setSurroundingIce(int ice)
    {
        surroundingIce = ice;
    }

    public int getSurroundingIce()
    {
        return surroundingIce;
    }

    public void increaseFallenDistance()
    {
        blockStatusData.increaseFallenDistance();
    }

    public int getHeightFallen()
    {
        return blockStatusData.getHeightFallen();
    }

    public void resetHeightFallen()
    {
        blockStatusData.resetHeightFallen();
    }

    public void setAchievementChainReactionII(boolean set)
    {
        achievementChainReactionII = set;
    }

    public boolean getAchievementChainReactionII()
    {
        return achievementChainReactionII;
    }

    public BitmapFlyWeight getBitmapFlyWeight()
    {
        return bitmapFlyWeight;
    }

    protected void setBitmap(Bitmap b)
    {
        mBitmap = b;
    }

    public Bitmap getBitmap()
    {
        return mBitmap;
    }

    public void drawMining(Canvas canvas, Rect src, Rect location)
    {
        if (isCurrentlyBeingMined())
        {
            Bitmap border = miningBitmapManager.getMiningborder();
            canvas.drawBitmap(border,src,location,null);
        }
        if (getMiningProgress() >= MINING_STAGE_2)
        {
            Bitmap mining2 = miningBitmapManager.getMining2();
            canvas.drawBitmap(mining2,src,location,null);
        }
        else if (getMiningProgress() >= MINING_STAGE_1)
        {
            Bitmap mining1 = miningBitmapManager.getMining1();
            canvas.drawBitmap(mining1,src,location,null);
        }
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean canTurnIntoIce()
    {
        return false;
    }
}
