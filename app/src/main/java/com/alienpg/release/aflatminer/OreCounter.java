package com.alienpg.release.aflatminer;

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
        oreArray[type] ++;
    }

    void setOre(int type, int amount)
    {
        oreArray[type] = amount;
    }

    String[] getOre()
    {
        String[] oreOutput = new String[GlobalConstants.MEMORY_LENGTH_ARRAY_ORE];
        for (int ii = 0; ii < GlobalConstants.MEMORY_LENGTH_ARRAY_ORE; ii++)
        {
            oreOutput[ii] = Integer.toString(ii) + "-" + Integer.toString(oreArray[ii]);
        }
        return oreOutput;
    }

    int getCount(int type)
    {
        return oreArray[type];
    }



}
