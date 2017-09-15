package com.example.thomas.miner;

import android.content.Context;

import java.io.File;

/**
 * Created by Thomas on 15/09/2017.
 */

public class Memory {

    private File file;
    private Context mContext;

    public Memory(Context context)
    {
        mContext = context;
    }

    protected void workOutFile()
    {
        File path = mContext.getFilesDir();
        file = new File(path, mContext.getResources().getString(R.string.ore_data_file_name));
    }

    public void checkFileExists()
    {
        if (!file.exists())
        {
            //createBlankFile();
        }
    }
}
