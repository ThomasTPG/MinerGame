package com.example.thomas.miner;

/**
 * Created by Thomas on 28/07/2017.
 */

public class NonSolidBlocks {

    private int waterPercentage= 0;
    private int gasPercentage = 0;
    private int lavaPercentage = 0;

    public NonSolidBlocks()
    {

    }

    public void setWaterPercentage(int water)
    {
        waterPercentage = water;
    }

    public void setGasPercentage(int gas)
    {
        gasPercentage = gas;
    }

    public void setLavaPercentage(int lava)
    {
        lavaPercentage = lava;
    }

    public int getWaterPercentage() {
        return waterPercentage;
    }

    public int getGasPercentage() {
        return gasPercentage;
    }

    public int getLavaPercentage() {
        return lavaPercentage;
    }

    public String getMemoryString()
    {
        return (Integer.toString(waterPercentage) + "!" + Integer.toString(gasPercentage) + "!" + Integer.toString(lavaPercentage));
    }

    public void setFromMemory(String dataString)
    {
        waterPercentage = Integer.parseInt(dataString.split("!")[0]);
        gasPercentage = Integer.parseInt(dataString.split("!")[1]);
        lavaPercentage = Integer.parseInt(dataString.split("!")[2]);
    }
}
