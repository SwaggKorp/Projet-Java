
package escape;

import java.util.ArrayList;

public class Grid extends javax.swing.JPanel {

    protected ArrayList<ArrayList<Block>> grid;                          
    public static final int margin = 10;
    public static final int length = 23;
    protected int gridWidth = 10;
    protected int gridHeight = 10;
    
    public Grid() {
        super();
        grid = new ArrayList<>();
        
        this.setLayout(null);                                                   // format the component            
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                resize();
            }
        });
        
        // create blocks
        for (int i = 0; i < gridWidth; i++) {
            grid.add(new ArrayList<Block>());
            for (int j = 0; j < gridHeight; j++) {
                Block block = new Block(length);
                block.setLocation(margin + i * (length+1) ,margin + j * (length+1));
                block.connect(this);
                
                this.add(block);
                grid.get(i).add(block);
            }
        }
        
        // fit the grid to the window
        resize();
    }
    // add a row of blocks to the grid
    private void addRow() {
        ArrayList<Block> row = new ArrayList<>();
        
        for (int j = 0; j < gridWidth; j++) {
            Block block = new Block(length);
            block.setLocation(margin + j * (length+1) ,margin + gridHeight * (length+1));
                
            this.add(block);
            row.add(block);
        }
        
        gridHeight++;
        grid.add(row);
    }
    // remove a row from the grid
    private void removeRow() {
        gridHeight--;       
        for (Block block : grid.get(gridHeight)) {            // for each block from the last row
            this.remove(block);                               // remove it from the UI
        }

        grid.remove(gridHeight);                              // remove the row
    }
    // add a column of blocks to the grid
    private void addColumn() {
        for (int j = 0; j < gridHeight; j++) {
            Block block = new Block(length);
            block.setLocation(margin + gridWidth * (length+1) ,
                    margin + j * (length+1));       
            this.add(block);
            grid.get(j).add(block);
        }
        gridWidth++;
    }
    // remove a column from the grid
    private void removeColumn() {
        gridWidth--;
        for (ArrayList<Block> row : grid) {                 // for each row
            this.remove(row.get(gridWidth));                // remove the last block from the UI
            row.remove(gridWidth);                          // remove the last block from the grid
        }
        
    }
    public void resize() {
        int newW = (getWidth()-2*margin)/(length+1);
        int newH = (getHeight()-2*margin)/(length+1);

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
    public void resize(int width, int height) {
        int newW = (width-2*margin)/(length+1);
        int newH = (height-2*margin)/(length+1);

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
    public int getGridWidth() {
        return gridWidth;
    }
    public int getGridHeight() {
        return gridHeight;
    }
    public Block getBlock(int x, int y) {
        return grid.get(y).get(x);
    }
}

