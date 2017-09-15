package com.example.thomas.miner;

/**
 * Created by Thomas on 09/09/2017.
 */

public class BlockPhysics {

    ArrayOfBlocksOnScreen blocksOnScreen;
    private int horizontalBlockLimit;
    private int verticalBlockLimit;
    private int delay = 0;
    private int lifeFactor = 3;
    Block[][] blockArray;

    public BlockPhysics(ArrayOfBlocksOnScreen arrayOfBlocksOnScreen)
    {
        blocksOnScreen = arrayOfBlocksOnScreen;
        horizontalBlockLimit = blocksOnScreen.getHorizontalBlockLimit();
        verticalBlockLimit = blocksOnScreen.getVerticalBlockLimit();
    }

    public void updateDynamicBlocks()
    {
        blockArray = blocksOnScreen.getBlockArray();
        delay ++;
        for (int ii = horizontalBlockLimit-1; ii >=0; ii --)
        {
            for (int jj = verticalBlockLimit - 1; jj >=0; jj--)
            {
                if (blockArray[ii][jj].isCavern())
                {
                    if (!updateWaterBelow(ii,jj) && (ii - delay) % 5 == 0)
                    {
                        updateWaterSides(ii,jj);
                    }
                    if (!updateGasAbove(ii,jj) && (ii - delay) % 10 == 0)
                    {
                        updateGasSides(ii,jj);
                    }
                }
                updateSpring(ii,jj);
                updateLifeBlocks(ii,jj);
                updateIceBlocks(ii,jj);
                updateCrystalBlocks(ii,jj);
                updateFallingBlocks(ii,jj);
                createGas(ii,jj);
                decayGas(ii,jj);
                blowUpGas(ii,jj);
                blowUpExplodium(ii,jj);
            }
        }
        blocksOnScreen.setBlockArray(blockArray);
    }


    private void blowUpGas(int ii, int jj)
    {
        if (blockArray[ii][jj].isFire())
        {
            if (ii > 0)
            {
                if (blockArray[ii-1][jj].getGasPercentage() > 0)
                {
                    blockArray[ii-1][jj].blowUp();
                }
            }
            if (ii < horizontalBlockLimit-1)
            {
                if (blockArray[ii+1][jj].getGasPercentage() > 0)
                {
                    blockArray[ii+1][jj].blowUp();
                }
            }
            if (jj < verticalBlockLimit - 1)
            {
                if (blockArray[ii][jj+1].getGasPercentage() > 0)
                {
                    blockArray[ii][jj+1].blowUp();
                }
            }
            if (jj > 0)
            {
                if (blockArray[ii][jj-1].getGasPercentage() > 0)
                {
                    blockArray[ii][jj-1].blowUp();
                }
            }
        }
    }

    private void blowUpExplodium(int ii, int jj)
    {
        if (blockArray[ii][jj].getType() == GlobalConstants.EXPLODIUM)
        {
            if (ii > 0)
            {
                if (shouldExplodeExplodium(ii-1,jj))
                {
                    blocksOnScreen.explodeBlock(blockArray[ii][jj]);
                    return;
                }
            }
            if (ii < horizontalBlockLimit-1)
            {
                if (shouldExplodeExplodium(ii+1,jj))
                {
                    blocksOnScreen.explodeBlock(blockArray[ii][jj]);
                    return;
                }
            }
            if (jj > 0)
            {
                if (shouldExplodeExplodium(ii,jj-1))
                {
                    blocksOnScreen.explodeBlock(blockArray[ii][jj]);
                    return;
                }
            }
            if (jj < verticalBlockLimit - 1)
            {
                if (shouldExplodeExplodium(ii,jj+1))
                {
                    blocksOnScreen.explodeBlock(blockArray[ii][jj]);
                    return;
                }
            }
        }

    }

    private boolean shouldExplodeExplodium(int ii, int jj)
    {
        return (blockArray[ii][jj].getLiquidData().getWaterPercentage() + blockArray[ii][jj].getLiquidData().getGasPercentage() > 0) || (blockArray[ii][jj].isFire());
    }

    private void decayGas(int ii, int jj)
    {
        if (blockArray[ii][jj].getGasPercentage() > 10)
        {
            if (ii > 0)
            {
                if (blockArray[ii-1][jj].isSolid())
                {
                    blockArray[ii-1][jj].tryDecaying();
                }
            }
            if (ii < horizontalBlockLimit-1)
            {
                if (blockArray[ii+1][jj].isSolid())
                {
                    blockArray[ii+1][jj].tryDecaying();
                }
            }
            if (jj > 0)
            {
                if (blockArray[ii][jj-1].isSolid())
                {
                    blockArray[ii][jj-1].tryDecaying();
                }
            }
        }
    }

