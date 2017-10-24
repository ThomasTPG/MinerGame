package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Thomas on 22/10/2017.
 */

public class InGameNotifications {

    public static int PICKAXE_UPGRADE = 1;
    public static int BOULDER_MINING = 2;
    public static int HARD_BOULDER_MINING = 3;
    public static int NEW_ENCYCLOPEDIA_ENTRY = 4;
    private int currentNotification = 0;
    private int notificationPeriod = 2000;
    private Rect notificationBoundary;
    private Rect imageBoundary;
    private Bitmap notificationBoundaryBmp;
    private Context mContext;
    private int blockSize;
    private int canvasHeight;
    private int canvasWidth;
    private int currentNotificationID = 1;
    private Bitmap bitmapToDraw;
    private Bitmap pickaxeUpgrade;
    private Bitmap boulderMining;
    private Bitmap hardBoulderMining;

    public InGameNotifications(int canvasWidth, int canvasHeight, int blockSize, Context context)
    {
        this.blockSize = blockSize;
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        mContext = context;
        notificationBoundary = new Rect(0, canvasHeight - blockSize, canvasWidth, canvasHeight);
        int imageBorder = (int) Math.ceil((double) blockSize/(double) 8);
        imageBoundary = new Rect(imageBorder, canvasHeight - blockSize + imageBorder, blockSize - imageBorder, canvasHeight - imageBorder);
        loadBitmaps();
    }


    private void loadBitmaps()
    {
        notificationBoundaryBmp = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.notificationborder);
        pickaxeUpgrade = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.pickaxe_wood);
        boulderMining = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.rock);
        hardBoulderMining = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.hardrock_notification);
    }

    public void setBitmap(Bitmap newBitmap)
    {
        bitmapToDraw = newBitmap;
    }

    public void setCurrentNotification(int newNotification)
    {
        Thread notificationTimer = new Thread(new Runnable() {
            @Override
            public void run() {
                int ID = ++currentNotificationID;
                synchronized (this)
                {
                    try
                    {
                        wait(notificationPeriod);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace(System.out);
                    }
                }
                if (ID == currentNotificationID - 1)
                {
                    currentNotification = 0;
                }
            }
        });

        currentNotification = newNotification;
        notificationTimer.start();
    }

    public void onDraw(Canvas c)
    {
        String text = "";
        switch (currentNotification)
        {
            case (0):
                return;
            case (1):
                text = "Upgrade your pickaxe.";
                bitmapToDraw = pickaxeUpgrade;
                break;
            case (2):
                text = "Cannot mine boulder.";
                bitmapToDraw = boulderMining;
                break;
            case (3):
                text = "Cannot mine boulder.";
                bitmapToDraw = hardBoulderMining;
                break;
            case (4):
                text = "New encyclopedia entry.";
                break;
        }
        c.drawBitmap(notificationBoundaryBmp,null, notificationBoundary,null);
        Paint p = new Paint();
        int sideTextBorder = blockSize/2;
        int tHeight = setTextSizeForWidth(p, canvasWidth - blockSize - 2*sideTextBorder, text);
        int bottomTextBorder = (int) Math.floor((double)(blockSize - tHeight)/(double)2);
        c.drawText(text,blockSize + sideTextBorder,canvasHeight - bottomTextBorder,p);
        c.drawBitmap(bitmapToDraw,null,imageBoundary,null);
    }

    private static int setTextSizeForWidth(Paint paint, float desiredWidth,
                                            String text) {

        // Pick a reasonably large value for the test. Larger values produce
        // more accurate results, but may cause problems with hardware
        // acceleration. But there are workarounds for that, too; refer to
        // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
        final float testTextSize = 48f;

        // Get the bounds of the text, using our testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize.
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();

        // Set the paint for that size.
        paint.setTextSize(desiredTextSize);
        return Math.round(bounds.height() * desiredWidth / bounds.width());
    }

}
