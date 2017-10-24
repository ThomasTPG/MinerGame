package com.alienpg.release.aflatminer;

/**
 * Created by Thomas on 28/01/2017.
 */

public class MinedLocations {

    private Node currentNode;

    public MinedLocations()
    {

    }

    public synchronized void addToMinedLocations(int ii, BlockStatusData blockStatusData, NonSolidBlocks blockData)
    {
        if (currentNode != null)
        {
            Node newNode = currentNode.addItem(ii,blockStatusData, blockData);
            currentNode = newNode;
        }
        else
        {
            currentNode = new Node(ii,blockStatusData, blockData);
        }
    }

    public boolean isItemContained(int ii)
    {
        if (currentNode != null)
        {
            Node foundNode = currentNode.findIndex(ii);
            if (foundNode != null)
            {
                currentNode = foundNode;
                return true;
            }
        }
        return false;
    }

    public int getThisType()
    {
        return currentNode.getType();
    }


    protected int getWaterPercentage(int ii)
    {
        if (currentNode != null)
        {
            Node foundNode = currentNode.findIndex(ii);
            if (foundNode != null)
            {
                currentNode = foundNode;
                return currentNode.getWaterPercentage();
            }
        }
        return 0;
    }

    protected int getGasPercentage(int ii)
    {
        if (currentNode != null)
        {
            Node foundNode = currentNode.findIndex(ii);
            if (foundNode != null)
            {
                currentNode = foundNode;
                return currentNode.getGasPercentage();
            }
        }
        return 0;
    }

    protected int getMinedPercentage(int index)
    {
        if (currentNode != null)
        {
            Node foundNode = currentNode.findIndex(index);
            if (foundNode != null)
            {
                currentNode = foundNode;
                return currentNode.getMinedPercentage();
            }
        }
        return 0;
    }


    protected Node getCurrentNode()
    {
        return currentNode;
    }

}
