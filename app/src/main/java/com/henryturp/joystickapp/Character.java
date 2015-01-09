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

    public void move(Direction direction){    // Can't be arsed with ifs. This is hefty but it works.
        switch(direction){
            case rightUp:
                moveRight(Direction.rightUp);
                break;
            case rightDown:
                moveRight(Direction.rightDown);
                break;
            case leftUp:
                moveLeft(Direction.leftUp);
                break;
            case leftDown:
                moveLeft(Direction.leftDown);
                break;
            case upRight:
                moveUp(Direction.upRight);
                break;
            case upLeft:
                moveUp(Direction.upLeft);
                break;
            case downLeft:
                moveDown(Direction.downLeft);
                break;
            case downRight:
                moveDown(Direction.downRight);
                break;
            default:
                break;
        }
    }

    protected void moveUp(Direction direction){ // direction can only be upRight, upLeft or none.
        int xPos = characterBlock.getxPos();
        int yPos = characterBlock.getyPos();

        if(yPos != 0) {
            Block newBlock = blocks.get(yPos-1).get(xPos);
            if(newBlock.getState() != Block.STATE_WALL) { // Target block isn't a wall.
                newBlock.setCharacter(this);
                characterBlock.removeCharacter();
                characterBlock = newBlock;
            }
            else { // If it is, glide left or right
                if(direction == Direction.upLeft)
                    moveLeft(Direction.none);
                if(direction == Direction.upRight)
                    moveRight(Direction.none);
            }
        }
        else { // If it is, glide left or right
            if(direction == Direction.upLeft)
                moveLeft(Direction.none);
            if(direction == Direction.upRight)
                moveRight(Direction.none);
        }
    }
    protected void moveLeft(Direction direction){
        int xPos = characterBlock.getxPos();
        int yPos = characterBlock.getyPos();

        if(xPos != 0) {
            Block newBlock = blocks.get(yPos).get(xPos-1);
            if(newBlock.getState() != Block.STATE_WALL) {
                newBlock.setCharacter(this);
                characterBlock.removeCharacter();
                characterBlock = newBlock;
            }
            else { // If it is, glide up or down
                if(direction == Direction.leftUp)
                    moveUp(Direction.none);
                if(direction == Direction.leftDown)
                    moveDown(Direction.none);
            }
        }
        else { // If it is, glide up or down
            if(direction == Direction.leftUp)
                moveUp(Direction.none);
            if(direction == Direction.leftDown)
                moveDown(Direction.none);
        }
    }
    protected void moveRight(Direction direction){
        int xPos = characterBlock.getxPos();
        int yPos = characterBlock.getyPos();

        if(xPos != MainActivity.gridColumnNumber-1) {
            Block newBlock = blocks.get(yPos).get(xPos+1);
            if(newBlock.getState() != Block.STATE_WALL) {
                newBlock.setCharacter(this);
                characterBlock.removeCharacter();
                characterBlock = newBlock;
            }
            else { // If it is, glide up or down
                if(direction == Direction.rightUp)
                    moveUp(Direction.none);
                if(direction == Direction.rightDown)
                    moveDown(Direction.none);
            }
        }
        else { // If it is, glide up or down
            if(direction == Direction.rightUp)
                moveUp(Direction.none);
            if(direction == Direction.rightDown)
                moveDown(Direction.none);
        }
    }
    protected void moveDown(Direction direction){
        int xPos = characterBlock.getxPos();
        int yPos = characterBlock.getyPos();

        if(yPos != MainActivity.gridRowNumber-1) {
            Block newBlock = blocks.get(yPos+1).get(xPos);
            if(newBlock.getState() != Block.STATE_WALL) {
                newBlock.setCharacter(this);
                characterBlock.removeCharacter();
                characterBlock = newBlock;
            }
            else { // If it is, glide left or right
                if(direction == Direction.downLeft)
                    moveLeft(Direction.none);
                if(direction == Direction.downRight)
                    moveRight(Direction.none);
            }
        }
        else { // If it is, glide left or right
            if(direction == Direction.downLeft)
                moveLeft(Direction.none);
            if(direction == Direction.downRight)
                moveRight(Direction.none);
        }
    }

    public Block getCharacterBlock(){
        return characterBlock;
    }

    public boolean getStatus(){ return enemy; }

}
