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
    public Player(EscapeBlock block, EscapeGrid grid) {
        super(block, grid, new Color(44,128,143));
    }
    public void keyPressed(int keyCode) {
        switch(keyCode) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                move(keyCode);
        }
    }
}
