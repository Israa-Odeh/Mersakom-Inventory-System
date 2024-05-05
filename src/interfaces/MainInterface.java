/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author User
 */
public class MainInterface extends javax.swing.JFrame {

    /**
     * Creates new form UDPPeer1
     */
    public MainInterface() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
    
    
    //A Function to make the label's background white when the mouse is entered.
    public void scaleimage__backcwhite(JLabel label, String path) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image imgscale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgscale);
        label.setIcon(scaledIcon);
    }

    //A Function to return the label's original background when the mouse is exited.
    public void scaleimage__backc_white_remove() {
        ImageIcon icon = new ImageIcon("");
        Image img = icon.getImage();
        Image imgscale = img.getScaledInstance(this.jLbl_EnterApp.getWidth(), this.jLbl_EnterApp.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgscale);
        jLbl_EnterApp.setIcon(scaledIcon);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLbl_Logo = new javax.swing.JLabel();
        jLbl_Title = new javax.swing.JLabel();
        jLbl_UpBar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLbl_EnterApp = new javax.swing.JLabel();
        jLbl_InventoryGIF = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mersakom Inventory System");
        setIconImage(new ImageIcon("src\\Images\\Logo Mersal.png").getImage());
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLbl_Logo.setFont(new java.awt.Font("Script MT Bold", 1, 17)); // NOI18N
        jLbl_Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Logo.png"))); // NOI18N
        jPanel1.add(jLbl_Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 130, 70));

        jLbl_Title.setFont(new java.awt.Font("Century Gothic", 3, 45)); // NOI18N
        jLbl_Title.setForeground(new java.awt.Color(255, 255, 255));
        jLbl_Title.setText("Mersakom");
        jPanel1.add(jLbl_Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 250, 70));

        jLbl_UpBar.setBackground(new java.awt.Color(38, 50, 54));
        jLbl_UpBar.setOpaque(true);
        jPanel1.add(jLbl_UpBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 70));

        jLabel1.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        jLabel1.setText("your existing products by using");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 290, 60));

        jLabel2.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        jLabel2.setText("Mersakom application!");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, 220, 40));

        jLabel3.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        jLabel3.setText("Stay productive and stay on top of all");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 350, 60));

        jLbl_EnterApp.setFont(new java.awt.Font("Zilla Slab SemiBold", 0, 24)); // NOI18N
        jLbl_EnterApp.setForeground(new java.awt.Color(0, 0, 0));
        jLbl_EnterApp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbl_EnterApp.setText("Get Started");
        jLbl_EnterApp.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(38, 50, 54)));
        jLbl_EnterApp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLbl_EnterApp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbl_EnterAppMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLbl_EnterAppMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLbl_EnterAppMouseExited(evt);
            }
        });
        jPanel1.add(jLbl_EnterApp, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, 210, 50));

        jLbl_InventoryGIF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/InventorySystem.gif"))); // NOI18N
        jPanel1.add(jLbl_InventoryGIF, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 720, 510));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLbl_EnterAppMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_EnterAppMouseExited
        // TODO add your handling code here:
        scaleimage__backc_white_remove();
        jLbl_EnterApp.setForeground(Color.black);
    }//GEN-LAST:event_jLbl_EnterAppMouseExited

    private void jLbl_EnterAppMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_EnterAppMouseEntered
        // TODO add your handling code here:
        scaleimage__backcwhite(jLbl_EnterApp, "src\\Images\\lightGreen.PNG");
        jLbl_EnterApp.setForeground(new Color(38,50,54));
    }//GEN-LAST:event_jLbl_EnterAppMouseEntered

    private void jLbl_EnterAppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_EnterAppMouseClicked
        // TODO add your handling code here:
        new Login().setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_jLbl_EnterAppMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainInterface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLbl_EnterApp;
    private javax.swing.JLabel jLbl_InventoryGIF;
    private javax.swing.JLabel jLbl_Logo;
    private javax.swing.JLabel jLbl_Title;
    private javax.swing.JLabel jLbl_UpBar;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}