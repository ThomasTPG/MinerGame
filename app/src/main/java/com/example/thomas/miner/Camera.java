package com.example.thomas.miner;

/**
 * Created by Thomas on 22/02/2017.
 */

public class Camera {

    private int cameraX;
    private int cameraY;

    public Camera()
    {
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
}
