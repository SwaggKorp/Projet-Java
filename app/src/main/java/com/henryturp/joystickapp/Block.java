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
    private int backgroundColour = Color.rgb(137,220,115);
    private int wallColour = Color.rgb(136,102,57);
    private int side;

    private int blockState = 0;

    public Block(Context context, int side){
        super(context);

        paint = new Paint();
        mContext = context;
        this.side = side;

        setLayoutParams(new ViewGroup.LayoutParams(side,side));

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
    }
    public int getState(){
        return blockState;
    }
}
