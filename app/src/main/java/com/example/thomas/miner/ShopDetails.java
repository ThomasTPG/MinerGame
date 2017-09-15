package com.example.thomas.miner;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
                //nextItemBenefit.setText(getResources().getString(R.string.house_up));
                break;
        }
    }
}
