/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectakhir;

import java.awt.print.PrinterException;
import java.sql.*;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Nantodd
 */
public class FormAdministrasi extends javax.swing.JFrame {

    /**
     * Creates new form FormAdministrasi
     */
    
    Connection Con;
    ResultSet RsBrg;
    Statement stm;

    Boolean ada = false;
//    String iIpk;
//    String uUmur;
    Boolean edit=false;

    private Object[][] dataTable = null;
    private String[] header ={"Kode", "Nama", "Umur", "Jurusan", "IPK"};
    public FormAdministrasi() {
        initComponents();
        open_db();
//        baca_data();
//        aktif(false);
//        setTombol(true);
    }

    private void open_db() {
        try{
            KoneksiMysql kon = new KoneksiMysql("localhost","root","","ppkp");
            Con = kon.getConnection();
            //System.out.println("Berhasil ");
        }catch (Exception e) {
            System.out.println("Error : "+e);
        }
    }
    
    private void seleksi_data() {
        try {
            float iIpk = Float.parseFloat(txtIpk.getText());
            int uUmur = Integer.parseInt(txtUmur.getText());
//            System.out.println(iIpk);
            stm = Con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            RsBrg = stm.executeQuery("select kode, nama, umur, jurusan, ipk from peserta where ipk >= "+iIpk+" and umur <= "+uUmur+" ");

            ResultSetMetaData meta = RsBrg.getMetaData();
            int col = meta.getColumnCount();
            int baris = 0; 
            while(RsBrg.next()) {
                baris = RsBrg.getRow();
            } 
            dataTable = new Object[baris][col];
            int x = 0;
            RsBrg.beforeFirst();
            while(RsBrg.next()) {
                dataTable[x][0] = RsBrg.getString("kode");
                dataTable[x][1] = RsBrg.getString("nama");
                dataTable[x][2] = RsBrg.getInt("umur");
                dataTable[x][3] = RsBrg.getString("jurusan");
                dataTable[x][4] = RsBrg.getFloat("ipk");
                x++;
            } 
            tblAdm.setModel(new DefaultTableModel(dataTable,header)); 
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void baca_data() {
        try {
            stm = Con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            RsBrg = stm.executeQuery("select * from administrasi");

            ResultSetMetaData meta = RsBrg.getMetaData();
            int col = meta.getColumnCount();
            int baris = 0; 
            while(RsBrg.next()) {
                baris = RsBrg.getRow();
            } 
            dataTable = new Object[baris][col];
            int x = 0;
            RsBrg.beforeFirst();
            while(RsBrg.next()) {
                dataTable[x][0] = RsBrg.getString("kode");
                dataTable[x][1] = RsBrg.getString("nama");
                dataTable[x][2] = RsBrg.getInt("umur");
                dataTable[x][3] = RsBrg.getString("jurusan");
                dataTable[x][4] = RsBrg.getFloat("ipk");
                x++;
            } 
            tblAdm.setModel(new DefaultTableModel(dataTable,header)); 
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private class PrintingTask extends SwingWorker<Object, Object> {
        private final MessageFormat headerFormat;
        private final MessageFormat footerFormat;
        private final boolean interactive;
        private volatile boolean complete = false;
        private volatile String message;
        public PrintingTask(MessageFormat header, MessageFormat footer, boolean interactive) {
            this.headerFormat = header;
            this.footerFormat = footer;
            this.interactive = interactive;
        }
        @Override
        protected Object doInBackground() {
            try {
                complete = text.print(headerFormat, footerFormat, true, null, null, interactive);
                message = "Printing " + (complete ? "complete" : "canceled");
            } catch (PrinterException ex) {
                message = "Sorry, a printer error occurred";
            } catch (SecurityException ex) {
                message = "Sorry, cannot access the printer due to security reasons";
            }
            return null;
        }
        
        @Override
        protected void done() {
            message(!complete, message);
        }
    }
    private MessageFormat createFormat(String source) {
        String text = source;
        if (text != null && text.length() > 0) {
            try {
                return new MessageFormat(text);
            } catch (IllegalArgumentException e) {
                error("Sorry, this format is invalid.");
            }
        }
        return null;
    }
    private void message(boolean error, String msg) {
        int type = (error ? JOptionPane.ERROR_MESSAGE :
        JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(this, msg, "Printing", type);
    }
    
    private void error(String msg) {
        message(true, msg);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtUmur = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtIpk = new javax.swing.JTextField();
        cmdLihat = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAdm = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextArea();
        cmdSimpan = new javax.swing.JButton();
        cmdCetak = new javax.swing.JButton();
        cmdSeleksi1 = new javax.swing.JButton();
        cmdReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form Seleksi Administrasi");

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel1.setText("Seleksi Administrasi");

        txtUmur.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel2.setText("Umur Maksimal");

        jLabel12.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel12.setText("IPK Minimum");

        txtIpk.setToolTipText("");

        cmdLihat.setText("Lihat");
        cmdLihat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLihatActionPerformed(evt);
            }
        });

        tblAdm.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode", "Nama", "Umur", "Jurusan", "IPK"
            }
        ));
        tblAdm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAdmMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblAdm);

        text.setColumns(20);
        text.setRows(5);
        jScrollPane3.setViewportView(text);

        cmdSimpan.setText("Simpan");
        cmdSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSimpanActionPerformed(evt);
            }
        });

        cmdCetak.setText("Cetak");
        cmdCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCetakActionPerformed(evt);
            }
        });

        cmdSeleksi1.setText("Seleksi");
        cmdSeleksi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSeleksi1ActionPerformed(evt);
            }
        });

        cmdReset.setText("Reset");
        cmdReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane3)
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtIpk, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtUmur, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmdSeleksi1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmdSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmdCetak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmdLihat, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmdReset, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmdSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmdLihat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmdCetak)
                                    .addComponent(cmdReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(txtIpk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtUmur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmdSeleksi1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblAdmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAdmMouseClicked

    }//GEN-LAST:event_tblAdmMouseClicked

    private void cmdSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSimpanActionPerformed
//        int row = tblAdm.getRowCount();
        for(int row=0;row<tblAdm.getRowCount();row++) {
            String tKode = (String)tblAdm.getValueAt(row, 0);
//            System.out.println(tKode);
            String tNama = (String) tblAdm.getValueAt(row, 1);
            int uUmur = (Integer)tblAdm.getValueAt(row, 2);
            String tJurusan = (String) tblAdm.getValueAt(row, 3);
            float iIpk = (Float)tblAdm.getValueAt(row, 4);
            try {
                if(edit == true) {
                    stm.executeUpdate("update administrasi set nama='"+tNama+"',umur='"+uUmur+"',jurusan="+tJurusan+",ipk="+iIpk+" where kode='" + tKode + "'"); 

                } else {
                    stm.executeUpdate("INSERT into administrasi VALUES('"+tKode+"','"+tNama+"','"+uUmur+"','"+tJurusan+"','"+iIpk+"')"); 
                }
                tblAdm.setModel(new DefaultTableModel(dataTable,header));
            } catch(SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        JOptionPane.showMessageDialog(null, "Data Tersimpan");
    }//GEN-LAST:event_cmdSimpanActionPerformed

    private void cmdCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCetakActionPerformed
        String ctk="Daftar Peserta Lolos Seleksi Administrasi";
        ctk=ctk+"\n"+"-----------------------------------------------------------------------------------------"+"\n";
        ctk=ctk+"\n"+"-----------------------------------------------------------------------------------------"+"";
        ctk=ctk+"\n"+"Kode Peserta\tNama\tUmur\tJurusan\tIPK";
        ctk=ctk+"\n"+"-------------------------------------------------------------------------------------------------";
        for(int i=0;i<tblAdm.getRowCount();i++) {
            String xkd=(String)tblAdm.getValueAt(i,0);
            String xnama=(String)tblAdm.getValueAt(i,1);
            int xumur=(Integer)tblAdm.getValueAt(i,2);
            String xjurusan=(String)tblAdm.getValueAt(i,3);
            Float xipk=(Float)tblAdm.getValueAt(i,4);
            ctk=ctk+"\n"+xkd+"\t"+xnama+"\t"+xumur+"\t"+xjurusan+"\t"+xipk;
        }
        ctk=ctk+"\n"+"-------------------------------------------------------------------------------------------------";
        text.setText(ctk);
        String headerField="";
        String footerField="";
        MessageFormat header = createFormat(headerField);
        MessageFormat footer = createFormat(footerField);
        boolean interactive = true;//interactiveCheck.isSelected();
        boolean background = true;//backgroundCheck.isSelected();
        PrintingTask task = new PrintingTask(header, footer, interactive);
        if (background) {
            task.execute();
        } else {
            task.run();
        }
    }//GEN-LAST:event_cmdCetakActionPerformed

    private void cmdLihatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLihatActionPerformed
        baca_data();
    }//GEN-LAST:event_cmdLihatActionPerformed

    private void cmdSeleksi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSeleksi1ActionPerformed
        try {
            seleksi_data();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Isi Data Dulu");
        }
    }//GEN-LAST:event_cmdSeleksi1ActionPerformed

    private void cmdResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdResetActionPerformed
        try{
            String sql="delete from administrasi ";
            stm.executeUpdate(sql);
            baca_data();
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_cmdResetActionPerformed

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
            java.util.logging.Logger.getLogger(FormAdministrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormAdministrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormAdministrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormAdministrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormAdministrasi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdCetak;
    private javax.swing.JButton cmdLihat;
    private javax.swing.JButton cmdReset;
    private javax.swing.JButton cmdSeleksi1;
    private javax.swing.JButton cmdSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblAdm;
    private javax.swing.JTextArea text;
    private javax.swing.JTextField txtIpk;
    private javax.swing.JTextField txtUmur;
    // End of variables declaration//GEN-END:variables
}
