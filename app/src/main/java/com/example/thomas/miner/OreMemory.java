package com.example.thomas.miner;

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
 * Created by Thomas on 24/02/2017.
 */

public class OreMemory {

    /*
    We save the ore file in the following format:
    123-123-123
    123-123-123
    Where the first number is the total number of that ore mined
    The second number is the amount currently owned
    The third number is the amount mined on the previous run
    The rows are ordered by those in GlobalConstants, starting with soil
    */

    int[] totalOreArray = new int[GlobalConstants.MEMORY_LENGTH_ARRAY_ORE];
    int[] currentOreArray = new int[GlobalConstants.MEMORY_LENGTH_ARRAY_ORE];
    int[] previouslyMinedOre = new int[GlobalConstants.MEMORY_LENGTH_ARRAY_ORE];
    private File oreFile;

    public OreMemory(Context context)
    {
        File path = context.getFilesDir();
        oreFile = new File(path, context.getResources().getString(R.string.ore_data_file_name));
        checkFileExists();
        readFile();
    }

    private void checkFileExists()
    {
        if (!oreFile.exists())
        {
            createBlankFile();
        }
    }

    private void createBlankFile()
    {
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(oreFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            try
            {
                for (int ii = 0; ii < GlobalConstants.MEMORY_LENGTH_ARRAY_ORE; ii++)
                {
                    bufferedWriter.write("0-0-0");
                    bufferedWriter.newLine();
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

    private void readFile()
    {
        try
        {
            FileInputStream fstream = new FileInputStream(oreFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            try
            {
                String line = "";
                for (int ii = 0; ii < GlobalConstants.MEMORY_LENGTH_ARRAY_ORE; ii++)
                {
                    if ((line = bufferedReader.readLine()) != null)
                    {
                        totalOreArray[ii] = Integer.parseInt(line.split("-")[0]);
                        currentOreArray[ii] = Integer.parseInt(line.split("-")[1]);
                        previouslyMinedOre[ii] = Integer.parseInt(line.split("-")[2]);
                    }
                    else
                    {
                        totalOreArray[ii] = 0;
                        currentOreArray[ii] = 0;
                        previouslyMinedOre[ii] = 0;
                    }
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
    }

    public void writeFile(OreCounter oreCounter)
    {
        readFile();
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(oreFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            try
            {
                for (int ii = 0; ii < GlobalConstants.MEMORY_LENGTH_ARRAY_ORE; ii++)
                {
                    int lastMined = oreCounter.getCount(ii);
                    totalOreArray[ii] += lastMined;
                    int totalMined = totalOreArray[ii];
                    currentOreArray[ii] += lastMined;
                    int currentMined = currentOreArray[ii];
                    previouslyMinedOre[ii] = lastMined;
                    String toWrite = Integer.toString(totalMined) + "-" + Integer.toString(currentMined) + "-" + Integer.toString(lastMined);
                    bufferedWriter.write(toWrite);
                    bufferedWriter.newLine();
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

    public int[] getTotalOre()
    {
        readFile();
        return totalOreArray;
    }

    public int getCurrentOre(int ore)
    {
        return currentOreArray[ore];
    }

    public int getTotalOre(int ore)
    {
        return totalOreArray[ore];
    }

    public int[] getPreviouslyMinedOre()
    {
        readFile();
        return previouslyMinedOre;
    }

}
