package com.alienpg.release.aflatminer;

/**
 * Created by User on 21/12/2017.
 */

public class EnemyCreator {

    BlockManager blockManager;

    public EnemyCreator(BlockManager blockManager)
    {
        this.blockManager = blockManager;
    }

    public Enemy createEnemy(String type, int x, int y)
    {
        return (new Fish(x, y, 10, 10, blockManager));
    }

}
