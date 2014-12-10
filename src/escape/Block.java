/*
Block class:
handles block display and user input in editting the grid 

 */
package escape;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public abstract class Block extends JComponent {
    public final static int length = 23;                                        // the size of a block
    public final static Color aliveColor = new Color(255,206,87);
    public final static Color deadColor = new Color(57,57,57);
    
    protected boolean alive = true;
    protected Color characterColor;
    protected boolean hasCharacter;
    
    
    public Block() {
        super();
        
        this.setSize(length, length);
        this.setFocusable(false);
        
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
    @Override
    public void paint(Graphics g) {
        if(alive)
            g.setColor(aliveColor);
        else 
            g.setColor(deadColor);
       
        g.fillRect(0, 0, length, length);
        
        if(hasCharacter) {
            g.setColor(characterColor); 
            g.fillOval(Character.margin, Character.margin, length-2*Character.margin, length-2*Character.margin);
        }
       
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
        repaint();
    }
    public boolean alive() {
        return alive;
    }
    protected void onClick() {
        alive = !alive;
        repaint();
    }
    protected void addCharacter(Character character) {
        characterColor = character.getColor();
        hasCharacter = true;
        repaint();
    }
 }
