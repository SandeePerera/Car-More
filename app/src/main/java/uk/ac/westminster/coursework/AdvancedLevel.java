package uk.ac.westminster.coursework;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AdvancedLevel extends AppCompatActivity {
    ActionBar titleBar;
    ImageView car1, car2, car3;
    TextView points, car1_name, car2_name, car3_name, answerStatus, timer;
    EditText ans_1, ans_2, ans_3;
    Button button;
    Random r;
    Car car;
    ListOfCars listOfCars;
    Car currentCar1, currentCar2, currentCar3;

    //Arraylist
    ArrayList<Car> carArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);

        //changing title bar color
        titleBar= getSupportActionBar();
        titleBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#392613")));

        // Initialize all variables.
        car1 = findViewById(R.id.imageViewcar1);
        car2 = findViewById(R.id.imageViewcar2);
        car3 = findViewById(R.id.imageViewcar3);
        points = findViewById(R.id.points);
        car1_name = findViewById(R.id.car1Name);
        car2_name = findViewById(R.id.car2Name);
        car3_name = findViewById(R.id.car3Name);
        timer = findViewById(R.id.timer4);
        ans_1 = findViewById(R.id.guss1);
        ans_2 = findViewById(R.id.guess2);
        ans_3 = findViewById(R.id.guess3);
        answerStatus = findViewById(R.id.answerStatus_textView);
        button = findViewById(R.id.advancedbutton);

        points.setText("0");

        r = new Random();

        carArrayList = new ArrayList();


        //add answers to array
        for (int i = 0; i < listOfCars.answers.length; i++) {
            Car values = new Car(listOfCars.answers[i], listOfCars.images[i]);
            carArrayList.add(values);
        }
        Collections.shuffle(carArrayList);

        //the method of answer check
        loadNew();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().equals("Submit")){
                    checkAnswers();
                }else {
                    loadNew();
                }
            }
        });
    }

    void loadNew(){
        //getting car images randomly
        int i = r.nextInt(carArrayList.size());
        int i2 = r.nextInt(carArrayList.size());
        int i3 = r.nextInt(carArrayList.size());

        while (i2 == i || i3 == i || i3 == i2){
            i2 = r.nextInt(carArrayList.size());
            i3 = r.nextInt(carArrayList.size());
        }

        currentCar1 = carArrayList.get(i);
        currentCar2 = carArrayList.get(i2);
        currentCar3 = carArrayList.get(i3);

        car1.setImageResource(currentCar1.getImage());
        car2.setImageResource(currentCar2.getImage());
        car3.setImageResource(currentCar3.getImage());

        car1_name.setText("");
        car2_name.setText("");
        car3_name.setText("");
        ans_1.setText("");
        ans_2.setText("");
        ans_3.setText("");
        ans_1.setTextColor(Color.parseColor("black"));
        ans_2.setTextColor(Color.parseColor("black"));
        ans_3.setTextColor(Color.parseColor("black"));
        button.setText("Submit");
        answerStatus.setText("");

        //calling timer
       if (MainActivity.isTimerOn){
            setTimer();
        }
    }

    void checkAnswers(){
        //user typing answer
        String answer1 = ans_1.getText().toString();
        String answer2 = ans_2.getText().toString();
        String answer3 = ans_3.getText().toString();
        //textview value in score
        int score = Integer.parseInt(points.getText().toString());

        //boolean value for answer 1,2,3 cheacking correction
        boolean answer1Correct = false;
        boolean answer2Correct = false;
        boolean answer3Correct = false;

        //check user typed answer 1
        if (!(answer1.equals(currentCar1.getName()))){
            //user typed answer 1 and it's wrong
            car1_name.setText(currentCar1.getName());
            ans_1.setTextColor(Color.parseColor("red"));

        }else{
            //user typed answer 1 and it's correct then add one point to score
            ans_1.setTextColor(Color.parseColor("green"));
            answer1Correct = true;
            score +=1 ;
            //set score into the text view
           points.setText(score+"");
        }

        if (!(answer2.equals(currentCar1.getName()))){
            //user typed answer 2 and it's wrong
            car2_name.setText(currentCar2.getName());
            ans_2.setTextColor(Color.parseColor("red"));
        }else{
            //user typed answer 2 and it's correct then add one point to score
            ans_2.setTextColor(Color.parseColor("green"));
            answer2Correct = true;
            score +=1 ;
            points.setText(score+"");
        }

        if (!(answer3.equals(currentCar3.getName()))){
            //user typed answer 3 and it's wrong
            car3_name.setText(currentCar3.getName());
            ans_3.setTextColor(Color.parseColor("red"));
        }else {
            //user typed answer 3 and it's correct and add one point to score
            ans_3.setTextColor(Color.parseColor("green"));
            answer3Correct = true;
            score +=1 ;
            points.setText(score+"");
        }

        //check answer 1,2 & 3 correct or not
        if(answer1Correct && answer2Correct && answer3Correct){
            correct();
        }else {
            wrong();
        }

        button.setText("Next");
    }

    //Timer method
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

    void correct(){
        answerStatus.setTextColor(Color.parseColor("#008000"));
        answerStatus.setText("Correct!");
    }

    void wrong(){
        answerStatus.setTextColor(Color.parseColor("#FF0000"));
        answerStatus.setText("Wrong!");
    }
}
