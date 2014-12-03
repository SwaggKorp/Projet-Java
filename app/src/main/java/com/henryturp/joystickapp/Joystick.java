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

    private boolean touchDown = false;

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
    public void drawStick(MotionEvent event){
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

    public float getDistance(){
        return distance;
    }
    public int getxPos(){
        return xPos;
    }
    public int getyPos(){
        return yPos;
    }
    public float getAngle(){
        return angle;
    }

    public int getDirection(){
        if(touchDown && distance >= minDistance){
            float quarterPi = (float) Math.PI/4;
            if(angle < quarterPi || angle > 7*quarterPi)
                return 1;
            else if(angle < 3*quarterPi && angle > quarterPi)
                return 2;
            else if(angle < 5*quarterPi && angle > 3*quarterPi)
                return 3;
            else if(angle < 7*quarterPi && angle > 5*quarterPi)
                return 4;
        }
        return 0;
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
    }
}
