package com.alienpg.release.aflatminer;

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
    private double pickaxeRequired;
    private int pickaxeLevel;
    private OreCounter oreCounter;
    private boolean miningInProcess = false;
    private InGameNotifications inGameNotifications;

    public Mining(int height, int width, ArrayOfBlocksOnScreen blocks, int blockSize, OreCounter oreCounter, ShopMemory shopMemory, InGameNotifications inGameNotifications)
    {
        gameHeight = height;
        gameWidth = width;
        blocksOnScreen = blocks;
        this.blockSize = blockSize;
        this.oreCounter = oreCounter;
        this.inGameNotifications = inGameNotifications;
        pickaxeLevel = shopMemory.getItem(GlobalConstants.PICKAXEUPGRADE);
    }

    public boolean isCurrentlyMining()
    {
        return (miningInProcess);
    }

    public void stopMining()
    {
        if (miningInProcess)
        {
            currentlyMining.resetMiningProgress();
            miningInProcess = false;
            miningOctant = 0;
        }
    }

    public void setCurrentlyMining(Block b)
    {
        if (!b.isSolid())
        {
            miningInProcess = false;
            miningOctant = 0;
            return;
        }
        if (currentlyMining != null)
        {
            if (currentlyMining.getX() == b.getX() && currentlyMining.getY() == b.getY())
            {
                //We are still mining the same block
                miningInProcess = true;
            }
            else
            {
                //The block has changed
                currentlyMining.resetMiningProgress();
                currentlyMining = b;
                miningInProcess = true;
                pickaxeRequired = b.getPickaxeRequired();
            }
        }
        else
        {
            //We are mining a block for the first time
            currentlyMining = b;
            miningInProcess = true;
            pickaxeRequired = b.getPickaxeRequired();
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

    public int getMiningOctant()
    {
        return miningOctant;
    }

    public void mine()
    {
        switch (miningOctant)
        {
            case (0):
                setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2, gameHeight/2)));
                break;
            case (1):
                if (!blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 - blockSize, gameHeight/2)).isSolid() ||
                        !blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2, gameHeight/2 - blockSize)).isSolid())
                setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 - blockSize, gameHeight/2 - blockSize)));
                break;
            case (2):
                setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 - blockSize, gameHeight/2)));
                break;
            case (3):
                if (!blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 - blockSize, gameHeight/2)).isSolid() ||
                        !blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2, gameHeight/2 + blockSize)).isSolid())
                setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 - blockSize, gameHeight/2 + blockSize)));
                break;
            case (4):
                setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2, gameHeight/2 - blockSize)));
                break;
            case (5):
                setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2, gameHeight/2 + blockSize)));
                break;
            case (6):
                if (!blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 + blockSize, gameHeight/2)).isSolid() ||
                        !blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2, gameHeight/2 - blockSize)).isSolid())
                setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 + blockSize, gameHeight/2 - blockSize)));
                break;
            case (7):
                setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 + blockSize, gameHeight/2)));
                break;
            case (8):
                if (!blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 + blockSize, gameHeight/2)).isSolid() ||
                        !blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2, gameHeight/2 + blockSize)).isSolid())
                setCurrentlyMining(blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 + blockSize, gameHeight/2 + blockSize)));
                break;
        }
        if (miningInProcess)
        {
            if (pickaxeRequired < 0)
            {
                if (currentlyMining.getType() == GlobalConstants.BOULDER)
                {
                    inGameNotifications.setCurrentNotification(InGameNotifications.BOULDER_MINING);
                }
                if (currentlyMining.getType() == GlobalConstants.HARD_BOULDER)
                {
                    inGameNotifications.setCurrentNotification(InGameNotifications.HARD_BOULDER_MINING);
                }
                return;
            }
            if (canBeMined())
            {
                updateMiningProgress();
            }
        }
    }

    private boolean canBeMined()
    {
        if (pickaxeLevel >= pickaxeRequired)
        {
            return true;
        }
        else
        {
            inGameNotifications.setCurrentNotification(InGameNotifications.PICKAXE_UPGRADE);
            stopMining();
            return false;
        }
    }

    private void updateMiningProgress()
    {
        Random miningRandom = new Random(System.nanoTime());
        if (miningRandom.nextDouble() > 0.1)
        {
            if (miningInProcess && currentlyMining.mineFurther(oreCounter))
            {
                getRidOfBlock();
                miningInProcess = false;
                miningOctant = 0;
            }
        }
    }

    public void getRidOfBlock() {
        Coordinates coordinates = new Coordinates();
        switch (miningOctant) {
            case (0):
                coordinates.setCoordinates(gameWidth / 2, gameHeight / 2);
                break;
            case (1):
                coordinates.setCoordinates(gameWidth / 2 - blockSize, gameHeight / 2 - blockSize);
                break;
            case (2):
                coordinates.setCoordinates(gameWidth / 2 - blockSize, gameHeight / 2);
                break;
            case (3):
                coordinates.setCoordinates(gameWidth / 2 - blockSize, gameHeight / 2 + blockSize);
                break;
            case (4):
                coordinates.setCoordinates(gameWidth / 2, gameHeight / 2 - blockSize);
                break;
            case (5):
                coordinates.setCoordinates(gameWidth / 2, gameHeight / 2 + blockSize);
                break;
            case (6):
                coordinates.setCoordinates(gameWidth / 2 + blockSize, gameHeight / 2 - blockSize);
                break;
            case (7):
                coordinates.setCoordinates(gameWidth / 2 + blockSize, gameHeight / 2);
                break;
            case (8):
                coordinates.setCoordinates(gameWidth / 2 + blockSize, gameHeight / 2 + blockSize);
                break;
        }
        Block oldBlock = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(coordinates);
        CavernBlock newBlock = new CavernBlock(oldBlock);
        blocksOnScreen.setBlockUsingScreenCoordinates(coordinates, newBlock);
    }
}
