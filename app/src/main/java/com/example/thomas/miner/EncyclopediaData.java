package com.example.thomas.miner;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Thomas on 15/09/2017.
 */

public class EncyclopediaData extends Activity {

    int screenWidth;
    int ore;
    TextView oreName;
    ImageView oreImage;
    TextView oreDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encyclopedia_data);
        ore = getIntent().getIntExtra("Ore",GlobalConstants.SOIL);
        findScreenWidth();
        getViews();
        populateFields();
    }

    private void getViews()
    {
        oreName = (TextView) findViewById(R.id.enc_ore_name);
        oreImage = (ImageView) findViewById(R.id.enc_ore_img);
        oreDetails = (TextView) findViewById(R.id.enc_ore_details);
    }

    private void populateFields()
    {
        switch (ore)
        {
            case(GlobalConstants.SOIL):
                oreName.setText(getResources().getString(R.string.soil_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.soil));
                break;
            case(GlobalConstants.BOULDER):
                oreName.setText(getResources().getString(R.string.hard_rock_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.rock));
                break;
            case(GlobalConstants.HARD_BOULDER):
                oreName.setText(getResources().getString(R.string.hard_rock_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.hardrock));
                break;
            case(GlobalConstants.WATER):
                oreName.setText(getResources().getString(R.string.water_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.water));
                break;
        }
    }

    private void findScreenWidth()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }
}
