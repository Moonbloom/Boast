package com.boastdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.moonbloom.boast.BConstants;
import com.moonbloom.boast.BStyle;
import com.moonbloom.boast.Boast;

public class MainActivity extends Activity {

    private int differentStylesCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if(getActionBar() != null) {
            getActionBar().setTitle(R.string.main_title);
        }

        Button differentStylesButton = (Button) findViewById(R.id.different_styles_button);
        differentStylesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                differentStylesCounter++;

                BStyle style;

                switch (differentStylesCounter) {
                    case 1:
                        style = BStyle.OK;
                        break;

                    case 2:
                        style = BStyle.MESSAGE;
                        break;

                    case 3:
                        style = BStyle.CAUTION;
                        break;

                    case 4:
                        style = BStyle.ALERT;
                        break;

                    default:
                        style = BStyle.OK;
                        break;
                }

                Boast.makeText(MainActivity.this, "This is a simple Boast", style);

                if (differentStylesCounter == 4) {
                    differentStylesCounter = 0;
                }
            }
        });

        Button customStyleOneButton = (Button) findViewById(R.id.custom_style_one_button);
        customStyleOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BStyle style = new BStyle.Builder().setBackgroundColorValue(0xFF673AB7).setPadding(5, 5).build();

                Boast.makeText(MainActivity.this, "This is a Boast with a custom style\nSmaller padding and different background color", style);
            }
        });

        Button customStyleTwoButton = (Button) findViewById(R.id.custom_style_two_button);
        customStyleTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BStyle style = new BStyle.Builder().setDuration(BConstants.BDuration.Long).setTextSize(16).build();

                Boast.makeText(MainActivity.this, "This is a Boast with a custom style\nLong duration and bigger text size", style);
            }
        });

        Button withImageButton = (Button) findViewById(R.id.with_image_button);
        withImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BStyle style = new BStyle.Builder().setImageResource(R.drawable.android_mascot).setBackgroundColor(BConstants.BColor.GreenOk).build();

                Boast.makeText(MainActivity.this, "This is a Boast with an image", style);
            }
        });
    }
}