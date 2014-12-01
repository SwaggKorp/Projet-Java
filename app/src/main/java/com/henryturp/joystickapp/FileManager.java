package com.henryturp.joystickapp;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by Henry on 30/11/2014.
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
        int len = files.length;
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

    public void readFile(String fileTitle){
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
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(mContext, "Reading failed", Toast.LENGTH_SHORT).show();
            }
        }
        else Toast.makeText(mContext, "File doesn't exist!", Toast.LENGTH_SHORT).show();
    }

    public void deleteFile(String fileTitle){
        String fileName = fileTitle + ".maze";
        if(fileNames.contains(fileName)){
            mContext.deleteFile(fileName);
            fileNames.remove(fileName);
            Toast.makeText(mContext, "File deleted.", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(mContext, "File doesn't exist!", Toast.LENGTH_SHORT).show();
    }

    private byte[] gridData(){   //Convert grid to byte[] for saving.
        int size = blocks.size();
        int len = size*(size + 1);
        byte[] data = new byte[len];
        int q = 0;
        int NEXT_LINE = 3;

        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
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
            } else if (current == 3) {
                i++;
                j = 0;
            }
        }
    }

    public ArrayList<String> getFileNames(){
        return fileNames;
    }
}
