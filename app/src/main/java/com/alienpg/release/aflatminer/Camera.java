package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 22/02/2017.
 */

public class Camera {

    private int gameHeight;
    private int gameWidth;
    private int cameraX;
    private int cameraY;

    public Camera(int gameHeight, int gameWidth)
    {
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
        cameraX = 0;
        cameraY = 0;
    }

    public int getCameraY() {
        return cameraY;
    }

    public void setCameraY(int cameraY) {
        this.cameraY = cameraY;
    }

    public int getCameraX() {
        return cameraX;
    }

    public void setCameraX(int cameraX) {
        this.cameraX = cameraX;
    }

    public Coordinates getScreenTopLeft()
    {
        return (new Coordinates((int)Math.floor(cameraX - gameWidth/2), (int)Math.floor(cameraY - gameHeight/2)));
    }
}
