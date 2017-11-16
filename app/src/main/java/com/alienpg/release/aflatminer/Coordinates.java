package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 15/11/2017.
 */

public class Coordinates {

    int x;
    int y;

    public Coordinates()
    {

    }

    public Coordinates(int newX, int newY)
    {
        x = newX;
        y = newY;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setCoordinates(int newX,int newY)
    {
        x = newX;
        y = newY;
    }

    int getX()
    {
        return x;
    }

    int getY()
    {
        return y;
    }


}
