
package escape;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;

/*
 * An object that computes and display the score
 */
public class ScoreCounter extends JLabel{
    private static Grid grid;                                // reference to the grid
    public final static int survivalPoints = 100;            // score added each 0.5 seconds (survival reward)
    public final static int blockPoints = 23;                // score added when the player is on a block that has never been passed by (exploration reward)
    public final static int enemyDestroyedPoints = 400;      // score added when two enemy collide (strategy reward)
    private int score = 0;
    private Timer survivalTimer;
    
    // when created, the grid provides link to Scorecounter 
    public static void setGrid(Grid grid) {               
        ScoreCounter.grid = grid;                            
    }
    
    public ScoreCounter() {
        super();
        this.setText(String.valueOf(score));
        setFocusable(false);                                      // make sure the component can't be selected (will absorb key events)
        setVisible(true); 
        survivalTimer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    score += survivalPoints;
                    showScore();
                }
        });
    }
    public void startGame() {
        score = 0;
        showScore();
        survivalTimer.start();
        setVisible(true);
    }
    public void stopGame() {
        survivalTimer.stop();
        setVisible(false);
    }
    public int getScore() {
        return score;
    }
    public void resetScore() {
        score = 0;
        showScore();
    }
    // update the score on the display
    private void showScore() {
        setText(String.valueOf(score));
    }
    // add points when the player passes by a block it has never passed by
    public void addBlockPoints() {
        score += blockPoints;
        showScore();
    }
    // add points when two enemy collide
    public void addEnemyDestroyedPoints() {                           
        score += enemyDestroyedPoints;
        showScore();
    }
}
