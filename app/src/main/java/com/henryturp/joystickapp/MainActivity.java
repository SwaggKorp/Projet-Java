package com.henryturp.joystickapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends Activity {

    RelativeLayout layout;
    TextView textViewX, textViewY, textViewDirection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (RelativeLayout) findViewById(R.id.layout_joystick);
        textViewX = (TextView) findViewById(R.id.textViewX);
        textViewY = (TextView) findViewById(R.id.textViewY);
        textViewDirection = (TextView) findViewById(R.id.textViewDirection);

        final Joystick test = new Joystick(getApplicationContext(),layout,R.drawable.image_button);
        test.setMinDistance(80);
        test.setOffset(50);
        test.setLayoutAlpha(150);
        test.setStickAlpha(100);
        test.setStickSize(100,100);
        test.setLayoutSize(350,350);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                test.drawStick(event);
                String direction = "";

                if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN){
                    int directionNum = test.getDirection();
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
