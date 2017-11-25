package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Thomas on 25/11/2017.
 */

public class BlockBitmapManager {

    private Bitmap soil1Bitmap;
    private Bitmap soil2Bitmap;
    private Bitmap boulderBitmap;
    private Bitmap hardBoulderBitmap;
    private Bitmap fireBallBitmap;
    private Bitmap copperBitmap;
    private Bitmap ironBitmap;
    private Bitmap explodiumBitmap;
    private Bitmap marbleBitmap;
    private Bitmap springBitmap;
    private Bitmap waterBitmap;
    private Bitmap gasBitmap;
    private Bitmap gasWaterBitmap;
    private Bitmap life1;
    private Bitmap life2;
    private Bitmap life3;
    private Bitmap life4;
    private Bitmap life5;
    private Bitmap life6;
    private Bitmap iceBitmap;
    private Bitmap goldBitmap;
    private Bitmap crystalBase;
    private Bitmap gasRockBitmap;
    private Bitmap costumeGemBitmap;
    private Bitmap tinBitmap;
    private Bitmap crystal1;
    private Bitmap crystal2;
    private Bitmap crystal3;
    private Bitmap crystal4;
    private Bitmap crystal5;
    private Bitmap dynamite;
    private Bitmap iceBomb;
    private Bitmap background1;

    private Context context;

    //An integer that will increase up to CRYSTAL_SWITCH. At this point, we will change the crystal bitmap
    //creating a sparkling effect.
    private int crystalCount = 0 ;
    private int CRYSTAL_SWITCH = 20;

    public BlockBitmapManager(Context c)
    {
        context = c;
        loadBitmaps();
    }

    private void loadBitmaps()
    {
        waterBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.water_test);
        gasBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gas_background);
        gasWaterBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gaswater);
        soil1Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil);
        soil2Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.soil2);
        boulderBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rock);
        hardBoulderBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hardrock);
        fireBallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fireball);
        copperBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.copper);
        ironBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.iron);
        explodiumBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.explodium);
        marbleBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marble);
        springBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.spring);
        tinBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tin);
        life1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.life_1);
        life2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.life_2);
        life3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.life_3);
        life4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.life_4);
        life5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.life_5);
        life6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.life_6);
        iceBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ice_test);
        goldBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gold);
        crystalBase = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystalbase);
        gasRockBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gasrock);
        costumeGemBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.costumegem);
        crystal1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystal1);
        crystal2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystal2);
        crystal3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystal3);
        crystal4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystal4);
        crystal5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.crystal5);
        dynamite = BitmapFactory.decodeResource(context.getResources(), R.drawable.dynamitesquare);
        iceBomb = BitmapFactory.decodeResource(context.getResources(), R.drawable.icedynamitesquare);
        background1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_cave_1);
    }

    private Bitmap workOutBitmap(Block currentBlock)
    {
        int type = currentBlock.getType();
        Bitmap blockBitmap ;
        switch (type)
        {
            case(GlobalConstants.CAVERN):
                blockBitmap = null;
                break;
            case(GlobalConstants.SOIL):
                if (currentBlock.getIndex() % 2 == 0)
                {
                    blockBitmap = soil1Bitmap;
                }
                else
                {
                    blockBitmap = soil2Bitmap;
                }
                break;
            case (GlobalConstants.BOULDER):
                blockBitmap = boulderBitmap;
                break;
            case (GlobalConstants.HARD_BOULDER):
                blockBitmap = hardBoulderBitmap;
                break;
            case (GlobalConstants.FIREBALL):
                blockBitmap = fireBallBitmap;
                break;
            case (GlobalConstants.COPPER):
                blockBitmap = copperBitmap;
                break;
            case (GlobalConstants.IRON):
                blockBitmap = ironBitmap;
                break;
            case (GlobalConstants.EXPLODIUM):
                blockBitmap = explodiumBitmap;
                break;
            case (GlobalConstants.MARBLE):
                blockBitmap = marbleBitmap;
                break;
            case (GlobalConstants.SPRING):
                blockBitmap = springBitmap;
                break;
            case (GlobalConstants.LIFE):
                if (currentBlock.getWaterPercentage() >= 100)
                {
                    blockBitmap = life6;
                }
                else if (currentBlock.getWaterPercentage() > 80)
                {
                    blockBitmap = life5;
                }
                else if (currentBlock.getWaterPercentage() > 60)
                {
                    blockBitmap = life4;
                }
                else if (currentBlock.getWaterPercentage() > 40)
                {
                    blockBitmap = life3;
                }
                else
                {
                    blockBitmap = life2;
                }
                break;
            case (GlobalConstants.ICE):
                blockBitmap = iceBitmap;
                break;
            case (GlobalConstants.GOLD):
                blockBitmap = goldBitmap;
                break;
            case (GlobalConstants.CRYSTAL):
                int surroundingIce = currentBlock.getSurroundingIce();
                if (surroundingIce == 0)
                {
                    blockBitmap = crystal1;
                }
                else if (surroundingIce < 3)
                {
                    blockBitmap = crystal2;
                }
                else if (surroundingIce < GlobalConstants.CRYSTALFREEZEAMOUNT)
                {
                    blockBitmap = crystal3;
                }
                else
                {
                    if (crystalCount < 10)
                    {
                        blockBitmap = crystal4;
                    }
                    else
                    {
                        blockBitmap = crystal5;
                    }
                    crystalCount ++;
                    if (crystalCount == CRYSTAL_SWITCH)
                    {
                        crystalCount = 0;
                    }
                }
                break;
            case (GlobalConstants.GASROCK):
                blockBitmap = gasRockBitmap;
                break;
            case (GlobalConstants.COSTUMEGEM):
                blockBitmap = costumeGemBitmap;
                break;
            case (GlobalConstants.TIN):
                blockBitmap = tinBitmap;
                break;
            default:
                blockBitmap = null;
                break;
        }
        return blockBitmap;
    }

    public Bitmap getBackground1() {
        return background1;
    }

    public Bitmap getCopperBitmap()
    {
        return copperBitmap;
    }
}
