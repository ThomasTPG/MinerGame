package com.example.thomas.miner;

/**
 * Created by Thomas on 23/02/2017.
 */

public final class GlobalConstants {
    //Caverns are empty rooms
    public static final int CAVERN = 1;
    //Boulders can be destroyed by dynamite only
    public static final int BOULDER = 2;
    //Hard boulders cannot be destroyed by anything
    public static final int HARD_BOULDER = 3;
    //When a bomb detonates the surrounding blocks are fireball types
    public static final int FIREBALL = 4;
    static final int WATER = 5;
    public static final int SOIL = 6;
    public static final int COPPER = 7;
    public static final int IRON = 8;
    public static final int ALIENITE = 9;
    public static final int MARBLE = 10;
    //Spring blocks create water
    static final int SPRING = 11;
    //Life blocks require water to grow
    static final int LIFE = 12;
    static final int ICE = 13;
    static final int GOLD = 14;
    static final int CRYSTAL = 15;
    //Number of types of block
    public static final int NUMBEROFTYPES = 15;

    //Items in shop
    public static final int NUMBEROFITEMS = 5;
    public static final int PICKAXEUPGRADE = 0;
    public static final int HOUSEUPGRADE = 1;
    public static final int GARDENUPGRADE = 2;
    public static final int CLAMBERUPGRADE = 3;
    public static final int AIRTANKUPGRADE = 4;

}
