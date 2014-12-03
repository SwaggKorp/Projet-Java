package com.henryturp.joystickapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Henry on 28/11/2014.
 */
public class Block extends View {

    private Context mContext;
    private Paint paint;
    private int backgroundColour = Color.parseColor(MainActivity.FIELD_COLOUR);
    private int wallColour = Color.parseColor(MainActivity.WALL_COLOUR);
    private int side;
    private ViewGroup.LayoutParams params;

    public static final int STATE_FIELD = 0;
    public static final int STATE_WALL = 1;

    private int blockState = STATE_FIELD;

    public Block(Context context, int side){
        super(context);

        paint = new Paint();
        mContext = context;
        this.side = side;

        params = new ViewGroup.LayoutParams(side,side);
        setLayoutParams(params);

//        setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                    setState(1 - blockState);
//                    invalidate();
//                }
//                return true;
//            }
//        });

    }

    @Override
    public void onDraw(Canvas canvas){
        if(blockState==0)
            paint.setColor(backgroundColour);
        else paint.setColor(wallColour);
        canvas.drawRect(1,1,side-1,side-1,paint);
    }

    public void setState(int state){
        blockState = state;
        invalidate();
    }
    public int getState(){
        return blockState;
    }

}
