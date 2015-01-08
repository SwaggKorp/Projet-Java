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

    private Context mContext; // Needed to instantiate View...
    private GameHandler gameHandler;
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

    public Block(Context context,GameHandler gameHandler, int side, int i, int j){
        super(context);
        this.gameHandler = gameHandler;

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
        if(blockState==STATE_FIELD)
            paint.setColor(backgroundColour);
        else
            paint.setColor(wallColour);

        canvas.drawRect(1, 1, side - 1, side - 1, paint);

        if(character != null) {
            canvas.drawBitmap(character.getSkin(), 0, 0, paint);

        }
    }

    /* CHARACTER RELATED FUNCTIONS*/
    public void setCharacter(Character charact) {         // Doesn't check if there is already a character!!
        if (!hasCharacter) {
            character = charact;
            hasCharacter = true;
            invalidate();
        }
        else if(charact.getStatus() == character.getStatus()){  // Two enemies collide.
            gameHandler.killEnemy((Enemy)charact,true);
            gameHandler.killEnemy((Enemy)character,true);        }
        else{  // Enemy and player collide
            gameHandler.endGame();
        }
    }


    public void removeCharacter(){
        character = null;
        hasCharacter = false;
        invalidate();
    }


    public boolean equals(Block block) {
        return (block.getxPos()==xPos && block.getyPos()==yPos);
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
