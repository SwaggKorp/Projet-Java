package com.henryturp.joystickapp;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by Henry on 30/11/2014.
 * Class for dealing with/saving and reopening created mazes.
 */
public class FileManager {
    private ArrayList<ArrayList<Block>> blocks;
    private ArrayList<String> fileNames;
    private Context mContext;

    public FileManager(ArrayList<ArrayList<Block>> blocks, Context context){
        mContext = context;
        fileNames = new ArrayList<String>();
        this.blocks = blocks;

        File[] files = mContext.getFilesDir().listFiles();  // Gets already saved files.
        for(File file : files){
            fileNames.add(file.getName());
        }
    }

    public void saveFile(String fileTitle){
        String fileName = fileTitle + ".maze";
        if(!fileNames.contains(fileName)) {
            byte[] data = gridData();
            try {
                FileOutputStream foStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
                foStream.write(data,0,data.length);
                foStream.close();
                fileNames.add(fileName);
                Toast.makeText(mContext, "File Saved", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(mContext, "Something went wrong! :(", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        else Toast.makeText(mContext, "File name already used!", Toast.LENGTH_SHORT).show();
    }

    public boolean readFile(String fileTitle){         //return true if success.
        String fileName = fileTitle + ".maze";
        if(fileNames.contains(fileName)) {
            int size = 0;
            byte[] data = null;

            try {
                File file = new File(mContext.getFilesDir(),fileName);
                if(file.exists())
                    size = (int) file.length();

                if(size != 0){
                    FileInputStream fiStream = mContext.openFileInput(fileName);
                    data = new byte[size];
                    fiStream.read(data,0,size);
                    fiStream.close();
                }
                getGridFromData(data);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(mContext, "Reading failed", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else Toast.makeText(mContext, "File doesn't exist!", Toast.LENGTH_SHORT).show();
        return false;
    }

    public boolean deleteFile(String fileTitle){          // return true if success.
        String fileName = fileTitle + ".maze";
        if(fileNames.contains(fileName)){
            mContext.deleteFile(fileName);
            fileNames.remove(fileName);
            Toast.makeText(mContext, "File deleted.", Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(mContext, "File doesn't exist!", Toast.LENGTH_SHORT).show();
        return false;
    }

    public byte[] gridData(){

        int numColumns = blocks.get(0).size();
        int numRows = blocks.size();
        int len = numRows*(numColumns + 1);
        byte[] data = new byte[len];
        int q = 0;
        int NEXT_LINE = 2;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                data[q] = (byte) blocks.get(i).get(j).getState();
                q++;
            }
            data[q] = (byte) NEXT_LINE;
            q++;
        }
        return data;
    }

    private void getGridFromData(byte[] data){
        int i = 0, j = 0;
        int len = data.length;

        for (int q = 0; q < len; q++) {
            int current = data[q];
            if (current == 0) {
                blocks.get(i).get(j).setState(Block.STATE_FIELD);
                j++;
            } else if (current == 1) {
                blocks.get(i).get(j).setState(Block.STATE_WALL);
                j++;
            } else if (current == 2) {
                i++;
                j = 0;
            }
        }
    }

    public ArrayList<String> getFileNames(){
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0; i<fileNames.size();i++){
            String temp = fileNames.get(i);
            list.add(temp.substring(0, temp.length() - 5));
        }
        return list;
    }
}
