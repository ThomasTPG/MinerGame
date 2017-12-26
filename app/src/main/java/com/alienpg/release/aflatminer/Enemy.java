package com.alienpg.release.aflatminer;

/**
 * Created by User on 21/12/2017.
 */

public abstract class Enemy {

    private int xCoord;
    private int yCoord;
    private int width;
    private int height;
    private String type;

    public Enemy(int x, int y, int w, int h)
    {
        xCoord = x;
        yCoord = y;
        width = w;
        height = h;
    }

    public abstract void move();

    protected void setType(String input)
    {
        type = input;
    }

    public String getType()
    {
        return type;
    }

    public int getxCoord()
    {
        return xCoord;
    }

    public int getyCoord()
    {
        return yCoord;
    }
}
