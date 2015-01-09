package com.henryturp.joystickapp;

import java.util.ArrayList;

/**
 * Created by Henry on 09/12/2014.
 * Calculates the shortest path from enemy to player. Not dijsktra but a modified dfs.
 */
public class PathFinder {
    private GameGrid gameGrid;
    private ArrayList<ArrayList<Mark>> marks;

    public PathFinder(GameGrid gameGrid){
        this.gameGrid = gameGrid;
        marks = new ArrayList<ArrayList<Mark>>();
    }

    public ArrayList<Block> shortestPath(Block root, Block target){   // Implement also Dijkstra later
        ArrayList<Block> waitingBlocks = new ArrayList<Block>();
        Block current;
        initializeMarks();

        marks.get(root.getyPos()).get(root.getxPos()).setMark(true);

        /*
        Snippet of Arnaud's optimization. This will be useful when you implement a common pathfinder for the enemies.
        Don't erase it!!
         */

//        // optimisation :
//        for(Block block : gameGrid.getNeighbours(root)) {
//            Mark temp = marks.get(block.getyPos()).get(block.getxPos());
//            if(temp.getFather()==root) {                                 // if father of block == root
//                temp.setMark(true);
//            }
//        }

        waitingBlocks.add(root);

        while(waitingBlocks.size() > 0) {
            current = waitingBlocks.get(0);
            waitingBlocks.remove(0);

            for(Block block : gameGrid.getNeighbours(current)) {
                Mark temp = marks.get(block.getyPos()).get(block.getxPos());

                if(!temp.getMark()) {
                    temp.setMark(true);
                    temp.setFather(current);

                    if(block.equals(target)) {
                        return getPath(root, target);
                    }
                    waitingBlocks.add(block);
                }
            }
        }
        return new ArrayList<Block>();
    }

    private ArrayList<Block> getPath(Block root, Block target) { // path[0] = target!! and path[max] = root's son. This is used to retrace the enemies movements.
        ArrayList<Block> path = new ArrayList<Block>();
        Block current = target;

        path.add(target);

        while(!current.equals(root)) {
            current = marks.get(current.getyPos()).get(current.getxPos()).getFather();
            path.add(current);
        }

        return path;
    }
    private class Mark{
        private boolean mark;
        private Block father;

        public Mark(){
            father = null;   // If something fucks up, it'll be here.
            mark = false;
        }

        public boolean getMark(){
            return mark;
        }
        public Block getFather(){
            return father;
        }
        public void setMark(boolean mark){
            this.mark = mark;
        }
        public void setFather(Block father){
            this.father = father;
        }
    }  // Small inner class used to handle marks for the pathfinding algorithm.
    private void initializeMarks(){     // Resets all the marks. Used in initialization of pathfinder algorithm.
        while(marks.size() != 0) // Empty marksList
            marks.remove(0);

        for(int i = 0; i<MainActivity.gridRowNumber; i++){
            ArrayList<Mark> tempRow = new ArrayList<Mark>();
            for(int j = 0; j<MainActivity.gridColumnNumber; j++){
                Mark temp = new Mark();
                tempRow.add(temp);
            }
            marks.add(tempRow);
        }
    }

}
