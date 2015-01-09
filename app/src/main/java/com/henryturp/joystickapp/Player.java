package com.henryturp.joystickapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Created by Henry on 03/12/2014.
 * Abstract class from which will derive player and enemy.
 */
public class Player extends Character {  // Superclass is needed to be able to list together player and enemies.
    private Context mContext;
    private Bitmap skin;

    public Player(Block source, Context context,ArrayList<ArrayList<Block>> blocks, int playerSkinId){
        super(source,false,blocks);
        mContext = context;

        skin = BitmapFactory.decodeResource(mContext.getResources(),playerSkinId);
        skin = Bitmap.createScaledBitmap(skin,source.getSide(),source.getSide(),false);
    }


    public Bitmap getSkin(){
        return skin;
    }

}
