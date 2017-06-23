package com.example.thomas.miner;

import java.util.Random;

/**
 * Created by Thomas on 27/01/2017.
 */

public class Mining {

    private int gameHeight;
    private int gameWidth;
    private Block currentlyMining = null;
    private int miningOctant;
    private ArrayOfBlocksOnScreen blocksOnScreen;
    private int blockSize;
    private double softness;
    private int pickaxeHardness = 1;
    private OreCounter oreCounter;


    public Mining(int height, int width, ArrayOfBlocksOnScreen blocks, int blockSize, OreCounter oreCounter)
    {
        gameHeight = height;
        gameWidth = width;
        blocksOnScreen = blocks;
        this.blockSize = blockSize;
        this.oreCounter = oreCounter;
    }

    public boolean isCurrentlyMining()
    {
        return (miningOctant != 0);
    }

    public void stopMining()
    {
        if (currentlyMining!= null)
        {
            currentlyMining.resetMiningProgress();
            currentlyMining = null;
            miningOctant = 0;
        }
    }

    public void setCurrentlyMining(Block b)
    {
        if (!b.isSolid())
        {
            return;
        }
        if (currentlyMining != null)
        {
            if (currentlyMining.getX() == b.getX() && currentlyMining.getY() == b.getY())
            {
                //We are still mining the same block
            }
            else
            {
                //The block has changed
                currentlyMining.resetMiningProgress();
                currentlyMining = b;
                softness = b.getSoftness();
            }
        }
        else
        {
            //We are mining a block for the first time
            currentlyMining = b;
            softness = b.getSoftness();
        }
    }

    /*
    Returns a number for the octant:
    146
    207
    358
     */
    public void calculateMiningOctant(int x, int y)
    {
        int newMiningOctant = 0;
        if (x < gameWidth * 3 / 8)
        {
            if (y < gameHeight/2 - gameWidth/8)
            {
                newMiningOctant = 1;
            }
            else if (y < gameHeight/2 + gameWidth/8)
            {
                newMiningOctant = 2;
            }
            else
            {
                newMiningOctant = 3;
            }
        }
        else if (x < gameWidth * 5 / 8)
        {
            if (y < gameHeight/2 - gameWidth/8)
            {
                newMiningOctant = 4;
            }
            else if (y > gameHeight/2 + gameWidth/8)
            {
                newMiningOctant = 5;
            }
        }
        else
        {
            if (y < gameHeight/2 - gameWidth/8)
            {
                newMiningOctant = 6;
            }
            else if (y < gameHeight/2 + gameWidth/8)
            {
                newMiningOctant = 7;
            }
            else
            {
                newMiningOctant = 8;
            }
        }
        if (newMiningOctant != miningOctant)
        {
            if (currentlyMining != null)
            {
                stopMining();
            }
            miningOctant = newMiningOctant;
        }
    }

    public void mine()
    {
        switch (miningOctant)
        {
            case (0):
                break;
            case (1):
                if (!blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 - blockSize, gameHeight/2).isSolid()
                        || !blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2, gameHeight/2 - blockSize).isSolid())
                {
                    setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 - blockSize, gameHeight/2 - blockSize));
                }
                break;
            case (2):
                setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 - blockSize, gameHeight/2));
                break;
            case (3):
                if (!blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 - blockSize, gameHeight/2).isSolid()
                        || !blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2, gameHeight/2 + blockSize).isSolid())
                {
                    setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 - blockSize, gameHeight/2 + blockSize));
                }
                break;
            case (4):
                setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2, gameHeight/2 - blockSize));
                break;
            case (5):
                setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2, gameHeight/2 + blockSize));
                break;
            case (6):
                if (!blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 + blockSize, gameHeight/2).isSolid()
                        || !blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2, gameHeight/2 - blockSize).isSolid())
                {
                    setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 + blockSize, gameHeight/2 - blockSize));
                }
                break;
            case (7):
                setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 + blockSize, gameHeight/2));
                break;
            case (8):
                if (!blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 + blockSize, gameHeight/2).isSolid()
                        || !blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2, gameHeight/2 + blockSize).isSolid())
                {
                    setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 + blockSize, gameHeight/2 + blockSize));
                }
                break;
        }
        updateMiningProgress();
    }

    private void updateMiningProgress()
    {
        Random miningRandom = new Random(System.nanoTime());
        if (miningRandom.nextDouble() * softness * pickaxeHardness > 0.5)
        {
            if (currentlyMining != null && currentlyMining.mineFurther(oreCounter))
            {
                currentlyMining = null;
            }
        }
    }
}
