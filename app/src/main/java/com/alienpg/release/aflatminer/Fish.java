package com.alienpg.release.aflatminer;

/**
 * Created by User on 21/12/2017.
 */

public class Fish extends Enemy {

    BlockManager blockManager;
    Camera camera;

    public Fish(int x, int y, int width, int height, BlockManager blockManager, Camera camera)
    {
        super(x, y, width, height);
        this.blockManager = blockManager;
        this.camera = camera;
        setType(EnemyList.FISH_TYPE);
    }

    private Block getCurrentBlock()
    {
        Coordinates screenTopLeft = camera.getScreenTopLeft();
        Coordinates screenCoords = new Coordinates(getxCoord() - screenTopLeft.getX(), getyCoord() - screenTopLeft.getY());

        if (blockManager.isInBlockArray(screenCoords))
        {
            return (blockManager.getBlockFromArrayUsingScreenCoordinates(screenCoords));
        }
        return null;
    }

    public void move()
    {
        Block myBlock = getCurrentBlock();
        if (myBlock == null)
        {
            return;
        }
        else
        {
            if (myBlock.getWaterPercentage() < 95)
            {

            }
        }
    }






}
