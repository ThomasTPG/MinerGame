package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 27/07/2017.
 */

public class ActiveBombs {

    public static final int NO_BOMB = 0;
    public static final int DYNAMITE = 1;
    public static final int ICEBOMB = 2;
    public static final int TYPES = 2;
    private boolean isBombActive = false;
    private Bomb activeBomb;
    private Block bombBlock;
    private int type;

    public ActiveBombs()
    {

    }

    /**
     * Returns true if we can make a new bomb. Only one bomb can be created at a time
     * @param newType the type of bomb
     * @return true if a new bomb is created
     */
    public boolean newBomb(int newType, Block location)
    {
        if (!isBombActive)
        {
            activeBomb = new Bomb();
            type = newType;
            setBlock(location);
            isBombActive = true;
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

    /**
     * Function called to set the new location of a bomb
     * @param newLocation New block that the bomb resides
     */
    public void updateLocation(Block newLocation)
    {
        bombBlock.setBomb(NO_BOMB);
        bombBlock = newLocation;
        bombBlock.setBomb(type);
    }

    public Block getBombBlock()
    {
        return bombBlock;
    }

    public boolean isBombActive() {
        return isBombActive;
    }
}
