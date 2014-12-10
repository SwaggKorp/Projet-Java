/*
Grid Class :
handles the creation of blocks, resizes the grid and provides a structure for the blocks
*/
package escape;

import java.util.ArrayList;

public class Grid extends javax.swing.JPanel {
    public static final int margin = 10;                                        // margin between the border of the Panel to the blocks
    
    private ArrayList<ArrayList<EscapeBlock>> grid;                          
    protected int gridWidth = 0;       
    protected int gridHeight = 0;
    private boolean editable = true;
    
    public Grid() {
        super();                                                                // create a new JPanel
        grid = new ArrayList<>();                                               // initialize a list containing the blocks
        
        setLayout(null);                                                        // format the component
        setFocusable(false);
        setBackground(Block.deadColor);
        addComponentListener(new java.awt.event.ComponentAdapter() {            // detects when the window is resized to resize the grid
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                resize();
            }
        });
        
        // provide a link to the grid to classes so that all instances have access to it
        PathFinder.setGrid(this);
        EscapeBlock.setGrid(this);
        Character.setGrid(this);
    }
    
    // #################   SIZE MANAGEMENT   ###################
    // add a row of blocks to the grid
    private void addRow() {
        ArrayList<EscapeBlock> row = new ArrayList<>();
        grid.add(row);
        
        for (int j = 0; j < gridWidth; j++) {
            
            EscapeBlock block = new EscapeBlock(j, gridHeight);
            block.setLocation(margin + j * (Block.length+1) ,
                    margin + gridHeight * (Block.length+1));  
                
            this.add(block);                                                    // add to the JPanel   
            row.add(block);                                                     // and to the row
        }
        
        gridHeight++;
    }
    // remove a row from the grid
    private void removeRow() {
        gridHeight--;       
        for (EscapeBlock block : grid.get(gridHeight)) {                        // for each block from the last row
            block.disconnectAll();
            this.remove(block);                                                 // remove it from the UI
        }

        grid.remove(gridHeight);                                                // remove the row
    }
    // add a column of blocks to the grid
    private void addColumn() {
        for (int j = 0; j < gridHeight; j++) {
            EscapeBlock block = new EscapeBlock(gridWidth,j);
            block.setLocation(margin + gridWidth * (Block.length+1) ,
                    margin + j * (Block.length+1));       
            this.add(block);
            grid.get(j).add(block);
        }
        gridWidth++;
    }
    // remove a column from the grid
    private void removeColumn() {
        gridWidth--;
        for (ArrayList<EscapeBlock> row : grid) {                               // for each row
            this.remove(row.get(gridWidth));                                    // remove the last block from the UI
            for(EscapeBlock block : row)
                block.disconnectAll();
            row.remove(gridWidth);                                              // remove the last block from the grid
        }
        
    }
    // resize the grid : adds and removes blocks
    public void resize() {
        resize(getWidth(),getHeight());
    }
    public void resize(int width, int height) {
        int newW = (width-2*margin)/(Block.length+1);
        int newH = (height-2*margin)/(Block.length+1);

        for(int i=gridWidth;i<newW;i++)
            addColumn();
        for(int i=gridWidth;i>newW;i--)
            removeColumn();
        for(int i=gridHeight;i<newH;i++)
            addRow();
        for(int i=gridHeight;i>newH;i--)
            removeRow();
        repaint();
    }
    
    // #################   GETTERS & SETTERS   ###################
    public int getGridWidth() {  
        return gridWidth;
    }
    public int getGridHeight() {
        return gridHeight;
    }
    public EscapeBlock getBlock(int x, int y) {
        return grid.get(y).get(x);
    }
    public boolean isEditable() {
        return editable;
    }
    public void setEditable(boolean editable) {
       this.editable = editable; 
    }
}

