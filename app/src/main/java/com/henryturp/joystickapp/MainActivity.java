package com.henryturp.joystickapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/*
TO DO:
Randomize enemy spawn location?
Load default map?
Allow 'creator mode' or at least hind player between games?
 */

public class MainActivity extends Activity {

    private RelativeLayout joystickLayout;
    private RelativeLayout menuLayout;
    private GridLayout gameGridLayout;
    private RelativeLayout mainLayout;
    private ImageView menu_button;
    private Button resetButton, saveButton, deleteButton, startButton;
    private EditText saveEditText;
    private TextView scoreTextView;
    private Spinner fileSpinner;

    private GameGrid gameGrid;
    private FileManager fileManager;
    private Joystick joystick;
    private GameHandler gameHandler;
    private Player player;

    private Timer playerTimer;
    private TimerTask playerTimerTask;
    private Timer enemyTimer;
    private TimerTask enemyTimerTask;
    private Timer spawnTimer;
    private TimerTask spawnTimerTask;
    private Timer scoreTimer;
    private TimerTask scoreTimerTask;

    private Direction playerDirection = Direction.none;   // Is used for joystick listener. Avoids useless Timer restarts.

    private final ArrayList<Enemy> enemies = new ArrayList<>();

    public final static int gridColumnNumber = 20; // FIXED GRID SIZE. We chose 20x25.
    public final static int gridRowNumber = 25;
    public final static String FIELD_COLOUR = "#ffffce57";
    public final static String WALL_COLOUR = "#ff393939";
    public final static String BACKGROUND_COLOUR = "#ffffffff";
    public final static String VISITED_COLOUR = "#ff0000bb";
    public final static int playDelay = 100;
    public final static int enemyDelay = 120;
    public final static int spawnRate = 5000;
    public final static int scoreUpdateDelay = 100;
    public final static long scoreSurvivalBonus = 10;   // Amount of points constantly gained by not dying.
    public final static long enemyDeathBonus = 500;    // When an enemy dies. Bear in mind that two enemies die at a time!


    boolean firstOpen = true;    //To handle OnItemSelectedListener problem.. Awful, but works :/
    boolean spinnerModified = false; //To handle OnItem... when spinner is modified. Also bullshit, cba to sort
    boolean firstPlay = true;        //Used to schedule timers the first time. There's probably a neater way to do it, but this works.

    // Enemy pathfinder needs to be finished
    // !! Optimization for multiple enemies

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
        startButton = (Button) findViewById(R.id.startButton);
        scoreTextView = (TextView) findViewById(R.id.scoreView);
        saveEditText = (EditText) findViewById(R.id.saveEditText);

        joystick = new Joystick(getApplicationContext(),joystickLayout,R.drawable.image_button);  // Create joystick
        joystick.setMinDistance(20);
        joystick.setOffset(50);
        joystick.setLayoutAlpha(255);
        joystick.setStickAlpha(255);
        joystick.setStickSize(100,100);
        joystick.setLayoutSize(270,270);

