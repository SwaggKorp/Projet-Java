
package escape;

import com.apple.eawt.Application;
import javax.swing.ImageIcon;
import javax.swing.UnsupportedLookAndFeelException;

public class Escape extends javax.swing.JFrame {
    
    private FileManager fileManager;
    private Player player;
    
    // Creates new form Escape
    public Escape() {
        // use OS X menu bar
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Escape");
        // set OS X dock icon
        Application.getApplication().setDockIconImage(new ImageIcon("src/Escape/app.png").getImage());
        //initialize all components
        initComponents();
        // initialize open/save 
        fileManager = new FileManager(this, grid);
        
        Character.setWindow(this);
        gameOverMenu.setWindow(this);
    }

    // auto-generated UI settings
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grid = new escape.Grid();
        gameOverMenu = new escape.GameOverMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        newMenu = new javax.swing.JMenuItem();
        openMenu = new javax.swing.JMenuItem();
        saveMenu = new javax.swing.JMenuItem();
        saveasMenu = new javax.swing.JMenuItem();
        PlayerMenu = new javax.swing.JMenu();
        spawn = new javax.swing.JMenuItem();

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

        jMenu5.setText("File");

        newMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.META_MASK));
        newMenu.setText("New");
        newMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuActionPerformed(evt);
            }
        });
        jMenu5.add(newMenu);

        openMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.META_MASK));
        openMenu.setText("Open ...");
        openMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuActionPerformed(evt);
            }
        });
        jMenu5.add(openMenu);

        saveMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.META_MASK));
        saveMenu.setText("Save");
        saveMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuActionPerformed(evt);
            }
        });
        jMenu5.add(saveMenu);

        saveasMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.META_MASK));
        saveasMenu.setText("Save as ...");
        saveasMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveasMenuActionPerformed(evt);
            }
        });
        jMenu5.add(saveasMenu);

        jMenuBar3.add(jMenu5);

        PlayerMenu.setText("Player");

        spawn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.META_MASK));
        spawn.setText("Spawn");
        spawn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spawnActionPerformed(evt);
            }
        });
        PlayerMenu.add(spawn);

        jMenuBar3.add(PlayerMenu);

        setJMenuBar(jMenuBar3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(grid, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(grid, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
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

    private void spawnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spawnActionPerformed
        newGame();
    }//GEN-LAST:event_spawnActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if(player!=null)
            player.startMoving(Direction.fromKeyCode(evt.getKeyCode()));
    }//GEN-LAST:event_formKeyPressed
    /**
     * @param args the command line arguments
     */
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
    
    public void newGame() {
        gameOverMenu.setVisible(false);
        grid.setEditable(false);
        
        player = new Player(grid.getBlock(0, 0),this);
        Enemy.reset();
    }
    public void displayGameOver() {
        Enemy.freeze();
        gameOverMenu.resize();
        gameOverMenu.setVisible(true);
        
        player.destroy();
        player = null;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu PlayerMenu;
    private escape.GameOverMenu gameOverMenu;
    private escape.Grid grid;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem newMenu;
    private javax.swing.JMenuItem openMenu;
    private javax.swing.JMenuItem saveMenu;
    private javax.swing.JMenuItem saveasMenu;
    private javax.swing.JMenuItem spawn;
    // End of variables declaration//GEN-END:variables
}
