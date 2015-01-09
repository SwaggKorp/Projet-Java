
package escape;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Player extends Character {
    private Timer movementManager;
    private Direction currentDirection;
    
    public Player(EscapeBlock block) {
        super(block, new Color(44,128,143));
        this.enemy = false;
        
        movementManager = new Timer(90, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                 move(currentDirection);
            }
        });
        Enemy.setPlayer(this);
    }
    public void startMoving(Direction direction) {
        if(direction != Direction.none) {
            currentDirection = direction;
            move(currentDirection);
            movementManager.start();
        }
    }
    public void stopMoving(Direction direction) {
        if(direction == currentDirection) {
            movementManager.stop();
        }
    }
    public void move(Direction direction) {
        int x = block.getPosition()[0];
        int y = block.getPosition()[1];
        EscapeBlock newBlock = null;
        
        switch(direction) {
            case left:
                if(x>0 && grid.getBlock(x-1, y).alive())
                    newBlock = grid.getBlock(x-1, y);
                break;
            case right:
                if(x<grid.getGridWidth()-1 && grid.getBlock(x+1, y).alive())
                    newBlock = grid.getBlock(x+1, y);
                break;
            case up:
                if(y>0 && grid.getBlock(x, y-1).alive())
                    newBlock = grid.getBlock(x, y-1);
                break;
            case down:
                if(y<grid.getGridHeight()-1 && grid.getBlock(x, y+1).alive())
                    newBlock = grid.getBlock(x, y+1);
                break;
        }
        if(newBlock != null) {                                 // if the player can move toward the specified direction
            moveTo(newBlock);
            if(!newBlock.isDarker()) {                         // if the player has never been here
                score.addBlockPoints();
                newBlock.setDarker(true);
            }
            if(newBlock.hasCharacter() && newBlock.hasEnemy()) // if the block contains an enemy
                window.displayGameOver(); 
        }
    }
    public void destroy() {
        block.removeCharacter();
        movementManager.stop();
    }
    
}
