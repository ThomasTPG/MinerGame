package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 03/12/2017.
 */

public class Block_Crystal extends  Block{

    int crystalCount = 0;

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

    private void setCrystalBitmap()
    {
        int surroundingIce = getSurroundingIce();
        if (surroundingIce == 0)
        {
            setBitmap(blockBitmapManager.getCrystal1());

        }
        else if (surroundingIce < 3)
        {
            setBitmap(blockBitmapManager.getCrystal2());

        }
        else if (surroundingIce < GlobalConstants.CRYSTALFREEZEAMOUNT)
        {
            setBitmap(blockBitmapManager.getCrystal3());

        }
        else
        {
            if (crystalCount < 10)
            {
                setBitmap(blockBitmapManager.getCrystal4());

            }
            else
            {
                setBitmap(blockBitmapManager.getCrystal5());

            }
            crystalCount ++;
            if (crystalCount == 20)
            {
                crystalCount = 0;
            }
        }
    }

    private void setData()
    {
        setCrystalBitmap();
        setSoftness(PickaxeTypes.IRON_PICKAXE);
        setType(GlobalConstants.CRYSTAL);
    }
}
