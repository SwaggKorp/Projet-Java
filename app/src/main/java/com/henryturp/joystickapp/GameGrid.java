package com.henryturp.joystickapp;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;

/**
 * Created by Henry on 28/11/2014.
 */
public class GameGrid {
    private Context mContext;
    private GridLayout mLayout;
    private ArrayList<ArrayList<Block>> blocks;

    private int columns, rows;

    private boolean touchDown = false;
    private Block lastBlock;

    public GameGrid(Context context, GridLayout layout, int row, int col, int width){
        mContext = context;
        mLayout = layout;
        columns = col;
        rows = row;

        blocks = new ArrayList<ArrayList<Block>>();

        int blockSize = width / columns;
        rows = columns;                     // Square Grid for the moment. Beware of Filemanager(square grid)

        mLayout.setColumnCount(columns);
        mLayout.setRowCount(rows);

        for(int i = 0; i<columns; i++){                           // Adds the blocks.
            ArrayList<Block> blockRow = new ArrayList<Block>();
            for(int j = 0; j<rows; j++){
                Block temp = new Block(mContext, blockSize);
                blockRow.add(temp);
                mLayout.addView(temp);
            }
            blocks.add(blockRow);
        }

        mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                for(int i =0; i< mLayout.getChildCount(); i++)
                {
                    Block child = (Block) mLayout.getChildAt(i);
                    Rect outRect = new Rect(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
                    if(outRect.contains((int)event.getX(), (int)event.getY()))
                    {
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            touchDown = true;
                            lastBlock = child;
                            child.setState(1-child.getState());
                            child.invalidate();
                        }
                        if(event.getAction() == MotionEvent.ACTION_MOVE){
                            if(child != lastBlock) {
                                lastBlock = child;
                                child.setState(1 - child.getState());
                                child.invalidate();
                            }
                        }
                        if(event.getAction() == MotionEvent.ACTION_UP){
                            touchDown = false;
                        }
                    }
                }
                return true;
            }
        });

    }

    public void resetGrid(){
        for(int i = 0; i<columns; i++){
            for(int j = 0; j<rows; j++){
                if(blocks.get(i).get(j).getState() == Block.STATE_WALL)
                    blocks.get(i).get(j).setState(Block.STATE_FIELD);
            }
        }
    }

    public ArrayList<ArrayList<Block>> getBlocks(){
        return blocks;
    }
}
