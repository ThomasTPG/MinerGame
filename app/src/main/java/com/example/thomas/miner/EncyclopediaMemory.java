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
 * Created by Thomas on 15/09/2017.
 */

public class EncyclopediaMemory {

    private File oreFile;
    private int[] oreArray;

    public EncyclopediaMemory(Context context)
    {
        File path = context.getFilesDir();
        oreFile = new File(path, context.getResources().getString(R.string.encyclopedia_data_file_name));
        oreArray = new int[GlobalConstants.MEMORY_LENGTH_ARRAY_ORE];
        checkFileExists();
        readFile();
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
                for (int ii = 0; ii < GlobalConstants.MEMORY_LENGTH_ARRAY_ORE; ii++)
                {
                    bufferedWriter.write("0");
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
                        oreArray[ii] = Integer.parseInt(line);
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

    public void writeFile(int oreNumber)
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
                    int entry = oreArray[ii];
                    if (ii == oreNumber)
                    {
                        entry = 1;
                    }
                    bufferedWriter.write(Integer.toString(entry));
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

    public boolean isOreUnlocked(int ore)
    {
        if (oreArray[ore] == 1)
        {
            return true;
        }
        return false;
    }

}
