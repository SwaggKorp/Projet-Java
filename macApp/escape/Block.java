
package escape;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/*
 * Block class handles block display and user input in editting the grid 
 */
public abstract class Block extends JComponent {
    public final static int length = 23;                                        // the size of a block
    public final static Color aliveColor = new Color(255,206,87);               // color of the block when alive and never passed by 
    public final static Color darkerAliveColor = new Color(253,189,69);         // color when the player has passed by the block during this game
    public final static Color deadColor = new Color(57,57,57);                  // color when the block is dead (is a wall)
    
    protected boolean alive = true;                                             // whether the block can be passed by (alive) or not (dead)
    protected Color characterColor;                                             // the color of the character that is on the block (if any)
    protected boolean hasCharacter;                                             // whether a character is on the block
    protected boolean isDarker;                                                 // whether is has been passed by during this game
    
    public Block() {
        super();
        
        this.setSize(length, length);
        this.setFocusable(false);                                     // make sure the component can't be selected (will absorb key events)
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(evt.getButton()==java.awt.event.MouseEvent.BUTTON1)
                    onClick();
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                onClick();
            }
        });
        
        repaint();
    }
    // function called by swing to display the block
    @Override
    public void paint(Graphics g) {
        if(alive&&isDarker) 
            g.setColor(darkerAliveColor);
        else if(alive)
            g.setColor(aliveColor);
        else 
            g.setColor(deadColor);
        g.fillRect(0, 0, length, length);
        
        if(hasCharacter) {
            g.setColor(characterColor); 
            g.fillOval(Character.margin, Character.margin, length-2*Character.margin, length-2*Character.margin);
        }
       
    }
    // set whether the block is alive (can be passed by) or dead (is a wall)
    public void setAlive(boolean alive) {
        this.alive = alive;
        repaint();
    }
    public boolean alive() {
        return alive;
    }
    // whenever the block is clicked on
    protected void onClick() {
        alive = !alive;
        repaint();
    }
    // a character (player or enemy is on the block
    protected void addCharacter(Character character) {
        characterColor = character.getColor();
        hasCharacter = true;
        repaint();
    }
 }
