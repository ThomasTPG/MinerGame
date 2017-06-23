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
        oreArray = new int[GlobalConstants.NUMBEROFTYPES];
        for (int ii = 0; ii < GlobalConstants.NUMBEROFTYPES; ii++)
        {
            oreArray[ii] = 0;
        }
    }

    void empty()
    {
        for (int ii = 0; ii < GlobalConstants.NUMBEROFTYPES; ii++)
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
        String[] oreOutput = new String[GlobalConstants.NUMBEROFTYPES+1];
        oreOutput[0] = Integer.toString(GlobalConstants.NUMBEROFTYPES);
        for (int ii = 0; ii < GlobalConstants.NUMBEROFTYPES; ii++)
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
