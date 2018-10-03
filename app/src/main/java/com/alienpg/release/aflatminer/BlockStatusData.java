package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 29/07/2017.
 */

public class BlockStatusData {

    int type;
    int minedPercentage = 0;
    int heightFallen = 0;

    public BlockStatusData()
    {

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void incrementMinedPercentage()
    {
        minedPercentage = minedPercentage + 3;
    }


    public int getMinedPercentage() {
        return minedPercentage;
    }

    public void setMinedPercentage(int minedPercentage) {
        this.minedPercentage = minedPercentage;
    }

    public String getMemoryString()
    {
        System.out.print(type);
        if (type == GlobalConstants.FIREBALL)
        {
            return (Integer.toString(GlobalConstants.CAVERN) + "!" + Integer.toString(minedPercentage));
        }
        return (Integer.toString(type) + "!" + Integer.toString(minedPercentage));
    }

    public void setFromMemory(String dataString)
    {
        type = Integer.parseInt(dataString.split("!")[0]);
        minedPercentage = Integer.parseInt(dataString.split("!")[1]);
    }

    public void increaseFallenDistance()
    {
        heightFallen ++;
    }

    public int getHeightFallen()
    {
        return heightFallen;
    }

    public void resetHeightFallen()
    {
        heightFallen = 0;
    }
}
