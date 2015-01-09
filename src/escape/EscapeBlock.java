
package escape;

import java.util.ArrayList;

/*
 * EscapeBlock class handles pathfinding variables and connections for each block
 */
public class EscapeBlock extends Block{
    private static Grid grid;                                 // reference to the grid shared by all the EscapeBlocks
    
    private boolean marked;                                   // shorter path
    private EscapeBlock father;
    private ArrayList<EscapeBlock> neighbours;                // links to the neighbours blocks (linked for shorter path)
    
    private int x;                                            // position in the grid
    private int y;
    private Character character;                              // reference to the character on the block
    
    // constructor where x and y are the position of the block on the grid
    public EscapeBlock(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        neighbours = new ArrayList<>();               
        
        if(x>0) {
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
    
    // #################   PATH FINDING   ###################
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
        if(grid.isEditable()) {
            super.onClick();
            reconnect();
        }
    }
    @Override
    public void setAlive(boolean alive) {
        super.setAlive(alive);
        reconnect();
    }
    // add and remove connection with other blocks
    public void connect(EscapeBlock block) {
        if(!neighbours.contains(block))
            neighbours.add(block);
    }
    public void disconnect(EscapeBlock block){
        neighbours.remove(block);
    }
    // improve equals efficiency
    public boolean equals(EscapeBlock block) {
        return (block.getPosition()[0]==x && block.getPosition()[1]==y);
    }
    // remove all neighbours references ( when the block is destroyed )
    public void disconnectAll() {
        for(EscapeBlock block : neighbours)
            block.disconnect(this);
    }
    // make sure all the alive neighbours are connected
    public void reconnectAll() {
        for(int i=0; i<neighbours.size(); i++)
            neighbours.get(i).reconnect();
    }
    
    // #####################   CHARACTER   #######################
    @Override
    public void addCharacter(Character character) {
        this.character = character;
        super.addCharacter(character);
    }
    public void removeCharacter() {
        character = null;
        hasCharacter = false;
        repaint();
    }
    public boolean hasCharacter() {
        return hasCharacter;
    }
    public boolean hasEnemy() {
        return character.isEnemy();
    }
    
    // #################   GETTERS & SETTERS   ###################
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
    public static void setGrid(Grid grid) {
        EscapeBlock.grid = grid;
    }
    public boolean isDarker() {
        return isDarker;
    }
    public void setDarker(boolean darker) {
        isDarker = darker;
    }
}
