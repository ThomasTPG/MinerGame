package com.example.thomas.miner;

import android.content.Context;
import java.io.File;

/**
 * Created by Thomas on 28/01/2017.
 */

public class MinedLocations {

    private Node currentNode;

    public MinedLocations()
    {
    }

    public void addToMinedLocations(int ii, int type, int water)
    {
        if (currentNode != null)
        {
            Node newNode = currentNode.addItem(ii,type, water);
            currentNode = newNode;
        }
        else
        {
            currentNode = new Node(ii,type, water);
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


    protected Node getCurrentNode()
    {
        return currentNode;
    }

}
