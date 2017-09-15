package com.example.thomas.miner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OreMemory oreMemory = new OreMemory(this);
        ShopMemory shopMemory = new ShopMemory(this);
        shopMemory.checkFileExists();
        final Intent startIntent = new Intent(this, GameMain.class);
        final Intent shopIntent = new Intent(this, Shop.class);
        final Intent viewOreIntent = new Intent(this, OreView.class);
        final Intent encyclopediaIntent = new Intent(this, OreEncyclopedia.class);

        Button start = (Button) findViewById(R.id.buttonStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(startIntent);
            }
        });

        Button shop = (Button) findViewById(R.id.buttonShop);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(shopIntent);
            }
        });

        Button viewOres = (Button) findViewById(R.id.buttonViewOres);
        viewOres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(viewOreIntent);
            }
        });

        Button encyclopedia = (Button) findViewById(R.id.buttonEncyclopedia);
        encyclopedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(encyclopediaIntent);
            }
        });
    }
}
