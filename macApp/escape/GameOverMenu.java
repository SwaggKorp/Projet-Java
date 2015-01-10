
package escape;

/*
 * Create and display the game over sub window
 */
public class GameOverMenu extends javax.swing.JPanel {
    private Escape window;
    private ScoreCounter score;
    
    // Creates new form GameOverMenu
    public GameOverMenu() {
        initComponents();
        setFocusable(false);               // make sure the component can't be selected (will absorb key events)
        setVisible(false);
        jButton1.setFocusable(false);
        jLabel1.setFocusable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(81, 81, 81));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/escape/gameover.jpg"))); // NOI18N

        jButton1.setFont(new java.awt.Font("Lucida Grande", 0, 23)); // NOI18N
        jButton1.setText("RETRY");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Score :");

        scoreLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        scoreLabel.setForeground(new java.awt.Color(255, 255, 255));
        scoreLabel.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        window.newGame();
    }//GEN-LAST:event_jButton1ActionPerformed
    public void setWindow(Escape window) {
        this.window = window;
        score = window.getScoreCounter();
    }
    // when the game over menu is displayed, make sure it is centered and show score
    @Override
    public void setVisible(boolean isVisible) {
        super.setVisible(isVisible);
        if(isVisible) {
            // center sub window
            int x = (window.getWidth()-385)/2;
            int y = (window.getHeight()-295)/2;
            this.setBounds(x, y, 385, 295);
            //update score
            scoreLabel.setText(String.valueOf(score.getScore()));
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel scoreLabel;
    // End of variables declaration//GEN-END:variables
}
