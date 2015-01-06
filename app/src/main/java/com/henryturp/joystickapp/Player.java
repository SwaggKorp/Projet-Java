package com.henryturp.joystickapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Created by Henry on 03/12/2014.
 */
public class Player extends Character {
    private Context mContext;
    private Bitmap skin;

    public Player(Block source, Context context,ArrayList<ArrayList<Block>> blocks, int playerSkinId){
        super(source,false,blocks);
        mContext = context;

        skin = BitmapFactory.decodeResource(mContext.getResources(),playerSkinId);
        skin = Bitmap.createScaledBitmap(skin,source.getSide(),source.getSide(),false);
    }

    @Override
    public void kill() {

    }

    public Bitmap getSkin(){
        return skin;
    }

}
