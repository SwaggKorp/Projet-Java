package com.henryturp.joystickapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Henry on 27/11/2014.
 * Class that deals with the joystick.
 */
public class Joystick {
    private int xPos = 0, yPos = 0, minDistance = 0;
    private float angle = 0, distance = 0;
    private int offset = 0;
    private int stickWidth, stickHeight;

    private Bitmap stick;
    private Paint paint;
    private DrawCanvas draw;

    private Context mContext;
    private ViewGroup mLayout;
    private ViewGroup.LayoutParams params;

    private boolean touchDown = false;  // Determines whether or not the finger is currently in use vis-à-vis joystick.

    public Joystick(Context Context,ViewGroup Layout, int joystickId){
        mContext = Context;
        mLayout = Layout;
        params = mLayout.getLayoutParams();

        stick = BitmapFactory.decodeResource(mContext.getResources(),joystickId);
        paint = new Paint();
        draw = new DrawCanvas(mContext);

        stickWidth = stick.getWidth();
        stickHeight = stick.getHeight();

    }
    public void drawStick(MotionEvent event){    // Draws the stick at the right position when user moves his finger.
        xPos = (int) (event.getX() - (params.width/2));
        yPos = (int) (event.getY() - (params.height/2));
        distance = (float) Math.sqrt((xPos*xPos) + (yPos*yPos));
        angle = (float) calculateAngle(xPos,yPos);

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(distance <= (params.width/2) - offset) {
                draw.position(event.getX(),event.getY());
                draw();
                touchDown = true;
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE && touchDown){
            if(distance <= (params.width/2) - offset) {
                draw.position(event.getX(),event.getY());
                draw();
            }
            else if (distance > (params.width/2) - offset){
                float x = (float) ((Math.cos(calculateAngle(xPos,yPos))) * ((params.width/2) - offset));
                float y = (float) ((Math.sin(calculateAngle(xPos,yPos))) * ((params.height/2) - offset));
                x += (float) ((params.width/2));
                y += (float) ((params.height/2)
                );
                draw.position(x,y);
                draw();
            }
            else{
                mLayout.removeView(draw);
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            touchDown = false;
            mLayout.removeView(draw);
        }

    }

    // Various setters so that I can reuse this in another game. Also allows me to
    // play with the look of the joystick.

    public void setMinDistance(int minDist){
        minDistance = minDist;
    }
    public void setOffset(int offset){
        this.offset = offset;
    }
    public void setStickAlpha(int alpha){
        paint.setAlpha(alpha);
    }
    public void setLayoutAlpha(int alpha){
        mLayout.getBackground().setAlpha(alpha);
    }
    public void setStickSize(int width, int height){
        stick = Bitmap.createScaledBitmap(stick,width,height,false);
        stickWidth = stick.getWidth();
        stickHeight = stick.getHeight();
    }
    public void setLayoutSize(int width, int height){
        params.width = width;
        params.height = height;
    }

    public Direction getDirection(){   // Returns a direction which is later used to move the player.
        if(touchDown && distance >= minDistance){
            float quarterPi = (float) Math.PI/4;
            if(angle < quarterPi)
                return Direction.rightDown;
            else if(angle < 2*quarterPi && angle >= quarterPi)
                return Direction.downRight;
            else if(angle < 3*quarterPi && angle >= 2*quarterPi)
                return Direction.downLeft;
            else if(angle < 4*quarterPi && angle >= 3*quarterPi)
                return Direction.leftDown;
            else if(angle < 5*quarterPi && angle >= 4*quarterPi)
                return Direction.leftUp;
            else if(angle < 6*quarterPi && angle >= 5*quarterPi)
                return Direction.upLeft;
            else if(angle < 7*quarterPi && angle >= 6*quarterPi)
                return Direction.upRight;
            else if(angle < 8*quarterPi && angle >= 6*quarterPi)
                return Direction.rightUp;
        }
        return Direction.none;
    }

    private double calculateAngle(float x, float y){   // Between 0 and 2Pi clockwise!
        if(x > 0 && y >= 0)
            return (Math.atan(y/x));
        else if(x<0 && y>=0)
            return (Math.atan(y/x) + Math.PI);
        else if(x>0 && y < 0)
            return (Math.atan(y/x) + 2*Math.PI);
        else if(x<0 && y <= 0)
            return (Math.atan(y/x) + Math.PI);
        else if(x == 0 && y > 0)
            return Math.PI/2;
        else if(x == 0 && y<0)
            return (3*Math.PI)/2;
        return 0;

    }

    private void draw(){
        try{
            mLayout.removeView(draw);
        } catch(Exception e){ }
            mLayout.addView(draw);
    }

    private class DrawCanvas extends View {
        float x, y;

        private DrawCanvas(Context mContext) {
            super(mContext);
        }

        @Override
        public void onDraw(Canvas canvas) {
            canvas.drawBitmap(stick, x, y, paint);
        }

        private void position(float xpos, float ypos) {
            x = xpos - (stickWidth / 2);
            y = ypos - (stickHeight / 2);
        }
    } // small inner class used to make drawing the stick in the appropriate location easier.
}
