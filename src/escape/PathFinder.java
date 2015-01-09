
package escape;

import java.util.ArrayList;

public class PathFinder {
    private static Grid grid;
    
    public static ArrayList<EscapeBlock> shorterPath(EscapeBlock root, EscapeBlock target) {
        ArrayList<EscapeBlock> waitingBlocks = new ArrayList<>();
        ArrayList<EscapeBlock> path;
        EscapeBlock current;
        clearMarks();
        
        /* optimisation : 
        for(EscapeBlock block : root.getNeighbours()) {
            if(block.getFather()==root) {
                block.setMarked(true);
            }
        }*/
        
        root.setMarked(true);
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
   
    public static ArrayList<EscapeBlock> getPath(EscapeBlock root, EscapeBlock target) {
        ArrayList<EscapeBlock> path = new ArrayList<>();
        EscapeBlock current = target;
        while(!current.equals(root)) {
            current = current.getFather();
            path.add(current);
        }
        return path;
    }
    private static void clearMarks() {
        for(int i=0;i<grid.getGridWidth(); i++) {
            for(int j=0;j<grid.getGridHeight();j++) {
                grid.getBlock(i,j).setMarked(false);
            }
        }
    }
    public static void setGrid(Grid grid) {
        PathFinder.grid = grid;
    }
}
