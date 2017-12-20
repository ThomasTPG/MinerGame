package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 26/11/2017.
 */

public class BlockSavedData {

    private BlockLocaleData localeData;
    private NonSolidBlocks nonSolidBlocks;
    private BlockStatusData blockStatusData;

    public BlockSavedData(BlockLocaleData localeData)
    {
        this.localeData = localeData;
        nonSolidBlocks = new NonSolidBlocks();
        blockStatusData = new BlockStatusData();
    }

    public int getIndex() {
        return localeData.getIndex();
    }

    public BlockLocaleData getLocaleData() { return localeData; }

    public NonSolidBlocks getNonSolidBlocks() {
        return nonSolidBlocks;
    }

    public BlockStatusData getBlockStatusData() {
        return blockStatusData;
    }
}
