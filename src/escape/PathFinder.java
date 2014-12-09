/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escape;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author arnaud
 */
public class PathFinder {
    private EscapeGrid grid;
    
    public PathFinder(EscapeGrid grid) {
        this.grid = grid;
    }
    public ArrayList<EscapeBlock> shorterPath(EscapeBlock root, EscapeBlock target) {
        ArrayList<EscapeBlock> waitingBlocks = new ArrayList<>();
        ArrayList<EscapeBlock> path = new ArrayList<>();
        EscapeBlock current;
        grid.clearMarks();
        
        root.setMarked(true);
        // optimisation : 
        for(EscapeBlock block : root.getNeighbours()) {
            if(block.getFather()==root) {
                block.setMarked(true);
            }
        }
        waitingBlocks.add(root);
        
        while(waitingBlocks.size()>0) {
            current = waitingBlocks.get(0);
            waitingBlocks.remove(0);
            for(EscapeBlock block : current.getNeighbours()) {
                if(!block.isMarked()) {
                    block.setMarked(true);
                    block.setFather(current);
                    if(block.equals(target)) {
                        path = getPath(root, target);
                        return path;
                    }
                    waitingBlocks.add(block);
                }
            }
        }
        return new ArrayList<>();
    }
    public void shorterPath(int[] rootLocation, int[] targetLocation) {
        shortedPath(rootLocation[0],rootLocation[1],targetLocation[0],targetLocation[1]);
    }
    public void shortedPath(int rootx, int rooty, int targetx, int targety) {
        shorterPath(grid.getBlock(rootx, rooty), grid.getBlock(targetx, targety));
    }
    public ArrayList<EscapeBlock> getPath(EscapeBlock root, EscapeBlock target) {
        ArrayList<EscapeBlock> path = new ArrayList<>();
        EscapeBlock current = target;
        while(!current.equals(root)) {
            current = current.getFather();
            path.add(current);
        }
        return path;
    }
}
