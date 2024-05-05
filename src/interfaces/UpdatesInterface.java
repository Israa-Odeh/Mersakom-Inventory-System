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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author User
 */
public class UpdatesInterface extends javax.swing.JFrame {
    String dataStr = "";
    private int priceInUSD;

    /**
     * Creates new form UDPPeer1
     */
    public UpdatesInterface() {
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
    
    
    private void sendDataByPOST(String source, String serverURL) {
        InputStream inputStream;
        String PID = this.jTxt_PID.getText();
        if(source.equals("View")) {
            addParameter("PID", PID);   
            dataStr = "PID=" + PID;
        }
        else {
            String amount = jTxt_Amount.getText();
            addParameter("PID", PID);
            addParameter("amount", amount);
            if(source.equals("Insert")) {
                addParameter("Insert", source);
                dataStr = "PID=" + PID + "&amount=" + amount + "&Insert=" + source;
            }
            else {
                addParameter("Withdraw", source);
                dataStr = "PID=" + PID + "&amount=" + amount + "&Withdraw=" + source;
            }
        }
        
        String urlStr = serverURL;
        try {
            String contentStr = "application/x-www-form-urlencoded";
            URL myURL = new URL(urlStr);
            URLConnection connection = myURL.openConnection();
            
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", contentStr);
            connection.setUseCaches(false);
            
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
            if (source.equals("View")) {
                if (str.equals("There is no such product with this ID!")) {
                    displayNotification(str);
                } 
                else {
                    String[] productInfo = str.split(":");
                    jTxt_Pname.setText(productInfo[0]);
                    jTxt_Amount.setText(productInfo[1]);
                    jTxt_Price.setText(productInfo[2]);
                    jTB_currencyType.setEnabled(true);
                    priceInUSD = Integer.parseInt(jTxt_Price.getText());
                }
            } 
            else if (source.equals("Insert")) {
                displayNotification(str);
            } 
            else {
                displayNotification(str);
            }
            inputStream.close();
        } 
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    
    private void sendDataByGet(String source, String serverURL) {
        String PID = this.jTxt_PID.getText();
        try {
            String URLStr = null;
            if (source.equals("View")) {
                URLStr = serverURL + "?PID=" + PID;
            } 
        
            else {
                String amount = jTxt_Amount.getText();
                if(source.equals("Insert")) {
                    URLStr = serverURL + "?PID=" + PID + "&amount=" + amount + "&Insert=" + source;
                }
                else {
                    URLStr = serverURL + "?PID=" + PID + "&amount=" + amount + "&Withdraw=" + source;
                }
            }
            URL url = new URL(URLStr);
            URLConnection connection = url.openConnection();

            //receive the response from the server.
            InputStream inputStream = connection.getInputStream();
            
            //Creating a BufferedReader object
            InputStreamReader isReader = new InputStreamReader(inputStream);
            
            BufferedReader reader = new BufferedReader(isReader);
            String str = reader.readLine();
            if (source.equals("View")) {
                if (str.equals("There is no such product with this ID!")) {
                    displayNotification(str);
                } 
                else {
                    String[] productInfo = str.split(":");
                    jTxt_Pname.setText(productInfo[0]);
                    jTxt_Amount.setText(productInfo[1]);
                    jTxt_Price.setText(productInfo[2]);
                    jTB_currencyType.setEnabled(true);
                    priceInUSD = Integer.parseInt(jTxt_Price.getText());
                }
            } 
            else if (source.equals("Insert")) {
                displayNotification(str);
            } 
            else {
                displayNotification(str);
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
        jLbl_Image = new javax.swing.JLabel();
        jTB_currencyType = new javax.swing.JToggleButton();
        jTxt_PID = new javax.swing.JTextField();
        jTxt_Pname = new javax.swing.JTextField();
        jTxt_Amount = new javax.swing.JTextField();
        jTxt_Price = new javax.swing.JTextField();
        jLbl_View = new javax.swing.JLabel();
        jLbl_Insert = new javax.swing.JLabel();
        jLbl_Withdraw = new javax.swing.JLabel();
        jLbl_price = new javax.swing.JLabel();
        jLbl_ID = new javax.swing.JLabel();
        jLbl_name = new javax.swing.JLabel();
        jLbl_amount = new javax.swing.JLabel();
        jLbl_clear = new javax.swing.JLabel();

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

        jLbl_Image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/MersakomSystem.png"))); // NOI18N
        jPanel1.add(jLbl_Image, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, 490, 510));

        jTB_currencyType.setBackground(new java.awt.Color(255, 255, 255));
        jTB_currencyType.setFont(new java.awt.Font("Zilla Slab SemiBold", 0, 20)); // NOI18N
        jTB_currencyType.setText("USD");
        jTB_currencyType.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(176, 190, 197)));
        jTB_currencyType.setEnabled(false);
        jTB_currencyType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jTB_currencyTypeItemStateChanged(evt);
            }
        });
        jPanel1.add(jTB_currencyType, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 320, 60, 30));

        jTxt_PID.setBackground(new Color(255, 255, 255, 0));
        jTxt_PID.setFont(new java.awt.Font("Source Sans Pro SemiBold", 0, 22)); // NOI18N
        jTxt_PID.setForeground(new java.awt.Color(0, 0, 0));
        jTxt_PID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxt_PID.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(176, 190, 197)));
        jPanel1.add(jTxt_PID, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 270, 40));

        jTxt_Pname.setEditable(false);
        jTxt_Pname.setBackground(new Color(255, 255, 255, 0));
        jTxt_Pname.setFont(new java.awt.Font("Source Sans Pro SemiBold", 0, 22)); // NOI18N
        jTxt_Pname.setForeground(new java.awt.Color(0, 0, 0));
        jTxt_Pname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxt_Pname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(176, 190, 197)));
        jPanel1.add(jTxt_Pname, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 270, 40));

        jTxt_Amount.setBackground(new Color(255, 255, 255, 0));
        jTxt_Amount.setFont(new java.awt.Font("Source Sans Pro SemiBold", 0, 22)); // NOI18N
        jTxt_Amount.setForeground(new java.awt.Color(0, 0, 0));
        jTxt_Amount.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxt_Amount.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(176, 190, 197)));
        jPanel1.add(jTxt_Amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 270, 40));

        jTxt_Price.setEditable(false);
        jTxt_Price.setBackground(new Color(255, 255, 255, 0));
        jTxt_Price.setFont(new java.awt.Font("Source Sans Pro SemiBold", 0, 22)); // NOI18N
        jTxt_Price.setForeground(new java.awt.Color(0, 0, 0));
        jTxt_Price.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxt_Price.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(176, 190, 197)));
        jPanel1.add(jTxt_Price, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 260, 40));

        jLbl_View.setFont(new java.awt.Font("Zilla Slab SemiBold", 0, 23)); // NOI18N
        jLbl_View.setForeground(new java.awt.Color(0, 0, 0));
        jLbl_View.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbl_View.setText("View Product");
        jLbl_View.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(38, 50, 54)));
        jLbl_View.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLbl_View.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbl_ViewMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLbl_ViewMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLbl_ViewMouseExited(evt);
            }
        });
        jPanel1.add(jLbl_View, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 180, 50));

        jLbl_Insert.setFont(new java.awt.Font("Zilla Slab SemiBold", 0, 23)); // NOI18N
        jLbl_Insert.setForeground(new java.awt.Color(0, 0, 0));
        jLbl_Insert.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbl_Insert.setText("Insert Items");
        jLbl_Insert.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(38, 50, 54)));
        jLbl_Insert.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLbl_Insert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbl_InsertMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLbl_InsertMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLbl_InsertMouseExited(evt);
            }
        });
        jPanel1.add(jLbl_Insert, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 400, 180, 50));

        jLbl_Withdraw.setFont(new java.awt.Font("Zilla Slab SemiBold", 0, 23)); // NOI18N
        jLbl_Withdraw.setForeground(new java.awt.Color(0, 0, 0));
        jLbl_Withdraw.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbl_Withdraw.setText("Withdraw Items");
        jLbl_Withdraw.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(38, 50, 54)));
        jLbl_Withdraw.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLbl_Withdraw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbl_WithdrawMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLbl_WithdrawMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLbl_WithdrawMouseExited(evt);
            }
        });
        jPanel1.add(jLbl_Withdraw, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 180, 50));

        jLbl_price.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Price.png"))); // NOI18N
        jPanel1.add(jLbl_price, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 320, 30, 30));

        jLbl_ID.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ID.png"))); // NOI18N
        jPanel1.add(jLbl_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 30, 30));

        jLbl_name.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Picture1.png"))); // NOI18N
        jPanel1.add(jLbl_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 30, 30));

        jLbl_amount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/amount.png"))); // NOI18N
        jPanel1.add(jLbl_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 30, 30));

        jLbl_clear.setFont(new java.awt.Font("Zilla Slab SemiBold", 0, 23)); // NOI18N
        jLbl_clear.setForeground(new java.awt.Color(0, 0, 0));
        jLbl_clear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbl_clear.setText("Clear Fields");
        jLbl_clear.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(38, 50, 54)));
        jLbl_clear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLbl_clear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbl_clearMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLbl_clearMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLbl_clearMouseExited(evt);
            }
        });
        jPanel1.add(jLbl_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 460, 180, 50));

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
        new AvailableServices().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLbl_ReturnBackMouseClicked

    private void jLbl_ReturnBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_ReturnBackMouseEntered
        scaleimage__backcwhite(jLbl_ReturnBack, "src\\Images\\GrayBack.png");
    }//GEN-LAST:event_jLbl_ReturnBackMouseEntered

    private void jLbl_ReturnBackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_ReturnBackMouseExited
        scaleimage__replaceButton(jLbl_ReturnBack, "src\\Images\\BlackBack.png");
    }//GEN-LAST:event_jLbl_ReturnBackMouseExited

    private void jLbl_InsertMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_InsertMouseExited
        // TODO add your handling code here:
        scaleimage__backc_white_remove(jLbl_Insert);
        jLbl_Insert.setForeground(Color.black);
    }//GEN-LAST:event_jLbl_InsertMouseExited

    private void jLbl_InsertMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_InsertMouseEntered
        // TODO add your handling code here:
        scaleimage__backcwhite(jLbl_Insert, "src\\Images\\lightOrange.PNG");
        jLbl_Insert.setForeground(new Color(38,50,54));
    }//GEN-LAST:event_jLbl_InsertMouseEntered

    private void jLbl_InsertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_InsertMouseClicked
        // TODO add your handling code here:
        if (!jTxt_PID.getText().equals("") && !jTxt_Amount.getText().equals("")) {
            try {
                int isIntValue = Integer.parseInt(jTxt_PID.getText());
                isIntValue = Integer.parseInt(jTxt_Amount.getText());
                if(isIntValue < 0) {
                    displayNotification("Please ensure to enter non-negative amount!");
                } 
                else {
                    if(Login.methodName.equals("Post") && Login.serverType.equals("Servlet")) {
                        sendDataByPOST("Insert", "http://localhost:8081/Servers/ServletServer");
                    } 
                    else if(Login.methodName.equals("Get") && Login.serverType.equals("Servlet")) {
                        sendDataByGet("Insert", "http://localhost:8081/Servers/ServletServer");
                    } 
                    else if(Login.methodName.equals("Post") && Login.serverType.equals("PHP")) {
                        sendDataByPOST("Insert", "http://127.0.0.1:80/PHPServer.php");
                    } 
                    else {
                        sendDataByGet("Insert", "http://127.0.0.1:80/PHPServer.php");
                    }
                }
            }
            catch (NumberFormatException e) {
                displayNotification("Please make sure to enter valid values!");
            }
        } 
        else {
            displayNotification("Enter both the product's ID and the amount!");
        }
    }//GEN-LAST:event_jLbl_InsertMouseClicked

    private void jLbl_ViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_ViewMouseClicked
        // TODO add your handling code here:
        if(!jTxt_PID.getText().equals("") && jTxt_Amount.getText().equals("")) {
            try {
                int isIntValue = Integer.parseInt(jTxt_PID.getText());
                if(Login.methodName.equals("Post") && Login.serverType.equals("Servlet")) {
                    sendDataByPOST("View" ,"http://localhost:8081/Servers/ServletServer");
                } 
                else if(Login.methodName.equals("Get") && Login.serverType.equals("Servlet")) {
                    sendDataByGet("View" ,"http://localhost:8081/Servers/ServletServer");
                } 
                else if(Login.methodName.equals("Post") && Login.serverType.equals("PHP")) {
                    sendDataByPOST("View" ,"http://127.0.0.1:80/PHPServer.php");
                } 
                else {
                    sendDataByGet("View" ,"http://127.0.0.1:80/PHPServer.php");
                }
            } 
            catch (NumberFormatException e) {
                displayNotification("Please make sure to enter a valid ID!");
            }
        }
        else {
            displayNotification("Please ensure to enter the product ID only!");
        }
        
    }//GEN-LAST:event_jLbl_ViewMouseClicked

    private void jLbl_ViewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_ViewMouseEntered
        // TODO add your handling code here:
        scaleimage__backcwhite(jLbl_View, "src\\Images\\lightOrange.PNG");
        jLbl_View.setForeground(new Color(38,50,54));
    }//GEN-LAST:event_jLbl_ViewMouseEntered

    private void jLbl_ViewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_ViewMouseExited
        // TODO add your handling code here:
        scaleimage__backc_white_remove(jLbl_View);
        jLbl_View.setForeground(Color.black);
    }//GEN-LAST:event_jLbl_ViewMouseExited

    private void jLbl_WithdrawMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_WithdrawMouseClicked
        // TODO add your handling code here:
        if (!jTxt_PID.getText().equals("") && !jTxt_Amount.getText().equals("")) {
            try {
                int isIntValue = Integer.parseInt(jTxt_PID.getText());
                isIntValue = Integer.parseInt(jTxt_Amount.getText());
                if (isIntValue < 0) {
                    displayNotification("Please ensure to enter non-negative amount!");
                } 
                else {
                    if(Login.methodName.equals("Post") && Login.serverType.equals("Servlet")) {
                        sendDataByPOST("Withdraw", "http://localhost:8081/Servers/ServletServer");
                    } 
                    else if(Login.methodName.equals("Get") && Login.serverType.equals("Servlet")) {
                        sendDataByGet("Withdraw", "http://localhost:8081/Servers/ServletServer");
                    } 
                    else if(Login.methodName.equals("Post") && Login.serverType.equals("PHP")) {
                        sendDataByPOST("Withdraw", "http://127.0.0.1:80/PHPServer.php");
                    } 
                    else {
                        sendDataByGet("Withdraw", "http://127.0.0.1:80/PHPServer.php");
                    }
                }
            } 
            catch (NumberFormatException e) {
                displayNotification("Please make sure to enter valid values!");
            }
        } 
        else {
            displayNotification("Enter both the product's ID and the amount!");
        }
    }//GEN-LAST:event_jLbl_WithdrawMouseClicked

    private void jLbl_WithdrawMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_WithdrawMouseEntered
        // TODO add your handling code here:
        scaleimage__backcwhite(jLbl_Withdraw, "src\\Images\\lightOrange.PNG");
        jLbl_Withdraw.setForeground(new Color(38,50,54));
    }//GEN-LAST:event_jLbl_WithdrawMouseEntered

    private void jLbl_WithdrawMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_WithdrawMouseExited
        // TODO add your handling code here:
        scaleimage__backc_white_remove(jLbl_Withdraw);
        jLbl_Withdraw.setForeground(Color.black);
    }//GEN-LAST:event_jLbl_WithdrawMouseExited

    private void jLbl_clearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_clearMouseClicked
        // TODO add your handling code here:
        jTxt_PID.setText("");
        jTxt_Pname.setText("");
        jTxt_Amount.setText("");
        jTxt_Price.setText("");
        jTB_currencyType.setEnabled(false);
    }//GEN-LAST:event_jLbl_clearMouseClicked

    private void jLbl_clearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_clearMouseEntered
        // TODO add your handling code here:
        scaleimage__backcwhite(jLbl_clear, "src\\Images\\lightOrange.PNG");
        jLbl_clear.setForeground(new Color(38,50,54));
    }//GEN-LAST:event_jLbl_clearMouseEntered

    private void jLbl_clearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbl_clearMouseExited
        // TODO add your handling code here:
        scaleimage__backc_white_remove(jLbl_clear);
        jLbl_clear.setForeground(Color.black);
    }//GEN-LAST:event_jLbl_clearMouseExited

    private void jTB_currencyTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jTB_currencyTypeItemStateChanged
        // TODO add your handling code here:
        if (jTB_currencyType.isSelected()) {
            try {
                jTB_currencyType.setBackground(new Color(176,190,197));
                jTB_currencyType.setForeground(new Color(255, 255, 255));
                jTB_currencyType.setText("EUR");
                
                //Convert the price from USD to EUR.
                String API_URL = "https://v6.exchangerate-api.com/v6/6bcb7d5f9ed33705239b4995/pair/USD/EUR/" + priceInUSD;
                URL myURL = new URL(API_URL);
                URLConnection connection = myURL.openConnection();
                
                InputStream inputStream = connection.getInputStream();
                InputStreamReader isReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isReader);
                String str = reader.readLine();
                
                String[] priceInEUR = str.split("\"conversion_result\":");
                priceInEUR = priceInEUR[1].split("}");
                this.jTxt_Price.setText(priceInEUR[0]);
                
            } 
            catch(MalformedURLException ex) {
                System.out.println(ex.toString());
            } 
            catch(IOException ex) {
                System.out.println(ex.toString());
            }
            
        }
        else {
            jTB_currencyType.setBackground(new Color(255,255,255));
            jTB_currencyType.setForeground(new Color(51,51,51));
            jTB_currencyType.setText("USD");
            this.jTxt_Price.setText(String.valueOf(priceInUSD));
        }
    }//GEN-LAST:event_jTB_currencyTypeItemStateChanged

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
            java.util.logging.Logger.getLogger(UpdatesInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdatesInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdatesInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdatesInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new UpdatesInterface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLbl_ID;
    private javax.swing.JLabel jLbl_Image;
    private javax.swing.JLabel jLbl_Insert;
    private javax.swing.JLabel jLbl_Logo;
    private javax.swing.JLabel jLbl_ReturnBack;
    private javax.swing.JLabel jLbl_Title;
    private javax.swing.JLabel jLbl_UpBar;
    private javax.swing.JLabel jLbl_View;
    private javax.swing.JLabel jLbl_Withdraw;
    private javax.swing.JLabel jLbl_amount;
    private javax.swing.JLabel jLbl_clear;
    private javax.swing.JLabel jLbl_name;
    private javax.swing.JLabel jLbl_price;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToggleButton jTB_currencyType;
    private javax.swing.JTextField jTxt_Amount;
    private javax.swing.JTextField jTxt_PID;
    private javax.swing.JTextField jTxt_Pname;
    private javax.swing.JTextField jTxt_Price;
    // End of variables declaration//GEN-END:variables
}
