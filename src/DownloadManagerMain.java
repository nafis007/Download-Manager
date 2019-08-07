import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;


public class DownloadManagerMain extends javax.swing.JFrame implements Observer {

    public DownloadManagerMain() {
        initComponents();
        
        menuItemOpenFile.setEnabled(false);
        menuItemPause.setEnabled(false);
        menuItemResume.setEnabled(false);
        menuItemStop.setEnabled(false);
        menuItemClear.setEnabled(false);
        menuItemCopyURL.setEnabled(false);
        
        popupMenu.add(menuItemOpenFile);
        popupMenu.add(menuItemOpenFolder);
        popupMenu.add(menuItemCopyURL);
        popupMenu.add(menuItemPause);
        popupMenu.add(menuItemStop);
        popupMenu.add(menuItemResume);
        popupMenu.add(menuItemClear);
        popupMenu.add(menuItemCancel);
        my_table.setComponentPopupMenu(popupMenu);
        
        menuItemOpenFile.addMouseListener( new MouseAdapter(){   
            public void mouseReleased(MouseEvent e){
                openfile();
            }
        });
        
        menuItemOpenFolder.addMouseListener( new MouseAdapter(){   
            public void mouseReleased(MouseEvent e){
                openfolder();
            }
        });
        
        menuItemCopyURL.addMouseListener( new MouseAdapter(){public void mouseReleased(MouseEvent e){
            String str = selectedDownload.getUrl();

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();
            StringSelection strSel = new StringSelection(str);
            clipboard.setContents(strSel, null);
            }
        });
        
        menuItemPause.addMouseListener( new MouseAdapter(){   
            public void mouseReleased(MouseEvent e){
                actionPause();
            }
        });
        
        menuItemStop.addMouseListener( new MouseAdapter(){   
            public void mouseReleased(MouseEvent e){
                actionStop();
            }
        });
        
        menuItemResume.addMouseListener( new MouseAdapter(){   
            public void mouseReleased(MouseEvent e){
                actionResume();
            }
        });
        
        menuItemClear.addMouseListener( new MouseAdapter(){   
            public void mouseReleased(MouseEvent e){
                actionClear();
            }
        });
        
        menuItemCancel.addMouseListener( new MouseAdapter(){public void mouseReleased(MouseEvent e){;}});
        
        popupMenu1.add(menuItemPaste);
        url_field.setComponentPopupMenu(popupMenu1);
        
        menuItemPaste.addMouseListener( new MouseAdapter(){   
            public void mouseReleased(MouseEvent e){
                
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();
            Transferable clipData = clipboard.getContents(this);
            String s;
            try {
                s = (String)(clipData.getTransferData(
                       DataFlavor.stringFlavor));
            } catch (Exception ee) {
                s = ee.toString();
            }
            url_field.setText(s);
            }
        });
        
        url_field.addKeyListener(new KeyAdapter() { 
        
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                actionAdd();
                }
            }
        });
            
        tableModel = new DownloadMyTable();
        my_table.setModel(tableModel);
        my_table.getSelectionModel().addListSelectionListener(new
                ListSelectionListener() {
            
            public void valueChanged(ListSelectionEvent e) {
                tableSelectionChanged();
            }
        });
        
        
        
        ProgressBar renderer = new ProgressBar(0, 100);
        renderer.setStringPainted(true); 
        my_table.setDefaultRenderer(JProgressBar.class, renderer);
        
        my_table.setRowHeight((int) renderer.getPreferredSize().getHeight());
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelUp = new javax.swing.JPanel();
        user_promt = new javax.swing.JLabel();
        url_field = new javax.swing.JTextField();
        download_button = new javax.swing.JButton();
        paneldown = new javax.swing.JPanel();
        pause_button = new javax.swing.JButton();
        resume_button = new javax.swing.JButton();
        stop_button = new javax.swing.JButton();
        clear_button = new javax.swing.JButton();
        panelcentre = new javax.swing.JPanel();
        table_intro = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        my_table = new javax.swing.JTable();
        menubar = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        History = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Download Madness");
        setBounds(new java.awt.Rectangle(350, 80, 0, 0));
        setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N

        user_promt.setFont(new java.awt.Font("Tempus Sans ITC", 0, 12)); // NOI18N
        user_promt.setText("Enter your url below:");

        url_field.setFont(new java.awt.Font("Tempus Sans ITC", 0, 11)); // NOI18N

        download_button.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        download_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images.jpg"))); // NOI18N
        download_button.setText("Start Download");
        download_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        download_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                download_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelUpLayout = new javax.swing.GroupLayout(panelUp);
        panelUp.setLayout(panelUpLayout);
        panelUpLayout.setHorizontalGroup(
            panelUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUpLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(panelUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUpLayout.createSequentialGroup()
                        .addComponent(url_field, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(download_button))
                    .addComponent(user_promt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelUpLayout.setVerticalGroup(
            panelUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(user_promt, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(url_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(download_button))
                .addContainerGap())
        );

        paneldown.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        pause_button.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        pause_button.setText("Pause");
        pause_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pause_button.setEnabled(false);
        pause_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pause_buttonActionPerformed(evt);
            }
        });

        resume_button.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        resume_button.setText("Resume");
        resume_button.setEnabled(false);
        resume_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resume_buttonActionPerformed(evt);
            }
        });

        stop_button.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        stop_button.setText("Stop");
        stop_button.setEnabled(false);
        stop_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stop_buttonActionPerformed(evt);
            }
        });

        clear_button.setFont(new java.awt.Font("Tempus Sans ITC", 1, 12)); // NOI18N
        clear_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recycle Bin Full.jpg"))); // NOI18N
        clear_button.setText("Clear Download");
        clear_button.setEnabled(false);
        clear_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneldownLayout = new javax.swing.GroupLayout(paneldown);
        paneldown.setLayout(paneldownLayout);
        paneldownLayout.setHorizontalGroup(
            paneldownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldownLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(pause_button, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(resume_button, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(stop_button, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(clear_button, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paneldownLayout.setVerticalGroup(
            paneldownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldownLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneldownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resume_button)
                    .addComponent(stop_button)
                    .addComponent(pause_button)
                    .addComponent(clear_button))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        table_intro.setFont(new java.awt.Font("Tempus Sans ITC", 1, 12)); // NOI18N
        table_intro.setText("Downloads");

        my_table.setFont(new java.awt.Font("Tempus Sans ITC", 0, 12)); // NOI18N
        my_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "File Name", "Size", "Progress", "Speed", "Time Remaining", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        my_table.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        my_table.setRowHeight(20);
        my_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(my_table);
        my_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout panelcentreLayout = new javax.swing.GroupLayout(panelcentre);
        panelcentre.setLayout(panelcentreLayout);
        panelcentreLayout.setHorizontalGroup(
            panelcentreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcentreLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelcentreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(table_intro, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        panelcentreLayout.setVerticalGroup(
            panelcentreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcentreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(table_intro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        file.setText("File");
        file.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        file.setFont(new java.awt.Font("Tempus Sans ITC", 0, 12)); // NOI18N

        History.setFont(new java.awt.Font("Tempus Sans ITC", 0, 12)); // NOI18N
        History.setText("History");
        History.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        History.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HistoryActionPerformed(evt);
            }
        });
        file.add(History);

        exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        exit.setFont(new java.awt.Font("Tempus Sans ITC", 0, 12)); // NOI18N
        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        file.add(exit);

        menubar.add(file);

        setJMenuBar(menubar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelUp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(paneldown, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelcentre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelcentre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paneldown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        int showConfirmDialog = JOptionPane.showConfirmDialog(this,"Do you relly want to exit?", "Confirmation",JOptionPane.YES_NO_OPTION);
        if(showConfirmDialog==0)System.exit(0);    
    }//GEN-LAST:event_exitActionPerformed

    private void pause_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pause_buttonActionPerformed
        actionPause();
    }//GEN-LAST:event_pause_buttonActionPerformed

    private void download_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_download_buttonActionPerformed
        actionAdd();
    }//GEN-LAST:event_download_buttonActionPerformed

    private void resume_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resume_buttonActionPerformed
       actionResume();
    }//GEN-LAST:event_resume_buttonActionPerformed

    private void stop_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stop_buttonActionPerformed
        actionStop();
    }//GEN-LAST:event_stop_buttonActionPerformed

    private void clear_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_buttonActionPerformed
        actionClear();
    }//GEN-LAST:event_clear_buttonActionPerformed

    private void HistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HistoryActionPerformed
       
        new NewJFrame().setVisible(true);
    }//GEN-LAST:event_HistoryActionPerformed
    
    private void actionAdd() {
        
        URL verifiedUrl = verifyUrl(url_field.getText());
        
        if (verifiedUrl != null) {
            
            tableModel.addDownload(new HttpAccel(verifiedUrl));
            url_field.setText(""); 
        } 
        else {
            url_field.setText("");
            JOptionPane.showMessageDialog(this,"Invalid Download URL", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private URL verifyUrl(String url) {
        
        if (!url.toLowerCase().startsWith("http://") && !url.toLowerCase().startsWith("ftp://"))
            return null;
        
        URL verifiedUrl=null;
        try {
            verifiedUrl = new URL(url);
        } catch (MalformedURLException e) {
            return null;
        }
        if (verifiedUrl.getFile().length() < 1)
            return null;
        return verifiedUrl;
    }
    
    private void tableSelectionChanged() {
   
        if (selectedDownload != null)
            selectedDownload.deleteObserver(this);
        
        if (!clearing) {
            selectedDownload =tableModel.getDownload(my_table.getSelectedRow());
            selectedDownload.addObserver(this);
            updateButtons();
        }
    }
    
    private void actionPause() {
        selectedDownload.pause();
        updateButtons();
    }
    
    private void actionResume() {
        if(selectedDownload.getDirectUrl().toString().toLowerCase().startsWith("http://") 
                && selectedDownload.getSize()>1){
            selectedDownload.resume();
            updateButtons();
        }
        else{
            selectedDownload.restart();
            updateButtons();
        }
    }
    
    private void actionStop() {
        selectedDownload.stop();
        updateButtons();
    }
    
    private void actionClear() {
        clearing = true;
        tableModel.clearDownload(my_table.getSelectedRow());
        clearing = false;
        selectedDownload = null;
        updateButtons();
    }
    
    private void openfolder(){
        try{
            Desktop.getDesktop().open(new File("C:/Users/Sanjana/Documents/Downloads"));
        }catch(IOException ex){
             ;
         }
    }
    
    private void openfile(){
        try{
            File file = new File("C:/Users/Sanjana/Documents/Downloads/" + selectedDownload.getFileName(selectedDownload.getDirectUrl()));
            Desktop.getDesktop().open( file );
        }catch(IOException ex){
             ;
         }
    }
    
    private void updateButtons() {
        
        if (selectedDownload != null) {
            
            my_table.setToolTipText(selectedDownload.getUrl());
            int status = selectedDownload.getStatus();
            
            switch (status) {
                case Download.DOWNLOADING:
                    menuItemOpenFile.setEnabled(false);
                    
                    menuItemCopyURL.setEnabled(true);
                    
                    pause_button.setEnabled(true);
                    menuItemPause.setEnabled(true);
                    
                    resume_button.setEnabled(false);
                    menuItemResume.setEnabled(false);
                    
                    stop_button.setEnabled(true);
                    menuItemStop.setEnabled(true);
                    
                    clear_button.setEnabled(false);
                    menuItemClear.setEnabled(false);
                    break;
                case Download.PAUSED:
                    menuItemOpenFile.setEnabled(false);
                    
                    menuItemCopyURL.setEnabled(true);
                    
                    pause_button.setEnabled(false);
                    menuItemPause.setEnabled(false);
                    
                    resume_button.setEnabled(true);
                    menuItemResume.setEnabled(true);
                    
                    stop_button.setEnabled(true);
                    menuItemStop.setEnabled(true);
                    
                    clear_button.setEnabled(false);
                    menuItemClear.setEnabled(false);
                    break;
                case Download.ERROR:
                    menuItemOpenFile.setEnabled(false);
                    
                    menuItemCopyURL.setEnabled(true);
                    
                    pause_button.setEnabled(false);
                    menuItemPause.setEnabled(false);
                    
                    resume_button.setEnabled(false);
                    menuItemResume.setEnabled(false);
                    
                    stop_button.setEnabled(false);
                    menuItemStop.setEnabled(false);
                    
                    clear_button.setEnabled(true);
                    menuItemClear.setEnabled(true);
                    break;
                case Download.STOPPED: 
                    menuItemOpenFile.setEnabled(false);
                    
                    menuItemCopyURL.setEnabled(true);
                    
                    pause_button.setEnabled(false);
                    menuItemPause.setEnabled(false);
                    
                    resume_button.setEnabled(false);
                    menuItemResume.setEnabled(false);
                    
                    stop_button.setEnabled(false);
                    menuItemStop.setEnabled(false);
                    
                    clear_button.setEnabled(true);
                    menuItemClear.setEnabled(true);
                    break;
                case Download.APPENDING:
                    menuItemOpenFile.setEnabled(false);
                    
                    menuItemCopyURL.setEnabled(true);
                    
                    pause_button.setEnabled(true);
                    menuItemPause.setEnabled(true);
                    
                    resume_button.setEnabled(false);
                    menuItemResume.setEnabled(false);
                    
                    stop_button.setEnabled(true);
                    menuItemStop.setEnabled(true);
                    
                    clear_button.setEnabled(false);
                    menuItemClear.setEnabled(false);
                    break;
                default: 
                    menuItemOpenFile.setEnabled(true);
                    
                    menuItemCopyURL.setEnabled(true);
                    
                    pause_button.setEnabled(false);
                    menuItemPause.setEnabled(false);
                    
                    resume_button.setEnabled(false);
                    menuItemResume.setEnabled(false);
                    
                    stop_button.setEnabled(false);
                    menuItemStop.setEnabled(false);
                    
                    clear_button.setEnabled(true);
                    menuItemClear.setEnabled(true);
            }
        } else {
            menuItemOpenFile.setEnabled(false);
            
            menuItemCopyURL.setEnabled(false);
            
            pause_button.setEnabled(false);
            menuItemPause.setEnabled(false);
            
            resume_button.setEnabled(false);
            menuItemResume.setEnabled(false);
            
            stop_button.setEnabled(false);
            menuItemStop.setEnabled(false);
            
            clear_button.setEnabled(false);
            menuItemClear.setEnabled(false);
        }
    }
    
    
    public void update(Observable o, Object arg) {
        if (selectedDownload != null && selectedDownload.equals(o))
            updateButtons();
    }
    
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                DownloadManagerMain main=new DownloadManagerMain();
                main.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem History;
    private javax.swing.JButton clear_button;
    private javax.swing.JButton download_button;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenu file;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar menubar;
    private javax.swing.JTable my_table;
    private javax.swing.JPanel panelUp;
    private javax.swing.JPanel panelcentre;
    private javax.swing.JPanel paneldown;
    private javax.swing.JButton pause_button;
    private javax.swing.JButton resume_button;
    private javax.swing.JButton stop_button;
    private javax.swing.JLabel table_intro;
    private javax.swing.JTextField url_field;
    private javax.swing.JLabel user_promt;
    // End of variables declaration//GEN-END:variables
    
    private DownloadMyTable tableModel;
    private HttpAccel selectedDownload;
    private boolean clearing;
    
    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem menuItemOpenFile = new JMenuItem("Open File");
    JMenuItem menuItemOpenFolder = new JMenuItem("Open Containing folder");
    JMenuItem menuItemCopyURL = new JMenuItem("Copy URL");
    JMenuItem menuItemPause = new JMenuItem("Pause");
    JMenuItem menuItemStop = new JMenuItem("Stop");
    JMenuItem menuItemResume = new JMenuItem("Resume");
    JMenuItem menuItemClear = new JMenuItem("Clear Download");
    JMenuItem menuItemCancel = new JMenuItem("Cancel");
    JPopupMenu popupMenu1 = new JPopupMenu();
    JMenuItem menuItemPaste = new JMenuItem("Paste");
    
}

class NewJFrame extends javax.swing.JFrame {

    public NewJFrame() {
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setTitle("History");
        setBounds(new java.awt.Rectangle(400, 150, 0, 0));

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel1.setText("My Downloads");

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(java.awt.SystemColor.control);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tempus Sans ITC", 0, 12)); // NOI18N
        jTextArea1.setRows(15);
        jScrollPane1.setViewportView(jTextArea1);

        jMenu1.setText("File");
        jMenu1.setFont(new java.awt.Font("Tempus Sans ITC", 0, 12)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Tempus Sans ITC", 0, 12)); // NOI18N
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addContainerGap())
        );
        
        try
        {
            File f= new File("history.txt");
            FileReader fr= new FileReader(f);
            char[] data=new char[(int)f.length()];
            fr.read(data);
            
            jTextArea1.append(new String(data));
            fr.close();
            
        }
        catch(IOException ioe){}

        pack();
    }
    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
            setVisible(false);       
    }
    
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
}