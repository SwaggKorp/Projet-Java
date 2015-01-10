package com.henryturp.joystickapp;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Henry on 06/01/2015.
 *
 * This class handles the enemies and collisions. It triggers the death of two enemies that collide. Also contains score and current game state.
 */
public class GameHandler {
    private ArrayList<Enemy> enemies;
    private boolean playState = false;  // False = not running.
    private long score = 0;

    public GameHandler(ArrayList<Enemy> enemyList){
        enemies = enemyList;
    }

    public void killEnemy(Enemy enemy, boolean modifyScore){   // Once this method is called enemy IS NULL POINTER!
        enemies.remove(enemy);
        enemy.getCharacterBlock().removeCharacter();
        enemy = null;    // The memory instance of enemy should be collected by garbage collector at some point.
        if(modifyScore)  // bonus points for making enemies kill each other!
            addScore(MainActivity.enemyDeathBonus);
    }

    public void endGame(){
        int len = enemies.size();
        for(int i = 0; i<len; i++)   // Destroys each enemy
            killEnemy(enemies.get(0),false);
        playState = false;

    }

    public void addEnemy(Enemy enemy){
        enemies.add(enemy);
    }

    public boolean getPlayState(){ return playState; }
    public void setPlayState(boolean state){ playState = state; }

    public void addScore(long score){
        this.score += score;
    }
    public void setScore(long score){
        this.score = score;
    }
    public long getScore(){
        return score;
    }
}
