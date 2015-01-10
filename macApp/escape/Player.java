
package escape;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
/*
 * Player handles the player movements according to the user keyboard commands
 */
public class Player extends Character {
    private Timer movementManager;        // repeats last move
    private Direction currentDirection;   // the current direction (if any) the player is moving in
    
    public Player(EscapeBlock block) {
        super(block, new Color(44,128,143));
        this.enemy = false;
        // repeat a previous step if the corresponding arrow is maintained down
        movementManager = new Timer(90, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                 move(currentDirection);
            }
        });
        Enemy.setPlayer(this);   // provide a link to the player
    }
    // when a key is pressed, start moving in the specified direction
    public void startMoving(Direction direction) {
        if(direction != Direction.none) {   // if the key is actually an arrow
            currentDirection = direction;
            move(currentDirection);
            movementManager.start();
        }
    }
    // when the key is released, stop moving
    public void stopMoving(Direction direction) {
        if(direction == currentDirection) {          // if the previously pressed arrow has been released
            movementManager.stop();
        }
    }
    // move the player in the specified direction
    public void move(Direction direction) {
        int x = block.getPosition()[0];
        int y = block.getPosition()[1];
        EscapeBlock newBlock = null;
        // get the new block the player is on if it can move there
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
    // remove the player from the grid
    public void destroy() {
        block.removeCharacter();
        movementManager.stop();
    }
}
