package com.example.thomas.miner;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Thomas on 11/09/2017.
 */

public class ShopDetails extends Activity{

    int item;
    TextView itemName;
    TextView currentLevel;
    TextView nextItemLevel;
    TextView nextItemBenefit;
    ShopMemory shopMemory;
    OreMemory oreMemory;
    EncyclopediaMemory encyclopediaMemory;
    int screenWidth;
    int[] oreTypeNeeded = new int[GlobalConstants.MEMORY_LENGTH_ARRAY_ORE];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_details);
        item = getIntent().getIntExtra("Item", GlobalConstants.HOUSEUPGRADE);
        itemName = (TextView) findViewById(R.id.itemForChange);
        currentLevel = (TextView) findViewById(R.id.currentItemLevel);
        nextItemLevel = (TextView) findViewById(R.id.nextItemLevel);
        nextItemBenefit = (TextView) findViewById(R.id.nextItemBenefit);
        shopMemory = new ShopMemory(this);
        oreMemory = new OreMemory(this);
        encyclopediaMemory = new EncyclopediaMemory(this);
        findScreenWidth();
        setItemDetails();
    }

    public void setItemDetails()
    {
        switch (item)
        {
            case (GlobalConstants.PICKAXEUPGRADE):
                itemName.setText(getResources().getString(R.string.pickaxe_update_name));
                setPickAxeUpdates();
                break;
            case (GlobalConstants.AIRTANKUPGRADE):
                itemName.setText(getResources().getString(R.string.airtank_update_name));
                setAirTankUpdates();
                break;
            case (GlobalConstants.HOUSEUPGRADE):
                itemName.setText(getResources().getString(R.string.house_update_name));
                setHouseUpdates();
                break;
        }
        setCosts();
    }

    private void setCosts()
    {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.shopdetails_cost_ll);
        getOreNeeded();

        for (int ii = GlobalConstants.SOIL; ii < GlobalConstants.NUMBEROFTYPES; ii++)
        {
            if (oreTypeNeeded[ii] > 0)
            {
                LinearLayout newRow = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                newRow.setOrientation(LinearLayout.HORIZONTAL);
                newRow.setLayoutParams(layoutParams);

                newRow.addView(getImageHolder(ii));

                newRow.addView(addCurrentOres(ii));

                newRow.addView(addRequiredOres(ii));

                linearLayout.addView(newRow);

            }
        }
    }


    private void setPickAxeUpdates()
    {
        currentLevel.setText(getResources().getString(R.string.current_pickaxe));
        nextItemLevel.setText(getResources().getString(R.string.next_pickaxe));
        switch (shopMemory.getItem(GlobalConstants.PICKAXEUPGRADE))
        {
            case (0):
                currentLevel.append(getResources().getString(R.string.pickaxe_update_0));
                nextItemLevel.append(getResources().getString(R.string.pickaxe_update_1));
                nextItemBenefit.setText(getResources().getString(R.string.pickaxe_update_1_benefit));
                break;
        }
    }

    private void setAirTankUpdates()
    {
        currentLevel.setText(getResources().getString(R.string.current_air_tank));
        nextItemLevel.setText(getResources().getString(R.string.next_air_tank));
        switch (shopMemory.getItem(GlobalConstants.AIRTANKUPGRADE))
        {
            case (0):
                currentLevel.append(getResources().getString(R.string.air_tank_update_0));
                nextItemLevel.append(getResources().getString(R.string.air_tank_update_1));
                nextItemBenefit.setText(getResources().getString(R.string.air_tank_update_1_benefit));
                break;
        }
    }

    private void setHouseUpdates()
    {
        currentLevel.setText(getResources().getString(R.string.current_house));
        nextItemLevel.setText(getResources().getString(R.string.next_house));
        switch (shopMemory.getItem(GlobalConstants.HOUSEUPGRADE))
        {
            case (0):
                currentLevel.append(getResources().getString(R.string.house_update_0));
                nextItemLevel.append(getResources().getString(R.string.house_update_1));
                nextItemBenefit.setText(getResources().getString(R.string.house_update_1_benefit));
                break;
        }
    }


    private LinearLayout getImageHolder(int ii)
    {
        LinearLayout imageHolder = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParamsImage = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.4f);
        imageHolder.setOrientation(LinearLayout.HORIZONTAL);
        imageHolder.setLayoutParams(layoutParamsImage);
        imageHolder.setGravity(View.TEXT_ALIGNMENT_CENTER);
        ImageView imageView = new ImageView(this);
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
        if (!encyclopediaMemory.isOreUnlocked(ii))
        {
            return getResources().getDrawable(R.drawable.locked_ore);
        }
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
        TextView currentOres = new TextView(this);
        LinearLayout.LayoutParams layoutParamsCurrentOre = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.3f);
        currentOres.setGravity(View.TEXT_ALIGNMENT_CENTER);
        currentOres.setLayoutParams(layoutParamsCurrentOre);
        currentOres.setText("Current: " + oreMemory.getCurrentOre(ii));
        return currentOres;
    }

    private TextView addRequiredOres(int ii)
    {
        TextView totalOres = new TextView(this);
        LinearLayout.LayoutParams layoutParamsTotalOre = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.3f);
        totalOres.setGravity(View.TEXT_ALIGNMENT_CENTER);
        totalOres.setLayoutParams(layoutParamsTotalOre);
        totalOres.setText("Required: " + oreTypeNeeded[ii]);
        return totalOres;
    }

    private void findScreenWidth()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }

    private void getOreNeeded()
    {
        switch (item)
        {
            case (GlobalConstants.HOUSEUPGRADE):
                getCostsHouse();
                break;
        }
    }

    private void getCostsHouse()
    {
        switch (shopMemory.getItem(item))
        {
            case (0):
                oreTypeNeeded[GlobalConstants.COPPER] = 10;
                break;
        }
    }

}