    private void updateSpring(int ii ,int jj)
    {
        if (blockArray[ii][jj].getType() == GlobalConstants.SPRING)
        {
            if (ii > 0)
            {
                if (!blockArray[ii-1][jj].isSolid())
                {
                    blockArray[ii-1][jj].setWaterPercentage(100);
                }
            }
            if (ii < horizontalBlockLimit-1)
            {
                if (!blockArray[ii+1][jj].isSolid())
                {
                    blockArray[ii+1][jj].setWaterPercentage(100);
                }
            }
            if (jj < verticalBlockLimit - 1)
            {
                if (!blockArray[ii][jj+1].isSolid())
                {
                    blockArray[ii][jj+1].setWaterPercentage(100);
                }
            }
        }
    }

    private boolean updateGasAbove(int ii, int jj)
    {
        boolean updated = false;
        if (jj > 0)
        {
            //Check above
            if ((blockArray[ii][jj].isCavern()) && (blockArray[ii][jj].getGasPercentage() > 0) && (blockArray[ii][jj-1].isCavern()))
            {
                //Move gas up
                if (blockArray[ii][jj-1].getGasPercentage() < 100)
                {
                    int gasMoved = Math.min(blockArray[ii][jj].getGasPercentage(), 100-blockArray[ii][jj-1].getGasPercentage());
                    blockArray[ii][jj].setGasPercentage(blockArray[ii][jj].getGasPercentage() - gasMoved);
                    blockArray[ii][jj-1].setGasPercentage(blockArray[ii][jj-1].getGasPercentage() + gasMoved);
                    if (gasMoved > 0)
                    {
                        updated = true;
                    }
                }
            }
        }
        return updated;
    }

    private void updateGasSides(int ii, int jj)
    {
        if (ii < horizontalBlockLimit - 1 && ii > 0)
        {
            boolean leftValid = blockArray[ii-1][jj].isCavern();
            boolean rightValid = blockArray[ii+1][jj].isCavern();
            int blocksToAverage = 1;
            int totalGas = blockArray[ii][jj].getGasPercentage();
            int incrementPercent = 1;
            if (leftValid)
            {
                blocksToAverage ++;
                totalGas = totalGas + blockArray[ii-1][jj].getGasPercentage();
            }
            if (rightValid)
            {
                blocksToAverage ++;
                totalGas = totalGas + blockArray[ii+1][jj].getGasPercentage();
            }
            int average = (int) Math.floor(totalGas/(double)blocksToAverage);
            int conservation = 0;
            if (leftValid)
            {
                if (blockArray[ii-1][jj].getGasPercentage() - incrementPercent> average)
                {
                    int amountToChange = Math.max((int) ((blockArray[ii-1][jj].getGasPercentage() - average)/4.0), incrementPercent);
                    blockArray[ii-1][jj].setGasPercentage(blockArray[ii-1][jj].getGasPercentage() - amountToChange);
                    conservation = conservation -amountToChange;
                }
                else if (blockArray[ii-1][jj].getGasPercentage() + incrementPercent< average)
                {
                    int amountToChange = Math.max((int) ((-blockArray[ii-1][jj].getGasPercentage() + average)/4.0), incrementPercent);

                    blockArray[ii-1][jj].setGasPercentage(blockArray[ii-1][jj].getGasPercentage() +amountToChange);
                    conservation = conservation + amountToChange;
                }

            }
            if (rightValid)
            {
                if (blockArray[ii+1][jj].getGasPercentage()- incrementPercent> average)
                {
                    int amountToChange = Math.max((int) ((blockArray[ii+1][jj].getGasPercentage() - average)/4.0), incrementPercent);
                    blockArray[ii+1][jj].setGasPercentage(blockArray[ii+1][jj].getGasPercentage() - amountToChange);
                    conservation = conservation -amountToChange;
                }
                else if (blockArray[ii+1][jj].getGasPercentage()+incrementPercent < average)
                {
                    int amountToChange = Math.max((int) ((-blockArray[ii+1][jj].getGasPercentage() + average)/4.0), incrementPercent);
                    blockArray[ii+1][jj].setGasPercentage(blockArray[ii+1][jj].getGasPercentage() +amountToChange);
                    conservation = conservation +amountToChange;
                }
            }
            blockArray[ii][jj].setGasPercentage(blockArray[ii][jj].getGasPercentage() - conservation);
        }
    }

