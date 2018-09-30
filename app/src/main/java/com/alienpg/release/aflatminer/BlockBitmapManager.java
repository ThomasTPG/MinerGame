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

    public Bitmap getBackground1() {
        return background1;
    }

    public Bitmap getCopperBitmap()
    {
        return copperBitmap;
    }

    public Bitmap getSoil1Bitmap() {
        return soil1Bitmap;
    }

    public Bitmap getSoil2Bitmap() {
        return soil2Bitmap;
    }

    public Bitmap getBoulderBitmap() {
        return boulderBitmap;
    }

    public Bitmap getHardBoulderBitmap() {
        return hardBoulderBitmap;
    }

    public Bitmap getFireBallBitmap() {
        return fireBallBitmap;
    }

    public Bitmap getIronBitmap() {
        return ironBitmap;
    }

    public Bitmap getExplodiumBitmap() {
        return explodiumBitmap;
    }

    public Bitmap getMarbleBitmap() {
        return marbleBitmap;
    }

    public Bitmap getSpringBitmap() {
        return springBitmap;
    }

    public Bitmap getWaterBitmap() {
        return waterBitmap;
    }

    public Bitmap getGasBitmap() {
        return gasBitmap;
    }

    public Bitmap getGasWaterBitmap() {
        return gasWaterBitmap;
    }

    public Bitmap getLife1() {
        return life1;
    }

    public Bitmap getLife2() {
        return life2;
    }

    public Bitmap getLife3() {
        return life3;
    }

    public Bitmap getLife4() {
        return life4;
    }

    public Bitmap getLife5() {
        return life5;
    }

    public Bitmap getLife6() {
        return life6;
    }

    public Bitmap getIceBitmap() {
        return iceBitmap;
    }

    public Bitmap getGoldBitmap() {
        return goldBitmap;
    }

    public Bitmap getCrystalBase() {
        return crystalBase;
    }

    public Bitmap getGasRockBitmap() {
        return gasRockBitmap;
    }

    public Bitmap getCostumeGemBitmap() {
        return costumeGemBitmap;
    }

    public Bitmap getTinBitmap() {
        return tinBitmap;
    }

    public Bitmap getCrystal1() {
        return crystal1;
    }

    public Bitmap getCrystal2() {
        return crystal2;
    }

    public Bitmap getCrystal3() {
        return crystal3;
    }

    public Bitmap getCrystal4() {
        return crystal4;
    }

    public Bitmap getCrystal5() {
        return crystal5;
    }

    public Bitmap getDynamite() {
        return dynamite;
    }

    public Bitmap getIceBomb() {
        return iceBomb;
    }
}
