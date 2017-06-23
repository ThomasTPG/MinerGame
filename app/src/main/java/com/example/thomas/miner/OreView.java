package com.example.thomas.miner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Thomas on 11/03/2017.
 */

public class OreView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oreview_layout);

        TextView ores = (TextView) findViewById(R.id.ores);
        OreMemory oreMemory = new OreMemory(this);
        int[] oreArray = oreMemory.getTotalOre();
        String oreString = "";
        for (int ii = 0; ii < GlobalConstants.NUMBEROFTYPES; ii++)
        {
            oreString = oreString + " " + oreArray[ii];
        }
        System.out.println("OREVIEW" + oreString);


        ores.setText(oreString);

    }
}
