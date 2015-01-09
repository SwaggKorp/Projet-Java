
package escape;

import com.apple.eawt.Application;
import javax.swing.ImageIcon;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * The main class that creates the window and handles user inputs (keyboard and menus)
 */
public class Escape extends javax.swing.JFrame {
    
    private FileManager fileManager;
    private Player player;
    
    // Creates new form Escape
    public Escape() {
        // use OS X menu bar
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Escape");
        // set OS X dock icon
        Application.getApplication().setDockIconImage(
                new ImageIcon(getClass().getResource("/app.png")).getImage());
        //initialize all components
        initComponents();
        // initialize open/save 
        fileManager = new FileManager(this, grid);
        // allow these components to access the window
        Character.setWindow(this);
        gameOverMenu.setWindow(this);
    }

    // auto-generated UI settings
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grid = new escape.Grid();
        gameOverMenu = new escape.GameOverMenu();
        scoreCounter = new escape.ScoreCounter();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newMenu = new javax.swing.JMenuItem();
        openMenu = new javax.swing.JMenuItem();
        saveMenu = new javax.swing.JMenuItem();
        saveasMenu = new javax.swing.JMenuItem();
        PlayerMenu = new javax.swing.JMenu();
        gameMode = new javax.swing.JMenuItem();
        editMode = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        grid.add(gameOverMenu);
        gameOverMenu.setBounds(70, 20, 397, 300);

        scoreCounter.setForeground(new java.awt.Color(255, 255, 255));
        scoreCounter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        grid.add(scoreCounter);
        scoreCounter.setBounds(0, 0, 70, 16);

        fileMenu.setText("File");

        newMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.META_MASK));
        newMenu.setText("New");
        newMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuActionPerformed(evt);
            }
        });
        fileMenu.add(newMenu);

        openMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.META_MASK));
        openMenu.setText("Open ...");
        openMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuActionPerformed(evt);
            }
        });
        fileMenu.add(openMenu);

        saveMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.META_MASK));
        saveMenu.setText("Save");
        saveMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenu);

        saveasMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.META_MASK));
        saveasMenu.setText("Save as ...");
        saveasMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveasMenuActionPerformed(evt);
            }
        });
        fileMenu.add(saveasMenu);

        menuBar.add(fileMenu);

        PlayerMenu.setText("Mode");

        gameMode.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.META_MASK));
        gameMode.setText("game");
        gameMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameModeActionPerformed(evt);
            }
        });
        PlayerMenu.add(gameMode);

        editMode.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.META_MASK));
        editMode.setText("edit");
        editMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editModeActionPerformed(evt);
            }
        });
        PlayerMenu.add(editMode);

        menuBar.add(PlayerMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(grid, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(grid, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveasMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveasMenuActionPerformed
        fileManager.saveFileDialog();
    }//GEN-LAST:event_saveasMenuActionPerformed

    private void newMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuActionPerformed
        fileManager.newFile();
    }//GEN-LAST:event_newMenuActionPerformed

    private void saveMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuActionPerformed
        fileManager.save();
    }//GEN-LAST:event_saveMenuActionPerformed

    private void openMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuActionPerformed
        fileManager.openFileDialog();
    }//GEN-LAST:event_openMenuActionPerformed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if(player!=null)
            player.stopMoving(Direction.fromKeyCode(evt.getKeyCode()));  
    }//GEN-LAST:event_formKeyReleased

    private void gameModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameModeActionPerformed
        newGame();
    }//GEN-LAST:event_gameModeActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if(player!=null)
            player.startMoving(Direction.fromKeyCode(evt.getKeyCode()));
    }//GEN-LAST:event_formKeyPressed

    private void editModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editModeActionPerformed
        gameOverMenu.setVisible(false);
        Enemy.reset();          // remove all enemies (and restart spawner)
        Enemy.freeze();         // suspend spawner
        grid.resetBlockColor(); // reset alive block colors
        if(player!=null)        // if a player was created
            player.destroy();
        player = null;
        grid.setEditable(true); 
    }//GEN-LAST:event_editModeActionPerformed

    public static void main(String args[]) {
        // Set the System look and feel
        try { 
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        // Create and display the form 
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Escape().setVisible(true);
                
            }
        });
    }
    // start a game
    public void newGame() {
        gameOverMenu.setVisible(false); 
        grid.setEditable(false);                       // make sure the game grid can't be edited during the game
        
        player = new Player(grid.getBlock(0, 0));      
        Enemy.reset();                                 // destroy all previous enemy and restart spawner
        grid.resetBlockColor();                        // reset alive block colors
        scoreCounter.startGame();                      // start counting score
    }
    // when the player was hit, show game over 
    public void displayGameOver() {
        Enemy.freeze();                                
        gameOverMenu.setVisible(true);
        
        player.destroy();
        player = null;
        scoreCounter.stopGame();
    }
    public ScoreCounter getScoreCounter() {
        return scoreCounter;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu PlayerMenu;
    private javax.swing.JMenuItem editMode;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem gameMode;
    private escape.GameOverMenu gameOverMenu;
    private escape.Grid grid;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newMenu;
    private javax.swing.JMenuItem openMenu;
    private javax.swing.JMenuItem saveMenu;
    private javax.swing.JMenuItem saveasMenu;
    private escape.ScoreCounter scoreCounter;
    // End of variables declaration//GEN-END:variables
}
