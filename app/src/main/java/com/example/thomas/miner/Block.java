package com.example.thomas.miner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

/**
 * Created by Thomas on 25/01/2017.
 */

public class Block {

    private int type;
    private int xCoord;
    private int yCoord;

    private int minePercentage = 0;
    private int waterPercentage = 0;
    private int index;
    Context context;
    private int blocksAcross;
    private int seed;
    private MinedLocations mMinedLocations;
    private int miningLimit = 20;
    private double softness;
    private int blocksPerScreen;

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
        mMinedLocations = minedLocations;
        determineType();
    }

    private void determineType()
    {
        if (mMinedLocations.isItemContained(index))
        {
            setType(mMinedLocations.getThisType());
            waterPercentage = mMinedLocations.getWaterPercentage(index);
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
                        waterPercentage = 100;
                        type = GlobalConstants.WATER;
                        mMinedLocations.addToMinedLocations(index,type,waterPercentage);
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
            setType(GlobalConstants.BOULDER);
        }
        if (yCoord == 0)
        {
            if (Math.abs(xCoord - blocksAcross) < blocksPerScreen )
            {
                setType(GlobalConstants.BOULDER);
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

    public void resetMiningProgress()
    {
        determineType();
        minePercentage = 0;
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
        if (type == GlobalConstants.LIFE && waterPercentage < 100)
        {
            return false;
        }
        if (type == GlobalConstants.CRYSTAL && !frozen)
        {
            return false;
        }
        minePercentage ++;
        if (minePercentage == miningLimit)
        {
            oreCounter.incrementOre(type);
            setType(GlobalConstants.CAVERN);
            waterPercentage = 0;
            mMinedLocations.addToMinedLocations(index, type,waterPercentage);
            minePercentage = 0;
            return true;
        }
        return false;
    }


    private void setType(int newType)
    {
        type = newType;
        setSoftness();
    }

    public boolean isCavern()
    {
        return (type == GlobalConstants.CAVERN);
    }

    public boolean isWater()
    {
        return (type == GlobalConstants.WATER);
    }

    public boolean isIce()
    {
        return (type == GlobalConstants.ICE);
    }

    public boolean isSolid()
    {
        return (type != GlobalConstants.WATER && type!= GlobalConstants.CAVERN);
    }

    public int getWaterPercentage()
    {
        return waterPercentage;
    }

    public void setWaterPercentage(int pct)
    {
        if (pct < 0)
        {
            pct = 0;
        }
        waterPercentage = pct;
        if (type != GlobalConstants.LIFE)
        {
            if (waterPercentage == 0)
            {
                setType(GlobalConstants.CAVERN);
            }
            else
            {
                setType(GlobalConstants.WATER);
            }
        }

        if (yCoord >= 0)
        {
            mMinedLocations.addToMinedLocations(index, type ,pct);
        }
    }

    private void setSoftness()
    {
        switch (type)
        {
            case (GlobalConstants.CAVERN):
                softness = 1;
                break;
            case (GlobalConstants.BOULDER):
                softness = 0;
                break;
            case (GlobalConstants.WATER):
                softness = 1;
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
            case (GlobalConstants.ALIENITE):
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
        }
    }

    public double getSoftness()
    {
        return softness;
    }

    public int getMiningProgress()
    {
        return(int) (minePercentage/(double)miningLimit * 100);
    }

    public int getType()
    {
        return type;
    }

    public void tryFreezing()
    {
        Random iceRandom = new Random(System.nanoTime());
        if (iceRandom.nextDouble() > 0.99)
        {
            setType(GlobalConstants.ICE);
            waterPercentage = 0;
            mMinedLocations.addToMinedLocations(index, type,waterPercentage);
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
}
