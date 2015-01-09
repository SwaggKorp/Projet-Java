
package escape;

import java.util.ArrayList;

/*
 * PathFinder contains the shorter path algorithme 
 */
public class PathFinder {
    private static Grid grid;
    
    /* computes the shorter path from root to target using a BFS (Breadth First Search)
     * algorithme, which is much more efficient that Dijkstra which was initially 
     * implemented (complexity is O(gridWidth*gridHeight), to be compared to the 
     * O((gridWidth*gridHeight)^2) of Dijkstra), and actually provides a shorter 
     * path as all the weights are 1 in a non-oriented graph. The method won't
     * be commented as it is a mere translation of BFS algorithme in pseudo-code
     * (see http://en.wikipedia.org/wiki/Breadth-first_search) 
     * @param : root : the block the algorithme starts from
     *          target : when this block is found, the algorithme stops
     * @return : a list describing the path from root to target
    */
    public static ArrayList<EscapeBlock> shorterPath(EscapeBlock root, EscapeBlock target) {
        ArrayList<EscapeBlock> waitingBlocks = new ArrayList<>();
        ArrayList<EscapeBlock> path;
        EscapeBlock current;
        
        clearMarks();
        
        root.setMarked(true);
        waitingBlocks.add(root);
        
        while(waitingBlocks.size()>0) {
            current = waitingBlocks.get(0);
            waitingBlocks.remove(0);
            for(EscapeBlock block : current.getNeighbours()) {
                if(!block.isMarked()) {
                    block.setMarked(true);
                    block.setFather(current);
                    if(block.equals(target)) {                   // if the target has been reached
                        path = getPath(root, target);            // return the path from root to target
                        return path;
                    }
                    waitingBlocks.add(block);
                }   
            }
        }
        return new ArrayList<>();  // if no path to target has been found, return an empty list
    }
    // constructs the path after the algorithme has been executed
    public static ArrayList<EscapeBlock> getPath(EscapeBlock root, EscapeBlock target) {
        ArrayList<EscapeBlock> path = new ArrayList<>();
        EscapeBlock current = target;
        while(!current.equals(root)) {
            current = current.getFather();
            path.add(current);
        }
        return path;
    }
    // set every block to not marked
    private static void clearMarks() {
        for(int i=0;i<grid.getGridWidth(); i++) {
            for(int j=0;j<grid.getGridHeight();j++) {
                grid.getBlock(i,j).setMarked(false);
            }
        }
    }
    // provide a link to the grid (called when the grid is initialized)
    public static void setGrid(Grid grid) {
        PathFinder.grid = grid;
    }
}
