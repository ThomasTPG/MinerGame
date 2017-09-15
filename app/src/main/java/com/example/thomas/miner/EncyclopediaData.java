package com.example.thomas.miner;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
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
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.soil_details)));
                break;
            case(GlobalConstants.BOULDER):
                oreName.setText(getResources().getString(R.string.hard_rock_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.rock));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.hard_rock_details)));
                break;
            case(GlobalConstants.HARD_BOULDER):
                oreName.setText(getResources().getString(R.string.hard_rock_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.hardrock));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.pernament_rock_details)));
                break;
            case(GlobalConstants.WATER):
                oreName.setText(getResources().getString(R.string.water_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.water));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.water_details)));
                break;
            case(GlobalConstants.GAS):
                oreName.setText(getResources().getString(R.string.gas_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.gas));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.gas_details)));
                break;
            case(GlobalConstants.COPPER):
                oreName.setText(getResources().getString(R.string.copper_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.copper));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.copper_details)));
                break;
            case(GlobalConstants.IRON):
                oreName.setText(getResources().getString(R.string.iron_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.iron));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.iron_details)));
                break;
            case(GlobalConstants.EXPLODIUM):
                oreName.setText(getResources().getString(R.string.explodium_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.explodium));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.explodium_details)));
                break;
            case(GlobalConstants.MARBLE):
                oreName.setText(getResources().getString(R.string.marble_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.marble));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.marble_details)));
                break;
            case(GlobalConstants.SPRING):
                oreName.setText(getResources().getString(R.string.spring_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.spring));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.spring_details)));
                break;
            case(GlobalConstants.LIFE):
                oreName.setText(getResources().getString(R.string.life_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.life_2));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.life_details)));
                break;
            case(GlobalConstants.ICE):
                oreName.setText(getResources().getString(R.string.ice_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.ice));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.ice_details)));
                break;
            case(GlobalConstants.GOLD):
                oreName.setText(getResources().getString(R.string.gold_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.gold));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.gold_details)));
                break;
            case(GlobalConstants.GASROCK):
                oreName.setText(getResources().getString(R.string.gasrock_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.gasrock));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.gas_details)));
                break;
            case(GlobalConstants.CRYSTAL):
                oreName.setText(getResources().getString(R.string.crystal_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.crystalbase));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.crystal_details)));
                break;
            case(GlobalConstants.COSTUMEGEM):
                oreName.setText(getResources().getString(R.string.costume_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.costumegem));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.costume_details)));
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