    private void updateFallingBlocks(int ii, int jj)
    {
        if (blockArray[ii][jj].getMinedStage() == GlobalConstants.ALMOST_MINED && !blockArray[ii][jj].isCurrentlyBeingMined())
        {
            if (jj < verticalBlockLimit - 1)
            {
                if (!blockArray[ii][jj+1].isSolid())
                {
                    Block upper = blockArray[ii][jj];
                    Block lower = blockArray[ii][jj+1];
                    NonSolidBlocks nonSolidBlocksUpper = upper.getBlockLiquidData();
                    NonSolidBlocks nonSolidBlocksLower = lower.getBlockLiquidData();
                    BlockStatusData blockStatusDataUpper = upper.getBlockStatusData();
                    BlockStatusData blockStatusDataLower = lower.getBlockStatusData();
                    blockArray[ii][jj].setBlockLiquidData(nonSolidBlocksLower);
                    blockArray[ii][jj+1].setBlockLiquidData(nonSolidBlocksUpper);
                    blockArray[ii][jj].setBlockStatusData(blockStatusDataLower);
                    blockArray[ii][jj+1].setBlockStatusData(blockStatusDataUpper);
                    blockArray[ii][jj].saveToMemory();
                    blockArray[ii][jj+1].saveToMemory();

                    //Check explodium
                    if (jj < verticalBlockLimit - 2)
                    {
                        if (blockArray[ii][jj+2].isSolid() && blockArray[ii][jj+1].getType() ==GlobalConstants.EXPLODIUM)
                        {
                            blocksOnScreen.explodeBlock(blockArray[ii][jj+1]);
                        }
                        if (blockArray[ii][jj+2].getType() == GlobalConstants.EXPLODIUM)
                        {
                            blocksOnScreen.explodeBlock(blockArray[ii][jj+2]);
                        }
                    }

                }
            }
        }
    }

    private void createGas(int ii, int jj)
    {
        if (blockArray[ii][jj].getType() == GlobalConstants.GASROCK)
        {
            if (ii > 0)
            {
                if (blockArray[ii-1][jj].isCavern())
                {
                    blockArray[ii-1][jj].incrementGas();
                }
            }
            if (ii < horizontalBlockLimit-1)
            {
                if (blockArray[ii+1][jj].isCavern())
                {
                    blockArray[ii+1][jj].incrementGas();
                }
            }
            if (jj < verticalBlockLimit - 1)
            {
                if (blockArray[ii][jj+1].isCavern())
                {
                    blockArray[ii][jj+1].incrementGas();
                }
            }
            if (jj > 0)
            {
                if (blockArray[ii][jj-1].isCavern())
                {
                    blockArray[ii][jj-1].incrementGas();
                }
            }
        }
    }

    private void updateIceBlocks(int ii ,int jj)
    {
        if (blockArray[ii][jj].getType() == GlobalConstants.ICE)
        {
            if (ii > 0)
            {
                if (blockArray[ii-1][jj].hasWater())
                {
                    blockArray[ii-1][jj].tryFreezing();
                }
            }
            if (ii < horizontalBlockLimit-1)
            {
                if (blockArray[ii+1][jj].hasWater())
                {
                    blockArray[ii+1][jj].tryFreezing();
                }
            }
            if (jj < verticalBlockLimit - 1)
            {
                if (blockArray[ii][jj+1].hasWater())
                {
                    blockArray[ii][jj+1].tryFreezing();
                }
            }
            if (jj > 0)
            {
                if (blockArray[ii][jj-1].hasWater())
                {
                    blockArray[ii][jj-1].tryFreezing();
                }
            }
        }
    }

    private void updateLifeBlocks(int ii ,int jj)
    {
        if (blockArray[ii][jj].getType() == GlobalConstants.LIFE)
        {
            if (ii > 0)
            {
                if (blockArray[ii-1][jj].hasWater())
                {
                    int waterMoved = 0;
                    int neighbouringWater =  blockArray[ii-1][jj].getWaterPercentage();
                    if (blockArray[ii][jj].getWaterPercentage() < 100)
                    {
                        waterMoved = Math.min(neighbouringWater, 100 - blockArray[ii][jj].getWaterPercentage());
                    }
                    blockArray[ii-1][jj].setWaterPercentage(neighbouringWater - waterMoved);
                    blockArray[ii][jj].setWaterPercentage(blockArray[ii][jj].getWaterPercentage() + (int)Math.ceil(((double)waterMoved/(double)lifeFactor)));
                }
            }
            if (ii < horizontalBlockLimit-1)
            {
                int waterMoved = 0;
                int neighbouringWater =  blockArray[ii+1][jj].getWaterPercentage();
                if (blockArray[ii][jj].getWaterPercentage() < 100)
                {
                    waterMoved = Math.min(neighbouringWater, 100 - blockArray[ii][jj].getWaterPercentage());
                }
                blockArray[ii+1][jj].setWaterPercentage(neighbouringWater - waterMoved);
                blockArray[ii][jj].setWaterPercentage(blockArray[ii][jj].getWaterPercentage() + (int)Math.ceil(((double)waterMoved/(double)lifeFactor)));
            }
            if (jj > 0)
            {
                int waterMoved = 0;
                int neighbouringWater =  blockArray[ii][jj-1].getWaterPercentage();
                if (blockArray[ii][jj].getWaterPercentage() < 100)
                {
                    waterMoved = Math.min(neighbouringWater, 100 - blockArray[ii][jj].getWaterPercentage());
                }
                blockArray[ii][jj-1].setWaterPercentage(neighbouringWater - waterMoved);
                blockArray[ii][jj].setWaterPercentage(blockArray[ii][jj].getWaterPercentage() + (int)Math.ceil(((double)waterMoved/(double)lifeFactor)));
            }
        }
    }