        gameHandler = new GameHandler(enemies);
        gameGrid = new GameGrid(getApplicationContext(),gameHandler,gameGridLayout,gridRowNumber,gridColumnNumber,984,1295);   // Creates game Grid
        fileManager = new FileManager(gameGrid.getBlocks(),getApplicationContext());
        addItemsToSpinner(fileManager.getFileNames());
        player = new Player(gameGrid.getBlocks().get(10).get(12),getApplicationContext(),gameGrid.getBlocks(),R.drawable.star_shape);

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
        enemyTimerTask = new TimerTask(){
            @Override
            public void run() {        // Deals with all the enemies' movements.
                runOnUiThread(new Runnable() {  // Timer can't access views that aren't from its own thread!!
                    @Override
                    public void run(){
                        int len = enemies.size();
                        for(int i = 0; i<len; i++)    // Moves each existing enemy
                        try {                     // Throws OutOfBoundsException if there is a collision because one enemy destroys the next.
                            enemies.get(i).move();
                        } catch (Exception e) {}

                        if(!gameHandler.getPlayState()){    // If a player/enemy collision has happened...
                            gameOver();
                        }
                    }
                });
            }
        };
        spawnTimerTask = new TimerTask(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        addEnemy(1,1);
                    }
                });
            }
        };
        scoreTimerTask = new TimerTask(){
            @Override
            public void run() {   // Deals with score evolution.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gameHandler.addScore(scoreSurvivalBonus);
                        updateScore();
                    }
                });
            }
        };
        playerTimer = new Timer();
        playerTimer.scheduleAtFixedRate(playerTimerTask, 0, playDelay);

        fileManager.readFile("lol"); // load a map at the start

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
                    playerDirection = Direction.none;    // Player stops moving
                }

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    playerDirection = joystick.getDirection();
                    playerTimer = new Timer();
                }

                return true;
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();     // Hides menu (because it's already visible.)
                startGame();
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
        else if(!gameHandler.getPlayState()){    // Not playing
            menuLayout.setVisibility(View.VISIBLE);
            menu_button.bringToFront();
        }
    }   // Show or hide menu.
    private void hideSaveEditTextKeyBoard(){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(saveEditText.getWindowToken(), 0);
    } // Hides keyboard.

    private void addItemsToSpinner(List<String> fileNames){
        fileSpinner = (Spinner) findViewById(R.id.fileSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_textview,fileNames);
        adapter.setDropDownViewResource(R.layout.spinner_textview);

        fileSpinner.setAdapter(adapter);
    } // Add existing file names to spinner.

    private void addEnemy(int x, int y){
        Enemy enemy = new Enemy(gameGrid.getBlocks().get(y).get(x),player,getApplicationContext(),gameGrid,R.drawable.diamond_shape);
        gameHandler.addEnemy(enemy);
    } // Adds an enemy.

    private void startGame(){
        gameHandler.setScore(0);  // Reset score to 0.
        updateScore();            // Updates score value in the text view
        gameHandler.setPlayState(true); // Game is now afoot.

        TimerTask enemyTask = new TimerTask(){
            @Override
            public void run() {        // Deals with all the enemies' movements.
                runOnUiThread(new Runnable() {  // Timer can't access views that aren't from its own thread!!
                    @Override
                    public void run(){
                        int len = enemies.size();
                        for(int i = 0; i<len; i++)    // Moves each existing enemy
                            try {                     // Throws OutOfBoundsException if there is a collision because one enemy destroys the next.
                                enemies.get(i).move();
                            } catch (Exception e) {}

                        if(!gameHandler.getPlayState()){    // If a player/enemy collision has happened...
                            gameOver();
                        }
                    }
                });
            }
        };
        TimerTask spawnTask = new TimerTask(){
            @Override
            public void run() {                       // Deals with spawning enemies at a regular interval.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        addEnemy(1,1);
                    }
                });
            }
        };

        TimerTask scoreTask = new TimerTask(){
            @Override
            public void run() {   // Deals with score evolution.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gameHandler.addScore(scoreSurvivalBonus);
                        updateScore();
                    }
                });
            }
        };

        enemyTimer = new Timer();
        spawnTimer = new Timer();
        scoreTimer = new Timer();

        enemyTimer.scheduleAtFixedRate(enemyTask, 0, enemyDelay);
        spawnTimer.scheduleAtFixedRate(spawnTask, 0, spawnRate);
        scoreTimer.scheduleAtFixedRate(scoreTask,0,scoreUpdateDelay);

    }
    public void updateScore(){
        scoreTextView.setText("Score : " + gameHandler.getScore());
    }

    public void gameOver(){
        updateScore();

        enemyTimer.cancel();
        spawnTimer.cancel();
        scoreTimer.cancel();

        // GameOverlayout show

    }

}
