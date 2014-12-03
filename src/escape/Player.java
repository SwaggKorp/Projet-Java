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
public class Player extends Character {
    public Player(EscapeBlock block, Escape window) {
        super(block, window, new Color(44,128,143));
        this.enemy = false;
    }
    public void move(Direction direction) {
        int x = block.getPosition()[0];
        int y = block.getPosition()[1];
        switch(direction) {
            case left:
                if(x>0 && grid.getBlock(x-1, y).alive())
                    moveTo(grid.getBlock(x-1, y));
                break;
            case right:
                if(x<grid.getGridWidth()-1 && grid.getBlock(x+1, y).alive())
                    moveTo(grid.getBlock(x+1, y));
                break;
            case up:
                if(y>0 && grid.getBlock(x, y-1).alive())
                    moveTo(grid.getBlock(x, y-1));
                break;
            case down:
                if(y<grid.getGridHeight()-1 && grid.getBlock(x, y+1).alive())
                    moveTo(grid.getBlock(x, y+1));
                break;
        }
        
        if(block.hasCharacter() && block.hasEnemy())
            window.displayGameOver();
    }
    public void destroy() {
        block.removeCharacter();
    }
}
