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
    private int mWidth, mHeight;

    private int blockSize;

    private boolean touchDown = false;
    private Block lastBlock;

    public GameGrid(Context context, GridLayout layout, int col, int width, int height){
        mContext = context;
        mLayout = layout;

        mHeight = height;
        mWidth = width;

        setupGrid(col);

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

    public void setupGrid(int col){    // MUST BE MADE PRIVATE when FileManager is recoded.
        columns = col;

        blockSize = mWidth / columns;
        rows = (mHeight / blockSize);

        blocks = new ArrayList<ArrayList<Block>>();

        mLayout.setColumnCount(columns);
        mLayout.setRowCount(rows);

        for(int i = 0; i<rows; i++){                           // Adds the blocks.
            ArrayList<Block> blockRow = new ArrayList<Block>();
            for(int j = 0; j<columns; j++){
                Block temp = new Block(mContext, blockSize);
                blockRow.add(temp);
                mLayout.addView(temp);
            }
            blocks.add(blockRow);
        }
    }

    public void resetGrid(){
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
}
