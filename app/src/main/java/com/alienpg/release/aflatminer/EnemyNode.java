package com.alienpg.release.aflatminer;

/**
 * Created by User on 21/12/2017.
 */

public class EnemyNode {

    private Enemy myEnemy;
    private EnemyNode nextNode = null;
    private EnemyNode previousNode = null;

    public EnemyNode(Enemy enemy)
    {
        myEnemy = enemy;
    }

    public Enemy getEnemy()
    {
        return myEnemy;
    }

    public void setNextNode(EnemyNode next)
    {
        nextNode = next;
    }

    public void setPreviousNode(EnemyNode prior)
    {
        previousNode = prior;
    }

    public EnemyNode nextNode()
    {
        return nextNode;
    }

    public EnemyNode previousNode()
    {
        return previousNode;
    }

    public void sort()
    {
        if (previousNode != null)
        {
            if (previousNode.getEnemy().getyCoord() > myEnemy.getyCoord())
            {
                if (previousNode.previousNode() != null)
                {
                    previousNode.previousNode().setNextNode(this);
                    previousNode.setNextNode(nextNode);
                    if (nextNode != null)
                    {
                        nextNode.setPreviousNode(previousNode);
                    }
                    setPreviousNode(previousNode.previousNode());
                    sort();
                }
                else
                {
                    previousNode.setNextNode(nextNode);
                    setNextNode(previousNode);
                    setPreviousNode(null);
                }
            }
        }
        if (nextNode != null)
        {
            if (nextNode.getEnemy().getyCoord() < myEnemy.getyCoord())
            {
                if (nextNode.nextNode() != null)
                {
                    nextNode.nextNode().setPreviousNode(this);
                    nextNode.setPreviousNode(previousNode);
                    if (previousNode != null)
                    {
                        previousNode.setNextNode(nextNode);
                    }
                    setNextNode(nextNode.nextNode());
                    sort();
                }
                else
                {
                    nextNode.setPreviousNode(previousNode);
                    setPreviousNode(nextNode);
                    setNextNode(null);
                }
            }
        }
    }

    public String getData()
    {
        return (myEnemy.getType() + Integer.toString(myEnemy.getxCoord())+ Integer.toString(myEnemy.getyCoord()));
    }
}
