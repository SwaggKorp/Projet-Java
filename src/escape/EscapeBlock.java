/*
EscapeBlock class:
handles pathfinding variables and connections for each block

 */
package escape;

import java.awt.Color;
import java.util.ArrayList;

public class EscapeBlock extends Block{
    private boolean marked;
    private EscapeBlock father;
    private ArrayList<EscapeBlock> neighbours; 
    private int x;
    private int y;
    private Grid grid;
    
    public EscapeBlock(int length, Grid grid, int x, int y) {
        super(length);
        this.grid = grid;
        this.x = x;
        this.y = y;
        neighbours = new ArrayList<>();
        //System.out.println(x);
        //System.out.println(y);
        
        if(x>0) {
            grid.getBlock(0,0);
            grid.getBlock(x-1, y).connect(this);
            if(grid.getBlock(x-1, y).alive())
                neighbours.add(grid.getBlock(x-1, y));
        }
        if(y>0) {
            grid.getBlock(x, y-1).connect(this);
            if(grid.getBlock(x, y-1).alive())
                neighbours.add(grid.getBlock(x, y-1));
        }
    }
    
    // make sure all the surrounding blocks are connected if alive, and disconnected if not
    public void reconnect() {
        if(alive) {
            if(x>0)
                grid.getBlock(x-1, y).connect(this);
            if(x<grid.getGridWidth()-1)
                grid.getBlock(x+1, y).connect(this);
            if(y>0)
                grid.getBlock(x, y-1).connect(this);
            if(y<grid.getGridHeight()-1)
                grid.getBlock(x, y+1).connect(this);
        } else {
            if(x>0)
                grid.getBlock(x-1, y).disconnect(this);
            if(x<grid.getGridWidth()-1)
                grid.getBlock(x+1, y).disconnect(this);
            if(y>0)
                grid.getBlock(x, y-1).disconnect(this);
            if(y<grid.getGridHeight()-1)
                grid.getBlock(x, y+1).disconnect(this);
        }
    }
    // methods changing alive have to reconnect the block
    @Override
    protected void onClick() {
        super.onClick();
        reconnect();
    }
    @Override
    public void setAlive(boolean alive) {
        super.setAlive(alive);
        reconnect();
    }
    // add and remove connection with other blocks
    public void connect(EscapeBlock block) {
        neighbours.add(block);
    }
    public void disconnect(EscapeBlock block){
        neighbours.remove(block);
    }
    // improve equals efficiency
    public boolean equals(EscapeBlock block) {
        return (block.getPosition()[0]==x && block.getPosition()[1]==y);
    }
    // getters and setters
    public boolean isMarked() {
        return marked;
    }
    public void setMarked(boolean marked) {
        this.marked = marked;
    }
    public EscapeBlock getFather() {
        return father;
    }
    public void setFather(EscapeBlock father) {
        this.father = father;
    }
    public ArrayList<EscapeBlock> getNeighbours() {
        return neighbours;
    }
    public void setNeighbours(ArrayList<EscapeBlock> neighbours) {
        this.neighbours = neighbours;
    }
    public int[] getPosition() {
        int[] pos = {x,y};
        return pos;
    }
    public void setAliveColor(Color color) {
        aliveColor = color;
        repaint();
    }
}
