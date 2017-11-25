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
    private int yCoord;
    private NonSolidBlocks blockLiquidData;
    private BlockStatusData blockStatusData;

    private int thresholdWaterToFreeze = 15;
    private int waterProducedFromIce = 12;
    private int index;
    Context context;
    private int blocksAcross;
    private int seed;
    private MinedLocations mMinedLocations;
    private int miningLimit = 100;
    private double pickaxeRequired;
    private int blocksPerScreen;
    private int bombType = 0;
    private boolean currentlyBeingMined = false;
    private int surroundingIce = 0;
    private int heightFallen = 0;
    private boolean achievementChainReactionII = false;
    private Bitmap mBitmap;
    private BitmapStore bitmapStore;
    protected BlockBitmapManager blockBitmapManager;
    protected MiningBitmapManager miningBitmapManager;
    private int MINING_STAGE_1 = 40;
    private int MINING_STAGE_2 = 70;

    //If this is a crystal block, we need to record the maximum ice that has surrounded it.
    private boolean frozen = false;

    public Block (Coordinates coordinates,int seed, Context context, int blocksAcross, MinedLocations minedLocations, BitmapStore bitmapStore)
    {
        xCoord = coordinates.getX();
        yCoord = coordinates.getY();
        index = yCoord * blocksAcross + xCoord;
        this.context = context;
        this.blocksAcross = context.getResources().getInteger(R.integer.blocks_across);
        this.blocksPerScreen = context.getResources().getInteger(R.integer.blocks_per_screen_width);
        this.seed = seed;
        blockLiquidData = new NonSolidBlocks();
        blockStatusData = new BlockStatusData();
        mMinedLocations = minedLocations;
        this.bitmapStore = bitmapStore;
        miningBitmapManager = bitmapStore.getMiningBitmapManager();
        blockBitmapManager = bitmapStore.getBlockBitmapManager();
        determineType();
    }

    public Block(Block oldBlock)
    {
        index = oldBlock.getIndex();
        xCoord = oldBlock.getX();
        yCoord = oldBlock.getY();
        context = oldBlock.getContext();
        blocksAcross = context.getResources().getInteger(R.integer.blocks_across);
        blocksPerScreen = context.getResources().getInteger(R.integer.blocks_per_screen_width);
        seed = oldBlock.getSeed();
        blockLiquidData = oldBlock.getBlockLiquidData();
        blockStatusData = oldBlock.getBlockStatusData();
        mMinedLocations = oldBlock.getmMinedLocations();
        bitmapStore = oldBlock.getBitmapStore();
        miningBitmapManager = bitmapStore.getMiningBitmapManager();
        blockBitmapManager = bitmapStore.getBlockBitmapManager();
        saveToMemory();
        determineType();
    }

    public MinedLocations getmMinedLocations()
    {
        return mMinedLocations;
    }

    int getSeed()
    {
        return seed;
    }

    public Context getContext()
    {
        return context;
    }

    private void determineType()
    {
        if (mMinedLocations.isItemContained(index))
        {
            setType(mMinedLocations.getThisType());
            blockLiquidData.setWaterPercentage(mMinedLocations.getWaterPercentage(index));
            blockLiquidData.setGasPercentage(mMinedLocations.getGasPercentage(index));
            blockStatusData.setMinedPercentage(mMinedLocations.getMinedPercentage(index));
        }
        else
        {
            CavernCreator c = new CavernCreator(xCoord,yCoord,blocksAcross,seed);
            if (c.getIsCavern())
            {
                //Determine if the type oft he block is a cavern. Also includes y<0 regions at the top.
                setType(GlobalConstants.CAVERN);

                int distance = c.getDistanceToCavern();
                if (yCoord > 0)
                {
                    Random waterRandom = new Random(seed * (xCoord + yCoord) * yCoord * xCoord * xCoord);
                    int rand = (int) (waterRandom.nextDouble() * 1000);
                    int waterLiklihood = 0;
                    if (distance < 50)
                    {
                        waterLiklihood = 50 - distance;
                    }
                    if (rand > 990 - waterLiklihood)
                    {
                        blockLiquidData.setWaterPercentage(100);
                        saveToMemory();
                    }

                }
            }
            else{
                // Not a cavern - generate a solid block instead
                //Find the distance to the nearest cavern
                int distance = c.getDistanceToCavern();
                int oreLiklihood = 0;
                if (distance < 20)
                {
                    oreLiklihood = (int) (4*Math.pow((20-distance),1.2));
                }
                Random generator = new Random(seed * (xCoord + yCoord) * yCoord * xCoord * xCoord);
                int rand = (int) (generator.nextDouble() * 1000);
                if (rand > 900)
                {
                    setType(GlobalConstants.BOULDER);
                }
                else if (rand > 890 - oreLiklihood)
                {
                    setOre();
                }
                else
                {
                    setType(GlobalConstants.SOIL);
                }
            }
        }
        if (xCoord < context.getResources().getInteger(R.integer.left_hand_side_wiggle_room) || xCoord > blocksAcross)
        {
            setType(GlobalConstants.HARD_BOULDER);
        }
        if (yCoord == 0)
        {
            if (Math.abs(xCoord - blocksAcross) < blocksPerScreen )
            {
                setType(GlobalConstants.HARD_BOULDER);
            }
        }
    }


    private void setOre()
    {
        Random oreRandom = new Random(seed * xCoord * yCoord + yCoord*xCoord);
        double randomOreType = oreRandom.nextDouble();
        if (yCoord < 20)
        {
            setType(OreHeightTables.determineOreTable1(randomOreType));
        }
        else if (yCoord < 40)
        {
            setType(OreHeightTables.determineOreTable2(randomOreType));
        }
        else if (yCoord < 60)
        {
            setType(OreHeightTables.determineOreTable3(randomOreType));
        }
        else if (yCoord < 80)
        {
            setType(OreHeightTables.determineOreTable4(randomOreType));
        }
        else
        {
            setType(OreHeightTables.determineOreTable5(randomOreType));
        }
    }

    public int getMinedStage()
    {
        if (blockStatusData.getMinedPercentage() >= GlobalConstants.ALMOST_PERCENTAGE)
        {
            return GlobalConstants.ALMOST_MINED;
        }
        if (blockStatusData.getMinedPercentage() >= GlobalConstants.SLIGHT_PERCENTAGE)
        {
            return GlobalConstants.SLIGHTLY_MINED;
        }
        return GlobalConstants.UNMINED;
    }

    public void resetMiningProgress()
    {
        determineType();
        if (getMinedStage() == GlobalConstants.ALMOST_MINED)
        {
            blockStatusData.setMinedPercentage(GlobalConstants.ALMOST_PERCENTAGE);
        }
        else if (getMinedStage() == GlobalConstants.SLIGHTLY_MINED)
        {
            blockStatusData.setMinedPercentage(GlobalConstants.SLIGHT_PERCENTAGE);
        }
        else
        {
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

    public void saveToMemory()
    {
        mMinedLocations.addToMinedLocations(index, blockStatusData,blockLiquidData);
    }

    public int getX()
    {
        return xCoord;
    }

    public int getY()
    {
        return yCoord;
    }

    public boolean mineFurther(OreCounter oreCounter)
    {
        currentlyBeingMined = true;
        if (blockStatusData.getType() == GlobalConstants.LIFE && blockLiquidData.getWaterPercentage() < 100)
        {
            return false;
        }
        if (blockStatusData.getType() == GlobalConstants.CRYSTAL && getSurroundingIce() < GlobalConstants.CRYSTALFREEZEAMOUNT)
        {
            return false;
        }
        blockStatusData.incrementMinedPercentage();
        if (blockStatusData.getMinedPercentage() >= miningLimit)
        {
            oreCounter.incrementOre(blockStatusData.getType());
            if (blockStatusData.getType() == GlobalConstants.ICE)
            {
                blockLiquidData.setWaterPercentage(waterProducedFromIce);
            }
            else
            {
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

    public void detonateIceBomb()
    {
        setBomb(ActiveBombs.NO_BOMB);
        if (blockStatusData.getType() == GlobalConstants.CAVERN)
        {
            setType(GlobalConstants.ICE);
            blockLiquidData.setWaterPercentage(0);
            saveToMemory();
        }
    }

    public void explode()
    {
        final int initialType = getType();
        Thread explosionTimer = new Thread()
        {
            @Override
            public void run()
            {
                setGasPercentage(0);
                try {
                    synchronized (this)
                    {
                        int time = 2 + ((index * xCoord * yCoord )*(index * xCoord * yCoord));
                        wait((time*time)%200);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                setType(GlobalConstants.FIREBALL);

                try {
                    synchronized (this)
                    {
                        wait(200 + (index * xCoord * yCoord )%83);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                setType(GlobalConstants.CAVERN);
                if (initialType == GlobalConstants.ICE)
                {
                    setWaterPercentage(waterProducedFromIce);
                }
                achievementChainReactionII =false;
                saveToMemory();
            }
        };
        if (blockStatusData.getType() != GlobalConstants.HARD_BOULDER)
        {
            explosionTimer.start();
        }
    }

    public void blowUp()
    {
        setBomb(ActiveBombs.NO_BOMB);
        explode();
    }

    protected void setType(int newType)
    {
        blockStatusData.setType(newType);
        setSoftness();
    }

    public boolean hasWater()
    {
        return (blockLiquidData.getWaterPercentage() > 0);
    }

    public boolean hasGas()
    {
        return (blockLiquidData.getGasPercentage() > 0);
    }

    public NonSolidBlocks getLiquidData()
    {
        return blockLiquidData;
    }

    public boolean isIce()
    {
        return (blockStatusData.getType() == GlobalConstants.ICE);
    }

    public boolean isGasRock()
    {
        return (blockStatusData.getType() == GlobalConstants.GASROCK);
    }

    public boolean isFire()
    {
        return (blockStatusData.getType() == GlobalConstants.FIREBALL);
    }

    public boolean isSolid()
    {
        return (blockStatusData.getType()!= GlobalConstants.CAVERN && blockStatusData.getType()!= GlobalConstants.FIREBALL);
    }

    public boolean canNeedBorder()
    {
        return (isSolid() && !isIce());
    }


    public boolean isCavern()
    {
        return (blockStatusData.getType() == GlobalConstants.CAVERN);
    }

    public int getWaterPercentage()
    {
        return blockLiquidData.getWaterPercentage();
    }

    public int getGasPercentage()
    {
        return blockLiquidData.getGasPercentage();
    }

    public void setWaterPercentage(int pct)
    {
        if (pct < 0)
        {
            pct = 0;
        }
        blockLiquidData.setWaterPercentage(pct);
        if (blockStatusData.getType() != GlobalConstants.LIFE)
        {
            setType(GlobalConstants.CAVERN);
        }

        if (yCoord >= 0)
        {
            saveToMemory();
        }
    }

    public void setGasPercentage(int pct)
    {
        if (pct < 0)
        {
            pct = 0;
        }
        blockLiquidData.setGasPercentage(pct);
        setType(GlobalConstants.CAVERN);
        if (yCoord >= 0)
        {
            saveToMemory();
        }
    }

    protected void setSoftness(int pickaxeType)
    {
        pickaxeRequired = pickaxeType;
    }
    private void setSoftness()
    {
        switch (blockStatusData.getType())
        {
            case (GlobalConstants.CAVERN):
                pickaxeRequired = PickaxeTypes.CANNOT_BE_MINED;
                break;
            case (GlobalConstants.BOULDER):
                pickaxeRequired = PickaxeTypes.CANNOT_BE_MINED;
                break;
            case (GlobalConstants.HARD_BOULDER):
                pickaxeRequired = PickaxeTypes.CANNOT_BE_MINED;
                break;
            case (GlobalConstants.FIREBALL):
                pickaxeRequired = PickaxeTypes.CANNOT_BE_MINED;
                break;
            case (GlobalConstants.SOIL):
                pickaxeRequired = PickaxeTypes.WOOD_PICKAXE;
                break;
            case (GlobalConstants.COPPER):
                pickaxeRequired = PickaxeTypes.TIN_PICKAXE;
                break;
            case (GlobalConstants.IRON):
                pickaxeRequired = PickaxeTypes.COPPER_PICKAXE;
                break;
            case (GlobalConstants.EXPLODIUM):
                pickaxeRequired = PickaxeTypes.TIN_PICKAXE;
                break;
            case (GlobalConstants.MARBLE):
                pickaxeRequired = PickaxeTypes.COPPER_PICKAXE;
                break;
            case (GlobalConstants.SPRING):
                pickaxeRequired = PickaxeTypes.TIN_PICKAXE;
                break;
            case (GlobalConstants.LIFE):
                pickaxeRequired = PickaxeTypes.COPPER_PICKAXE;
                break;
            case (GlobalConstants.ICE):
                pickaxeRequired = PickaxeTypes.WOOD_PICKAXE;
                break;
            case (GlobalConstants.GOLD):
                pickaxeRequired = PickaxeTypes.IRON_PICKAXE;
                break;
            case (GlobalConstants.CRYSTAL):
                pickaxeRequired = PickaxeTypes.IRON_PICKAXE;
                break;
            case (GlobalConstants.GASROCK):
                pickaxeRequired = PickaxeTypes.COPPER_PICKAXE;
                break;
            case(GlobalConstants.TIN):
                pickaxeRequired = PickaxeTypes.WOOD_PICKAXE;
                break;
            case (GlobalConstants.COSTUMEGEM):
                pickaxeRequired = PickaxeTypes.WOOD_PICKAXE;
                break;
        }
    }

    public double getPickaxeRequired()
    {
        return pickaxeRequired;
    }

    public int getMiningProgress()
    {
        return blockStatusData.getMinedPercentage();
    }

    public int getType()
    {
        return blockStatusData.getType();
    }

    public boolean isCurrentlyBeingMined()
    {
        return currentlyBeingMined;
    }

    public void tryFreezing()
    {
        if (blockLiquidData.getWaterPercentage() > thresholdWaterToFreeze && isCavern())
        {
            Random iceRandom = new Random(System.nanoTime());
            if (iceRandom.nextDouble() > 0.99)
            {
                setType(GlobalConstants.ICE);
                blockLiquidData.setWaterPercentage(0);
                saveToMemory();
            }
        }
    }

    public void tryDecaying()
    {
        if (blockStatusData.getType() != GlobalConstants.GASROCK &&
                blockStatusData.getType() != GlobalConstants.BOULDER &&
                blockStatusData.getType() != GlobalConstants.HARD_BOULDER)
        {
            Random decayRandom = new Random(System.nanoTime() * index);
            if (decayRandom.nextDouble() > 0.99992)
            {
                blockStatusData.setMinedPercentage(GlobalConstants.ALMOST_PERCENTAGE);
                saveToMemory();
            }
        }
    }

    public void draw()
    {

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

    public boolean blockHasLiquid()
    {
        return (blockLiquidData.getWaterPercentage() > 0 || blockLiquidData.getGasPercentage() > 0 || blockLiquidData.getLavaPercentage()>0);
    }

    public void incrementGas()
    {
        int totalVol = getGasPercentage();
        if (totalVol < 100)
        {
            int incrementPct = Math.min(5, 100-totalVol);
            setGasPercentage(getGasPercentage() + incrementPct);
        }
    }

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

    public void setAchievementChainReactionII()
    {
        achievementChainReactionII = true;
    }

    public boolean getAchievementChainReactionII()
    {
        return achievementChainReactionII;
    }

    public  BitmapStore getBitmapStore()
    {
        return bitmapStore;
    }

    protected void setBitmap(Bitmap b)
    {
        mBitmap = b;
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

}
