package com.alienpg.release.aflatminer;

/**
 * Created by User on 21/12/2017.
 */

public class EnemyList {

    public final static String FISH_TYPE = "Fish";
    private EnemyCreator enemyCreator;
    private EnemyNode headNode = null;

    public EnemyList(EnemyCreator enemyCreator)
    {
        this.enemyCreator = enemyCreator;
    }

    public EnemyNode getHeadNode()
    {
        return headNode;
    }

    public void addEnemy(String type, int x, int y)
    {
        EnemyNode newNode = new EnemyNode(enemyCreator.createEnemy(type, x, y));

        if (headNode == null)
        {
            headNode = newNode;
            return;
        }

        EnemyNode testNode = headNode;
        while (testNode != null)
        {
            if (testNode.nextNode() == null)
            {
                testNode.setNextNode(newNode);
                newNode.setPreviousNode(testNode);
                return;
            }
            testNode = testNode.nextNode();
        }
    }

    public EnemyNode getFirstNodeWithHeightAbove(int height)
    {
        EnemyNode testNode = headNode;
        while (testNode != null)
        {
            if (testNode.getEnemy().getyCoord() >= height)
            {
                return testNode;
            }
        }
        return null;
    }
}
