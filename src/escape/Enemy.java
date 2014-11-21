/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escape;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author arnaud
 */
public class Enemy extends Character {
    private Player player;
    private PathFinder finder;
    private ArrayList<EscapeBlock> path;
    private Escape window;
    
    public Enemy(EscapeBlock block, Escape window) {
        super(block, window, new Color(164,12,12));
        
        this.window = window;
        this.player = window.getPlayer();
        this.finder = window.getPathFinder();
        
        path = new ArrayList<>();
    }
    public void move() {
        if(path.isEmpty()) {
            path = finder.shorterPath(player.getBlock(), this.getBlock());
        } else if(!path.get(0).alive()) {
            path = finder.shorterPath(player.getBlock(), this.getBlock());
        }
        else {
            if(path.get(0).hasCharacter() && !path.get(0).hasEnemy()) {
                window.displayGameOver();
            }
            this.moveTo(path.get(0));
            path.remove(0);
        }
    }
}
