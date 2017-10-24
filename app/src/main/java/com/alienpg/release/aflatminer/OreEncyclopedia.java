package com.alienpg.release.aflatminer;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Thomas on 12/09/2017.
 */

public class OreEncyclopedia extends OnClickFragment
{
    int screenWidth;
    EncyclopediaMemory encyclopediaMemory;
    Context context;
    View myView;
    OnButtonClickedListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.ore_encyclopedia, container, false);
        context = getActivity();
        encyclopediaMemory = new EncyclopediaMemory(context);
        findScreenWidth();
        populateEncyclopedia();
        return myView;
    }

    private void findScreenWidth()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }


    private void populateEncyclopedia()
    {
        for (int ii = 1; ii <= GlobalConstants.ENCYCLOPEDIA_NUMBERS; ii++)
        {
            String buttonID = "enc_button_" + ii;
            int resID = getResources().getIdentifier(buttonID, "id", getActivity().getPackageName());
            ImageButton imageButton = (ImageButton) myView.findViewById(resID);
            imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageButton.setAdjustViewBounds(true);
            ViewGroup.LayoutParams params = imageButton.getLayoutParams();
            params.height = screenWidth/4;
            params.width = screenWidth/4;
            imageButton.setLayoutParams(params);

            int whichOre = 0;
            int currentOre;
            switch (ii)
            {
                case (1):
                    currentOre = GlobalConstants.SOIL;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.soil));
                        whichOre = currentOre;
                    }
                    break;
                case (2):
                    currentOre = GlobalConstants.BOULDER;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.rock));
                        whichOre = currentOre;
                    }
                    break;
                case (3):
                    currentOre = GlobalConstants.HARD_BOULDER;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.hardrock));
                        whichOre = currentOre;
                    }
                    break;
                case (4):
                    currentOre = GlobalConstants.WATER;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.water_test));
                        whichOre = currentOre;
                    }
                    break;
                case (5):
                    currentOre = GlobalConstants.GAS;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.gas_background));
                        whichOre = currentOre;
                    }
                    break;
                case (6):
                    currentOre = GlobalConstants.COPPER;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.copper));
                        whichOre = currentOre;
                    }
                    break;
                case (7):
                    currentOre = GlobalConstants.IRON;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.iron));
                        whichOre = currentOre;
                    }
                    break;
                case (8):
                    currentOre = GlobalConstants.EXPLODIUM;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.explodium));
                        whichOre = currentOre;
                    }
                    break;
                case (9):
                    currentOre = GlobalConstants.MARBLE;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.marble));
                        whichOre = currentOre;
                    }
                    break;
                case (10):
                    currentOre = GlobalConstants.SPRING;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.spring));
                        whichOre = currentOre;
                    }
                    break;
                case (11):
                    currentOre = GlobalConstants.LIFE;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.life_2));
                        whichOre = currentOre;
                    }
                    break;
                case (12):
                    currentOre = GlobalConstants.ICE;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ice));
                        whichOre = currentOre;
                    }
                    break;
                case (13):
                    currentOre = GlobalConstants.GOLD;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.gold));
                        whichOre = currentOre;
                    }
                    break;
                case (14):
                    currentOre = GlobalConstants.GASROCK;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.gasrock));
                        whichOre = currentOre;
                    }
                    break;
                case (15):
                    currentOre = GlobalConstants.CRYSTAL;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.crystal1));
                        whichOre = currentOre;
                    }
                    break;
                case (16):
                    currentOre = GlobalConstants.COSTUMEGEM;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.costumegem));
                        whichOre = currentOre;
                    }
                    break;
                case (17):
                    currentOre = GlobalConstants.TIN;
                    if (encyclopediaMemory.isOreUnlocked(currentOre))
                    {
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.tin));
                        whichOre = currentOre;
                    }
                    break;
            }
            final int selectedOre = whichOre;
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedOre != 0)
                    {
                        mCallback.onButtonClicked(GlobalConstants.ENCYCLOPEDIA_PAGES, selectedOre);
                    }
                }
            });
        }
    }

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
