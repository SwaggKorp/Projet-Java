package com.henryturp.joystickapp;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Henry on 06/01/2015.
 *
 * This class handles the enemies and collisions. It triggers the death of two enemies that collide and Game Over if a player is killed.
 */
public class GameHandler {
    private ArrayList<Enemy> enemies;

    public GameHandler(ArrayList<Enemy> enemyList){
        enemies = enemyList;
    }

    public void killEnemy(Enemy enemy){   // Once this method is called enemy IS NULL POINTER!
        enemies.remove(enemy);
        enemy.getCharacterBlock().removeCharacter();
        enemy = null;    // The memory instance of enemy should be collected by garbage collector at some point.
    }

    public void endGame(){
        int len = enemies.size();
        for(int i = 0; i<len; i++)   // Destroys each enemy
            killEnemy(enemies.get(0));
    }

    public void addEnemy(Enemy enemy){
        enemies.add(enemy);
    }
}
