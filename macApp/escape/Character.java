
package escape;

import java.awt.Color;

/*
 *  Abstract object that reprensents a character, a player or an enemy
 */
public abstract class Character {
    public static final int margin = 3;                                         // size of the displayed circle
    protected static Grid grid;                                                 // reference to the grid available to all character
    protected static Escape window;                                             // reference to the window
    protected static ScoreCounter score;                                        // and the score counter
    
    protected Color color;                                                      // color of the character
    protected boolean enemy;                                                    // whether it's an enemy or not
    protected EscapeBlock block;                                                // refernec to the block it's on
    
    public Character(EscapeBlock source, Color color) {
        block = source;
        this.color = color;
        
        source.addCharacter(this);
    }
    // move the character to the specified block
    protected void moveTo(EscapeBlock block) {
        this.block.removeCharacter();
        this.block = block;
        block.addCharacter(this);
    }
    // Getters and setters
    public Color getColor() {
        return color;
    }
    public boolean isEnemy() {
        return enemy;
    }
    public EscapeBlock getBlock() {
        return block;
    }
    public static void setGrid(Grid grid) {
        Character.grid = grid;
    }
    public static void setWindow(Escape window) {
        Character.window = window;
        Character.score = window.getScoreCounter();
    }
}
