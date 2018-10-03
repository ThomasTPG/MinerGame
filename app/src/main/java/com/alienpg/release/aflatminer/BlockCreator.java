package com.alienpg.release.aflatminer;

import android.content.Context;

import java.util.Random;

/**
 * Created by Thomas on 15/11/2017.
 */

public class BlockCreator {

    int mSeed;
    Context mContext;
    int blocksAcross;
    int blocksPerScreen;
    MinedLocations minedLocations;
    private BlockArray blockArray;
    private BitmapFlyWeight bitmapFlyWeight;
    private int leftWiggleRoom;

    public BlockCreator(int seed, Context context, int blocksAcross, MinedLocations minedLocations, BlockArray blockArray)
    {
        mSeed = seed;
        mContext = context;
        this.blocksAcross = blocksAcross;
        this.minedLocations = minedLocations;
        this.blockArray = blockArray;
        blocksPerScreen = context.getResources().getInteger(R.integer.blocks_per_screen_width);
        leftWiggleRoom = context.getResources().getInteger(R.integer.left_hand_side_wiggle_room);
        bitmapFlyWeight = new BitmapFlyWeight(context);
    }

    public void setNewBlock(Coordinates coordinates, int ii, int jj)
    {
        int index = calculateIndex(coordinates.getX(),coordinates.getY());
        BlockSavedData blockSavedData = new BlockSavedData(new BlockLocaleData(index, coordinates));
        Block newBlock = determineType(blockSavedData, coordinates, mSeed);
        blockArray.setBlock(ii,jj,newBlock);
    }

    private int calculateIndex(int xCoord, int yCoord)
    {
        return (yCoord * blocksAcross + xCoord);
    }

    private boolean checkIfHardBoulder(Coordinates coordinates)
    {
        int xCoord = coordinates.getX();
        int yCoord = coordinates.getY();
        if (xCoord < leftWiggleRoom || xCoord > blocksAcross)
        {
            return true;
        }
        if (yCoord == 0)
        {
            if (Math.abs(xCoord - blocksAcross) < blocksPerScreen )
            {
                return true;
            }
        }
        return false;
    }

    private Block getNewBlock(int type, Coordinates coordinates, BlockSavedData blockSavedData)
    {
        switch (type)
        {
            case (GlobalConstants.CAVERN):
                return new Block_Cavern(blockSavedData,minedLocations, bitmapFlyWeight);
            case (GlobalConstants.BOULDER):
                return new Block_Boulder(blockSavedData,minedLocations, bitmapFlyWeight);
            case (GlobalConstants.HARD_BOULDER):
                return new Block_HardBoulder(blockSavedData,minedLocations, bitmapFlyWeight);
            case (GlobalConstants.FIREBALL):
                return new Block_FireBall(blockSavedData,minedLocations, bitmapFlyWeight);
            case (GlobalConstants.SOIL):
                return new Block_Soil(blockSavedData,minedLocations, bitmapFlyWeight);
            case (GlobalConstants.COPPER):
                return new Block_Copper(blockSavedData,minedLocations, bitmapFlyWeight);
            case (GlobalConstants.IRON):
                return new Block_Iron(blockSavedData,minedLocations, bitmapFlyWeight);
            case (GlobalConstants.EXPLODIUM):
                return new Block_Explodium(blockSavedData,minedLocations, bitmapFlyWeight);
            case (GlobalConstants.MARBLE):
                return new Block_Marble(blockSavedData,minedLocations, bitmapFlyWeight);
            case (GlobalConstants.SPRING):
                return new Block_Spring(blockSavedData,minedLocations, bitmapFlyWeight);
            case (GlobalConstants.LIFE):
                return new Block_Life(blockSavedData,minedLocations, bitmapFlyWeight);
            case (GlobalConstants.ICE):
                return new Block_Ice(blockSavedData,minedLocations, bitmapFlyWeight);
            case (GlobalConstants.GOLD):
                return new Block_Gold(blockSavedData,minedLocations, bitmapFlyWeight);
            case (GlobalConstants.CRYSTAL):
                return new Block_Crystal(blockSavedData,minedLocations, bitmapFlyWeight);
            case (GlobalConstants.GASROCK):
                return new Block_GasRock(blockSavedData,minedLocations, bitmapFlyWeight);
            case(GlobalConstants.TIN):
                return new Block_Tin(blockSavedData,minedLocations, bitmapFlyWeight);
            case (GlobalConstants.COSTUMEGEM):
                return new Block_CostumeGem(blockSavedData,minedLocations, bitmapFlyWeight);
            default:
                return new Block_Cavern(blockSavedData,minedLocations, bitmapFlyWeight);
        }
    }

