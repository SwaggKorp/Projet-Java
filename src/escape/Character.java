/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escape;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 *
 * @author arnaud
 */
public abstract class Character {
    protected Color color;
    private int margin = 3;
    protected boolean enemy;
    protected EscapeBlock block;
    protected EscapeGrid grid;
    
    public Character(EscapeBlock source, EscapeGrid grid, Color color) {
        block = source;
        this.grid = grid;
        this.color = color;
        source.addCharacter(this);
    }
    protected void move(int keyCode) {
        int x = block.getPosition()[0];
        int y = block.getPosition()[1];
        switch(keyCode) {
            case KeyEvent.VK_LEFT:
                if(x>0 && grid.getBlock(x-1, y).alive())
                    moveTo(grid.getBlock(x-1, y));
                break;
            case KeyEvent.VK_RIGHT:
                if(x<grid.getGridWidth()-1 && grid.getBlock(x+1, y).alive())
                    moveTo(grid.getBlock(x+1, y));
                break;
            case KeyEvent.VK_UP:
                if(y>0 && grid.getBlock(x, y-1).alive())
                    moveTo(grid.getBlock(x, y-1));
                break;
            case KeyEvent.VK_DOWN:
                if(y<grid.getGridHeight()-1 && grid.getBlock(x, y+1).alive())
                    moveTo(grid.getBlock(x, y+1));
                break;
        }
    }
    public Color getColor() {
        return color;
    }
    public int getMargin() {
        return margin;
    }
    public boolean isEnemy() {
        return enemy;
    }
    protected void moveTo(EscapeBlock block) {
        this.block.removeCharacter();
        this.block = block;
        block.addCharacter(this);
    }
    public EscapeBlock getBlock() {
        return block;
    }
}
