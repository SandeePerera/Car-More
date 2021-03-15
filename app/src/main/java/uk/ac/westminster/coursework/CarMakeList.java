package uk.ac.westminster.coursework;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CarMakeList extends AppCompatActivity {
    ActionBar titleBar;
    Spinner spinner;
    ImageView imageView;
    Button button;
    TextView correctName,answerStatus,timer;
    Random r;
    ListOfCars listOfCars;
    Car car;

    //Creating arraylist
    ArrayList<Car> carArrayList;
    // Create a list of strings
    List<String> answer_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_make_list);

        //changing title bar color
        titleBar= getSupportActionBar();
        titleBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#392613")));

        // Initialize all variables.
        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        correctName = findViewById(R.id.correctName);
        answerStatus = findViewById(R.id.answer);
        timer = findViewById(R.id.timer1);


        r = new Random();
        //calling array lists
        listOfCars = new ListOfCars();
        carArrayList = new ArrayList();
        answer_list = new ArrayList();

        //add answers to array
        for(int i = 0; i < listOfCars.answers.length; i++){
            Car c = new Car(listOfCars.answers[i], listOfCars.images[i]);
            carArrayList.add(c);
            answer_list.add(c.getName());
        }

        //Sort ArrayList
        Collections.sort(answer_list);
        // shuffle the list
        Collections.shuffle(carArrayList);

        // Creating adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, answer_list);
        // Drop down layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        loadNewCar();
        // attaching adapter to spinner
        spinner.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().equals("Submit")){
                    String selectedCar = spinner.getSelectedItem().toString();

                    if (car.getName().equals(selectedCar)){
                        correct();
                    }else {
                        wrong();
                    }

                }else{
                    loadNewCar();
                }
            }
        });



    }
    void loadNewCar(){
        int i = r.nextInt(carArrayList.size());
        car = carArrayList.get(i);
        imageView.setImageResource(car.getImage()); //image loading
        answerStatus.setText("");
        correctName.setText("");
        button.setText("Submit");

        if (MainActivity.isTimerOn){
            setTimer();
        }
    }
    void correct(){
        answerStatus.setTextColor(Color.parseColor("#008000"));
        answerStatus.setText("Correct!");
        button.setText("Next");
    }

    void wrong(){
        answerStatus.setTextColor(Color.parseColor("#FF0000"));
        answerStatus.setText("Wrong!");
        correctName.setText(car.getName());
        button.setText("Next");
    }


    void setTimer(){

        new CountDownTimer(11000, 1000) {
            // the countdown.
            public void onTick(long millisUntilFinished) {
                timer.setText("" + millisUntilFinished / 1000);
            }
            // countdown timer finishes - time is up.
            public void onFinish() {
                wrong();
            }
            //starts the countdown timer.
        }.start();
    }
}
