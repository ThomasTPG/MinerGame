package com.example.thomas.miner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Thomas on 11/03/2017.
 */

public class OreView extends OnClickFragment {

    int screenWidth;
    OreMemory oreMemory;
    EncyclopediaMemory encyclopediaMemory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.oreview_layout, container, false);
        findScreenWidth();
        oreMemory = new OreMemory(getActivity());
        encyclopediaMemory = new EncyclopediaMemory(getActivity());

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.oreview_linear_layout);

        for (int ii = GlobalConstants.SOIL; ii < GlobalConstants.NUMBEROFTYPES; ii++)
        {
            if (encyclopediaMemory.isOreUnlocked(ii) && ii != GlobalConstants.ICE)
            {
                LinearLayout newRow = new LinearLayout(getActivity());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                newRow.setOrientation(LinearLayout.HORIZONTAL);
                newRow.setLayoutParams(layoutParams);

                newRow.addView(getImageHolder(ii));

                newRow.addView(addCurrentOres(ii));

                newRow.addView(addTotalOres(ii));

                linearLayout.addView(newRow);

            }
        }
        return view;
    }

    private LinearLayout getImageHolder(int ii)
    {
        LinearLayout imageHolder = new LinearLayout(getActivity());
        LinearLayout.LayoutParams layoutParamsImage = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.4f);
        imageHolder.setOrientation(LinearLayout.HORIZONTAL);
        imageHolder.setLayoutParams(layoutParamsImage);
        imageHolder.setGravity(View.TEXT_ALIGNMENT_CENTER);
        ImageView imageView = new ImageView(getActivity());
        imageView.setImageDrawable(getCorrectDrawable(ii));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setAdjustViewBounds(true);
        int padding = (int) Math.floor((double) screenWidth / (double) 48);
        imageView.setPadding(padding,padding,padding,padding);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.height = screenWidth/4;
        params.width = screenWidth/4;
        imageView.setLayoutParams(params);
        imageHolder.addView(imageView);
        return imageHolder;
    }

    private Drawable getCorrectDrawable(int ii)
    {
        switch (ii)
        {
            case(GlobalConstants.SOIL):
                return getResources().getDrawable(R.drawable.soil);
            case(GlobalConstants.COPPER):
                return getResources().getDrawable(R.drawable.copper);
            case(GlobalConstants.IRON):
                return getResources().getDrawable(R.drawable.iron);
            case(GlobalConstants.EXPLODIUM):
                return getResources().getDrawable(R.drawable.explodium);
            case(GlobalConstants.MARBLE):
                return getResources().getDrawable(R.drawable.marble);
            case(GlobalConstants.SPRING):
                return getResources().getDrawable(R.drawable.spring);
            case(GlobalConstants.LIFE):
                return getResources().getDrawable(R.drawable.life_6);
            case(GlobalConstants.GOLD):
                return getResources().getDrawable(R.drawable.gold);
            case(GlobalConstants.CRYSTAL):
                return getResources().getDrawable(R.drawable.crystal4);
            case(GlobalConstants.GASROCK):
                return getResources().getDrawable(R.drawable.gasrock);
            default:
                return getResources().getDrawable(R.drawable.copper);
        }
    }


    private TextView addCurrentOres(int ii)
    {
        TextView currentOres = new TextView(getActivity());
        LinearLayout.LayoutParams layoutParamsCurrentOre = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.3f);
        currentOres.setGravity(View.TEXT_ALIGNMENT_CENTER);
        currentOres.setLayoutParams(layoutParamsCurrentOre);
        System.out.println("BEANS!" + oreMemory.getCurrentOre(ii));
        currentOres.setText("Current: " + oreMemory.getCurrentOre(ii));
        return currentOres;
    }

    private TextView addTotalOres(int ii)
    {
        TextView totalOres = new TextView(getActivity());
        LinearLayout.LayoutParams layoutParamsTotalOre = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.3f);
        totalOres.setGravity(View.TEXT_ALIGNMENT_CENTER);
        totalOres.setLayoutParams(layoutParamsTotalOre);
        totalOres.setText("Total: " + oreMemory.getTotalOre(ii));
        return totalOres;
    }


    private void findScreenWidth()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }


}
