package com.boastdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.moonbloom.boast.BStyle;
import com.moonbloom.boast.Boast;

public class MainActivity extends Activity {

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Button button = (Button) findViewById(R.id.demo_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;

                BStyle style;

                switch (counter) {
                    case 1:
                        style = BStyle.INFO;
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
                        style = BStyle.INFO;
                        break;
                }

                Boast.makeText(MainActivity.this, "This is a simple boast demo", style);

                if(counter == 4) {
                    counter = 0;
                }
            }
        });
    }
}