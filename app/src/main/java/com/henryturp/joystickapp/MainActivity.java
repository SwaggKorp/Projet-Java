package com.henryturp.joystickapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    RelativeLayout joystickLayout;
    RelativeLayout menuLayout;
    GridLayout gameGridLayout;
    RelativeLayout mainLayout;
    ImageView menu_button;
    Button resetButton, saveButton, deleteButton;
    EditText saveEditText;
    Spinner fileSpinner;

    GameGrid gameGrid;
    FileManager fileManager;
    Joystick joystick;

    Timer playerTimer;
    TimerTask playerTimerTask;
    int playerDirection = 0;

    public final static int gridColumnNumber = 20; // FIXED GRID SIZE. We chose 20, feels accurate enough.
    public final static int gridRowNumber = 25;
    public final static String FIELD_COLOUR = "#ff905358";
    public final static String WALL_COLOUR = "#ff372a3b";
    public final static String BACKGROUND_COLOUR = "#ff372a3b";
    public final static int playDelay = 110;


    boolean firstOpen = true;    //To handle OnItemSelectedListener problem.. Awful, but works :/
    boolean spinnerModified = false; //To handle OnItem... when spinner is modified. Also bullshit, cba to sort

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        mainLayout.setBackgroundColor(Color.parseColor(BACKGROUND_COLOUR));

        joystickLayout = (RelativeLayout) findViewById(R.id.layout_joystick);
        gameGridLayout = (GridLayout) findViewById(R.id.layout_grid);

        menuLayout = (RelativeLayout) findViewById(R.id.menuLayout);
        menu_button = (ImageView) findViewById(R.id.menu_icon);

        resetButton = (Button) findViewById(R.id.resetButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        saveEditText = (EditText) findViewById(R.id.saveEditText);

        joystick = new Joystick(getApplicationContext(),joystickLayout,R.drawable.image_button);
        joystick.setMinDistance(20);
        joystick.setOffset(50);
        joystick.setLayoutAlpha(255);
        joystick.setStickAlpha(255);
        joystick.setStickSize(100,100);
        joystick.setLayoutSize(270,270);

        gameGrid = new GameGrid(getApplicationContext(),gameGridLayout,gridRowNumber,gridColumnNumber,984,1295);   // Creates game Grid
        fileManager= new FileManager(gameGrid.getBlocks(),getApplicationContext());                  // Create joystick

        final Player player = new Player(gameGrid.getBlocks().get(10).get(10),getApplicationContext(),gameGrid.getBlocks(),R.drawable.star_shape);

        playerTimerTask = new TimerTask(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {  // Timer can't access views that aren't from its own thread!!
                    @Override
                    public void run(){
                        player.move(playerDirection);
                    }
                });
            }
        };
        playerTimer = new Timer();
        playerTimer.scheduleAtFixedRate(playerTimerTask, 0, playDelay);



        addItemsToSpinner(fileManager.getFileNames());

        /********************** LISTENERS FROM HERE ON ******************/

        joystickLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                joystick.drawStick(event);
                playerDirection = joystick.getDirection();

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(joystick.getDirection() != playerDirection) { //If direction has changed
                        playerDirection = joystick.getDirection();
                        playerTimer.cancel();
                        playerTimer = new Timer();
                    }
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    playerTimer.cancel();
                    playerDirection = 0;    // Player stops moving
                }

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    playerDirection = joystick.getDirection();
                    playerTimer = new Timer();
                }

                return true;
            }
        });

        menu_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showMenu();
                }
                return true;
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameGrid.resetGrid();
                showMenu();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileManager.saveFile(saveEditText.getText().toString());
                hideSaveEditTextKeyBoard();
                addItemsToSpinner(fileManager.getFileNames());
                saveEditText.setText("");
                spinnerModified = true;
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fileManager.deleteFile(saveEditText.getText().toString())) {
                    hideSaveEditTextKeyBoard();
                    addItemsToSpinner(fileManager.getFileNames());
                    spinnerModified = true;
                    saveEditText.setText("");
                }
            }
        });

        fileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(firstOpen)
                    firstOpen = false;
                else if(spinnerModified)
                    spinnerModified = false;
                else {
                    if(fileManager.readFile((String) parent.getItemAtPosition(position)))
                        showMenu();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void showMenu(){
        if (menuLayout.getVisibility() == View.VISIBLE)
            menuLayout.setVisibility(View.GONE);
        else {
            menuLayout.setVisibility(View.VISIBLE);
            menu_button.bringToFront();
        }
    }

    private void hideSaveEditTextKeyBoard(){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(saveEditText.getWindowToken(), 0);
    }

    private void addItemsToSpinner(List<String> fileNames){
        fileSpinner = (Spinner) findViewById(R.id.fileSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_textview,fileNames);
        adapter.setDropDownViewResource(R.layout.spinner_textview);

        fileSpinner.setAdapter(adapter);
    }
}
