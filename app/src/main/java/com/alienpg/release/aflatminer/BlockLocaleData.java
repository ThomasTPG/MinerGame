package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 17/12/2017.
 */

public class BlockLocaleData {

    private int index;
    private Coordinates coordinates;

    public BlockLocaleData(int index, Coordinates coordinates)
    {
        this.index = index;
        this.coordinates = coordinates;
    }

    public int getIndex() {
        return index;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
