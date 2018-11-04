package com.alienpg.release.aflatminer;

import android.content.Context;
import android.util.Log;

import java.util.Random;

/**
 * Created by Thomas on 09/09/2017.
 */

public class BlockPhysics {

    private static final String TAG = BlockPhysics.class.getName();

    private int horizontalBlockLimit;
    private int verticalBlockLimit;
    private int delay = 0;
    private int lifeFactor = 3;
    private ActiveBombs activeBombs;
    BlockArray blockArray;
    Achievements achievementManager;
    Context mContext;

    public static final int GAS_THRESHOLD = 5;

    public BlockPhysics(BlockArray blockArray, ActiveBombs activeBombs, Context context, Achievements achievements)
    {
        this.activeBombs = activeBombs;
        this.blockArray = blockArray;
        horizontalBlockLimit = blockArray.getHorizontalBlockLimit();
        verticalBlockLimit = blockArray.getVerticalBlockLimit();
        achievementManager = achievements;
        mContext = context;
    }

    public void updateDynamicBlocks()
    {
        delay ++;
        for (int ii = horizontalBlockLimit-1; ii >=0; ii --)
        {
            for (int jj = verticalBlockLimit - 1; jj >=0; jj--)
            {
                long startTime = System.nanoTime();
                if (blockArray.getBlock(ii, jj).isCavern())
                {
                    if ((ii - delay) % 5 == 0 && !updateWaterBelow(ii,jj))
                    {
                        decayGas(ii,jj);
                        updateSpring(ii,jj);
                        if (!updateWaterBelow(ii, jj))
                        {
                            updateWaterSides(ii,jj);
                        }
                    }
                    if ((ii - delay) % 10 == 0 && !updateGasAbove(ii,jj))
                    {
                        updateGasSides(ii,jj);
                    }
                }
                long endTime = System.nanoTime();
                long diff = endTime - startTime;

                if (diff > 10000000)
                {
                    Log.e(TAG, String.format("TIME1 = %d", diff));
                }


                long startTime1 = System.nanoTime();
                updateLifeBlocks(ii,jj);
                long endTimea = System.nanoTime();

                updateIceBlocks(ii,jj);
                long endTimeb = System.nanoTime();

                updateCrystalBlocks(ii,jj);
                long endTimec = System.nanoTime();

                updateFallingBlocks(ii,jj);
                long endTimed = System.nanoTime();

                createGas(ii,jj);
                long endTimee = System.nanoTime();

                checkBlowingUpGas(ii,jj);
                long endTimef = System.nanoTime();

                blowUpExplodium(ii,jj);
                long endTimeg = System.nanoTime();

                checkDynamiteFall(ii,jj);
                long endTime1 = System.nanoTime();
                long diff2 = endTime1 - startTime1;

                if (diff2 > 10000000)
                {
                    Log.e(TAG, String.format("TIME2 = %d", diff2));
                    Log.e(TAG, String.format("TIMEa = %d", endTimea - startTime1));
                    Log.e(TAG, String.format("TIMEb = %d", endTimeb - startTime1));
                    Log.e(TAG, String.format("TIMEc = %d", endTimec - startTime1));
                    Log.e(TAG, String.format("TIMEd = %d", endTimed - startTime1));
                    Log.e(TAG, String.format("TIMEe = %d", endTimee - startTime1));
                    Log.e(TAG, String.format("TIMEf = %d", endTimef - startTime1));
                    Log.e(TAG, String.format("TIMEg = %d", endTimeg - startTime1));







                }

            }
        }
    }

    private boolean blowUpGas(int ii, int jj)
    {
        if (blockArray.getBlock(ii, jj).getGasPercentage() > GAS_THRESHOLD)
        {
            explodeBlockByCoords(ii, jj);
            return true;
        }
        return false;
    }

