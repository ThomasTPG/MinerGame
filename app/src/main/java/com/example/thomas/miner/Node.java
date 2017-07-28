package com.example.thomas.miner;

/**
 * Created by Thomas on 27/01/2017.
 */

public class Node {

    private Node previousNode = null;
    private Node nextNode = null;
    private int index;
    private int type;
    private NonSolidBlocks blockLiquidData;
    private int amountOfGas;
    private int amountOfLava;

    public Node(int ii, int type,  NonSolidBlocks newBlockLiquidData)
    {
        index = ii;
        this.type = type;
        blockLiquidData = newBlockLiquidData;
    }

    public Node getNextNode()
    {
        return nextNode;
    }

    private void setNextNode(Node n)
    {
        nextNode = n;
    }

    private void setPreviousNode(Node n)
    {
        previousNode = n;
    }

    public int getIndex()
    {
        return index;
    }

    public Node addItem(int ii, int type, NonSolidBlocks newBlockLiquidData)
    {
        if (ii < index)
        {
            if (previousNode != null)
            {
                if (ii > previousNode.getIndex())
                {
                    Node newNode = new Node(ii, type, newBlockLiquidData);
                    newNode.setNextNode(this);
                    newNode.setPreviousNode(previousNode);
                    previousNode.setNextNode(newNode);
                    previousNode = newNode;
                    return newNode;
                }
                else
                {
                    return previousNode.addItem(ii,type,newBlockLiquidData);
                }
            }
            else
            {
                Node newNode = new Node(ii,type,newBlockLiquidData);
                previousNode = newNode;
                previousNode.setNextNode(this);
                return previousNode;
            }
        }
        else if (ii > index)
        {
            if (nextNode != null)
            {
                if (ii < nextNode.getIndex())
                {
                    Node newNode = new Node(ii,type,newBlockLiquidData);
                    newNode.setPreviousNode(this);
                    newNode.setNextNode(nextNode);
                    nextNode.setPreviousNode(newNode);
                    nextNode = newNode;
                    return newNode;
                }
                else
                {
                    return nextNode.addItem(ii,type,newBlockLiquidData);
                }
            }
            else
            {
                Node newNode = new Node(ii,type,newBlockLiquidData);
                nextNode = newNode;
                nextNode.setPreviousNode(this);
                return nextNode;
            }
        }
        else
        {
            //item already in list
            this.type = type;
            this.blockLiquidData = newBlockLiquidData;
            return this;
        }
    }

    public int getWaterPercentage()
    {
        return blockLiquidData.getWaterPercentage();
    }



    private Node searchPrevious(int ii)
    {
        if (index < ii)
        {
            //gone too far - it's not in the list
            return null;
        }
        else if (index == ii)
        {
            return this;
        }
        else
        {
            if (previousNode != null)
            {
                return previousNode.searchPrevious(ii);
            }
            else
            {
                return null;
            }
        }
    }

    private Node searchNext(int ii)
    {
        if (index > ii)
        {
            //gone too far - it's not in the list
            return null;
        }
        else if (index == ii)
        {
            return this;
        }
        else
        {
            if (nextNode != null)
            {
                return nextNode.searchNext(ii);
            }
            else
            {
                return null;
            }
        }
    }

    /*
    Returns
     */
    public Node findIndex(int ii)
    {
        if (ii < index)
        {
            return searchPrevious(ii);
        }
        else if (ii > index)
        {
            return searchNext(ii);
        }
        else
        {
            return this;
        }
    }

    public Node findFirstNode()
    {
        if (previousNode == null)
        {
            return this;
        }
        else
        {
            return previousNode.findFirstNode();
        }
    }

    protected String getData()
    {
        String output = Integer.toString(index) + "-" + Integer.toString(type)+ "-" + blockLiquidData.getMemoryString();
        return output;
    }

    int getType()
    {
        return type;
    }



}
