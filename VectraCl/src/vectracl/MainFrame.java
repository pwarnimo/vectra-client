/*
 * VECTRA DRAWING PROGRAM -- CLIENT
 * 
 * This file (MainFrame.java) is part of the Vectra Drawing Client.
 * The MainFrame contains the primary user interface of the Vectra Drawing 
 * Client.
 */

package vectracl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import xml.XMLManager;

/**
 *
 * @author pwarnimo
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        initComponents();
        
        ActionListener diffUpdate = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlDraw.loadDiff();
            }
        };
        
        Timer tmDiff = new Timer(1000, diffUpdate);
        //tmDiff.start();
        
        //drawPanel1.newDrawing("drw0", "pwarnimo");
        
        xmlMgr = new XMLManager();
        
        setIconImage(new ImageIcon(getClass().getResource("/resource/vectra.png")).getImage());
        
        File f = new File("config.properties");
        
        if (f.exists()) {
            System.out.println("> Loading settings from config.properties...");
            
            Properties prop = new Properties();
            InputStream input = null;
            try {
                input = new FileInputStream("config.properties");
                
                prop.load(input);
                
                System.out.println("INIT->PROP=SERVER->" + prop.getProperty("server"));
		System.out.println("INIT->PROP=USERNAME->" + prop.getProperty("username"));
		System.out.println("INIT->PROP=DRAWING->" + prop.getProperty("drawing"));
                
                pnlDraw.setServer(prop.getProperty("server"));
                pnlDraw.setUser(prop.getProperty("username"));
                pnlDraw.setDrawingName(prop.getProperty("drawing"));
                pnlDraw.loadDrawing();
                //drawPanel1.loadDiff();
                
                tmDiff.start();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("> Initialized!");
        }
        else {
            System.out.println("> No config.properties found! Starting from scratch...");
            FirstStartDialog dlgStart = new FirstStartDialog();
            dlgStart.setTmDiff(tmDiff);
            dlgStart.setDrawPanel(pnlDraw);
            dlgStart.setVisible(true);
            //pnlDraw.loadDrawing();
            System.out.println("> Initialized!");
        }
        
        /*System.out.println("*** DEBUG OPTIONS ***");
        
        XMLManager xml = new XMLManager();
        
        xml.setUser("pwarnimo");
        ArrayList<String> drawings = xml.getDrawings();
        
        for (int i = 0; i <= drawings.size() -1; i++) {
            System.out.println("DrawingID->" + drawings.get(i));
        }
        
        FirstStartDialog dlgStart = new FirstStartDialog();
        
        dlgStart.setList(drawings);
        
        dlgStart.setVisible(true);
        
        System.out.println("*** DEBUG OPTIONS ***");*/
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        btnNew = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnLine = new javax.swing.JToggleButton();
        btnRectangle = new javax.swing.JToggleButton();
        btnOval = new javax.swing.JToggleButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnFGColor = new javax.swing.JButton();
        btnBGColor = new javax.swing.JButton();
        btnFilled = new javax.swing.JToggleButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnAbout = new javax.swing.JButton();
        pnlDraw = new vectracl.DrawPanel();
        mmMainMenu = new javax.swing.JMenuBar();
        mmiFile = new javax.swing.JMenu();
        mmiNew = new javax.swing.JMenuItem();
        mmiOpen = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mmiQuit = new javax.swing.JMenuItem();
        mmiEdit = new javax.swing.JMenu();
        mmiFGColor = new javax.swing.JMenuItem();
        mmiBGColor = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mmiSettings = new javax.swing.JMenuItem();
        mmiHelp = new javax.swing.JMenu();
        mmiAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Vectra Client v0.9");
        setBounds(new java.awt.Rectangle(50, 50, 800, 600));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/new.png"))); // NOI18N
        btnNew.setToolTipText("Create a new drawing on the server.");
        btnNew.setFocusable(false);
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNewMouseClicked(evt);
            }
        });
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNew);

        btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/open.png"))); // NOI18N
        btnOpen.setToolTipText("Open a drawing from the server.");
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        jToolBar1.add(btnOpen);
        jToolBar1.add(jSeparator1);

        buttonGroup1.add(btnLine);
        btnLine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/line.png"))); // NOI18N
        btnLine.setSelected(true);
        btnLine.setToolTipText("Select a line for drawing.");
        btnLine.setFocusable(false);
        btnLine.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLine.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLineActionPerformed(evt);
            }
        });
        jToolBar1.add(btnLine);

        buttonGroup1.add(btnRectangle);
        btnRectangle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/rectangle.png"))); // NOI18N
        btnRectangle.setToolTipText("Select a rectangle for drawing.");
        btnRectangle.setFocusable(false);
        btnRectangle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRectangle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRectangle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRectangleActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRectangle);

        buttonGroup1.add(btnOval);
        btnOval.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/oval.png"))); // NOI18N
        btnOval.setToolTipText("Select an oval for drawing.");
        btnOval.setFocusable(false);
        btnOval.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOval.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOvalActionPerformed(evt);
            }
        });
        jToolBar1.add(btnOval);
        jToolBar1.add(jSeparator2);

        btnFGColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/color-f.png"))); // NOI18N
        btnFGColor.setToolTipText("Set foreground color...");
        btnFGColor.setFocusable(false);
        btnFGColor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFGColor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFGColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFGColorActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFGColor);

        btnBGColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/color-b.png"))); // NOI18N
        btnBGColor.setToolTipText("Set background color...");
        btnBGColor.setFocusable(false);
        btnBGColor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBGColor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBGColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBGColorActionPerformed(evt);
            }
        });
        jToolBar1.add(btnBGColor);

        btnFilled.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/fill.png"))); // NOI18N
        btnFilled.setToolTipText("Fill shapes.");
        btnFilled.setFocusable(false);
        btnFilled.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFilled.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFilled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilledActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFilled);
        jToolBar1.add(jSeparator3);

        btnAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/help.png"))); // NOI18N
        btnAbout.setToolTipText("About this program...");
        btnAbout.setFocusable(false);
        btnAbout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAbout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAbout);

        javax.swing.GroupLayout pnlDrawLayout = new javax.swing.GroupLayout(pnlDraw);
        pnlDraw.setLayout(pnlDrawLayout);
        pnlDrawLayout.setHorizontalGroup(
            pnlDrawLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlDrawLayout.setVerticalGroup(
            pnlDrawLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 577, Short.MAX_VALUE)
        );

        mmiFile.setText("File");

        mmiNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/new.png"))); // NOI18N
        mmiNew.setText("New drawing");
        mmiNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mmiNewActionPerformed(evt);
            }
        });
        mmiFile.add(mmiNew);

        mmiOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/open.png"))); // NOI18N
        mmiOpen.setText("Open drawing");
        mmiOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mmiOpenActionPerformed(evt);
            }
        });
        mmiFile.add(mmiOpen);
        mmiFile.add(jSeparator4);

        mmiQuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/exit.png"))); // NOI18N
        mmiQuit.setText("Quit");
        mmiQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mmiQuitActionPerformed(evt);
            }
        });
        mmiFile.add(mmiQuit);

        mmMainMenu.add(mmiFile);

        mmiEdit.setText("Edit");

        mmiFGColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/color-f.png"))); // NOI18N
        mmiFGColor.setText("Foreground color...");
        mmiEdit.add(mmiFGColor);

        mmiBGColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/color-b.png"))); // NOI18N
        mmiBGColor.setText("Background color...");
        mmiEdit.add(mmiBGColor);
        mmiEdit.add(jSeparator5);

        mmiSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/settings.png"))); // NOI18N
        mmiSettings.setText("Settings");
        mmiSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mmiSettingsActionPerformed(evt);
            }
        });
        mmiEdit.add(mmiSettings);

        mmMainMenu.add(mmiEdit);

        mmiHelp.setText("Help");

        mmiAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/help.png"))); // NOI18N
        mmiAbout.setText("About...");
        mmiAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mmiAboutActionPerformed(evt);
            }
        });
        mmiHelp.add(mmiAbout);

        mmMainMenu.add(mmiHelp);

        setJMenuBar(mmMainMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addComponent(pnlDraw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDraw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewMouseClicked
        
    }//GEN-LAST:event_btnNewMouseClicked

    private void btnFGColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFGColorActionPerformed
        Color selectedColor = JColorChooser.showDialog(this, "Select foreground color...", Color.BLUE);
        pnlDraw.setCurrentForegroundColor(selectedColor);
    }//GEN-LAST:event_btnFGColorActionPerformed

    private void btnBGColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBGColorActionPerformed
        Color selectedColor = JColorChooser.showDialog(this, "Select background color...", Color.BLUE);
        pnlDraw.setCurrentBackgroundColor(selectedColor);
    }//GEN-LAST:event_btnBGColorActionPerformed

    private void btnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutActionPerformed
        //AboutFrame frmAbout = new AboutFrame();
        new AboutFrame().setVisible(true);
    }//GEN-LAST:event_btnAboutActionPerformed

    private void btnRectangleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRectangleActionPerformed
        pnlDraw.setDrawMode(1);
    }//GEN-LAST:event_btnRectangleActionPerformed

    private void btnOvalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOvalActionPerformed
        pnlDraw.setDrawMode(2);
    }//GEN-LAST:event_btnOvalActionPerformed

    private void btnLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLineActionPerformed
        pnlDraw.setDrawMode(0);
    }//GEN-LAST:event_btnLineActionPerformed

    private void mmiQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mmiQuitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mmiQuitActionPerformed

    private void mmiNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mmiNewActionPerformed
        String drawing = (String)JOptionPane.showInputDialog(this, "Please enter a name for your new drawing.", "New drawing...", JOptionPane.PLAIN_MESSAGE, null, null, "");
        
        Properties prop = new Properties();
        InputStream input = null;
        FileOutputStream out = null;
        try {     
            input = new FileInputStream("config.properties");
            
            prop.load(input);
            prop.setProperty("drawing", drawing);

            System.out.println("SET->PROP=DRAWING->" + prop.getProperty("drawing"));
            
            out = new FileOutputStream("config.properties");
            prop.store(out, null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DialogOpen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DialogOpen.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        pnlDraw.newDrawing(drawing);
        pnlDraw.setDrawingName(drawing);
        pnlDraw.loadDrawing();
        pnlDraw.repaint();
    }//GEN-LAST:event_mmiNewActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        mmiNew.doClick();
    }//GEN-LAST:event_btnNewActionPerformed

    private void mmiSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mmiSettingsActionPerformed
        SettingsDialog dlgSettings = new SettingsDialog();
        dlgSettings.setDrawPanel(pnlDraw);
        dlgSettings.setVisible(true);
    }//GEN-LAST:event_mmiSettingsActionPerformed

    private void mmiOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mmiOpenActionPerformed
        DialogOpen dlgOpen = new DialogOpen();
        dlgOpen.setDrawPanel(pnlDraw);
        dlgOpen.setVisible(true);
    }//GEN-LAST:event_mmiOpenActionPerformed

    private void btnFilledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilledActionPerformed
        pnlDraw.setFilled(!pnlDraw.getFilled());
    }//GEN-LAST:event_btnFilledActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        mmiOpen.doClick();
    }//GEN-LAST:event_btnOpenActionPerformed

    private void mmiAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mmiAboutActionPerformed
        btnAbout.doClick();
    }//GEN-LAST:event_mmiAboutActionPerformed
    
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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbout;
    private javax.swing.JButton btnBGColor;
    private javax.swing.JButton btnFGColor;
    private javax.swing.JToggleButton btnFilled;
    private javax.swing.JToggleButton btnLine;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnOpen;
    private javax.swing.JToggleButton btnOval;
    private javax.swing.JToggleButton btnRectangle;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuBar mmMainMenu;
    private javax.swing.JMenuItem mmiAbout;
    private javax.swing.JMenuItem mmiBGColor;
    private javax.swing.JMenu mmiEdit;
    private javax.swing.JMenuItem mmiFGColor;
    private javax.swing.JMenu mmiFile;
    private javax.swing.JMenu mmiHelp;
    private javax.swing.JMenuItem mmiNew;
    private javax.swing.JMenuItem mmiOpen;
    private javax.swing.JMenuItem mmiQuit;
    private javax.swing.JMenuItem mmiSettings;
    private vectracl.DrawPanel pnlDraw;
    // End of variables declaration//GEN-END:variables
    public XMLManager xmlMgr;
}
