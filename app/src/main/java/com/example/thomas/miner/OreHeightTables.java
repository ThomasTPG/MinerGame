package com.example.thomas.miner;

/**
 * Created by Thomas on 16/02/2017.
 */

public final class OreHeightTables {

    public static int determineOreTable1(double random)
    {
        if (random < 0.01)
        {
            return GlobalConstants.COSTUMEGEM;
        }
        else if (random < 0.7)
        {
            return GlobalConstants.COPPER;
        }
        else
        {
            return GlobalConstants.IRON;
        }
    }

    public static int determineOreTable2(double random)
    {
        if (random < 0.01)
        {
            return GlobalConstants.COSTUMEGEM;
        }
        else if (random < 0.4)
        {
            return GlobalConstants.COPPER;
        }
        else if (random < 0.8)
        {
            return GlobalConstants.IRON;
        }
        else if (random < 0.99)
        {
            return GlobalConstants.EXPLODIUM;
        }
        else
        {
            return GlobalConstants.SPRING;
        }
    }

    public static int determineOreTable3(double random)
    {
        if (random < 0.01)
        {
            return GlobalConstants.COSTUMEGEM;
        }
        else if (random < 0.2)
        {
            return GlobalConstants.COPPER;
        }
        else if (random < 0.4)
        {
            return GlobalConstants.IRON;
        }
        else if (random < 0.5)
        {
            return GlobalConstants.GASROCK;
        }
        else if (random < 0.9)
        {
            return GlobalConstants.EXPLODIUM;
        }
        else if (random < 0.99)
        {
            return GlobalConstants.MARBLE;
        }
        else
        {
            return GlobalConstants.SPRING;
        }
    }

    public static int determineOreTable4(double random)
    {
        if (random < 0.01)
        {
            return GlobalConstants.COSTUMEGEM;
        }
        else if (random < 0.15)
        {
            return GlobalConstants.COPPER;
        }
        else if (random < 0.3)
        {
            return GlobalConstants.IRON;
        }
        else if (random < 0.4)
        {
            return GlobalConstants.GASROCK;
        }
        else if (random < 0.5)
        {
            return GlobalConstants.EXPLODIUM;
        }
        else if (random < 0.65)
        {
            return GlobalConstants.MARBLE;
        }
        else if (random < 0.8)
        {
            return GlobalConstants.ICE;
        }
        else if (random < 0.99)
        {
            return GlobalConstants.LIFE;
        }
        else
        {
            return GlobalConstants.SPRING;
        }
    }

    public static int determineOreTable5(double random)
    {
        if (random < 0.01)
        {
            return GlobalConstants.COSTUMEGEM;
        }
        else if (random < 0.05)
        {
            return GlobalConstants.COPPER;
        }
        else if (random < 0.1)
        {
            return GlobalConstants.IRON;
        }
        else if (random < 0.25)
        {
            return GlobalConstants.EXPLODIUM;
        }
        else if (random < 0.4)
        {
            return GlobalConstants.MARBLE;
        }
        else if (random < 0.61)
        {
            return GlobalConstants.ICE;
        }
        else if (random < 0.82)
        {
            return GlobalConstants.LIFE;
        }
        else if (random < 0.90)
        {
            return GlobalConstants.GOLD;
        }
        else if (random < 0.97)
        {
            return GlobalConstants.CRYSTAL;
        }
        else
        {
            return GlobalConstants.SPRING;
        }
    }

}
