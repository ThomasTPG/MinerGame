package com.example.thomas.miner;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
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
    123-123
    123-123
    Where the first number is the total number of that ore mined
    The second number is the amount mined on the previous run
    The rows are ordered by those in GlobalConstants, starting with soil
    */

    int[] oreArray = new int[GlobalConstants.NUMBEROFTYPES];
    int[] previouslyMinedOre = new int[GlobalConstants.NUMBEROFTYPES];
    private File oreFile;

    public OreMemory(Context context)
    {
        File path = context.getFilesDir();
        oreFile = new File(path, context.getResources().getString(R.string.ore_data_file_name));
    }

    public void checkFileExists()
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
                for (int ii = 0; ii < GlobalConstants.NUMBEROFTYPES; ii++)
                {
                    bufferedWriter.write("0-0");
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
                for (int ii = 0; ii < GlobalConstants.NUMBEROFTYPES; ii++)
                {
                    if ((line = bufferedReader.readLine()) != null)
                    {
                        oreArray[ii] = Integer.parseInt(line.split("-")[0]);
                        previouslyMinedOre[ii] = Integer.parseInt(line.split("-")[1]);
                    }
                    else
                    {
                        oreArray[ii] = 0;
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
                for (int ii = 0; ii < GlobalConstants.NUMBEROFTYPES; ii++)
                {
                    int lastMined = oreCounter.getCount(GlobalConstants.SOIL + ii);
                    int totalMined = oreArray[ii] + lastMined;
                    System.out.println("MINED" + lastMined + " " + totalMined);
                    bufferedWriter.write(Integer.toString(totalMined) + "-" + Integer.toString(lastMined));
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
        return oreArray;
    }

    public int[] getPreviouslyMinedOre()
    {
        readFile();
        return previouslyMinedOre;
    }

}
