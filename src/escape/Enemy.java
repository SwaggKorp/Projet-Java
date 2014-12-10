
package escape;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

public class Enemy extends Character {
    private static Player player;
    private static Timer spawner;
    private static Timer animator;
    private static ArrayList<Enemy> enemies;
    
    private ArrayList<EscapeBlock> path;
    
    static {
        spawner = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                     enemies.add(Enemy.spawn());
                }
        });
        animator = new Timer(200, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                 for(int i=0;i<enemies.size();i++) {
                     enemies.get(i).move();
                 }
            }
        });
        enemies = new ArrayList<>();
    }
    
    public Enemy(EscapeBlock block, Escape window) {
        super(block, window, new Color(164,12,12));
        
        this.enemy = true;
        path = new ArrayList<>();
    }
    public void move() {
        if(path.isEmpty()) {
            path = PathFinder.shorterPath(player.getBlock(), this.getBlock());
        } else if(!path.get(0).alive()) {
            path = PathFinder.shorterPath(player.getBlock(), this.getBlock());
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
        enemies.remove(this);
    }
    public static Enemy spawn() {
        boolean found = false;
        Enemy enemy = null;
        Random rand = new Random();
        EscapeBlock targetBlock;
        
        int position;
        int side;
        while(!found) {
            side = rand.nextInt(2);  // select the side to spawn on (left/right or up/down)
            if(rand.nextBoolean()) { // spawn on vertical (true) or horizontal (false) side
                position = rand.nextInt(grid.getGridHeight());
                targetBlock = grid.getBlock(side*(grid.getGridWidth()-1), position);
            } else {
                position = rand.nextInt(grid.getGridWidth());
                targetBlock = grid.getBlock(position, side*(grid.getGridHeight()-1));
            }
            if(targetBlock.alive() && !targetBlock.hasCharacter()) {
                    found = true;
                    enemy = new Enemy(targetBlock, window);
            }
        }
        return enemy;
    }
    public static void reset() {
        freeze();
        while(!enemies.isEmpty()) {
            enemies.get(0).destroy();
        }
        animate();
    }
    public static void freeze() {
        animator.stop();
        spawner.stop();
    }
    public static void animate() {
        animator.start();
        spawner.start();
    }
    public static void setPlayer(Player player) {
        Enemy.player = player;
    }
}
