
package escape;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

/*
 * Enemy provide static methods to spawn and move the enemies, while the object Enemy 
 * represents a single enemy moving towards the player
 */

public class Enemy extends Character {
    // ########## Static ###########
    private static Player player;                              // link to the player
    private static Timer spawner;                              // makes the enemy spawn
    private static Timer animator;                             // makes the enemies move
    private static ArrayList<Enemy> enemies;                   // list contaning all the enemies
    private static float strength;                             // the number of enemy spawned per wave, slowly increases
    private static final float strengthGrowth = 0.2f;          // the value added to strength after each wave
    
    // initialize timers
    static {
        spawner = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                for(int i=0;i<strength;i++) {                   // each 5seconds, spawn a wave of enemies
                     enemies.add(Enemy.spawn());
                }
                strength += strengthGrowth;                     // and make the next wave bigger
            } 
        });
        spawner.setInitialDelay(700);                           // wait in the beginning of the game for the player to get ready
        animator = new Timer(150, new ActionListener() {        
            @Override
            public void actionPerformed(ActionEvent evt) {
                 for(int i=0;i<enemies.size();i++) {            // each 150ms, all the enemies moves by one step
                     enemies.get(i).move();
                 }
            }
        });
        enemies = new ArrayList<>();
    }
    // randomly spawn an enemy on an alive block of the border of the grid
    public static Enemy spawn() {
        Random rand = new Random();          // create a new random number generator
        EscapeBlock targetBlock;
        int position;
        int side;
        
        while(true) {
            side = rand.nextInt(2);  // select the side to spawn on (left/right or up/down)
            if(rand.nextBoolean()) { // spawn on vertical (true) or horizontal (false) side
                position = rand.nextInt(grid.getGridHeight());  // select the position on the selected side
                targetBlock = grid.getBlock(side*(grid.getGridWidth()-1), position); 
            } else {
                position = rand.nextInt(grid.getGridWidth());   // select the position on the selected side
                targetBlock = grid.getBlock(position, side*(grid.getGridHeight()-1));
            }
            if(targetBlock.alive() && !targetBlock.hasCharacter()) {  // if the selected block is valid block to spawn on
                    return new Enemy(targetBlock);                    // create enemy on it
            }
        }
    }
    // remove all enemies
    public static void reset() {
        freeze();
        while(!enemies.isEmpty()) {
            enemies.get(0).destroy();
        }
        strength = 1;
    }
    // stop spawn and mouvement of the enemies
    public static void freeze() {
        animator.stop();
        spawner.stop();
    }
    // start spawn and mouvement of the enemies
    public static void animate() {
        animator.start();
        spawner.start();
    }
    // provide a link to the player shared by all enemies
    public static void setPlayer(Player player) {
        Enemy.player = player;
    }
    
    // ########## Dynamic ##########
    private ArrayList<EscapeBlock> path; // the last computed path to the player
    
    public Enemy(EscapeBlock block) {
        super(block, new Color(164,12,12));
        
        this.enemy = true;                                     // this character is an enemy
        path = new ArrayList<>();
    }
    /* move the enemy of one step
     * note on the AI strategy : to make this game a little bit fun (and not merely
     * impossible to play more than a few seconds) and in order to spare CPU 
     * ressources, the shorter path is computed only when the enemy reach the end
     * of the previously computed path. It may therefore give a feeling of erratic
     * move. 
     */
    public void move() {
        if(path.isEmpty() || !path.get(0).alive()) {                            // if there is no more computed path or the next step is a wall
            path = PathFinder.shorterPath(player.getBlock(), this.getBlock());  // recompute shorter path to player
        } else {
            if(path.get(0).hasCharacter()) {                        // if there is someone one the next step block
                if(path.get(0).hasEnemy()) {                        // collision with an enemy : this enemy is destroyed
                    destroy();
                    score.addEnemyDestroyedPoints();
                } else {                                            // colllision with the player : game over
                    window.displayGameOver();
                }
            } else {
                this.moveTo(path.get(0));                        
                path.remove(0);
            }
        }
    }
    // remove this enemy from the game
    public void destroy() {
        this.block.removeCharacter();
        enemies.remove(this);
    }
    
}
