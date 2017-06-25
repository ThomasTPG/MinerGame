package com.example.thomas.miner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Thomas on 24/06/2017.
 */

public class Clouds {

    private int counter = 0;
    private int timeBetweenMovement;
    private int cloudLocation = 0;
    private Bitmap cloudBmp;
    private Paint alphaPaintCloud;
    private Camera camera;
    private int LHSSky;
    private int topSky;
    private int RHSSky;
    private int bottomSky;

    public Clouds(int time, Bitmap cloud, Camera camera, int LHSSky, int topSky, int RHSSky, int bottomSky)
    {
        timeBetweenMovement = time;
        cloudBmp = cloud;
        alphaPaintCloud = new Paint();
        alphaPaintCloud.setAlpha(100);
        this.LHSSky = LHSSky;
        this.topSky = topSky;
        this.RHSSky = RHSSky;
        this.bottomSky = bottomSky;
        this.camera = camera;
    }

    public void drawCloud(Canvas canvas)
    {
        updateLocation();
        int rect1LHEdge = cloudLocation;
        int rect1RHEdge = Math.min(cloudLocation + cloudBmp.getWidth()/2, cloudBmp.getWidth());
        int rect2LHEdge = 0;
        int rect2RHEdge = cloudBmp.getWidth()/2 - (rect1RHEdge - rect1LHEdge);
        Rect rWhichCloud1 = new Rect(rect1LHEdge,0,rect1RHEdge,cloudBmp.getHeight());
        Rect rWhichCloud2 = new Rect(rect2LHEdge,0,rect2RHEdge,cloudBmp.getHeight());
        double percentMoved = (double)(Math.max(cloudLocation - cloudBmp.getWidth()/2,0))/(double)(cloudBmp.getWidth()/2);
        Rect rCloud1 = new Rect(LHSSky - camera.getCameraX(), topSky - camera.getCameraY()+canvas.getHeight()/2, RHSSky - camera.getCameraX() - (int) ((RHSSky-LHSSky) * percentMoved), bottomSky - camera.getCameraY()+canvas.getHeight()/2);
        Rect rCloud2 = new Rect(RHSSky - camera.getCameraX() - (int)((RHSSky - LHSSky) * percentMoved), topSky - camera.getCameraY()+canvas.getHeight()/2, RHSSky - camera.getCameraX(), bottomSky - camera.getCameraY()+canvas.getHeight()/2);
        canvas.drawBitmap(cloudBmp, rWhichCloud1,rCloud1,alphaPaintCloud);
        canvas.drawBitmap(cloudBmp, rWhichCloud2,rCloud2,alphaPaintCloud);
    }

    private void updateLocation()
    {
        counter++;
        if (counter%timeBetweenMovement == 0)
        {
            //We move the clouds
            cloudLocation = (cloudLocation + 1) % cloudBmp.getWidth();
        }
    }
}
