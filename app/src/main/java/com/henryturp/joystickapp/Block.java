package com.henryturp.joystickapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Henry on 28/11/2014.
 * Represents one block on the grid.
 */
public class Block extends View {

    private Context mContext;
    private Paint paint;
    private int backgroundColour = Color.parseColor(MainActivity.FIELD_COLOUR);
    private int wallColour = Color.parseColor(MainActivity.WALL_COLOUR);
    private int side;
    private ViewGroup.LayoutParams params;
    private int xPos = 0, yPos = 0;

    private Character character = null;
    private boolean hasCharacter = false;

    public static final int STATE_FIELD = 0;
    public static final int STATE_WALL = 1;

    private int blockState = STATE_FIELD;

    public Block(Context context, int side, int i, int j){
        super(context);

        paint = new Paint();
        mContext = context;
        this.side = side;

        xPos = j;
        yPos = i;

        params = new ViewGroup.LayoutParams(side,side);
        setLayoutParams(params);

    }

    @Override
    public void onDraw(Canvas canvas){
        if(blockState==0)
            paint.setColor(backgroundColour);
        else
            paint.setColor(wallColour);

        canvas.drawRect(1, 1, side - 1, side - 1, paint);

        if(character != null) {
            canvas.drawBitmap(character.getSkin(), 0, 0, paint);

        }
    }

    /* CHARACTER RELATED FUNCTIONS*/
    public void setCharacter(Character charact){
        character = charact;
        hasCharacter = true;
        invalidate();
    }
    public void removeCharacter(){
        character = null;
        hasCharacter = false;
        invalidate();
    }


    /* GETTERS AND SETTERS */
    public void setState(int state){
        blockState = state;
        invalidate();
    }
    public int getState(){
        return blockState;
    }
    public int getxPos(){
        return xPos;
    }
    public int getyPos(){
        return yPos;
    }
    public int getSide(){
        return side;
    }

}
