package com.alienpg.release.aflatminer;

import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Thomas on 15/09/2017.
 */

public class EncyclopediaData extends OnClickFragment {

    int screenWidth;
    int ore;
    TextView oreName;
    ImageView oreImage;
    TextView oreDetails;
    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.encyclopedia_data, container, false);
        Bundle bundle = getArguments();
        ore = bundle.getInt("PAGE",GlobalConstants.SOIL);
        findScreenWidth();
        getViews();
        populateFields();

        return myView;
    }

    private void getViews()
    {
        oreName = (TextView) myView.findViewById(R.id.enc_ore_name);
        oreImage = (ImageView) myView.findViewById(R.id.enc_ore_img);
        oreDetails = (TextView) myView.findViewById(R.id.enc_ore_details);
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
            case(GlobalConstants.TIN):
                oreName.setText(getResources().getString(R.string.tin_name));
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.tin));
                oreDetails.setText(Html.fromHtml(getResources().getString(R.string.tin_details)));
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
                oreImage.setImageDrawable(getResources().getDrawable(R.drawable.crystal1));
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
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }
}