    private void checkBlowingUpGas(int ii, int jj)
    {
        boolean detonated = false;
        if (blockArray.getBlock(ii, jj).isFire())
        {
            try {
                detonated = blowUpGas(ii-1,jj);
                detonated = detonated || blowUpGas(ii+1,jj);
                detonated = detonated || blowUpGas(ii,jj+1);
                detonated = detonated || blowUpGas(ii,jj-1);
            }
            catch (IllegalArgumentException ex)
            {
                Log.e(TAG, ex.getMessage());
            }
        }
        if (detonated && blockArray.getBlock(ii, jj).getAchievementChainReactionII())
        {
            achievementManager.unlockAchievement(mContext.getResources().getString(R.string.chain_reaction_ii));
        }
    }

    private void blowUpExplodium(int ii, int jj)
    {
        if (blockArray.getBlock(ii, jj).getType() == GlobalConstants.EXPLODIUM)
        {
            try {
                if (shouldExplodeExplodium(ii-1,jj) || shouldExplodeExplodium(ii+1,jj) || shouldExplodeExplodium(ii,jj-1) || shouldExplodeExplodiumBelow(ii,jj+1))
                {
                    explodeBlock(ii,jj);
                    return;
                }
            }
            catch (IllegalArgumentException ex)
            {
                Log.i(TAG, ex.getMessage());
            }
        }
    }