    private Block determineType(BlockSavedData blockSavedData, Coordinates coordinates, int seed)
    {
        int index = blockSavedData.getIndex();

        if (checkIfHardBoulder(coordinates))
        {
            return (getNewBlock(GlobalConstants.HARD_BOULDER, coordinates, blockSavedData));
        }

        if (minedLocations.isItemContained(index))
        {
            blockSavedData.getNonSolidBlocks().setWaterPercentage(minedLocations.getWaterPercentage(index));
            blockSavedData.getNonSolidBlocks().setGasPercentage(minedLocations.getGasPercentage(index));
            blockSavedData.getBlockStatusData().setMinedPercentage(minedLocations.getMinedPercentage(index));
            return (getNewBlock(minedLocations.getThisType(), coordinates, blockSavedData));
        }
        else
        {
            int xCoord = coordinates.getX();
            int yCoord = coordinates.getY();
            CavernCreator c = new CavernCreator(coordinates,blocksAcross,seed);
            if (c.getIsCavern())
            {
                //Determine if the type oft he block is a cavern. Also includes y<0 regions at the top.
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
                        blockSavedData.getNonSolidBlocks().setWaterPercentage(100);
                        minedLocations.addToMinedLocations(index, blockSavedData.getBlockStatusData(),blockSavedData.getNonSolidBlocks());
                    }

                }
                return (getNewBlock(GlobalConstants.CAVERN, coordinates,blockSavedData));
            }
            else{
                // Not a cavern - generate a solid block instead
                //Find the distance to the nearest cavern
                final int distance = c.getDistanceToCavern();
                int oreLiklihood = 0;
                if (distance < 20)
                {
                    oreLiklihood = (int) (4*Math.pow((20-distance),1.3));
                }
                Random generator = new Random(seed * (xCoord + yCoord) * yCoord * xCoord * xCoord);
                int rand = (int) (generator.nextDouble() * 1000);
                if (rand > 900)
                {
                    return (getNewBlock(GlobalConstants.BOULDER, coordinates, blockSavedData));
                }
                else if (rand > 890 - oreLiklihood)
                {
                    return (getNewBlock(setOre(coordinates), coordinates, blockSavedData));
                }
                else
                {
                    return (getNewBlock(GlobalConstants.SOIL, coordinates, blockSavedData));
                }
            }
        }
    }

    private int setOre(Coordinates coordinates)
    {
        int xCoord = coordinates.getX();
        int yCoord = coordinates.getY();
        Random oreRandom = new Random(mSeed * xCoord * yCoord + yCoord*xCoord);
        double randomOreType = oreRandom.nextDouble();
        if (yCoord < 20)
        {
            return (OreHeightTables.determineOreTable1(randomOreType));
        }
        else if (yCoord < 40)
        {
            return (OreHeightTables.determineOreTable2(randomOreType));
        }
        else if (yCoord < 60)
        {
            return (OreHeightTables.determineOreTable3(randomOreType));
        }
        else if (yCoord < 80)
        {
            return (OreHeightTables.determineOreTable4(randomOreType));
        }
        else
        {
            return (OreHeightTables.determineOreTable5(randomOreType));
        }
    }
}
