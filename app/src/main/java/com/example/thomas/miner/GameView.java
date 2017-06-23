package com.example.thomas.miner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by Thomas on 25/01/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    private SurfaceHolder mHolder;
    private GameThread mThread;
    private Context mContext;
    boolean running = true;
    int gameWidth = 0;
    int gameHeight = 0;
    int blockSize = 0;
    boolean jumpQueued = false;
    boolean moveLeft = false;
    boolean moveRight = false;
    boolean motion = false;
    boolean action;
    long jumpTime = 0;
    Mining miningClass = null;
    Sprite mainCharacter;
    private LevelMemory levelMemory;
    private OreMemory oreMemory;
    private ShopMemory shopMemory;
    private boolean gameOver = false;

    public GameView(Context context) {
        super(context);
        constructor(context);
    }

    public GameView(Context context, AttributeSet attrs){
        super(context,attrs);
        constructor(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        constructor(context);
    }

    private void constructor(Context context){
        mHolder = getHolder();
        mHolder.addCallback(this);
        mThread = new GameThread(this.getContext());
        mContext = context;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new GameThread(this.getContext());
        mThread.setRunning(true);
        mThread.start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    public boolean isRunning()
    {
        return running;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //this.onPause();
    }

    public void motionEvent(MotionEvent e)
    {
        if (gameHeight > 0 && gameWidth > 0 && motion)
        {
            if (e.getX() > gameWidth/2 + blockSize * 3/2)
            {
                moveRight = true;
                moveLeft = false;
            }
            else if (e.getX() < gameWidth/2 - blockSize * 3/2)
            {
                moveLeft = true;
                moveRight = false;
            }
            else
            {
                moveRight = false;
                moveLeft = false;
            }
        }
        else if (miningClass != null && action)
        {
            int xLocation = (int) e.getX();
            int yLocation = (int) e.getY();
            if (!mainCharacter.isInAir())
            {
                //Time to mine!
                miningClass.calculateMiningOctant(xLocation,yLocation);
            }

        }
    }

    public void stopMotion(MotionEvent e)
    {
        motion = false;
        action = false;
        if (miningClass != null)
        {
            miningClass.stopMining();
        }
        if (gameHeight > 0 && gameWidth > 0 && !mainCharacter.isInAir()) {
            int midX = gameWidth / 2;
            int midY = gameHeight / 2;
            if (Math.abs(e.getX() - midX) < blockSize * 3 / 2) {
                if (Math.abs(e.getY() - midY) < blockSize * 3 / 2) {
                    long elapsedTime = System.currentTimeMillis() - jumpTime;
                    if (elapsedTime < 1000)
                    {
                        jumpTime = 0;
                        jumpQueued = true;
                    }
                    else
                    {
                        jumpQueued = false;
                    }
                }
            }
        }
    }

    public void requestAction(MotionEvent e)
    {
        //Check screen initialized
        if (gameHeight > 0 && gameWidth > 0 )
        {
            int midX = gameWidth/2;
            int midY = gameHeight/2;
            if (Math.abs(e.getX() - midX) < blockSize * 3/2)
            {
                if (Math.abs(e.getY() - midY) < blockSize * 3/2)
                {
                    //Jumping or mining to be performed
                    jumpTime = System.currentTimeMillis();
                    action = true;
                    motion = false;
                }
            }
            else
            {
                //Movement to be performed
                motion = true;
                action = false;
                motionEvent(e);
            }
        }
    }

    public synchronized void onPause(){

        mThread.onPause();
    }

    public synchronized void onResume()
    {
        running = true;
        if (!mThread.isAlive())
        {
            mThread.start();
        }

    }

    public boolean isGameOver()
    {
        return gameOver;
    }

    public class GameThread extends Thread{
        long timeBefore;
        long difference;
        int fps = 45;
        long timeNow;
        Context c;
        int verticalBlockLimit;
        int horizontalBlockLimit;
        ArrayOfBlocksOnScreen blocksOnScreen;
        MapArt mapArt;
        // Define a number of blocks that the game will hold. Cannot move once reaches the edge?
        int blocksAcross = getResources().getInteger(R.integer.blocks_across);
        int blocksAcrossScreen = getResources().getInteger(R.integer.blocks_per_screen_width);

        // Work out how many pixels this is in total - to be used to calculate which blocks are on screen.
        int pixelsAcross;
        int[][] cavernLocation = new int[1][4];
        boolean initialized = false;
        //Sprite information
        int spriteDimension;
        int seed = 50;
        int AIR = 1;
        MinedLocations minedLocations;
        OreCounter oreCounter = new OreCounter();
        Camera camera;
        CheckExit checkExit;

        public GameThread(Context context){
            c = context;
            timeNow = System.nanoTime();
            seed = (int)(timeNow%1000000);

        }

        @Override
        public void run() {
            super.run();
            try{
                while(running){
                    timeCheck();
                    if(!mHolder.getSurface().isValid()){
                        continue;
                    }

                    Canvas c = mHolder.lockCanvas();

                    if (!initialized)
                    {
                        initialize(c);
                    }

                    drawBackGround(c);
                    blocksOnScreen.calculateCurrentBlocks();
                    blocksOnScreen.updateWater();
                    blocksOnScreen.drawCurrentBlocks(c);
                    mapArt.drawArt(camera,c);
                    mainCharacter.draw();
                    Paint p = new Paint();
                    p.setColor(Color.BLACK);
                    c.drawText(Integer.toString(camera.getCameraX()) + Integer.toString(camera.getCameraY()), 10,10,p);
                    if (miningClass.isCurrentlyMining())
                    {
                        miningClass.mine();
                    }
                    updateCamera();
                    if(checkExit.check(c))
                    {
                        //The player has decided to exit. Save the ores and delete the level file.
                        System.out.println("Game exit");
                        oreMemory.writeFile(oreCounter);
                        oreCounter.empty();
                        levelMemory.onEndGame();
                        gameOver = true;
                        running = false;
                    }
                    if (mainCharacter.isDead())
                    {
                        System.out.println("Game exit");
                        gameOver = true;
                        running = false;
                    }


                    mHolder.unlockCanvasAndPost(c);


                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        private void initialize(Canvas c)
        {
            camera = new Camera();
            gameHeight = c.getHeight();
            gameWidth = c.getWidth();
            blockSize = c.getWidth()/blocksAcrossScreen;
            spriteDimension= blockSize * 3 / 4;
            initialized = true;
            // Work out how many blocks we can see on the screen at any one time
            verticalBlockLimit = (int) Math.floor(gameHeight / blockSize + 2);
            horizontalBlockLimit = (int) Math.floor(gameWidth / blockSize + 2);

            //Work out the total pixel size of the game if all could be seen at once
            pixelsAcross = blocksAcross * blockSize;
            camera.setCameraX(pixelsAcross/2);
            camera.setCameraY(-3*blockSize - spriteDimension);
            minedLocations = new MinedLocations();
            levelMemory = new LevelMemory(mContext,minedLocations, seed, camera, oreCounter);
            oreMemory = new OreMemory(mContext);
            shopMemory = new ShopMemory(mContext);
            if (levelMemory.canLoadLevel())
            {
                seed = levelMemory.loadLevelData();
            }
            blocksOnScreen = new ArrayOfBlocksOnScreen(gameWidth, gameHeight, blockSize, mContext, seed, camera, minedLocations);
            blocksOnScreen.updateCurrentScreenDimensions();
            blocksOnScreen.updatePreviousScreenDimensions();
            mapArt = new MapArt(c,mContext,blockSize, shopMemory);
            mainCharacter = new Sprite(mContext, c, spriteDimension, blockSize, blocksOnScreen);
            miningClass = new Mining(gameHeight, gameWidth, blocksOnScreen, blockSize, oreCounter);
            checkExit = new CheckExit(camera, blockSize,mContext);
        }





        public void drawBackGround(Canvas c)
        {
            Paint p = new Paint();
            p.setColor(Color.WHITE);
            c.drawRect(0,0,gameWidth,gameHeight, p);
        }

        public void setRunning(boolean b){
            running = b;
        }

        public void timeCheck(){
            timeBefore = timeNow;
            timeNow = System.nanoTime();
            difference = timeNow-timeBefore;

            if (difference/1000000 - 1000/fps < 0){
                try{
                    Thread.sleep(1000/fps - difference/1000000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        public void updateCamera()
        {
            int newCameraX = camera.getCameraX();
            int newCameraY = camera.getCameraY();
            Block bottom = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2, gameHeight/2 + spriteDimension/2);
            if (mainCharacter.isClambering())
            {
                newCameraY = newCameraY + mainCharacter.clamberY();
                newCameraX = newCameraX + mainCharacter.clamberX();
            }

            else
            {
                //We need to know the block below the sprite's left and right x values in order to decide on whether we fall
                int baseGravitySpeed = mainCharacter.getBaseGravitySpeed();
                int speed = mainCharacter.getRunningSpeed();
                int TOL = speed/2;


                //Check gravity - if there is an entire empty block beneath us, fall down
                Block bottomLeft = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 - spriteDimension/2 + TOL, gameHeight/2 + spriteDimension/2 + baseGravitySpeed);
                Block bottomRight = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 + spriteDimension/2 - TOL, gameHeight/2 + spriteDimension/2 + baseGravitySpeed);
                //System.out.println("Y = " + bottomLeft.getY() + " " + bottomRight.getY());
                if (!mainCharacter.isJumping())
                {
                    if (!bottomLeft.isSolid() && !bottomRight.isSolid())
                    {
                        newCameraY = newCameraY + mainCharacter.getGravitySpeed(baseGravitySpeed);
                        if (mainCharacter.isInWater())
                        {
                            blocksOnScreen.splash();
                        }
                    }
                    else {
                        //If there is a gap between us and the floor that is less than a block height - fall
                        int gap = blockSize - (camera.getCameraY() + spriteDimension / 2) % blockSize;
                        newCameraY = newCameraY + mainCharacter.getGravitySpeed(gap);
                    }
                }

                //Check movement
                if (moveLeft)
                {
                    Block leftTop = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 - spriteDimension/2 - speed, gameHeight/2-spriteDimension/2 + TOL);
                    Block leftBottom = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 - spriteDimension/2 - speed, gameHeight/2+spriteDimension/2 - TOL);
                    if (!leftBottom.isSolid() && !leftTop.isSolid())
                    {
                        newCameraX = newCameraX - speed;
                        Block below = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2, gameHeight/2 + blockSize);
                        if (mainCharacter.isInWater() || below.getWaterPercentage() == 100)
                        {
                            blocksOnScreen.moveWaterLeft();
                        }
                    }
                    else
                    {
                        int gap = (camera.getCameraX() - spriteDimension/2)%blockSize;
                        if (gap <= 2*TOL)
                        {
                            if (speed <= gap)
                            {
                                newCameraX = newCameraX - speed;
                            }
                            else
                            {
                                newCameraX = newCameraX - gap;
                            }
                        }
                        else if (blockSize - gap <= 2*TOL)
                        {
                            if (speed <= gap)
                            {
                                newCameraX = newCameraX + speed;
                            }
                            else
                            {
                                newCameraX = newCameraX + blockSize - gap;
                            }
                        }
                        if (gap%blockSize == 0 && mainCharacter.isInAir())
                        {
                            //See if we should clamber up
                            Block above = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2, gameHeight/2 - blockSize + spriteDimension/2);
                            Block aboveLeft = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 - blockSize, gameHeight/2 - blockSize + spriteDimension/2);
                            Block below = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2, gameHeight/2 + spriteDimension/2 + blockSize);
                            if (!above.isSolid() && !aboveLeft.isSolid() && !below.isSolid())
                            {
                                //Start clambering
                                int heightToClamber = (camera.getCameraY() + spriteDimension/2)%blockSize;
                                mainCharacter.clamberLeft(heightToClamber);
                                newCameraY = newCameraY - baseGravitySpeed;

                            }
                        }



                    }
                    if (!motion)
                    {
                        moveLeft = false;
                    }
                }
                if (moveRight)
                {
                    Block rightTop = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 + spriteDimension/2 + speed, gameHeight/2 - spriteDimension/2 + TOL);
                    Block rightBottom = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 + spriteDimension/2 + speed, gameHeight/2 + spriteDimension/2 - TOL);
                    if (!rightBottom.isSolid() && !rightTop.isSolid())
                    {
                        newCameraX = newCameraX + speed;
                        Block below = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2, gameHeight/2 + blockSize);
                        if (mainCharacter.isInWater() || below.getWaterPercentage() == 100)
                        {
                            blocksOnScreen.moveWaterRight();
                        }

                    }
                    else {
                        int gap = blockSize - ((camera.getCameraX() + spriteDimension / 2) % blockSize);
                        if (gap <= 2 * TOL) {
                            if (speed <= gap) {
                                newCameraX = newCameraX + speed;
                            } else {
                                newCameraX = newCameraX + gap;
                            }
                        } else if (blockSize - gap <= 2 * TOL) {
                            if (speed <= blockSize - gap) {
                                newCameraX = newCameraX- speed;
                            } else {
                                newCameraX = newCameraX - blockSize + gap;
                            }
                        }
                        if (gap%blockSize == 0 && mainCharacter.isInAir())
                        {
                            //See if we should clamber up
                            Block above = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2, gameHeight/2 - blockSize + spriteDimension/2);
                            Block aboveRight = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 + blockSize, gameHeight/2 - blockSize + spriteDimension/2);
                            Block below = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2, gameHeight/2 + spriteDimension/2 + blockSize);
                            if (!above.isSolid() && !aboveRight.isSolid() && !below.isSolid())
                            {
                                //Start clambering
                                int heightToClamber = (camera.getCameraY() + spriteDimension/2)%blockSize;
                                mainCharacter.clamberRight(heightToClamber);
                                newCameraY = newCameraY - baseGravitySpeed;
                            }
                        }
                    }
                    if (!motion)
                    {
                        moveRight = false;
                    }
                }
                if (jumpQueued && !mainCharacter.isInAir())
                {
                    Block topRight = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 - spriteDimension/2 + TOL, gameHeight/2 - blockSize);
                    Block topLeft = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(gameWidth/2 + spriteDimension/2 - TOL, gameHeight/2 - blockSize);
                    int jumpHeight = blockSize - spriteDimension;
                    if (!topLeft.isSolid() && !topRight.isSolid())
                    {
                        jumpHeight = 2 * blockSize - spriteDimension;
                    }
                    mainCharacter.startJumping(jumpHeight);
                    jumpQueued = false;
                }
                if (mainCharacter.isJumping())
                {
                    newCameraY = newCameraY - mainCharacter.getJumpSpeed();
                }
            }


            camera.setCameraX(newCameraX);
            camera.setCameraY(newCameraY);
            blocksOnScreen.updatePreviousScreenDimensions();
            blocksOnScreen.updateCurrentScreenDimensions();

        }

        public void onPause()
        {
            if (running)
            {
                levelMemory.saveLocations();
                running = false;
            }
        }
    }


}