    public void explodeBlockByCoords(final int ii, final int jj)
    {
        final Block toExplode = blockArray.getBlock(ii, jj);
        final int initialType = toExplode.getType();
        Thread explosionTimer = new Thread()
        {
            @Override
            public void run()
            {
                Coordinates currenctCoords;
                try {
                    synchronized (blockArray)
                    {
                        currenctCoords = blockArray.getBlockCoordinatesByIndex(toExplode);
                        blockArray.getBlock(currenctCoords).setGasPercentage(0);
                        blockArray.getBlock(currenctCoords).setBomb(ActiveBombs.NO_BOMB);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    synchronized (this)
                    {
                        int number = 2 + ((ii * jj * ii )*(ii * jj * 75));
                        int msToWait = Math.abs((number*number)%200);
                        wait(msToWait);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    synchronized (blockArray)
                    {
                        currenctCoords = blockArray.getBlockCoordinatesByIndex(toExplode);
                        blockArray.setBlock(currenctCoords, new Block_FireBall(blockArray.getBlock(currenctCoords)));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    synchronized (this)
                    {
                        int msToWait = Math.abs(200 + (ii * jj * jj )%83);
                        wait(msToWait);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    synchronized (blockArray)
                    {
                        currenctCoords = blockArray.getBlockCoordinatesByIndex(toExplode);
                        blockArray.setBlock(currenctCoords, new Block_Cavern(blockArray.getBlock(currenctCoords)));

                        if (initialType == GlobalConstants.ICE)
                        {
                            blockArray.getBlock(currenctCoords).resetWaterAfterMining();
                        }
                        blockArray.getBlock(currenctCoords).setAchievementChainReactionII(false);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        };
        if (toExplode.getType() != GlobalConstants.HARD_BOULDER)
        {
            explosionTimer.start();
        }
    }

    private void blowUpIceBomb(final int ii, final int jj)
    {
        final Block toExplode = blockArray.getBlock(ii, jj);
        Thread explosionTimer = new Thread()
        {
            @Override
            public void run()
            {
                Coordinates currenctCoords;
                try {
                    synchronized (blockArray)
                    {
                        currenctCoords = blockArray.getBlockCoordinatesByIndex(toExplode);
                        blockArray.getBlock(currenctCoords).setGasPercentage(0);
                        blockArray.getBlock(currenctCoords).setBomb(ActiveBombs.NO_BOMB);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    synchronized (this)
                    {
                        int number = 2 + ((ii * jj * ii )*(ii * jj * 75));
                        int msToWait = Math.abs((number*number)%200);
                        wait(msToWait);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    synchronized (blockArray)
                    {
                        currenctCoords = blockArray.getBlockCoordinatesByIndex(toExplode);
                        blockArray.setBlock(currenctCoords, new Block_Ice(blockArray.getBlock(currenctCoords)));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        };
        if (toExplode.canTurnIntoIce())
        {
            explosionTimer.start();
        }
    }

    public void explodeBlock(int x,int y)
    {
        Block block = blockArray.getBlock(x,y);
        if (block.getType() == GlobalConstants.EXPLODIUM)
        {
            for (int aa = -2; aa <=2; aa++)
            {
                for (int bb = -2; bb<=2; bb++)
                {
                    try {
                        achievementManager.checkChainReactionII(block, blockArray.getBlock(x + aa, y + bb));
                        explodeBlockByCoords(x + aa, y + bb);
                    } catch (IllegalArgumentException ex)
                    {
                        Log.i(TAG, ex.getMessage());
                    }
                }
            }
        }
        else
        {
            switch(block.getBomb())
            {
                case(ActiveBombs.DYNAMITE):
                    int boulders = 0;
                    if (block.isIce())
                    {
                        achievementManager.unlockAchievement(mContext.getResources().getString(R.string.freeze_thaw));
                    }
                    for (int aa = -1; aa <=1; aa++)
                    {
                        for (int bb = -1; bb<=1; bb++)
                        {
                            if (blockArray.getBlock(x + aa, y + bb).getType() == GlobalConstants.BOULDER)
                            {
                                boulders ++;
                            }
                            if (blockArray.getBlock(x + aa, y + bb).getType() == GlobalConstants.COSTUMEGEM)
                            {
                                achievementManager.unlockAchievement(mContext.getResources().getString(R.string.naturism));
                            }
                            explodeBlockByCoords(x + aa, y + bb);
                        }
                    }
                    if (boulders >= 4)
                    {
                        achievementManager.unlockAchievement(mContext.getResources().getString(R.string.blast_miner));
                    }
                    break;
                case(ActiveBombs.ICEBOMB):
                    int numberConverted = 0;
                    int numberGas = 0;
                    for (int aa = -1; aa <=1; aa++)
                    {
                        for (int bb = -1; bb<=1; bb++)
                        {
                            if (blockArray.getBlock(x + aa, y + bb).canTurnIntoIce())
                            {
                                numberConverted ++;
                            }
                            if (blockArray.getBlock(x + aa, y + bb).getGasPercentage() > 0)
                            {
                                numberGas ++;
                            }
                            blowUpIceBomb(x + aa, y + bb);
                        }
                    }
                    if (numberConverted == 0)
                    {
                        achievementManager.unlockAchievement(mContext.getResources().getString(R.string.what_a_waste));
                    }
                    if (numberGas == 8)
                    {
                        achievementManager.unlockAchievement(mContext.getResources().getString(R.string.states_of_matter));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private boolean shouldExplodeExplodiumBelow(int ii, int jj)
    {
        return (blockArray.getBlock(ii, jj).getLiquidData().getGasPercentage() > 5) || (blockArray.getBlock(ii, jj).isFire());
    }

    private boolean shouldExplodeExplodium(int ii, int jj)
    {
        Block explodiumNeighbour = blockArray.getBlock(ii, jj);
        if (explodiumNeighbour.isSolid())
        {
            return false;
        }
        boolean willBlow = (explodiumNeighbour.getLiquidData().getWaterPercentage() + explodiumNeighbour.getLiquidData().getGasPercentage() > 5) || (explodiumNeighbour.isFire());
        if (willBlow)
        {
            Log.e(TAG, String.format("WILL BLOW %d", explodiumNeighbour.getLiquidData().getGasPercentage()));

        }
        return (willBlow);
    }

    private void decayGas(int ii, int jj)
    {
        if (blockArray.getBlock(ii, jj).getGasPercentage() > 10)
        {
            if (ii > 0)
            {
                if (blockArray.getBlock(ii - 1, jj).isSolid())
                {
                    blockArray.getBlock(ii - 1, jj).tryDecaying();
                }
            }
            if (ii < horizontalBlockLimit-1)
            {
                if (blockArray.getBlock(ii + 1, jj).isSolid())
                {
                    blockArray.getBlock(ii + 1, jj).tryDecaying();
                }
            }
            if (jj > 0)
            {
                if (blockArray.getBlock(ii, jj - 1).isSolid())
                {
                    blockArray.getBlock(ii, jj - 1).tryDecaying();
                }
            }
        }
    }

    private void updateSpring(int ii ,int jj)
    {
        if (blockArray.getBlock(ii, jj).getType() == GlobalConstants.SPRING)
        {
            if (ii > 0)
            {
                if (!blockArray.getBlock(ii - 1, jj).isSolid())
                {
                    blockArray.getBlock(ii - 1, jj).setWaterPercentage(100);
                }
            }
            if (ii < horizontalBlockLimit-1)
            {
                Block blockRight = blockArray.getBlock(ii + 1, jj);
                if (!blockRight.isSolid())
                {
                    blockRight.setWaterPercentage(100);
                }
            }
            if (jj < verticalBlockLimit - 1)
            {
                Block blockBelow = blockArray.getBlock(ii, jj+1);
                if (!blockBelow.isSolid())
                {
                    blockBelow.setWaterPercentage(100);
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
            Block blockAbove = blockArray.getBlock(ii, jj-1);
            if ((blockArray.getBlock(ii, jj).isCavern()) && (blockArray.getBlock(ii, jj).getGasPercentage() > 0) && (blockAbove.isCavern()))
            {
                //Move gas up
                if (blockAbove.getGasPercentage() < 100)
                {
                    int gasMoved = Math.min(blockArray.getBlock(ii, jj).getGasPercentage(), 100-blockAbove.getGasPercentage());
                    blockArray.getBlock(ii, jj).setGasPercentage(blockArray.getBlock(ii, jj).getGasPercentage() - gasMoved);
                    blockAbove.setGasPercentage(blockAbove.getGasPercentage() + gasMoved);
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
        Block currentBlock = blockArray.getBlock(ii, jj);
        if (ii < horizontalBlockLimit - 1 && ii > 0)
        {
            Block blockLeft = blockArray.getBlock(ii-1, jj);
            Block blockRight = blockArray.getBlock(ii+1,jj);
            boolean leftValid = blockLeft.isCavern();
            boolean rightValid = blockRight.isCavern();
            int blocksToAverage = 1;
            int totalGas = currentBlock.getGasPercentage();
            int incrementPercent = 1;
            if (leftValid)
            {
                blocksToAverage ++;
                totalGas = totalGas + blockLeft.getGasPercentage();
            }
            if (rightValid)
            {
                blocksToAverage ++;
                totalGas = totalGas + blockRight.getGasPercentage();
            }
            int average = (int) Math.floor(totalGas/(double)blocksToAverage);
            int conservation = 0;
            if (leftValid)
            {
                if (blockLeft.getGasPercentage() - incrementPercent> average)
                {
                    int amountToChange = Math.max((int) ((blockLeft.getGasPercentage() - average)/4.0), incrementPercent);
                    blockLeft.setGasPercentage(blockLeft.getGasPercentage() - amountToChange);
                    conservation = conservation -amountToChange;
                }
                else if (blockLeft.getGasPercentage() + incrementPercent< average)
                {
                    int amountToChange = Math.max((int) ((-blockLeft.getGasPercentage() + average)/4.0), incrementPercent);

                    blockLeft.setGasPercentage(blockLeft.getGasPercentage() +amountToChange);
                    conservation = conservation + amountToChange;
                }

            }
            if (rightValid)
            {
                if (blockRight.getGasPercentage()- incrementPercent> average)
                {
                    int amountToChange = Math.max((int) ((blockRight.getGasPercentage() - average)/4.0), incrementPercent);
                    blockRight.setGasPercentage(blockRight.getGasPercentage() - amountToChange);
                    conservation = conservation -amountToChange;
                }
                else if (blockRight.getGasPercentage()+incrementPercent < average)
                {
                    int amountToChange = Math.max((int) ((-blockRight.getGasPercentage() + average)/4.0), incrementPercent);
                    blockRight.setGasPercentage(blockRight.getGasPercentage() +amountToChange);
                    conservation = conservation +amountToChange;
                }
            }
            currentBlock.setGasPercentage(currentBlock.getGasPercentage() - conservation);
        }
    }

    private void updateFallingBlocks(int ii, int jj)
    {
        if (blockArray.getBlock(ii, jj).getMinedStage() == GlobalConstants.ALMOST_MINED && !blockArray.getBlock(ii, jj).isCurrentlyBeingMined())
        {
            if (jj < verticalBlockLimit - 1)
            {
                Block blockBelow = blockArray.getBlock(ii, jj+1);
                if (!blockBelow.isSolid())
                {
                    Block upper = blockArray.getBlock(ii, jj);

                    blockArray.setBlock(ii, jj , blockBelow);
                    blockArray.getBlock(ii,jj).setBlockLocaleData(upper.getBlockLocaleData());
                    blockArray.getBlock(ii,jj).saveToMemory();
                    blockArray.setBlock(ii, jj+1, upper);
                    blockArray.getBlock(ii,jj+1).setBlockLocaleData(blockBelow.getBlockLocaleData());
                    blockArray.getBlock(ii,jj+1).increaseFallenDistance();
                    blockArray.getBlock(ii,jj+1).saveToMemory();

                    if (jj < verticalBlockLimit - 2)
                    {
                        Block hasFallen = blockArray.getBlock(ii,jj+1);
                        Block fallenOnto = blockArray.getBlock(ii, jj + 2);
                        //Check explodium
                        if (fallenOnto.isSolid() && hasFallen.getType() ==GlobalConstants.EXPLODIUM)
                        {
                            Log.d(TAG, "Dropped an explodium. Will explode.");
                            achievementManager.checkOops(hasFallen);
                            explodeBlock(ii,jj+1);
                        }
                        if (fallenOnto.getType() == GlobalConstants.EXPLODIUM)
                        {
                            Log.d(TAG, "Dropped onto an explodium. Will explode.");
                            achievementManager.checkChainReactionI(hasFallen);
                            explodeBlock(ii,jj+2);
                        }

                        if (fallenOnto.isSolid())
                        {
                            //Check poisoning_the_well achievement
                            if (hasFallen.getType() == GlobalConstants.GASROCK)
                            {
                                if (blockArray.getBlock(ii, jj).getWaterPercentage() == 100)
                                {
                                    if (jj > 0 && blockArray.getBlock(ii, jj -1).getWaterPercentage() == 100)
                                    {
                                        achievementManager.unlockAchievement(mContext.getResources().getString(R.string.poisoning_the_well));
                                    }
                                }
                            }

                            //Reset height fallen
                            hasFallen.resetHeightFallen();
                        }
                    }
                }
            }
        }
    }

    private void createGas(int ii, int jj)
    {
        if (blockArray.getBlock(ii,jj).getType() == GlobalConstants.GASROCK)
        {
            if (ii > 0)
            {
                Block blockLeft = blockArray.getBlock(ii-1,jj);
                blockLeft.incrementGas();
            }
            if (ii < horizontalBlockLimit-1)
            {
                Block blockRight = blockArray.getBlock(ii+1,jj);
                blockRight.incrementGas();
            }
            if (jj < verticalBlockLimit - 1)
            {
                Block blockBelow = blockArray.getBlock(ii,jj+1);
                blockBelow.incrementGas();
            }
            if (jj > 0)
            {
                Block blockAbove = blockArray.getBlock(ii,jj-1);
                blockAbove.incrementGas();
            }
        }
    }

    private boolean shouldFreezeBlock(Block b)
    {
        if (b.canFreeze())
        {
            Random iceRandom = new Random(System.nanoTime());
            if (iceRandom.nextDouble() > 0.99)
            {
                return true;
            }
        }
        return false;
    }

    private void tryFreezeBlock(int ii, int jj)
    {
        Block blockLeft = blockArray.getBlock(ii,jj);
        if (shouldFreezeBlock(blockLeft))
        {
            blockArray.setBlock(ii, jj, new Block_Ice(blockArray.getBlock(ii, jj)));
        }
    }

    private void updateIceBlocks(int ii ,int jj)
    {
        if (blockArray.getBlock(ii,jj).getType() == GlobalConstants.ICE)
        {
            if (ii > 0)
            {
                tryFreezeBlock(ii - 1, jj);
            }
            if (ii < horizontalBlockLimit-1)
            {
                tryFreezeBlock(ii + 1, jj);
            }
            if (jj < verticalBlockLimit - 1)
            {
                tryFreezeBlock(ii, jj + 1);
            }
            if (jj > 0)
            {
                tryFreezeBlock(ii, jj - 1);
            }
        }
    }

    private void updateLifeBlocks(int ii ,int jj)
    {
        Block currentBlock = blockArray.getBlock(ii, jj);
        if (currentBlock.getType() == GlobalConstants.LIFE)
        {
            if (ii > 0)
            {
                Block blockLeft = blockArray.getBlock(ii-1,jj);
                int waterMoved = 0;
                int neighbouringWater =  blockLeft.getWaterPercentage();
                if (currentBlock.getWaterPercentage() < 100)
                {
                    waterMoved = Math.min(neighbouringWater, 100 - currentBlock.getWaterPercentage());
                }
                blockLeft.setWaterPercentage(neighbouringWater - waterMoved);
                currentBlock.setWaterPercentage(currentBlock.getWaterPercentage() + (int)Math.ceil(((double)waterMoved/(double)lifeFactor)));
            }
            if (ii < horizontalBlockLimit-1)
            {
                Block blockRight = blockArray.getBlock(ii + 1, jj);
                int waterMoved = 0;
                int neighbouringWater =  blockRight.getWaterPercentage();
                if (currentBlock.getWaterPercentage() < 100)
                {
                    waterMoved = Math.min(neighbouringWater, 100 - currentBlock.getWaterPercentage());
                }
                blockRight.setWaterPercentage(neighbouringWater - waterMoved);
                currentBlock.setWaterPercentage(currentBlock.getWaterPercentage() + (int)Math.ceil(((double)waterMoved/(double)lifeFactor)));
            }
            if (jj > 0)
            {
                Block blockAbove = blockArray.getBlock(ii, jj - 1);
                int waterMoved = 0;
                int neighbouringWater =  blockAbove.getWaterPercentage();
                if (currentBlock.getWaterPercentage() < 100)
                {
                    waterMoved = Math.min(neighbouringWater, 100 - currentBlock.getWaterPercentage());
                }
                blockAbove.setWaterPercentage(neighbouringWater - waterMoved);
                currentBlock.setWaterPercentage(currentBlock.getWaterPercentage() + (int)Math.ceil(((double)waterMoved/(double)lifeFactor)));
            }
        }
    }


    private boolean updateWaterBelow(int ii, int jj)
    {
        boolean updated = false;
        if (jj < verticalBlockLimit - 1)
        {
            //Check below
            Block currentBlock = blockArray.getBlock(ii ,jj);
            Block blockBelow = blockArray.getBlock(ii, jj+1);
            if ((currentBlock.isCavern()) && (currentBlock.getWaterPercentage() > 0) && (!blockBelow.isSolid()))
            {
                //Move water down
                int waterMoved = Math.min(currentBlock.getWaterPercentage(), 100-blockBelow.getWaterPercentage());
                currentBlock.setWaterPercentage(currentBlock.getWaterPercentage() - waterMoved);
                blockBelow.setWaterPercentage(blockBelow.getWaterPercentage() + waterMoved);
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
            boolean leftValid = !blockArray.getBlock(ii - 1, jj).isSolid();
            boolean rightValid = !blockArray.getBlock(ii + 1, jj).isSolid();
            int blocksToAverage = 1;
            int totalWater = blockArray.getBlock(ii, jj).getWaterPercentage();
            int incrementPercent = 1;
            if (leftValid)
            {
                blocksToAverage ++;
                totalWater = totalWater + blockArray.getBlock(ii - 1, jj).getWaterPercentage();
            }
            if (rightValid)
            {
                blocksToAverage ++;
                totalWater = totalWater + blockArray.getBlock(ii + 1, jj).getWaterPercentage();
            }
            int average = (int) Math.floor(totalWater/(double)blocksToAverage);
            int conservation = 0;
            if (leftValid)
            {
                Block blockLeft = blockArray.getBlock(ii - 1, jj);
                if (blockLeft.getWaterPercentage() - incrementPercent> average)
                {
                    int amountToChange = Math.max((int) ((blockLeft.getWaterPercentage() - average)/4.0), incrementPercent);
                    blockLeft.setWaterPercentage(blockLeft.getWaterPercentage() - amountToChange);
                    conservation = conservation -amountToChange;
                }
                else if (blockLeft.getWaterPercentage() + incrementPercent< average)
                {
                    int amountToChange = Math.max((int) ((-blockLeft.getWaterPercentage() + average)/4.0), incrementPercent);

                    blockLeft.setWaterPercentage(blockLeft.getWaterPercentage() +amountToChange);
                    conservation = conservation + amountToChange;
                }

            }
            if (rightValid)
            {
                Block blockRight = blockArray.getBlock(ii + 1, jj);
                if (blockRight.getWaterPercentage()- incrementPercent> average)
                {
                    int amountToChange = Math.max((int) ((blockRight.getWaterPercentage() - average)/4.0), incrementPercent);
                    blockRight.setWaterPercentage(blockRight.getWaterPercentage() - amountToChange);
                    conservation = conservation -amountToChange;
                }
                else if (blockRight.getWaterPercentage()+incrementPercent < average)
                {
                    int amountToChange = Math.max((int) ((-blockRight.getWaterPercentage() + average)/4.0), incrementPercent);
                    blockRight.setWaterPercentage(blockRight.getWaterPercentage() +amountToChange);
                    conservation = conservation +amountToChange;
                }
            }
            blockArray.getBlock(ii, jj).setWaterPercentage(blockArray.getBlock(ii, jj).getWaterPercentage() - conservation);
        }
    }


    private void updateCrystalBlocks(int ii ,int jj)
    {
        Block currentBlock = blockArray.getBlock(ii, jj);
        if (currentBlock.getType() == GlobalConstants.CRYSTAL && ii > 0 && jj > 0 && ii < horizontalBlockLimit-1 && jj < verticalBlockLimit - 1)
        {
            Block_Crystal crystalBlock = (Block_Crystal) currentBlock;
            int currentIce = 0;
            for (int hh = -1; hh < 2; hh ++)
            {
                for (int kk = -1; kk < 2; kk++)
                {
                    if (blockArray.getBlock(ii + hh, jj + kk).isIce())
                    {
                        currentIce ++;
                    }
                }
            }
            crystalBlock.setSurroundingIce(currentIce);
        }
    }

    private void checkDynamiteFall(int ii, int jj)
    {
        if (activeBombs.isBombActive())
        {
            if (activeBombs.getBombBlock().getIndex() == blockArray.getBlock(ii, jj).getIndex() )
            {
                //Have got the bomb block
                //Check below
                if (jj < verticalBlockLimit - 1)
                {
                    if (blockArray.getBlock(ii, jj + 1).isCavern())
                    {
                        activeBombs.updateLocation(blockArray.getBlock(ii, jj + 1));
                    }
                }
            }

        }
    }

}
