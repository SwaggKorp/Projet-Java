package com.henryturp.joystickapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class MainActivity extends Activity {

    RelativeLayout joystickLayout;
    GridLayout gameGridLayout;
    TextView textViewX, textViewY, textViewDirection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joystickLayout = (RelativeLayout) findViewById(R.id.layout_joystick);
        gameGridLayout = (GridLayout) findViewById(R.id.layout_grid);

        textViewX = (TextView) findViewById(R.id.textViewX);
        textViewY = (TextView) findViewById(R.id.textViewY);
        textViewDirection = (TextView) findViewById(R.id.textViewDirection);

        final Joystick joystick = new Joystick(getApplicationContext(),joystickLayout,R.drawable.image_button);
        joystick.setMinDistance(80);
        joystick.setOffset(50);
        joystick.setLayoutAlpha(150);
        joystick.setStickAlpha(100);
        joystick.setStickSize(100,100);
        joystick.setLayoutSize(250,250);


        GameGrid gamegrid = new GameGrid(getApplicationContext(),gameGridLayout,0,15,984);

        joystickLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                joystick.drawStick(event);
                String direction = "";

                if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN){
                    int directionNum = joystick.getDirection();
                    if(directionNum == 0)
                        direction = "";
                    else if(directionNum == 1)
                        direction = "Right!";
                    else if(directionNum == 2)
                        direction = "Down!";
                    else if (directionNum == 3)
                        direction = "Left!";
                    else if (directionNum == 4)
                        direction = "Up!";
                }
                textViewX.setText("X: " + event.getX());
                textViewY.setText("Y: " + event.getY());
                textViewDirection.setText("Direction: " + direction);
                return true;
            }
        });
    }


}
