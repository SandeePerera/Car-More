package uk.ac.westminster.coursework;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CarHints extends AppCompatActivity {
        ActionBar titleBar;
        ImageView imageView;
        TextView hintletters, answer, correction,timer;
        EditText editcharacters;
        Button button;
        Random r;
        ListOfCars listOfCars;
        Car car;

        //Creating arraylist
        ArrayList<Car> carArrayList;

        char[] car_Name;
        char[] hint_Letters;

        int wrongCount = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_car_hints);

            //changing title bar color
            titleBar= getSupportActionBar();
            titleBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#392613")));

            // Initialize all the view variables.
            imageView = findViewById(R.id.imageView);
            hintletters = findViewById(R.id.letters);
            editcharacters = findViewById(R.id.characters);
            answer = findViewById(R.id.correctName);
            correction = findViewById(R.id.mark);
            button = findViewById(R.id.btnsubmit);
            timer=findViewById(R.id.timer2);

            r = new Random();
            listOfCars = new ListOfCars();

            carArrayList = new ArrayList();

            //add answers to array
            for (int i = 0; i < listOfCars.answers.length; i++) {
                Car c = new Car(listOfCars.answers[i], listOfCars.images[i]);
                carArrayList.add(c);
            }
            // shuffle the list
            Collections.shuffle(carArrayList);

            Question();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (button.getText().equals("Submit")) {
                        checkAnswer();
                    } else {
                        Question();
                    }
                }
            });

        }

        private void checkAnswer() {
            //user enter the character
            answer.setText("");
            String s = editcharacters.getText().toString().toUpperCase();

            //allowed 3 incorrect character guesses
            if (wrongCount >= 3) {
                wrong();
            } else {
                //if user enter a character more than 1
                if (s.length() > 1) {
                    Toast.makeText(this, "You can enter only one character at a time!", Toast.LENGTH_LONG).show();
                    editcharacters.setText("");
                } else {
                    //create an array list for count characters for name of car
                    ArrayList<Integer> indexes = new ArrayList();
                    //boolean for found the entered character in somewhere through car name
                    boolean foundAtLeastOneCharacter = false;
                    //check charecter similarities
                    for (int i = 0; i < car_Name.length; i++) {
                        char c = car_Name[i];

                        //if character is there, position of the character add to the array list
                        if (s.charAt(0) == c) {
                            indexes.add(i);
                            foundAtLeastOneCharacter = true;
                        }
                    }

                    //if at least one character is there,
                    if (foundAtLeastOneCharacter) {
                        //entered character
                        for (int i : indexes) {
                            hint_Letters[i] = s.charAt(0);
                        }

                        //shows hint letters in text view
                        editcharacters.setText("");
                        hintletters.setText(String.valueOf(hint_Letters));
                    } else {
                        incorrectCharacter();
                    }

                    boolean hintsComplete = true;

                    //for loop for check whether empty
                    for (char c : hint_Letters) {
                        String ss = String.valueOf(c);

                        if (ss.equals("-")) {
                            hintsComplete = false;
                            break;
                        }

                    }
                    //if the word is complete and calling correct method
                    if (hintsComplete) {
                        correct();
                    }
                }

            }
        }
        void incorrectCharacter(){
            wrongCount += 1;

            answer.setTextColor(Color.parseColor("#FFFF00"));
            answer.setText("Incorrect Character!");
        }

        void Question() {
            wrongCount = 0;

            int i = r.nextInt(carArrayList.size());
            car = carArrayList.get(i);
            imageView.setImageResource(car.getImage()); //image loading

            //set as a char array of car name of picture
            car_Name = new char[car.getName().length()];
            //set a char array for hint letters
            hint_Letters = new char[car_Name.length];

            //shows car name in capital
            String ss = car.getName().toUpperCase();
            car_Name = ss.toCharArray();


            for (int k = 0; k < car_Name.length; k++) {
                char c = car_Name[k];

                if (!(String.valueOf(c).equals(" "))) {
                    String s = "-";
                    hint_Letters[k] = s.charAt(0);
                } else {
                    String s = " ";
                    hint_Letters[k] = s.charAt(0);
                }
            }

            System.out.println(car_Name);

            hintletters.setText(String.valueOf(hint_Letters));
            answer.setText("");
            correction.setText("");
            button.setText("Submit");

            if (MainActivity.isTimerOn){
                setTimer();
            }
        }

        void correct() {
            answer.setTextColor(Color.parseColor("#008000"));
            answer.setText("Correct!");
            button.setText("Next");
        }

        void wrong() {
            answer.setTextColor(Color.parseColor("#FF0000"));
            answer.setText("Wrong!");
            correction.setText(car.getName());
            button.setText("Next");
        }

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
    }

