package com.alienpg.release.aflatminer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Thomas on 11/09/2017.
 */

public class GameOverScreen extends OnClickFragment implements MainActivity.OnBackPressedListener
{
    View myView;
    OnButtonClickedListener mCallback;
    int screenWidth;
    int[] oreArray;
    int [] oresKept;
    int endReason;
    OreCounter oreCounter;
    OreMemory oreMemory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.game_over_screen_layout, container, false);
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
        Bundle bundle = getArguments();
        endReason = bundle.getInt("PAGE",GlobalConstants.ESCAPE);
        oreCounter = new OreCounter();
        oreMemory = new OreMemory(getActivity());

        oreArray = oreMemory.getPreviouslyMinedOre();
        setOresKept();
        findScreenWidth();

        generateScreen();

        if (endReason == GlobalConstants.ESCAPE)
        {
            oreMemory.updateOreStorage();
        }
        return myView;
    }

    @Override
    public void doBack() {
        mCallback.onButtonClicked(GlobalConstants.MAIN_MENU);
    }


    private void setOresKept()
    {
        oresKept = new int[GlobalConstants.MEMORY_LENGTH_ARRAY_ORE];
        if (endReason != GlobalConstants.ESCAPE)
        {
            for (int ii = 0; ii < GlobalConstants.MEMORY_LENGTH_ARRAY_ORE; ii++)
            {
                oresKept[ii] = 0;
            }
        }
        else
        {
            oresKept = oreArray;
        }
    }

    private void generateScreen()
    {
        TextView titleView = (TextView) myView.findViewById(R.id.game_over_title);
        String title;
        switch(endReason)
        {
            case (GlobalConstants.ESCAPE):
                title = getResources().getString(R.string.escape_title);
                break;
            case (GlobalConstants.SUFFOCATED):
                title = getResources().getString(R.string.suffocated_title);
                break;
            case (GlobalConstants.FROZEN):
                title = getResources().getString(R.string.frozen_title);
                break;
            case (GlobalConstants.EXPLOSION):
                title = getResources().getString(R.string.explosion_title);
                break;
            default:
                title = getResources().getString(R.string.escape_title);
                break;
        }
        titleView.setText(title);

        TextView oreTitleView = (TextView) myView.findViewById(R.id.ore_gained_title);
        String oreGainedTitle;
        if (endReason == GlobalConstants.ESCAPE)
        {
            oreGainedTitle = getResources().getString(R.string.escaped_wording);
        }
        else
        {
            oreGainedTitle = getResources().getString(R.string.death_wording);
        }
        oreTitleView.setText(oreGainedTitle);

        Button start = (Button) myView.findViewById(R.id.gameover_main_menu_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onButtonClicked(GlobalConstants.MAIN_MENU);
            }
        });

        final Button video = (Button) myView.findViewById(R.id.watch_video);
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oreMemory.updateOreStorage(oreCounter);
                video.setVisibility(View.GONE);
            }
        });

        setOresGained();

    }

    private void setOresGained()
    {
        LinearLayout linearLayout = (LinearLayout) myView.findViewById(R.id.oresgained_details);
        linearLayout.removeAllViews();

        for (int ii = GlobalConstants.SOIL; ii < GlobalConstants.NUMBEROFTYPES; ii++)
        {
            if (oreArray[ii] > 0)
            {
                LinearLayout newRow = new LinearLayout(getActivity());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                newRow.setOrientation(LinearLayout.HORIZONTAL);
                newRow.setLayoutParams(layoutParams);

                newRow.addView(getImageHolder(ii));

                newRow.addView(addOreAmount(ii));

                linearLayout.addView(newRow);
            }
        }
    }

    private TextView addOreAmount(int ii)
    {
        TextView amount = new TextView(getActivity());
        LinearLayout.LayoutParams layoutParamsCurrentOre = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.3f);
        amount.setGravity(View.TEXT_ALIGNMENT_CENTER);
        amount.setLayoutParams(layoutParamsCurrentOre);
        String oreAmount;
        if (endReason == GlobalConstants.ESCAPE)
        {
            oreAmount = "You mined: " + oreArray[ii];
        }
        else
        {
            Random random = new Random(oreArray[ii] * System.nanoTime());
            for (int gg = 0; gg < oreArray[ii]; gg++)
            {
                if (random.nextInt(3) == 0)
                {
                    oresKept[ii] ++;
                }
            }
            oreAmount = "You mined: " + oreArray[ii] + "\n" + "You can keep: " + oresKept[ii];
            oreCounter.setOre(ii,oresKept[ii]);
        }
        amount.setText(oreAmount);
        return amount;
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
        params.height = screenWidth/8;
        params.width = screenWidth/8;
        imageView.setLayoutParams(params);
        imageHolder.addView(imageView);
        return imageHolder;
    }

    private void findScreenWidth()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }

    private Drawable getCorrectDrawable(int ii)
    {
        switch (ii)
        {
            case(GlobalConstants.SOIL):
                return getResources().getDrawable(R.drawable.soil);
            case(GlobalConstants.COPPER):
                return getResources().getDrawable(R.drawable.copper);
            case(GlobalConstants.TIN):
                return getResources().getDrawable(R.drawable.tin);
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

    //onBackPressed
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnButtonClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnButtonClickedListener");
        }
    }
}
