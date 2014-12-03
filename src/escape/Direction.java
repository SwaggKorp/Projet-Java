/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escape;

import java.awt.event.KeyEvent;

/**
 *
 * @author arnaud
 */
public enum Direction {
    left,
    right,
    up,
    down,
    none;
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
