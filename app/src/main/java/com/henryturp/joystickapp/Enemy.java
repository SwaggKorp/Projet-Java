package com.henryturp.joystickapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Created by Henry on 08/12/2014.
 */
public class Enemy extends Character{
    private Context mContext;
    private GameGrid gameGrid;  // No choice for the mo -> personal finder
    private Bitmap skin;
    private ArrayList<Direction> path; // Create Pathfinder class! cf Arnaud's code
    private PathFinder pathfinder;    // Personal pathfinder for the mo, cf Arnaud's optimization
    private Direction currentDirection = Direction.none;
    private Player target;

    public Enemy(Block source, Player target, Context context,GameGrid gameGrid, int playerSkinId){
        super(source,true,gameGrid.getBlocks());
        mContext = context;
        this.target = target;
        pathfinder = new PathFinder(gameGrid);
        this.gameGrid = gameGrid;

        skin = BitmapFactory.decodeResource(mContext.getResources(), playerSkinId);
        skin = Bitmap.createScaledBitmap(skin,source.getSide(),source.getSide(),false);

        path = new ArrayList<Direction>();

        calculatePath();
    }




    public void move(){
        move(currentDirection);
        currentDirection = getNextDirection();

        if(currentDirection == Direction.none){
            calculatePath();
            currentDirection = getNextDirection();
        }
    }

    public void calculatePath(){
        ArrayList<Block> blocksPath = pathfinder.shortestPath(this.characterBlock, target.getCharacterBlock());
        path = directions(blocksPath);  // check pathfinder and stuff by colouring.
    }

    public Bitmap getSkin(){
        return skin;
    }

    private Direction getNextDirection(){
        Direction temp = Direction.none;
        if(path.size() != 0) {
            temp = path.get(0);
            path.remove(0);
        }
        return temp;
    }
    private ArrayList<Direction> directions(ArrayList<Block> pathBlocks){  // turn path arraylist into list of directional keycodes.
        ArrayList<Direction> temp = new ArrayList<Direction>();
        int len = pathBlocks.size();

        for(int i = len-1; i>0; i--){    // path[0] = target && path[len-1] = root's son
            temp.add(calculateDirection(pathBlocks.get(i), pathBlocks.get(i-1)));
        }
        return temp;
    }
    private Direction calculateDirection(Block block, Block nextBlock){
        int xPos = nextBlock.getxPos();
        int yPos = nextBlock.getyPos();

        int nowXPos = block.getxPos();
        int nowYPos = block.getyPos();

        if(xPos == nowXPos + 1 && yPos == nowYPos)
            return Direction.right;

        if(xPos == nowXPos && yPos == nowYPos + 1)
            return Direction.down;

        if(xPos == nowXPos - 1 && yPos == nowYPos)
            return Direction.left;

        if(xPos == nowXPos && yPos == nowYPos - 1)
            return Direction.up;

        return Direction.none;
    }
}
