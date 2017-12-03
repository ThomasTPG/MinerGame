package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 26/11/2017.
 */

public class BlockSavedData {

    private int index;
    private NonSolidBlocks nonSolidBlocks;
    private BlockStatusData blockStatusData;

    public BlockSavedData(int index)
    {
        this.index = index;
        nonSolidBlocks = new NonSolidBlocks();
        blockStatusData = new BlockStatusData();
    }

    public int getIndex() {
        return index;
    }

    public NonSolidBlocks getNonSolidBlocks() {
        return nonSolidBlocks;
    }

    public BlockStatusData getBlockStatusData() {
        return blockStatusData;
    }
}
