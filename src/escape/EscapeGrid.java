/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escape;

import java.util.ArrayList;

/**
 *
 * @author arnaud
 */
public class EscapeGrid extends Grid {
    public EscapeGrid() {
        super();
    }
    public boolean[][] getBlocksState() {
        boolean[][] states = new boolean[gridWidth][gridHeight];
        for(int i=0;i<gridWidth; i++) {
            for(int j=0;j<gridHeight;j++) {
                states[i][j] = grid.get(j).get(i).alive();
            }
        }
        return states;
    }
    public void setBlocksState(boolean[][] states) {
        for(int i=0;i<gridWidth; i++) {
            for(int j=0;j<gridHeight;j++) {
                grid.get(j).get(i).setAlive(states[i][j]);
            }
        }
    }
    public void clear() {
        for(ArrayList<Block> row : grid) {
            for(Block block : row)
                block.setAlive(true);
        }
        repaint();
    }
}
