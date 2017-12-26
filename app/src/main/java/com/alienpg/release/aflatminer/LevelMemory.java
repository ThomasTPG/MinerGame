package com.alienpg.release.aflatminer;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Thomas on 22/02/2017.
 */

public class LevelMemory {

    private File levelFile;
    private MinedLocations mMinedLocations;
    private int mSeed;
    private Camera camera;
    private OreCounter oreCounter;
    private Sprite mSprite;
    private EnemyList mEnemyList;
    private static String ENEMY_HEADER = "ENEMY_DATA";

    public LevelMemory(Context context, MinedLocations minedLocations, int seed, Camera cam, OreCounter oreCounter, Sprite sprite, EnemyList enemyList)
    {
        mMinedLocations = minedLocations;
        mSeed = seed;
        camera = cam;
        File path = context.getFilesDir();
        levelFile = new File(path, context.getResources().getString(R.string.level_data_file_name));
        this.oreCounter = oreCounter;
        mSprite = sprite;
        mEnemyList = enemyList;
    }

    public boolean canLoadLevel()
    {
        return levelFile.exists();
    }

    public int loadLevelData()
    {
        try
        {
            FileInputStream fstream = new FileInputStream(levelFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            try
            {
                String line = "";
                //Read the seed
                mSeed = Integer.parseInt(bufferedReader.readLine());
                //Read the camera position
                camera.setCameraY(Integer.parseInt(bufferedReader.readLine()));
                camera.setCameraX(Integer.parseInt(bufferedReader.readLine()));
                //Read the oxygen
                mSprite.setOxygen(Integer.parseInt(bufferedReader.readLine()));
                //Read the number of ore types
                for (int ii = 0; ii < GlobalConstants.MEMORY_LENGTH_ARRAY_ORE; ii++)
                {
                    String currentline = bufferedReader.readLine();
                    String[] parts = currentline.split("-");
                    oreCounter.setOre(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                }
                //Read the mined blocks
                while ((line = bufferedReader.readLine()) != ENEMY_HEADER)
                {
                    String liquidDataString = line.split("-")[2];
                    NonSolidBlocks blockLiquidData = new NonSolidBlocks();
                    blockLiquidData.setFromMemory(liquidDataString);
                    String statusDataString = line.split("-")[1];
                    BlockStatusData blockStatusData = new BlockStatusData();
                    blockStatusData.setFromMemory(statusDataString);
                    mMinedLocations.addToMinedLocations(Integer.parseInt(line.split("-")[0]),blockStatusData,blockLiquidData);
                }
                // Read the enemy data
                while ((line = bufferedReader.readLine()) != null)
                {
                    String typeString = line.split("-")[0];
                    int xCoOrd = Integer.parseInt(line.split("-")[1]);
                    int yCoOrd = Integer.parseInt(line.split("-")[2]);
                    mEnemyList.addEnemy(typeString, xCoOrd, yCoOrd);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        levelFile.delete();
        return mSeed;
    }

    public void saveLocations()
    {
        //Save the seed at the top of the file, followed by a new line for each node
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(levelFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            Node nodeToWrite;
            if (mMinedLocations.getCurrentNode() != null)
            {
                nodeToWrite = mMinedLocations.getCurrentNode().findFirstNode();
            }
            else
            {
                nodeToWrite = null;
            }

            try
            {
                //Write the seed
                bufferedWriter.write(Integer.toString(mSeed));
                bufferedWriter.newLine();
                //Write the camera position
                bufferedWriter.write(Integer.toString(camera.getCameraY()));
                bufferedWriter.newLine();
                bufferedWriter.write(Integer.toString(camera.getCameraX()));
                bufferedWriter.newLine();
                //Write the oxygen
                bufferedWriter.write(Integer.toString(mSprite.getOxygenAmount()));
                bufferedWriter.newLine();
                //Write the ores mined
                String[] oreOutput = oreCounter.getOre();
                for (int ii = 0; ii < GlobalConstants.MEMORY_LENGTH_ARRAY_ORE; ii++)
                {
                    bufferedWriter.write(oreOutput[ii]);
                    bufferedWriter.newLine();
                }

                //Write block data
                while (nodeToWrite != null)
                {
                    bufferedWriter.write(nodeToWrite.getData());
                    bufferedWriter.newLine();
                    nodeToWrite = nodeToWrite.getNextNode();
                }
                //Write enemy data
                bufferedWriter.write(ENEMY_HEADER);

                EnemyNode currentNode = mEnemyList.getHeadNode();
                while (currentNode != null)
                {
                    bufferedWriter.write(currentNode.getData());
                    bufferedWriter.newLine();
                    currentNode = currentNode.nextNode();
                }
                bufferedWriter.close();
                fileOutputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void onEndGame()
    {
        levelFile.delete();
    }

}
