/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class AvailableServices extends javax.swing.JFrame {
    String dataStr = "";

    /**
     * Creates new form UDPPeer1
     */
    public AvailableServices() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
        //This function is a timer that specifies the time of showing the notification.
    private void callTimer(JFrame notification) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                notification.setVisible(false);
            }
        }, 6000);
    }
    
    //This function receives a message and displays it on a notification.
    private void displayNotification(String message) {
        Notification error = new Notification();
        error.setText(message);
        error.setVisible(true);
        callTimer(error);
    }
    
    
    //Function to replace the button with another one with a different color.
    private void scaleimage__replaceButton(JLabel label, String path) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image imgscale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgscale);
        label.setIcon(scaledIcon);
    }
    
    //A Function to make the label's background white when the mouse is entered.
    private void scaleimage__backcwhite(JLabel label, String path) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image imgscale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgscale);
        label.setIcon(scaledIcon);
    }

    //A Function to return the label's original background when the mouse is exited.
    private void scaleimage__backc_white_remove(JLabel label) {
        ImageIcon icon = new ImageIcon("");
        Image img = icon.getImage();
        Image imgscale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgscale);
        label.setIcon(scaledIcon);
    }
    
    
        private void addParameter(String name, String value) {
        if (name == null || value == null || name.length() == 0 || value.length() == 0) {
            return;
        }
        if (dataStr.length() > 0) {
            dataStr += "&";
        }
        try {
            dataStr += name + "=" + URLEncoder.encode(value, "ASCII");
        } 
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    
    private void sendDataByPOST(String serverURL) {
        InputStream inputStream;
        String products = "products";
        
        addParameter("products", products);
        String urlStr = serverURL;
        try {
            String contentStr = "application/x-www-form-urlencoded";
            URL myURL = new URL(urlStr);
            URLConnection connection = myURL.openConnection();
            
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", contentStr);
            connection.setUseCaches(false);
            
            dataStr = "products=" + products;
            
            //Send the data (ID & Password) to the server.
            try (BufferedOutputStream output = new BufferedOutputStream(connection.getOutputStream())) {
                output.write(dataStr.getBytes());//"ACTION=add&NUMPTS=2&DATA=L0001\nL0002");
            } //"ACTION=add&NUMPTS=2&DATA=L0001\nL0002");
            
            
            //receive the response from the server.
            inputStream = connection.getInputStream();
            
            //Creating a BufferedReader object
            InputStreamReader isReader = new InputStreamReader(inputStream);
            
            BufferedReader reader = new BufferedReader(isReader);
            String str = reader.readLine();
            if(str.equals("Mersakom doesn't have any products yet!")) {
                displayNotification(str);
            }
            else {
                ProductsTable table = new ProductsTable();
                DefaultTableModel model = (DefaultTableModel) table.getTableName().getModel();
                model.setRowCount(0);
                while(str != null) {
                    String[] tableFields=str.split(":");
                    String spc = "                ";
                    model.addRow(new Object[]{spc + tableFields[0], spc + tableFields[1], spc + tableFields[2], spc + tableFields[3]});
                    str = reader.readLine();
                }
                table.setVisible(true);
                this.setVisible(false);
            }
           
            inputStream.close();
        } 
        catch (Exception e) {
            System.out.println(e.toString());
        }

    }
    
    
        void sendDataByGet(String serverURL) {
            String products = "products";
        try {
            String URLStr = serverURL + "?products=" + products;
            URL url = new URL(URLStr);
            URLConnection connection = url.openConnection();

            //receive the response from the server.
            InputStream inputStream = connection.getInputStream();
            
            //Creating a BufferedReader object
            InputStreamReader isReader = new InputStreamReader(inputStream);
            
            BufferedReader reader = new BufferedReader(isReader);
            StringBuilder sb = new StringBuilder();
            String str = reader.readLine();
            if(str.equals("Mersakom doesn't have any products yet!")) {
                displayNotification(str);
            }
            else {
                ProductsTable table = new ProductsTable();
                DefaultTableModel model = (DefaultTableModel) table.getTableName().getModel();
                model.setRowCount(0);
                while(str != null) {
                    String[] tableFields=str.split(":");
                    String spc = "                ";
                    model.addRow(new Object[]{spc + tableFields[0], spc + tableFields[1], spc + tableFields[2], spc + tableFields[3]});
                    str = reader.readLine();
                }
                table.setVisible(true);
                this.setVisible(false);
            }
           
            inputStream.close();
        } 
        catch (Exception e) {
            System.out.println(e.toString());
        }
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
        jLbl_ReturnBack = new javax.swing.JLabel();
        jLbl_UpBar = new javax.swing.JLabel();
        jLbl_PerformOperations = new javax.swing.JLabel();
        jLbl_EmployeesGIF = new javax.swing.JLabel();
        jLbl_ShowProducts = new javax.swing.JLabel();

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

        jLbl_ReturnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/BlackBack.png"))); // NOI18N
        jLbl_ReturnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbl_ReturnBackMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLbl_ReturnBackMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLbl_ReturnBackMouseExited(evt);
            }
        });
        jPanel1.add(jLbl_ReturnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 540, 35, 28));

        jLbl_UpBar.setBackground(new java.awt.Color(38, 50, 54));
        jLbl_UpBar.setOpaque(true);
        jPanel1.add(jLbl_UpBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 70));

        jLbl_PerformOperations.setFont(new java.awt.Font("Zilla Slab SemiBold", 0, 24)); // NOI18N
        jLbl_PerformOperations.setForeground(new java.awt.Color(0, 0, 0));
        jLbl_PerformOperations.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbl_PerformOperations.setText("Perform operations");
        jLbl_PerformOperations.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(38, 50, 54)));
        jLbl_PerformOperations.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLbl_PerformOperations.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbl_PerformOperationsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLbl_PerformOperationsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLbl_PerformOperationsMouseExited(evt);
            }
        });
        jPanel1.add(jLbl_PerformOperations, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, 240, 50));

        jLbl_EmployeesGIF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Employees.gif"))); // NOI18N
        jPanel1.add(jLbl_EmployeesGIF, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 550, 510));

        jLbl_ShowProducts.setFont(new java.awt.Font("Zilla Slab SemiBold", 0, 24)); // NOI18N
        jLbl_ShowProducts.setForeground(new java.awt.Color(0, 0, 0));
        jLbl_ShowProducts.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbl_ShowProducts.setText("Show Products");
        jLbl_ShowProducts.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(38, 50, 54)));
        jLbl_ShowProducts.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLbl_ShowProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbl_ShowProductsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLbl_ShowProductsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLbl_ShowProductsMouseExited(evt);
            }
        });
        jPanel1.add(jLbl_ShowProducts, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 240, 50));

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

    private void jLbl_ReturnBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_ReturnBackMouseClicked
        new Login().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLbl_ReturnBackMouseClicked

    private void jLbl_ReturnBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_ReturnBackMouseEntered
        scaleimage__backcwhite(jLbl_ReturnBack, "src\\Images\\GrayBack.png");
    }//GEN-LAST:event_jLbl_ReturnBackMouseEntered

    private void jLbl_ReturnBackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_ReturnBackMouseExited
        scaleimage__replaceButton(jLbl_ReturnBack, "src\\Images\\BlackBack.png");
    }//GEN-LAST:event_jLbl_ReturnBackMouseExited

    private void jLbl_PerformOperationsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_PerformOperationsMouseExited
        // TODO add your handling code here:
        scaleimage__backc_white_remove(jLbl_PerformOperations);
        jLbl_PerformOperations.setForeground(Color.black);
    }//GEN-LAST:event_jLbl_PerformOperationsMouseExited

    private void jLbl_PerformOperationsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_PerformOperationsMouseEntered
        // TODO add your handling code here:
        scaleimage__backcwhite(jLbl_PerformOperations, "src\\Images\\Gray.PNG");
        jLbl_PerformOperations.setForeground(new Color(38,50,54));
    }//GEN-LAST:event_jLbl_PerformOperationsMouseEntered

    private void jLbl_PerformOperationsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_PerformOperationsMouseClicked
        // TODO add your handling code here:
        new UpdatesInterface().setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_jLbl_PerformOperationsMouseClicked

    private void jLbl_ShowProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_ShowProductsMouseClicked
        // TODO add your handling code here:
        if(Login.methodName.equals("Post") && Login.serverType.equals("Servlet")) {
            sendDataByPOST("http://localhost:8081/Servers/ServletServer");
        }
        else if(Login.methodName.equals("Get") && Login.serverType.equals("Servlet")) {
            sendDataByGet("http://localhost:8081/Servers/ServletServer");
        }
        else if(Login.methodName.equals("Post") && Login.serverType.equals("PHP")) {
            sendDataByPOST("http://127.0.0.1:80/PHPServer.php");
        }
        else {
            sendDataByGet("http://127.0.0.1:80/PHPServer.php");
        }
    }//GEN-LAST:event_jLbl_ShowProductsMouseClicked

    private void jLbl_ShowProductsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_ShowProductsMouseEntered
        // TODO add your handling code here:
        scaleimage__backcwhite(jLbl_ShowProducts, "src\\Images\\Gray.PNG");
        jLbl_ShowProducts.setForeground(new Color(38,50,54));
    }//GEN-LAST:event_jLbl_ShowProductsMouseEntered

    private void jLbl_ShowProductsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_ShowProductsMouseExited
        // TODO add your handling code here:
        scaleimage__backc_white_remove(jLbl_ShowProducts);
        jLbl_ShowProducts.setForeground(Color.black);
    }//GEN-LAST:event_jLbl_ShowProductsMouseExited

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
            java.util.logging.Logger.getLogger(AvailableServices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AvailableServices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AvailableServices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AvailableServices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AvailableServices().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLbl_EmployeesGIF;
    private javax.swing.JLabel jLbl_Logo;
    private javax.swing.JLabel jLbl_PerformOperations;
    private javax.swing.JLabel jLbl_ReturnBack;
    private javax.swing.JLabel jLbl_ShowProducts;
    private javax.swing.JLabel jLbl_Title;
    private javax.swing.JLabel jLbl_UpBar;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
