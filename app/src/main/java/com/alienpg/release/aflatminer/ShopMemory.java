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
 * Created by Thomas on 21/06/2017.
 */

public class ShopMemory {

    private File shopFile;
    private int numberOfItems;
    private int[] shopArray;

    public ShopMemory(Context context)
    {
        File path = context.getFilesDir();
        shopFile = new File(path, context.getResources().getString(R.string.shop_data_file_name));
        numberOfItems = context.getResources().getInteger(R.integer.number_of_items_in_shop);
        shopArray = new int[numberOfItems];
        checkFileExists();
    }

    private void checkFileExists()
    {
        if (!shopFile.exists())
        {
            createBlankFile();
        }
    }

    private void createBlankFile()
    {
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(shopFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            try
            {
                for (int ii = 0; ii < numberOfItems; ii++)
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
            FileInputStream fstream = new FileInputStream(shopFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            try
            {
                String line = "";
                for (int ii = 0; ii < numberOfItems; ii++)
                {
                    if ((line = bufferedReader.readLine()) != null)
                    {
                        shopArray[ii] = Integer.parseInt(line);

                    }
                    else
                    {
                        shopArray[ii] = 0;
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

    public void writeFile(int item)
    {
        readFile();
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(shopFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            try
            {
                for (int ii = 0; ii < numberOfItems; ii++)
                {
                    if (item == ii)
                    {
                        shopArray[ii] = shopArray[ii] + 1;
                    }
                    bufferedWriter.write(Integer.toString(shopArray[ii]));
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

    public int getItem(int item)
    {
        readFile();
        return shopArray[item];
    }
}