    private boolean updateWaterBelow(int ii, int jj)
    {
        boolean updated = false;
        if (jj < verticalBlockLimit - 1)
        {
            //Check below
            if ((blockArray[ii][jj].isCavern()) && (blockArray[ii][jj].getWaterPercentage() > 0) && (!blockArray[ii][jj+1].isSolid()))
            {
                //Move water down
                int waterMoved = Math.min(blockArray[ii][jj].getWaterPercentage(), 100-blockArray[ii][jj+1].getWaterPercentage());
                blockArray[ii][jj].setWaterPercentage(blockArray[ii][jj].getWaterPercentage() - waterMoved);
                blockArray[ii][jj+1].setWaterPercentage(blockArray[ii][jj+1].getWaterPercentage() + waterMoved);
                if (waterMoved > 0)
                {
                    updated = true;
                }
            }
        }
        return updated;
    }

    private void updateWaterSides(int ii, int jj)
    {
        if (ii < horizontalBlockLimit - 1 && ii > 0)
        {
            boolean leftValid = !blockArray[ii-1][jj].isSolid();
            boolean rightValid = !blockArray[ii+1][jj].isSolid();
            int blocksToAverage = 1;
            int totalWater = blockArray[ii][jj].getWaterPercentage();
            int incrementPercent = 1;
            if (leftValid)
            {
                blocksToAverage ++;
                totalWater = totalWater + blockArray[ii-1][jj].getWaterPercentage();
            }
            if (rightValid)
            {
                blocksToAverage ++;
                totalWater = totalWater + blockArray[ii+1][jj].getWaterPercentage();
            }
            int average = (int) Math.floor(totalWater/(double)blocksToAverage);
            int conservation = 0;
            if (leftValid)
            {
                if (blockArray[ii-1][jj].getWaterPercentage() - incrementPercent> average)
                {
                    int amountToChange = Math.max((int) ((blockArray[ii-1][jj].getWaterPercentage() - average)/4.0), incrementPercent);
                    blockArray[ii-1][jj].setWaterPercentage(blockArray[ii-1][jj].getWaterPercentage() - amountToChange);
                    conservation = conservation -amountToChange;
                }
                else if (blockArray[ii-1][jj].getWaterPercentage() + incrementPercent< average)
                {
                    int amountToChange = Math.max((int) ((-blockArray[ii-1][jj].getWaterPercentage() + average)/4.0), incrementPercent);

                    blockArray[ii-1][jj].setWaterPercentage(blockArray[ii-1][jj].getWaterPercentage() +amountToChange);
                    conservation = conservation + amountToChange;
                }

            }
            if (rightValid)
            {
                if (blockArray[ii+1][jj].getWaterPercentage()- incrementPercent> average)
                {
                    int amountToChange = Math.max((int) ((blockArray[ii+1][jj].getWaterPercentage() - average)/4.0), incrementPercent);
                    blockArray[ii+1][jj].setWaterPercentage(blockArray[ii+1][jj].getWaterPercentage() - amountToChange);
                    conservation = conservation -amountToChange;
                }
                else if (blockArray[ii+1][jj].getWaterPercentage()+incrementPercent < average)
                {
                    int amountToChange = Math.max((int) ((-blockArray[ii+1][jj].getWaterPercentage() + average)/4.0), incrementPercent);
                    blockArray[ii+1][jj].setWaterPercentage(blockArray[ii+1][jj].getWaterPercentage() +amountToChange);
                    conservation = conservation +amountToChange;
                }
            }
            blockArray[ii][jj].setWaterPercentage(blockArray[ii][jj].getWaterPercentage() - conservation);
        }
    }


    private void updateCrystalBlocks(int ii ,int jj)
    {
        if (blockArray[ii][jj].getType() == GlobalConstants.CRYSTAL && ii > 0 && jj > 0 && ii < horizontalBlockLimit-1 && jj < verticalBlockLimit - 1)
        {
            int currentIce = 0;
            for (int hh = -1; hh < 2; hh ++)
            {
                for (int kk = -1; kk < 2; kk++)
                {
                    if (blockArray[ii+hh][jj+kk].isIce())
                    {
                        currentIce ++;
                    }
                }
            }
            blockArray[ii][jj].setFrozen(currentIce > 0);
        }
    }

}
