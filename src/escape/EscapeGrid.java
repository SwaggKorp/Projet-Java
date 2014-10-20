/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escape;

import java.awt.Color;
import java.util.ArrayList;

/*
EscapeGrid class :
provides high level methods helping is loading/saving the grid
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
                grid.get(j).get(i).reconnectAll();
                grid.get(j).get(i).setAliveColor(new Color(137,220,115));
            }
        }
    }
    public void clear() {
        for(ArrayList<EscapeBlock> row : grid) {
            for(EscapeBlock block : row) {
                block.setAlive(true);
                block.setAliveColor(new Color(137,220,115));
            }
        }
        repaint();
    }
    public void clearMarks() {
        for(ArrayList<EscapeBlock> row : grid) {
            for(EscapeBlock block : row) {
                block.setMarked(false);
                block.setAliveColor(new Color(137,220,115));
            }
        }
    }
}
