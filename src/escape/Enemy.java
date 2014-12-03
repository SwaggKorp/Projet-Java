/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escape;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Character {
    private Player player;
    private PathFinder finder;
    private ArrayList<EscapeBlock> path;
    
    public Enemy(EscapeBlock block, Escape window) {
        super(block, window, new Color(164,12,12));
        
        this.player = window.getPlayer();
        this.finder = window.getPathFinder();
        
        this.enemy = true;
        path = new ArrayList<>();
    }
    public void move() {
        if(path.isEmpty()) {
            path = finder.shorterPath(player.getBlock(), this.getBlock());
        } else if(!path.get(0).alive()) {
            path = finder.shorterPath(player.getBlock(), this.getBlock());
        }
        else {
            if(path.get(0).hasCharacter()) {
                if(path.get(0).hasEnemy()) {
                    destroy();
                } else {
                    window.displayGameOver();
                }
                
            }
            this.moveTo(path.get(0));
            path.remove(0);
        }
    }
    public void destroy() {
        this.block.removeCharacter();
        window.removeEnemy(this);
    }
    public static Enemy spawn(Escape window) {
        boolean found = false;
        Enemy enemy = null;
        Random rand = new Random();
        EscapeGrid grid = window.getGrid();
        int position;
        int side;
        while(!found) {
            side = rand.nextInt(2);  // select the side to spawn on (left/right or up/down)
            if(rand.nextBoolean()) { // spawn on vertical (true) or horizontal (false) side
                position = rand.nextInt(grid.getGridHeight()); 
                if(grid.getBlock(side*(grid.getGridWidth()-1), position).alive()) {
                    found = true;
                    enemy = new Enemy(grid.getBlock(side*(grid.getGridWidth()-1), position), window);
                }
            } else {
                position = rand.nextInt(grid.getGridWidth());
                if(grid.getBlock(position, side*(grid.getGridHeight()-1)).alive()) {
                    found = true;
                    enemy = new Enemy(grid.getBlock(position, side*(grid.getGridHeight()-1)), window);
                }
            }
        }
        return enemy;
    }
}
