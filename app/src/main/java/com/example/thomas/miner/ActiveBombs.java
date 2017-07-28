package com.example.thomas.miner;

import android.graphics.Canvas;

/**
 * Created by Thomas on 27/07/2017.
 */

public class ActiveBombs {

    public static final int NO_BOMB = 0;
    public static final int DYNAMITE = 1;
    public static final int ICEBOMB = 2;
    public static final int TYPES = 2;
    private boolean isBombActive;
    private Bomb activeBomb;
    private Block bombBlock;
    private int type;

    public ActiveBombs()
    {

    }

    public boolean newBomb(int newType, int x, int y)
    {
        if (!isBombActive)
        {
            activeBomb = new Bomb();
            isBombActive = true;
            type = newType;
            activeBomb.startTimer();
            return true;
        }
        return false;
    }

    public boolean hasBombExploded()
    {
        if (isBombActive)
        {
            if (activeBomb.isExploded())
            {
                isBombActive = false;
                type = NO_BOMB;
                return true;
            }
        }
        return false;
    }

    public void setBlock(Block location)
    {
        bombBlock = location;
        bombBlock.setBomb(type);
    }

    public Block getBombBlock()
    {
        return bombBlock;
    }
}
