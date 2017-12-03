package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 03/12/2017.
 */

public class Block_CostumeGem extends  Block{

    public Block_CostumeGem(Coordinates coordinates, BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(coordinates, blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_CostumeGem(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        setBitmap(blockBitmapManager.getCostumeGemBitmap());
        setSoftness(PickaxeTypes.WOOD_PICKAXE);
        setType(GlobalConstants.COSTUMEGEM);
    }
}