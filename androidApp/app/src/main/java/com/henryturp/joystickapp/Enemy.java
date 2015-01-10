package com.henryturp.joystickapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Created by Henry on 08/12/2014.
 *
 * The enemy always goes to the end of the calculated path. This gives the player a tactical advantage as the enemy
 * won't always be going straight for him.
 *
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
        pathfinder = new PathFinder(gameGrid);    // Each enemy has it's own pathfinder. ---> Sort this out at the end!
        this.gameGrid = gameGrid;

        skin = BitmapFactory.decodeResource(mContext.getResources(), playerSkinId);
        skin = Bitmap.createScaledBitmap(skin,source.getSide(),source.getSide(),false);

        path = new ArrayList<Direction>();

        calculatePath(); // Path to player is calculated.
    }

    public void move(){              // This is called by the timer. After every move, the next direction is computed.
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
            return Direction.rightUp;              // Any right will do

        if(xPos == nowXPos && yPos == nowYPos + 1)
            return Direction.downRight;

        if(xPos == nowXPos - 1 && yPos == nowYPos)
            return Direction.leftUp;

        if(xPos == nowXPos && yPos == nowYPos - 1)
            return Direction.upRight;

        return Direction.none;
    }
}
