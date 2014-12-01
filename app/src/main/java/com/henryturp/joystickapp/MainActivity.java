package com.henryturp.joystickapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    RelativeLayout joystickLayout;
    RelativeLayout menuLayout;
    GridLayout gameGridLayout;
    TextView textViewX, textViewY, textViewDirection;
    ImageView menu_button;
    Button resetButton, saveButton, openButton, deleteButton;
    EditText saveEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.main_layout).setBackgroundColor(Color.rgb(15, 15, 15));

        joystickLayout = (RelativeLayout) findViewById(R.id.layout_joystick);
        gameGridLayout = (GridLayout) findViewById(R.id.layout_grid);

        textViewX = (TextView) findViewById(R.id.textViewX);
        textViewY = (TextView) findViewById(R.id.textViewY);
        textViewDirection = (TextView) findViewById(R.id.textViewDirection);
        menuLayout = (RelativeLayout) findViewById(R.id.menuLayout);
        menu_button = (ImageView) findViewById(R.id.menu_icon);

        resetButton = (Button) findViewById(R.id.resetButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        openButton = (Button) findViewById(R.id.openButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        saveEditText = (EditText) findViewById(R.id.saveEditText);

        final Joystick joystick = new Joystick(getApplicationContext(),joystickLayout,R.drawable.image_button);
        joystick.setMinDistance(80);
        joystick.setOffset(50);
        joystick.setLayoutAlpha(200);
        joystick.setStickAlpha(150);
        joystick.setStickSize(100,100);
        joystick.setLayoutSize(250,250);


        final GameGrid gameGrid = new GameGrid(getApplicationContext(),gameGridLayout,0,15,984);
        final FileManager fileManager= new FileManager(gameGrid.getBlocks(),getApplicationContext());

        joystickLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                joystick.drawStick(event);
                String direction = "";

                if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
                    int directionNum = joystick.getDirection();
                    if (directionNum == 0)
                        direction = "";
                    else if (directionNum == 1)
                        direction = "Right!";
                    else if (directionNum == 2)
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

        menu_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (menuLayout.getVisibility() == View.VISIBLE)
                        menuLayout.setVisibility(View.INVISIBLE);
                    else menuLayout.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameGrid.resetGrid();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileManager.saveFile(saveEditText.getText().toString());
            }
        });

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileManager.readFile(saveEditText.getText().toString());
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileManager.deleteFile(saveEditText.getText().toString());
            }
        });
    }


}
