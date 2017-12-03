package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 03/12/2017.
 */

public class Block_Crystal extends  Block{

    public Block_Crystal(Coordinates coordinates, BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(coordinates, blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_Crystal(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        setBitmap(blockBitmapManager.getCrystal1());
        setSoftness(PickaxeTypes.IRON_PICKAXE);
        setType(GlobalConstants.CRYSTAL);
    }
}
