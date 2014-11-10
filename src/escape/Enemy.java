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
    
    public Enemy(EscapeBlock block, EscapeGrid grid, Player player, PathFinder finder) {
        super(block, grid, new Color(164,12,12));
        this.player = player;
        this.finder = finder;
        
        path = new ArrayList<>();
    }
    public void move() {
        if(path.isEmpty()) {
            path = finder.shorterPath(player.getBlock(), this.getBlock());
        } else if(!path.get(0).alive()) {
            path = finder.shorterPath(player.getBlock(), this.getBlock());
        }
        else {
            this.moveTo(path.get(0));
            path.remove(0);
        }
    }
}
