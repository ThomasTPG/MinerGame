package com.example.thomas.miner;

import android.content.Context;

import java.util.Random;

/**
 * Created by Thomas on 25/01/2017.
 */

public class Block {

    private int xCoord;
    private int yCoord;
    private NonSolidBlocks blockLiquidData;
    private BlockStatusData blockStatusData;

    private int thresholdWaterToFreeze = 10;
    private int waterProducedFromIce = 30;
    private int index;
    Context context;
    private int blocksAcross;
    private int seed;
    private MinedLocations mMinedLocations;
    private int miningLimit = 100;
    private double softness;
    private int blocksPerScreen;
    private int bombType = 0;
    private boolean currentlyBeingMined = false;

    //If this is a crystal block, we need to record the maximum ice that has surrounded it.
    private boolean frozen = false;

    public Block (int seed, int xCoord, int yCoord, Context context, int blocksAcross, MinedLocations minedLocations)
    {
        index = yCoord * blocksAcross + xCoord;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.context = context;
        this.blocksAcross = context.getResources().getInteger(R.integer.blocks_across);
        this.blocksPerScreen = context.getResources().getInteger(R.integer.blocks_per_screen_width);
        this.seed = seed;
        blockLiquidData = new NonSolidBlocks();
        blockStatusData = new BlockStatusData();
        mMinedLocations = minedLocations;
        determineType();
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
                else if (rand > 880 - oreLiklihood)
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
        if (yCoord < 30)
        {
            setType(OreHeightTables.determineOreTable1(randomOreType));
        }
        else if (yCoord < 60)
        {
            setType(OreHeightTables.determineOreTable2(randomOreType));
        }
        else if (yCoord < 90)
        {
            setType(OreHeightTables.determineOreTable3(randomOreType));
        }
        else if (yCoord < 120)
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
        if (blockStatusData.getType() == GlobalConstants.CRYSTAL && !frozen)
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
            setType(GlobalConstants.CAVERN);
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

    private void setType(int newType)
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


    private void setSoftness()
    {
        switch (blockStatusData.getType())
        {
            case (GlobalConstants.CAVERN):
                softness = 1;
                break;
            case (GlobalConstants.BOULDER):
                softness = 0;
                break;
            case (GlobalConstants.HARD_BOULDER):
                softness = 0;
                break;
            case (GlobalConstants.FIREBALL):
                softness = 0;
                break;
            case (GlobalConstants.SOIL):
                softness = 1;
                break;
            case (GlobalConstants.COPPER):
                softness = 0.9;
                break;
            case (GlobalConstants.IRON):
                softness = 0.7;
                break;
            case (GlobalConstants.EXPLODIUM):
                softness = 0.65;
                break;
            case (GlobalConstants.MARBLE):
                softness = 0.3;
                break;
            case (GlobalConstants.SPRING):
                softness = 0.8;
                break;
            case (GlobalConstants.LIFE):
                softness = 0.6;
            case (GlobalConstants.ICE):
                softness = 0.95;
            case (GlobalConstants.GOLD):
                softness = 0.6;
            case (GlobalConstants.CRYSTAL):
                softness = 0.6;
                break;
            case (GlobalConstants.GASROCK):
                softness = 0.5;
                break;
            case (GlobalConstants.COSTUMEGEM):
                softness = 1;
                break;
        }
    }

    public double getSoftness()
    {
        return softness;
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

    public boolean getFrozen()
    {
        return frozen;
    }

    public boolean setFrozen(boolean currentIce)
    {
        return frozen = frozen || currentIce;
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
}
