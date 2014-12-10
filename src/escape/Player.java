
package escape;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Player extends Character {
    private Timer movementManager;
    private Direction currentDirection;
    
    public Player(EscapeBlock block, Escape window) {
        super(block, window, new Color(44,128,143));
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
        movementManager.stop();
    }
    
}
