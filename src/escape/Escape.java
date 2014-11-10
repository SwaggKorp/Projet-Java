
package escape;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.Timer;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Escape extends javax.swing.JFrame {
    private FileManager fileManager;
    private Player player;
    private Timer arrowsManager;
    private Timer enemyManager;
    private int arrowKeyCode;
    private Enemy enemy;
    private Enemy enemy1;
    PathFinder finder;
    
    // Creates new form Escape
    public Escape() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Escape");
        
        initComponents();
        this.setTitle("Escape - New file");
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Escape file","ecp");
        jFileChooser1.addChoosableFileFilter(filter);
        jFileChooser1.setFileFilter(filter);
        fileManager = new FileManager(this, grid1);
        arrowsManager = new Timer(90, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                 player.keyPressed(arrowKeyCode);
            }
        });
        enemyManager = new Timer(200, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                 enemy.move();
                 enemy1.move();
            }
        });
        
        finder = new PathFinder(grid1);
    }

    // auto-generated UI settings
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        grid1 = new escape.EscapeGrid();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        newMenu = new javax.swing.JMenuItem();
        openMenu = new javax.swing.JMenuItem();
        saveMenu = new javax.swing.JMenuItem();
        saveasMenu = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
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

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.META_MASK));
        jMenuItem1.setText("test");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem1);

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(grid1, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(grid1, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveasMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveasMenuActionPerformed
        int result = jFileChooser1.showSaveDialog(this);
        if(result == JFileChooser.APPROVE_OPTION) {
            fileManager.save(jFileChooser1.getSelectedFile().getPath());
        }
    }//GEN-LAST:event_saveasMenuActionPerformed

    private void newMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuActionPerformed
        grid1.clear();
        fileManager.newFile();
    }//GEN-LAST:event_newMenuActionPerformed

    private void saveMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuActionPerformed
        if(fileManager.isNewFile()) {
            int result = jFileChooser1.showSaveDialog(this);
            if(result == JFileChooser.APPROVE_OPTION)
                fileManager.save(jFileChooser1.getSelectedFile().getPath()+".ecp");
        } else { 
            fileManager.save();
        }
    }//GEN-LAST:event_saveMenuActionPerformed

    private void openMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuActionPerformed
        int result = jFileChooser1.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION) {
            fileManager.open(jFileChooser1.getSelectedFile().getPath());
        }
    }//GEN-LAST:event_openMenuActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        finder.shortedPath(player.getBlock().getPosition()[0], player.getBlock().getPosition()[1], grid1.getGridWidth()-1, grid1.getGridHeight()-1);
        grid1.repaint();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if(evt.getKeyCode()==arrowKeyCode)
            arrowsManager.stop();  
    }//GEN-LAST:event_formKeyReleased

    private void spawnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spawnActionPerformed
        player = new Player(grid1.getBlock(0, 0),grid1);
        enemy = new Enemy(grid1.getBlock(grid1.getGridWidth()-1, grid1.getGridHeight()-1),grid1,player,finder);
        enemy1 = new Enemy(grid1.getBlock(0, grid1.getGridHeight()-1),grid1,player,finder);
        enemyManager.start();
    }//GEN-LAST:event_spawnActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if(player!=null) {
            if(evt.getKeyCode()!=arrowKeyCode) {
                player.keyPressed(evt.getKeyCode());
                arrowKeyCode = evt.getKeyCode();
                arrowsManager.start();
            } else if(!arrowsManager.isRunning()) {
                player.keyPressed(evt.getKeyCode());
                arrowKeyCode = evt.getKeyCode();
                arrowsManager.start();
            } 
        }
    }//GEN-LAST:event_formKeyPressed
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // Set the System look and feel
        try { 
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Escape.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Create and display the form 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Escape().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu PlayerMenu;
    private escape.EscapeGrid grid1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem newMenu;
    private javax.swing.JMenuItem openMenu;
    private javax.swing.JMenuItem saveMenu;
    private javax.swing.JMenuItem saveasMenu;
    private javax.swing.JMenuItem spawn;
    // End of variables declaration//GEN-END:variables
}
