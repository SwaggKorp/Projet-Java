/*
Block class:
handles block display and user input in editting the grid 

 */
package escape;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public abstract class Block extends JComponent {
    private int length;
    protected boolean alive = true;
    
    public final static Color aliveColor = new Color(255,206,87);
    public final static Color deadColor = new Color(57,57,57);
    
    protected Color characterColor;
    protected boolean hasCharacter;
    protected int charMargin;
    
    public Block(int length) {
        super();
        
        this.length = length;
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
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(0, 0, length, length);
        if(alive)
            g.setColor(aliveColor);
        else 
            g.setColor(deadColor);
       
        g.fillRect(0, 0, length, length);
        
        if(hasCharacter) {
            g.setColor(characterColor); 
            g.fillOval(charMargin, charMargin, length-2*charMargin, length-2*charMargin);
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
        charMargin = character.getMargin();
        characterColor = character.getColor();
        hasCharacter = true;
        repaint();
    }
 }
