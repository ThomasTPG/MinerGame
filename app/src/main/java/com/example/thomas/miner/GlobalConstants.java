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

    public static final int SOIL = 6;
    public static final int COPPER = 7;
    public static final int IRON = 8;
    public static final int EXPLODIUM = 9;
    public static final int MARBLE = 10;
    //Spring blocks create water
    static final int SPRING = 11;
    //Life blocks require water to grow
    static final int LIFE = 12;
    static final int ICE = 13;
    static final int GOLD = 14;
    static final int CRYSTAL = 15;
    static final int GASROCK = 16;
    static final int COSTUMEGEM = 17;
    static final int TIN = 18;
    static final int WATER = 19;
    static final int GAS = 20;
    //Number of types of block
    public static final int NUMBEROFTYPES = 19;
    public static final int ENCYCLOPEDIA_NUMBERS = 17;
    public static final int MEMORY_LENGTH_ARRAY_ORE = 40;

    //Items in shop
    public static final int NUMBEROFITEMS = 5;
    public static final int PICKAXEUPGRADE = 0;
    public static final int AIRTANKUPGRADE = 1;
    public static final int HOUSEUPGRADE = 2;
    public static final int GARDENUPGRADE = 3;
    public static final int DYNAMITEUPGRADE = 4;

    //Percentage to dislodge blocks
    public static final int UNMINED = 0;
    public static final int SLIGHTLY_MINED = 1;
    public static final int ALMOST_MINED = 2;
    public static final int SLIGHT_PERCENTAGE = 40;
    public static final int ALMOST_PERCENTAGE = 70;
    public static final int CRYSTALFREEZEAMOUNT = 6;

    //Buttons in menus
    public static final int START = 0;
    public static final int SHOP = 1;
    public static final int VIEWORES = 2;
    public static final int ENCYCLOPEDIA = 3;
    public static final int ENCYCLOPEDIA_PAGES = 4;
    public static final int SHOP_PAGES = 5;
    public static final int MAIN_MENU = 6;
    public static final int GAME_OVER = 7;


    public static final int DAY = 0;
    public static final int NIGHT = 1;
    public static final int SUNSET = 2;

    public static final int ESCAPE = 0;
    public static final int EXPLOSION = 1;
    public static final int SUFFOCATED = 2;
    public static final int FROZEN = 3;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

}
