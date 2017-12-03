package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 03/12/2017.
 */

public class Block_Gold extends  Block{

    public Block_Gold(Coordinates coordinates, BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(coordinates, blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_Gold(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        setBitmap(blockBitmapManager.getGoldBitmap());
        setSoftness(PickaxeTypes.IRON_PICKAXE);
        setType(GlobalConstants.GOLD);
    }
}
