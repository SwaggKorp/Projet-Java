
package escape;

import java.awt.event.KeyEvent;

/*
 *  Direction describe all possible direction for a character on the grid
 */
public enum Direction {
    left,
    right,
    up,
    down,
    none;
    // convert key code to Direction
    public static Direction fromKeyCode(int keyCode) {
        switch(keyCode) {
            case KeyEvent.VK_LEFT:
                return left;
            case KeyEvent.VK_RIGHT:
                return right;
            case KeyEvent.VK_UP:
                return up;
            case KeyEvent.VK_DOWN:
                return down;
            default:
                return none;
        }
    }
}
