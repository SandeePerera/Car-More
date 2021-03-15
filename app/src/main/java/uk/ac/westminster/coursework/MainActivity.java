package uk.ac.westminster.coursework;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

public class
MainActivity extends AppCompatActivity {
    ActionBar titleBar;
    private Button carmake_button;
    private Button hints_button;
    private Button carimage_button;
    private Button advancelevel_button;
    public Switch timerSwitch;
    public static boolean isTimerOn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //changing title bar color
        titleBar= getSupportActionBar();
        titleBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000080")));

        //Initialize the buttons
        carmake_button = (Button) findViewById(R.id.button);
        hints_button = (Button) findViewById(R.id.button2);
        carimage_button = (Button) findViewById(R.id.button3);
        advancelevel_button = (Button) findViewById(R.id.button4);
        timerSwitch = (Switch) findViewById(R.id.timer);

        timerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //when Switch button is on
                if (timerSwitch.isChecked()) {
                    timerSwitch.setText("Timer ON ");
                    isTimerOn = true;
                    //when Switch is off
                } else {
                    timerSwitch.setText("Timer OFF");
                    isTimerOn = false;
                }
            }
        });

        //call the intents
        carmake_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carmake();

            }
        });

       hints_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hints();

            }
        });


   carimage_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            carimages();

        }
    });

        advancelevel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               advancelevel();

            }
        });

}
    //methods for intents
    private void carmake() {
        Intent intent=new Intent(MainActivity.this, CarMakeList.class);
        startActivity(intent);
    }


    private void hints() {
       Intent intent=new Intent(MainActivity.this, CarHints.class);
        startActivity(intent);
    }

    private void carimages() {
        Intent intent=new Intent(MainActivity.this, CarImage.class);
        startActivity(intent);
    }

    private void advancelevel() {
        Intent intent=new Intent(MainActivity.this, AdvancedLevel.class);
        startActivity(intent);
    }

}