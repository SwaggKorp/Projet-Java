
package escape;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author arnaud
 */
public class Escape extends javax.swing.JFrame {
    FileManager fileManager;
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
    private escape.EscapeGrid grid1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem newMenu;
    private javax.swing.JMenuItem openMenu;
    private javax.swing.JMenuItem saveMenu;
    private javax.swing.JMenuItem saveasMenu;
    // End of variables declaration//GEN-END:variables
}
