package uk.ac.westminster.coursework;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CarImage extends AppCompatActivity {
    ActionBar titleBar;
    TextView car_name, answerstatus,timer;
    Button btn_next;
    ImageView car1,car2,car3,answer;
    ListOfCars listOfCars;
    Random r;
    Car car;

    //array
    ArrayList<Car> carArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_image);

        //changing title bar color
        titleBar= getSupportActionBar();
        titleBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#392613")));

        //inisializing variables
        car_name = findViewById(R.id.car_name);
        car1 = findViewById(R.id.image1);
        car2 = findViewById(R.id.image2);
        car3 = findViewById(R.id.image3);
        answerstatus = findViewById(R.id.correction);
        btn_next = findViewById(R.id.next);
        timer = findViewById(R.id.timer3);

        r = new Random();
        //database of cars
        listOfCars = new ListOfCars();
        carArrayList = new ArrayList();

        for (int i = 0; i < listOfCars.answers.length; i++) {
            Car values = new Car(listOfCars.answers[i], listOfCars.images[i]);
            carArrayList.add(values);
        }
        Collections.shuffle(carArrayList);




        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question();
            }
        });

        car1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ifCorrect(car1);
            }
        });

        car2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifCorrect(car2);
            }
        });

        car3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ifCorrect(car3);
            }
        });

        Question();
    }

    //method of timer
    void setTimer(){

        new CountDownTimer(11000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                wrong();
            }
        }.start();
    }

    void Question() {
        //answer cheaking
        answerstatus.setText("");
        int img1 = r.nextInt(carArrayList.size());
        int img2 = r.nextInt(carArrayList.size());
        int img3 = r.nextInt(carArrayList.size());
        int a = r.nextInt(3);
        car = carArrayList.get(img1);

        while (img2 == img1 || img3 == img1) {
            img2 = r.nextInt(carArrayList.size());
            img3 = r.nextInt(carArrayList.size());
        }

        if (a == 1) {
            car1.setImageResource(car.getImage());
            car2.setImageResource(carArrayList.get(img2).getImage());
            car3.setImageResource(carArrayList.get(img3).getImage());
            answer = car1;
        } else if (a == 2) {
            car1.setImageResource((carArrayList.get(img2).getImage()));
            car2.setImageResource(car.getImage());
            car3.setImageResource(carArrayList.get(img3).getImage());
            answer = car2;
        } else {
            car1.setImageResource(carArrayList.get(img3).getImage());
            car2.setImageResource(carArrayList.get(img2).getImage());
            car3.setImageResource(car.getImage());
            answer = car3;

        }

        car_name.setText(car.getName());

        //calling timer
        if (MainActivity.isTimerOn){
            setTimer();
        }

    }
    void ifCorrect(ImageView img){
        if (img == answer){
            correct();
        }else{
            wrong();
        }
    }

    void correct(){
        answerstatus.setTextColor(Color.parseColor("#008000"));
        answerstatus.setText("Correct!");
    }

    void wrong(){
        answerstatus.setTextColor(Color.parseColor("#FF0000"));
        answerstatus.setText("Wrong!");
    }
}
