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
import java.io.IOException;
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

/**
 *
 * @author User
 */
public class Login extends javax.swing.JFrame {
    String dataStr = "";
    public static String methodName;
    public static String serverType;

    /**
     * Creates new form UDPPeer1
     */
    public Login() {
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
    private void scaleimage__backc_white_remove() {
        ImageIcon icon = new ImageIcon("");
        Image img = icon.getImage();
        Image imgscale = img.getScaledInstance(this.jLbl_LogIn.getWidth(), this.jLbl_LogIn.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgscale);
        jLbl_LogIn.setIcon(scaledIcon);
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
        String ID = this.jTxt_ID.getText();
        String password = new String(this.jPasswordField.getPassword());
        
        addParameter("ID", ID);
        addParameter("password", password);
        
        String urlStr = serverURL;
        try {
            String contentStr = "application/x-www-form-urlencoded";
            URL myURL = new URL(urlStr);
            URLConnection connection = myURL.openConnection();
            
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", contentStr);
            connection.setUseCaches(false);
            
            dataStr = "ID=" + ID + "&password=" + password;
            
            //Send the data (ID & Password) to the server.
            try (BufferedOutputStream output = new BufferedOutputStream(connection.getOutputStream())) {
                output.write(dataStr.getBytes());//"ACTION=add&NUMPTS=2&DATA=L0001\nL0002");
            } //"ACTION=add&NUMPTS=2&DATA=L0001\nL0002");
            
            
            //receive the response from the server.
            inputStream = connection.getInputStream();
            
            //Creating a BufferedReader object
            InputStreamReader isReader = new InputStreamReader(inputStream);
            
            BufferedReader reader = new BufferedReader(isReader);
            StringBuilder sb = new StringBuilder();
            String str;
            while((str = reader.readLine())!= null){
                sb.append(str); //extract the strings from the input stream and append them to a string.
            }
            if(!sb.toString().equals("Post: Please ensure to enter valid information!")) {
                new AvailableServices().setVisible(true);
                this.setVisible(false);
                displayNotification(sb.toString());
            }
            else {
                displayNotification(sb.toString());
            }
            inputStream.close();
        } 
        catch (IOException e) {
            System.out.println(e.toString());
        }
    }
    
    
        void sendDataByGet(String serverURL) {
        String ID = this.jTxt_ID.getText();
        String password = new String(this.jPasswordField.getPassword());
        try {
            String URLStr = serverURL + "?ID=" + ID + "&password=" + password;
            URL url = new URL(URLStr);
            URLConnection connection = url.openConnection();

            //receive the response from the server.
            InputStream inputStream = connection.getInputStream();
            
            //Creating a BufferedReader object
            InputStreamReader isReader = new InputStreamReader(inputStream);
            
            BufferedReader reader = new BufferedReader(isReader);
            StringBuilder sb = new StringBuilder();
            String str;
            while((str = reader.readLine())!= null){
                sb.append(str); //extract the strings from the input stream and append them to a string.
            }
            if(!sb.toString().equals("Get: Please ensure to enter valid information!")) {
                new AvailableServices().setVisible(true);
                this.setVisible(false);
                displayNotification(sb.toString());
            }
            else {
                displayNotification(sb.toString());
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
        jLbl_ID = new javax.swing.JLabel();
        jLbl_ReturnBack = new javax.swing.JLabel();
        jLbl_Password = new javax.swing.JLabel();
        jLbl_UpBar = new javax.swing.JLabel();
        jLbl_LogIn = new javax.swing.JLabel();
        jLbl_EmployeesGIF = new javax.swing.JLabel();
        jLbl_hidePass = new javax.swing.JLabel();
        jLbl_showPass = new javax.swing.JLabel();
        jTxt_ID = new javax.swing.JTextField();
        jPasswordField = new javax.swing.JPasswordField();
        jComboBox_ServerType = new javax.swing.JComboBox<>();
        jComboBox_MethodType = new javax.swing.JComboBox<>();

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

        jLbl_ID.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ID.png"))); // NOI18N
        jPanel1.add(jLbl_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 30, 30));

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

        jLbl_Password.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/secured-lock.png"))); // NOI18N
        jPanel1.add(jLbl_Password, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 30, 40));

        jLbl_UpBar.setBackground(new java.awt.Color(38, 50, 54));
        jLbl_UpBar.setOpaque(true);
        jPanel1.add(jLbl_UpBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 70));

        jLbl_LogIn.setFont(new java.awt.Font("Zilla Slab SemiBold", 0, 24)); // NOI18N
        jLbl_LogIn.setForeground(new java.awt.Color(0, 0, 0));
        jLbl_LogIn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbl_LogIn.setText("Log In");
        jLbl_LogIn.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(38, 50, 54)));
        jLbl_LogIn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLbl_LogIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbl_LogInMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLbl_LogInMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLbl_LogInMouseExited(evt);
            }
        });
        jPanel1.add(jLbl_LogIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 480, 210, 50));

        jLbl_EmployeesGIF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Employees.gif"))); // NOI18N
        jPanel1.add(jLbl_EmployeesGIF, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 550, 510));

        jLbl_hidePass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbl_hidePass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/hidePass.png"))); // NOI18N
        jLbl_hidePass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbl_hidePassMouseClicked(evt);
            }
        });
        jPanel1.add(jLbl_hidePass, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, 230, -1, 30));

        jLbl_showPass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbl_showPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/showPass.png"))); // NOI18N
        jLbl_showPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbl_showPassMouseClicked(evt);
            }
        });
        jPanel1.add(jLbl_showPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, 230, -1, 30));

        jTxt_ID.setBackground(new Color(255, 255, 255, 0));
        jTxt_ID.setFont(new java.awt.Font("Source Sans Pro SemiBold", 0, 22)); // NOI18N
        jTxt_ID.setForeground(new java.awt.Color(0, 0, 0));
        jTxt_ID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxt_ID.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(176, 190, 197)));
        jPanel1.add(jTxt_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 270, 40));

        jPasswordField.setBackground(new Color(255, 255, 255, 0));
        jPasswordField.setFont(new java.awt.Font("Source Sans Pro SemiBold", 0, 24)); // NOI18N
        jPasswordField.setForeground(new java.awt.Color(0, 0, 0));
        jPasswordField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPasswordField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(176, 190, 197)));
        jPanel1.add(jPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 228, 270, 40));

        jComboBox_ServerType.setBackground(new java.awt.Color(255, 255, 255,0));
        jComboBox_ServerType.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox_ServerType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose a Server", "Servlet Server", "PHP Server" }));
        jComboBox_ServerType.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));
        jPanel1.add(jComboBox_ServerType, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 280, 40));

        jComboBox_MethodType.setBackground(new java.awt.Color(255, 255, 255,0));
        jComboBox_MethodType.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox_MethodType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose a Method", "Post Method", "Get Method" }));
        jComboBox_MethodType.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));
        jPanel1.add(jComboBox_MethodType, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 400, 280, 40));

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

    private void jLbl_LogInMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_LogInMouseExited
        // TODO add your handling code here:
        scaleimage__backc_white_remove();
        jLbl_LogIn.setForeground(Color.black);
    }//GEN-LAST:event_jLbl_LogInMouseExited

    private void jLbl_LogInMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_LogInMouseEntered
        // TODO add your handling code here:
        scaleimage__backcwhite(jLbl_LogIn, "src\\Images\\Gray.PNG");
        jLbl_LogIn.setForeground(new Color(38,50,54));
    }//GEN-LAST:event_jLbl_LogInMouseEntered

    private void jLbl_LogInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_LogInMouseClicked
        // TODO add your handling code here:
        if (this.jTxt_ID.getText().equals("") || this.jPasswordField.getPassword().length == 0 
                || jComboBox_ServerType.getSelectedItem().toString().equals("Choose a Server")
                || jComboBox_MethodType.getSelectedItem().toString().equals("Choose a Method")) {
            displayNotification("Please make sure that all fields are filled out!");
        }
        else {
            //Ensure that the entered ID is integer.
            try {
                int isIntValue = Integer.parseInt(this.jTxt_ID.getText());
                
                //Check the type of the selected server, and selected method.
                if(jComboBox_ServerType.getSelectedItem().toString().equals("Servlet Server") &&
                   jComboBox_MethodType.getSelectedItem().toString().equals("Post Method")) {
                    //Send HTTP Request (Post) to the Servlet Server.
                    serverType = "Servlet";
                    methodName = "Post";
                    sendDataByPOST("http://localhost:8081/Servers/ServletServer");
                }
                
                else if(jComboBox_ServerType.getSelectedItem().toString().equals("Servlet Server") &&
                        jComboBox_MethodType.getSelectedItem().toString().equals("Get Method")) {
                    //Send HTTP Request (Get) to the Servlet Server.
                    serverType = "Servlet";
                    methodName = "Get";
                    sendDataByGet("http://localhost:8081/Servers/ServletServer");
                }
                
                //Send HTTP Request to the PHP Server.
                else if(jComboBox_ServerType.getSelectedItem().toString().equals("PHP Server") &&
                        jComboBox_MethodType.getSelectedItem().toString().equals("Post Method")) {
                    //Send HTTP Request (Post) to the PHP Server.
                    serverType = "PHP";
                    methodName = "Post";
                    sendDataByPOST("http://127.0.0.1:80/PHPServer.php");
                }
                
                else {
                    //Send HTTP Request (Get) to the PHP Server.
                    serverType = "PHP";
                    methodName = "Get";
                    sendDataByGet("http://127.0.0.1:80/PHPServer.php");
                }
            }
            catch (NumberFormatException e) {
                displayNotification("Please make sure to enter a valid ID!");
            }
        }
        
    }//GEN-LAST:event_jLbl_LogInMouseClicked

    private void jLbl_ReturnBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_ReturnBackMouseClicked
        new MainInterface().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLbl_ReturnBackMouseClicked

    private void jLbl_ReturnBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_ReturnBackMouseEntered
        scaleimage__backcwhite(jLbl_ReturnBack, "src\\Images\\GrayBack.png");
    }//GEN-LAST:event_jLbl_ReturnBackMouseEntered

    private void jLbl_ReturnBackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_ReturnBackMouseExited
        scaleimage__replaceButton(jLbl_ReturnBack, "src\\Images\\BlackBack.png");
    }//GEN-LAST:event_jLbl_ReturnBackMouseExited

    private void jLbl_showPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_showPassMouseClicked
        // TODO add your handling code here:
        jLbl_hidePass.setVisible(true);
        jLbl_showPass.setVisible(false);
        jPasswordField.setEchoChar((char) 0);
    }//GEN-LAST:event_jLbl_showPassMouseClicked

    private void jLbl_hidePassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_hidePassMouseClicked
        // TODO add your handling code here:
        jLbl_showPass.setVisible(true);
        jLbl_hidePass.setVisible(false);
        jPasswordField.setEchoChar('*');
    }//GEN-LAST:event_jLbl_hidePassMouseClicked

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox_MethodType;
    private javax.swing.JComboBox<String> jComboBox_ServerType;
    private javax.swing.JLabel jLbl_EmployeesGIF;
    private javax.swing.JLabel jLbl_ID;
    private javax.swing.JLabel jLbl_LogIn;
    private javax.swing.JLabel jLbl_Logo;
    private javax.swing.JLabel jLbl_Password;
    private javax.swing.JLabel jLbl_ReturnBack;
    private javax.swing.JLabel jLbl_Title;
    private javax.swing.JLabel jLbl_UpBar;
    private javax.swing.JLabel jLbl_hidePass;
    private javax.swing.JLabel jLbl_showPass;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JTextField jTxt_ID;
    // End of variables declaration//GEN-END:variables
}
