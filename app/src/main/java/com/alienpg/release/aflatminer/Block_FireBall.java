package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 26/11/2017.
 */

public class Block_FireBall extends  Block{

    public Block_FireBall(BlockSavedData blockSavedData, MinedLocations minedLocations, BitmapFlyWeight bitmapFlyWeight)
    {
        super(blockSavedData, minedLocations, bitmapFlyWeight);
        setData();
    }

    public Block_FireBall(Block oldBlock)
    {
        super(oldBlock);
        setData();
    }

    private void setData()
    {
        setBitmap(blockBitmapManager.getFireBallBitmap());
        setSoftness(PickaxeTypes.CANNOT_BE_MINED);
        setType(GlobalConstants.FIREBALL);
    }

    @Override
    protected boolean isSolid() {
        return false;
    }
}
