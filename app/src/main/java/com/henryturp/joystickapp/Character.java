package com.henryturp.joystickapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Created by Henry on 03/12/2014.
 * Abstract class that designates a character, player or enemy.
 */
public abstract class Character {
    private boolean enemy;
    protected Block characterBlock;
    protected ArrayList<ArrayList<Block>> blocks;

    public Character(Block source, boolean isEnemy, ArrayList<ArrayList<Block>> blocks) {
        characterBlock = source;
        enemy = isEnemy;
        this.blocks = blocks;
        source.setCharacter(this);
    }

    public abstract Bitmap getSkin();

    public void move(int keycode){
        switch(keycode){
            case 1:
                moveRight();
                break;
            case 2:
                moveDown();
                break;
            case 3:
                moveLeft();
                break;
            case 4:
                moveUp();
                break;
        }
    }

    protected void moveUp(){
        int xPos = characterBlock.getxPos();
        int yPos = characterBlock.getyPos();

        if(yPos != 0) {
            Block newBlock = blocks.get(yPos-1).get(xPos);
            if(newBlock.getState() != Block.STATE_WALL) {
                newBlock.setCharacter(this);
                characterBlock.removeCharacter();
                characterBlock = newBlock;
            }
        }
    }
    protected void moveLeft(){
        int xPos = characterBlock.getxPos();
        int yPos = characterBlock.getyPos();

        if(xPos != 0) {
            Block newBlock = blocks.get(yPos).get(xPos-1);
            if(newBlock.getState() != Block.STATE_WALL) {
                newBlock.setCharacter(this);
                characterBlock.removeCharacter();
                characterBlock = newBlock;
            }
        }
    }
    protected void moveRight(){
        int xPos = characterBlock.getxPos();
        int yPos = characterBlock.getyPos();

        if(xPos != MainActivity.gridColumnNumber-1) {
            Block newBlock = blocks.get(yPos).get(xPos+1);
            if(newBlock.getState() != Block.STATE_WALL) {
                newBlock.setCharacter(this);
                characterBlock.removeCharacter();
                characterBlock = newBlock;
            }
        }
    }
    protected void moveDown(){
        int xPos = characterBlock.getxPos();
        int yPos = characterBlock.getyPos();

        if(yPos != MainActivity.gridRowNumber-1) {
            Block newBlock = blocks.get(yPos+1).get(xPos);
            if(newBlock.getState() != Block.STATE_WALL) {
                newBlock.setCharacter(this);
                characterBlock.removeCharacter();
                characterBlock = newBlock;
            }
    }
    }

}
