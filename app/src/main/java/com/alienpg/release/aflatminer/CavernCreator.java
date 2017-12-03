package com.alienpg.release.aflatminer;

import java.util.Random;

/**
 * Created by Thomas on 14/02/2017.
 */

public class CavernCreator {

    //Can have a cavern every 20 vertical blocks. There will be a percentage chance that we will actually have one though. If we do, then the block will generate it beased on the y cordinate, (and the 20 blocks above and below)

    private int blocksAcross;
    private int x;
    private int y;
    private int seed;
    private int minHeightBetweenCaverns = 35;
    private int closestCavernX = 0;
    private int closestCavernY = 0;
    private int distanceToCavern;
    private double skewX;
    private double skewY;
    private boolean isCavern = false;


    public CavernCreator(Coordinates coordinates, int blocksAcross, int seed)
    {
        x = coordinates.getX();
        y = coordinates.getY();
        this.seed = seed;
        this.blocksAcross = blocksAcross;


        calculateCavernLocations();
        calculateSkew();
        if (y < 0)
        {
            //No blocks above the horizon
            isCavern = true;
        }
        else if (y < 12)
        {
            //No caverns close to surface
            isCavern = false;
        }
        else
        {
            isCavern = calculateIfCavern();
        }
    }

    private void calculateCavernLocations()
    {
        int minDistance = 100000;
        for (int ii = 0; ii < 3; ii++)
        {
            int cavernNumber = (int) Math.floor(y/(double)minHeightBetweenCaverns) + ii - 1;
            Random cavernRandomHeight = new Random(cavernNumber * seed);
            int cavernHeightOffset = cavernRandomHeight.nextInt(minHeightBetweenCaverns);
            int newCavernY = minHeightBetweenCaverns * cavernNumber + cavernHeightOffset;
            int newCavernX = cavernRandomHeight.nextInt(blocksAcross);

            int distance = (int) Math.pow(Math.pow(x - newCavernX,2) + Math.pow(y-newCavernY,2),0.5);
            if (distance < minDistance)
            {
                minDistance = distance;
                closestCavernX = newCavernX;
                closestCavernY = newCavernY;
                distanceToCavern = distance;
            }
        }
    }

    private void calculateSkew()
    {
        Random skewRandom = new Random(closestCavernX * closestCavernY + seed);
        skewX = skewRandom.nextDouble()/4 + 0.75;
        skewY = skewRandom.nextDouble()/4 + 0.75;

    }

    private boolean calculateIfCavern()
    {
        Random cavernRandom = new Random(x * y * seed);
        int distortionFactor = cavernRandom.nextInt(200);
        int cavernProbability = (int) (1000/ (distortionFactor + (Math.pow(((x - closestCavernX) * skewX), 2) + Math.pow((y - closestCavernY) * skewY, 2))));
        return((cavernProbability > 5));
    }

    public int getDistanceToCavern()
    {
        return distanceToCavern;
    }

    public boolean getIsCavern()
    {
        return isCavern;
    }

}
