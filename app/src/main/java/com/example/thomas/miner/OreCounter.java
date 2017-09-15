package com.example.thomas.miner;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by Thomas on 23/02/2017.
 */

class OreCounter {

    private int[] oreArray;

    OreCounter()
    {
        //Initialise the ore array
        oreArray = new int[GlobalConstants.MEMORY_LENGTH_ARRAY_ORE];
        for (int ii = 0; ii < GlobalConstants.MEMORY_LENGTH_ARRAY_ORE; ii++)
        {
            oreArray[ii] = 0;
        }
    }

    void empty()
    {
        for (int ii = 0; ii < GlobalConstants.MEMORY_LENGTH_ARRAY_ORE; ii++)
        {
            oreArray[ii] = 0;
        }
    }

    void incrementOre(int type)
    {
        oreArray[type - GlobalConstants.SOIL] ++;
    }

    void setOre(int type, int amount)
    {
        oreArray[type - GlobalConstants.SOIL] = amount;
    }

    String[] getOre()
    {
        String[] oreOutput = new String[GlobalConstants.MEMORY_LENGTH_ARRAY_ORE+1];
        oreOutput[0] = Integer.toString(GlobalConstants.MEMORY_LENGTH_ARRAY_ORE);
        for (int ii = 0; ii < GlobalConstants.MEMORY_LENGTH_ARRAY_ORE; ii++)
        {
            oreOutput[ii+1] = Integer.toString(GlobalConstants.SOIL + ii) + "-" + Integer.toString(oreArray[ii]);
        }
        return oreOutput;
    }

    int getCount(int type)
    {
        return oreArray[type - GlobalConstants.SOIL];
    }



}
