package com.henryturp.joystickapp;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;

/**
 * Created by Henry on 28/11/2014.
 * Deals with organizing the blocks.
 * Also deals with various listeners so that the user can edit the maze.
 */
public class GameGrid {
    private Context mContext;
    private GameHandler gameHandler;
    private GridLayout mLayout;
    private ArrayList<ArrayList<Block>> blocks;

    private boolean editState = false;  // Whether or not the grid is editable.

    private int columns, rows;
    private int mWidth, mHeight;

    private int blockSize;

    private boolean touchDown = false;
    private Block lastBlock;

    public GameGrid(Context context,GameHandler gameHandler, GridLayout layout,int row, int col, int width, int height){
        mContext = context;
        this.gameHandler = gameHandler;
        mLayout = layout;

        mHeight = height;
        mWidth = width;

        rows = row;
        columns = col;
        setupGrid();

        mLayout.setOnTouchListener(new View.OnTouchListener() {    // Listener that handles the users finger sliging across the screen.
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(editState) {  // If grid is not in 'edit mode' this basically does nothing.
                    for (int i = 0; i < mLayout.getChildCount(); i++) {
                        Block child = (Block) mLayout.getChildAt(i);
                        Rect outRect = new Rect(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
                        if (outRect.contains((int) event.getX(), (int) event.getY())) {    // This checks that the user's finger hass changed blocks or not.
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                touchDown = true;
                                lastBlock = child;
                                child.setState(1 - child.getState());
                                child.invalidate();
                            }
                            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                                if (child != lastBlock) {
                                    lastBlock = child;
                                    child.setState(1 - child.getState());
                                    child.invalidate();
                                }
                            }
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                touchDown = false;
                            }
                        }
                    }
                }
                return true;
            }
        });

    }

    private void setupGrid(){ // Draws the grid for the first time.
        blockSize = mWidth / columns;

        blocks = new ArrayList<ArrayList<Block>>();

        mLayout.setColumnCount(columns);
        mLayout.setRowCount(rows);

        for(int i = 0; i<rows; i++){                           // Adds the blocks.
            ArrayList<Block> blockRow = new ArrayList<Block>();
            for(int j = 0; j<columns; j++){
                Block temp = new Block(mContext,gameHandler, blockSize,i,j);
                blockRow.add(temp);
                mLayout.addView(temp);
            }
            blocks.add(blockRow);
        }
    }
    public void resetGrid(){   // Resets all blocks to field state.
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<columns; j++){
                if(blocks.get(i).get(j).getState() == Block.STATE_WALL)
                    blocks.get(i).get(j).setState(Block.STATE_FIELD);
            }
        }
    }
    public ArrayList<ArrayList<Block>> getBlocks(){
        return blocks;
    }
    public ArrayList<Block> getNeighbours(Block block){  //
        ArrayList<Block> neighbours = new ArrayList<Block>();
        int j = block.getxPos();
        int i = block.getyPos();

        if(j > 0)
            if(blocks.get(i).get(j-1).getState() == Block.STATE_FIELD)
                neighbours.add(blocks.get(i).get(j-1));

        if(i > 0)
            if(blocks.get(i-1).get(j).getState() == Block.STATE_FIELD)
                neighbours.add(blocks.get(i-1).get(j));

        if(i < MainActivity.gridRowNumber-1)
            if(blocks.get(i+1).get(j).getState() == Block.STATE_FIELD)
                neighbours.add(blocks.get(i+1).get(j));

        if(j < MainActivity.gridColumnNumber-1)
            if(blocks.get(i).get(j+1).getState() == Block.STATE_FIELD)
                neighbours.add(blocks.get(i).get(j+1));

        return neighbours;
    }

    public void setEditState(boolean state){editState = state;}
    public boolean getEditState(){return editState;}
}
