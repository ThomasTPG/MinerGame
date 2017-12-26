package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.common.api.GoogleApiClient;

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
    int dynamiteButtonSize = 0;
    Rect dynamiteButtonLocation;
    Rect iceBombButtonLocation;
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
    private EncyclopediaMemory encyclopediaMemory;
    private boolean gameOver = false;
    private Bitmap dynamiteButton;
    private Bitmap iceBombButton;
    private PickaxeManager pickaxeManager;
    Camera camera;
    private ActiveBombs activeBombs;
    BlockManager blocksOnScreen;
    boolean initialized = false;
    BlockPhysics blockPhysics;
    BlockDrawing blockDrawing;
    private int endReason = GlobalConstants.ESCAPE;
    private InGameNotifications inGameNotifications;
    private GoogleApiClient googleApiClient = null;
    private Achievements achievementsManager = null;

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

    public void setApiClient(GoogleApiClient googleApiClient)
    {
        this.googleApiClient = googleApiClient;
        achievementsManager.setmGoogleApiClient(googleApiClient);
    }

    private void constructor(Context context){
        mHolder = getHolder();
        mHolder.addCallback(this);
        mThread = new GameThread(this.getContext());
        mContext = context;
        activeBombs = new ActiveBombs();
        dynamiteButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.dynamitebutton);
        iceBombButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.icebombbutton);
        achievementsManager = new Achievements(mContext);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new GameThread(this.getContext());
        mThread.setRunning(true);
        mThread.start();
    }

    public int getEndReason()
    {
        return endReason;
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
        if (initialized)
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
    }

    public void stopMotion(MotionEvent e)
    {
        if (initialized)
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
    }

    public void requestAction(MotionEvent e)
    {
        if (initialized)
        {
            //Check screen initialized
            if (gameHeight > 0 && gameWidth > 0 )
            {
                int midX = gameWidth/2;
                int midY = gameHeight/2;
                Coordinates myCoords = new Coordinates(gameWidth/2, gameHeight/2);
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
                else if (e.getX() < dynamiteButtonSize * 3 && e.getY() < dynamiteButtonSize * 3)
                {
                    //Player has pressed dynamite button
                    if (activeBombs.newBomb(ActiveBombs.DYNAMITE, camera.getCameraX(),camera.getCameraY()))
                    {
                        Block current = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(myCoords);
                        activeBombs.setBlock(current);
                    }
                }
                else if (e.getX() < dynamiteButtonSize * 3 && e.getY() < dynamiteButtonSize * 6)
                {
                    //Player has pressed icebomb button
                    if (activeBombs.newBomb(ActiveBombs.ICEBOMB, camera.getCameraX(),camera.getCameraY()))
                    {
                        Block current = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(myCoords);
                        activeBombs.setBlock(current);
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
        MapArt mapArt;
        // Define a number of blocks that the game will hold. Cannot move once reaches the edge?
        int blocksAcross = getResources().getInteger(R.integer.blocks_across);
        int blocksAcrossScreen = getResources().getInteger(R.integer.blocks_per_screen_width);

        // Work out how many pixels this is in total - to be used to calculate which blocks are on screen.
        int pixelsAcross;
        int[][] cavernLocation = new int[1][4];
        //Sprite information
        int spriteDimension;
        int seed = 50;
        int AIR = 1;
        MinedLocations minedLocations;
        OreCounter oreCounter = new OreCounter();

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
                    blockPhysics.updateDynamicBlocks();
                    blockDrawing.drawBlocks(c);
                    //blocksOnScreen.drawCurrentBlocks(c);
                    mapArt.drawArt(c);
                    mainCharacter.draw();
                    mapArt.drawForeGround(c);
                    mainCharacter.drawOxygen(c);
                    pickaxeManager.onDraw(c);
                    drawDynamiteButton(c);
                    drawIceBombButton(c);
                    if (activeBombs.hasBombExploded())
                    {
                        Coordinates explode = blocksOnScreen.getBlockCoordinatesByIndex(activeBombs.getBombBlock());
                        if (explode.getX() > 0)
                        {
                            blockPhysics.explodeBlock(explode.getX(), explode.getY());
                        }
                    }
                    miningClass.mine();
                    inGameNotifications.onDraw(c);
                    updateCamera();
                    if(checkExit.check(c))
                    {
                        //The player has decided to exit. Save the ores and delete the level file.
                        oreMemory.updatePreviouslyMined(oreCounter);
                        oreCounter.empty();
                        levelMemory.onEndGame();
                        gameOver = true;
                        running = false;
                        achievementsManager.checkExitAchievements(mapArt.getTime());
                    }
                    if (mainCharacter.isDead())
                    {
                        oreMemory.updatePreviouslyMined(oreCounter);
                        endReason = mainCharacter.getDeath();
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
            gameHeight = c.getHeight();
            gameWidth = c.getWidth();
            camera = new Camera(gameHeight, gameWidth);
            dynamiteButtonSize = gameWidth/15;
            dynamiteButtonLocation = new Rect(dynamiteButtonSize,dynamiteButtonSize,2*dynamiteButtonSize,2*dynamiteButtonSize);
            iceBombButtonLocation = new Rect(dynamiteButtonSize,4*dynamiteButtonSize,2*dynamiteButtonSize,5*dynamiteButtonSize);
            blockSize = c.getWidth()/blocksAcrossScreen;
            spriteDimension= blockSize * 3 / 4;
            initialized = true;
            // Work out how many blocks we can see on the screen at any one time
            verticalBlockLimit = (int) Math.floor(gameHeight / blockSize + 2);
            horizontalBlockLimit = (int) Math.floor(gameWidth / blockSize + 2);

            //Work out the total pixel size of the game if all could be seen at once
            pixelsAcross = blocksAcross * blockSize;
            camera.setCameraX(pixelsAcross/2);
            camera.setCameraY( - spriteDimension);
            minedLocations = new MinedLocations();
            mainCharacter = new Sprite(mContext, c, spriteDimension, blockSize, achievementsManager);
            levelMemory = new LevelMemory(mContext,minedLocations, seed, camera, oreCounter, mainCharacter);
            oreMemory = new OreMemory(mContext);
            shopMemory = new ShopMemory(mContext);
            encyclopediaMemory = new EncyclopediaMemory(mContext);
            if (levelMemory.canLoadLevel())
            {
                seed = levelMemory.loadLevelData();
            }
            blocksOnScreen = new BlockManager(gameWidth, gameHeight, blockSize, mContext, seed, camera, minedLocations, achievementsManager);
            blocksOnScreen.updateCurrentScreenDimensions();
            blocksOnScreen.updatePreviousScreenDimensions();
            achievementsManager.initialize(gameWidth,gameHeight,blocksOnScreen );
            mainCharacter.setBlocksOnScreen(blocksOnScreen);
            blockPhysics = new BlockPhysics(blocksOnScreen.getBlockArray(), activeBombs, mContext, achievementsManager);
            inGameNotifications = new InGameNotifications(gameWidth, gameHeight, blockSize, mContext);
            blockDrawing = new BlockDrawing(blocksOnScreen,mContext,blockSize, camera, gameWidth, gameHeight, encyclopediaMemory, inGameNotifications, achievementsManager);
            mapArt = new MapArt(c,mContext,blockSize, shopMemory,camera);
            miningClass = new Mining(gameHeight, gameWidth, blocksOnScreen, blockSize, oreCounter, shopMemory, inGameNotifications);
            pickaxeManager = new PickaxeManager(miningClass, c, mContext, blockSize, mainCharacter,shopMemory);
            checkExit = new CheckExit(camera, blockSize,mContext, gameHeight, gameWidth);
        }

        public void drawBackGround(Canvas c)
        {

            Paint p = new Paint();
            p.setColor(Color.WHITE);
            c.drawRect(0,0,gameWidth,gameHeight, p);

        }

        private void drawDynamiteButton(Canvas c)
        {
            c.drawBitmap(dynamiteButton,null,dynamiteButtonLocation,null);
        }

        private void drawIceBombButton(Canvas c)
        {
            c.drawBitmap(iceBombButton,null,iceBombButtonLocation,null);
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
            Block bottom = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2, gameHeight/2 + spriteDimension/2));
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
                Block bottomLeft = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 - spriteDimension/2 + TOL, gameHeight/2 + spriteDimension/2 + baseGravitySpeed));
                Block bottomRight = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 + spriteDimension/2 - TOL, gameHeight/2 + spriteDimension/2 + baseGravitySpeed));
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
                    mainCharacter.setDirection(GlobalConstants.LEFT);
                    Block leftTop = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 - spriteDimension/2 - speed, gameHeight/2-spriteDimension/2 + TOL));
                    Block leftBottom = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 - spriteDimension/2 - speed, gameHeight/2+spriteDimension/2 - TOL));
                    if (!leftBottom.isSolid() && !leftTop.isSolid())
                    {
                        newCameraX = newCameraX - speed;
                        Block below = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2, gameHeight/2 + blockSize));
                        if (mainCharacter.isInWater() || below.getWaterPercentage() == 100)
                        {
                            blocksOnScreen.moveWaterLeft();
                        }
                    }
                    else
                    {
                        int gap = (camera.getCameraX() - spriteDimension/2)%blockSize;
                        if (gap <= speed)
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
                            Block above = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2, gameHeight/2 - blockSize + spriteDimension/2));
                            Block aboveLeft = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 - blockSize, gameHeight/2 - blockSize + spriteDimension/2));
                            Block below = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2, gameHeight/2 + spriteDimension/2 + blockSize));
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
                    mainCharacter.setDirection(GlobalConstants.RIGHT);
                    Block rightTop = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 + spriteDimension/2 + speed, gameHeight/2 - spriteDimension/2 + TOL));
                    Block rightBottom = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 + spriteDimension/2 + speed, gameHeight/2 + spriteDimension/2 - TOL));
                    if (!rightBottom.isSolid() && !rightTop.isSolid())
                    {
                        newCameraX = newCameraX + speed;
                        Block below = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2, gameHeight/2 + blockSize));
                        if (mainCharacter.isInWater() || below.getWaterPercentage() == 100)
                        {
                            blocksOnScreen.moveWaterRight();
                        }

                    }
                    else {
                        int gap = blockSize - ((camera.getCameraX() + spriteDimension / 2) % blockSize);
                        if (gap <= speed) {
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
                            Block above = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2, gameHeight/2 - blockSize + spriteDimension/2));
                            Block aboveRight = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 + blockSize, gameHeight/2 - blockSize + spriteDimension/2));
                            Block below = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2, gameHeight/2 + spriteDimension/2 + blockSize));
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
                    Block topRight = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 - spriteDimension/2 + TOL, gameHeight/2 - blockSize));
                    Block topLeft = blocksOnScreen.getBlockFromArrayUsingScreenCoordinates(new Coordinates(gameWidth/2 + spriteDimension/2 - TOL, gameHeight/2 - blockSize));
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
                System.out.println("Paused");
            }
        }
    }


}
