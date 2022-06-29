/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectakhir;

import java.awt.print.PrinterException;
import java.sql.*;
import java.text.MessageFormat;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nantodd
 */
public class FormPenilaian extends javax.swing.JFrame {

    /**
     * Creates new form FormPenilaian
     */
    
    Connection Con;
    ResultSet RsBrg;
    ResultSet RsKons;
    Statement stm;
    double total=0;
    String kKd;
    Boolean edit=false;
    
    DefaultTableModel tableModel = new DefaultTableModel(
        new Object [][] {},
            new String [] {
                "Kode", "Nama", "Umur", "Jurusan", "IPK", "Nilai Tertulis", "Nilai Wawancara", "Nilai Akhir"
            }
    );
    private Object[][] dataTable = null;
    private String[] header ={"Kode", "Nama", "Umur", "Jurusan", "IPK", "Nilai Tertulis", "Nilai Wawancara", "Nilai Akhir"};
    public FormPenilaian() {
        initComponents();
        open_db();
        baca_peserta();
        inisialisasi_tabel();
        aktif(false);
        setTombol(true);
    }
    
    public void inisialisasi_tabel() {
        tblNilai.setModel(tableModel);
    }
    
    private void hitung_nilai() {
        float xtls, xwnc, xnilai;
        xtls = Float.parseFloat(txtTertulis.getText());
        xwnc = Float.parseFloat(txtWawancara.getText());
        xnilai = (xtls+xwnc)/2;
        System.out.println("tes");
        txtNakhir.setText(Float.toString(xnilai));
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
    
    public void baca_peserta() {
        try {
            stm=Con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=stm.executeQuery("select * from administrasi");
            rs.beforeFirst();
            while(rs.next()) {
                cmbKode.addItem(rs.getString(1).trim());
            }
            rs.close();
        } catch(SQLException e) {
            System.out.println("Error : "+e);
        }
    }
    
    private void detail_peserta(String xkode) {
        try{
            stm=Con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=stm.executeQuery("select * from administrasi where kode='"+xkode+"'");
            rs.beforeFirst();
            while(rs.next()) {
                txtNama.setText(rs.getString(2).trim());
                txtUmur.setText(Integer.toString((Integer)rs.getInt(3)));
                txtJurusan.setText(rs.getString(4).trim());
                txtIpk.setText(Float.toString((Float)rs.getFloat(5)));
            }
            rs.close();
        } catch(SQLException e){
            System.out.println("Error : "+e);
        } 
    }
    private void simpan_ditabel() {
        try{
            String tKode = cmbKode.getSelectedItem().toString();
//            System.out.println(tKode);
            String tNama = txtNama.getText();
            int uUmur = Integer.parseInt(txtUmur.getText());
            String tJurusan = txtJurusan.getText();
            float iIpk = Float.parseFloat(txtIpk.getText());
            float tTulis = Float.parseFloat(txtTertulis.getText());
            float tWnc = Float.parseFloat(txtWawancara.getText());
            float tAkhir = Float.parseFloat(txtNakhir.getText());
            tableModel.addRow(new Object[]{tKode, tNama, uUmur ,tJurusan, iIpk, tTulis, tWnc, tAkhir});
            inisialisasi_tabel();
        } catch(Exception e) {
            System.out.println("Error : "+e);
        }
    } 
    private void simpan_database() {
        for(int row=0;row<tblNilai.getRowCount();row++) {
            String tKode = (String)tblNilai.getValueAt(row, 0);
//            System.out.println(tKode);
            String tNama = (String) tblNilai.getValueAt(row, 1);
            int uUmur = (Integer)tblNilai.getValueAt(row, 2);
            String tJurusan = (String) tblNilai.getValueAt(row, 3);
            float iIpk = (Float)tblNilai.getValueAt(row, 4);
            float nTertulis = (Float)tblNilai.getValueAt(row, 5);
            float nWawancara = (Float)tblNilai.getValueAt(row, 6);
            float nAkhir = (Float)tblNilai.getValueAt(row, 7);
            
            try {
                if(edit == true) {
                    stm.executeUpdate("update penilaian set nama='"+tNama+"',umur='"+uUmur+"',jurusan="+tJurusan+",ipk="+iIpk+",tertulis="+nTertulis+",wawancara="+nWawancara+",nilai_akhir="+nAkhir+" where kode='" + tKode + "'"); 

                } else {
                    stm.executeUpdate("INSERT into penilaian VALUES('"+tKode+"','"+tNama+"','"+uUmur+"','"+tJurusan+"','"+iIpk+"','"+nTertulis+"','"+nWawancara+"','"+nAkhir+"')"); 
                }
                tblNilai.setModel(new DefaultTableModel(dataTable,header));
            } catch(SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    private void kosong() {
        cmbKode.setToolTipText("");
        txtNama.setText("");
        txtUmur.setText("");
        txtJurusan.setText("");
        txtIpk.setText("");
        txtTertulis.setText("");
        txtWawancara.setText("");
        txtNakhir.setText("");
    }
    
    private void aktif(boolean x) {
        cmbKode.setEnabled(x);
        txtNama.setEditable(x);
        txtUmur.setEditable(x);
        txtJurusan.setEditable(x);
        txtIpk.setEditable(x);
        txtTertulis.setEditable(x);
        txtWawancara.setEditable(x);
        txtNakhir.setEnabled(x);
        text.setEditable(x);
    }
    
    private void setTombol(boolean t) {
        cmdTambah.setEnabled(t);
        cmdSimpan.setEnabled(!t);
        cmdBatal.setEnabled(!t);
        cmdKeluar.setEnabled(t); 

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
        txtNama = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNilai = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        txtUmur = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtJurusan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtIpk = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtWawancara = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTertulis = new javax.swing.JTextField();
        txtNakhir = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cmdTambah = new javax.swing.JButton();
        cmdSimpan = new javax.swing.JButton();
        cmdBatal = new javax.swing.JButton();
        cmdCetak = new javax.swing.JButton();
        cmdKeluar = new javax.swing.JButton();
        cmbKode = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form Seleksi Penilaian");

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel1.setText("Seleksi Peniliaian");

        txtNama.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel2.setText("Nama ");

        jLabel12.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel12.setText("Kode Peserta");

        tblNilai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode", "Nama", "Umur", "Jurusan", "IPK", "Tertulis", "Wawancara", "Nilai Akhir"
            }
        ));
        tblNilai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNilaiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNilai);

        text.setColumns(20);
        text.setRows(5);
        jScrollPane3.setViewportView(text);

        jLabel13.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel13.setText("Umur");

        txtUmur.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel3.setText("Jurusan");

        txtJurusan.setToolTipText("");

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel4.setText("IPK");

        txtIpk.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel6.setText("Nilai Tes Wawancara");

        txtWawancara.setToolTipText("");
        txtWawancara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWawancaraActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel14.setText("Nilai Tes Tertulis");

        txtTertulis.setToolTipText("");

        txtNakhir.setToolTipText("");
        txtNakhir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNakhirMouseClicked(evt);
            }
        });
        txtNakhir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNakhirActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel7.setText("Rata - rata Nilai Akhir");

        cmdTambah.setText("Tambah");
        cmdTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTambahActionPerformed(evt);
            }
        });

        cmdSimpan.setText("Simpan");
        cmdSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSimpanActionPerformed(evt);
            }
        });

        cmdBatal.setText("Batal");
        cmdBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBatalActionPerformed(evt);
            }
        });

        cmdCetak.setText("Cetak");
        cmdCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCetakActionPerformed(evt);
            }
        });

        cmdKeluar.setText("Keluar");
        cmdKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKeluarActionPerformed(evt);
            }
        });

        cmbKode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- PILIH ---" }));
        cmbKode.setToolTipText("");
        cmbKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKodeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cmbKode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel13)
                                            .addGap(51, 51, 51)
                                            .addComponent(txtUmur, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtIpk, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTertulis, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtWawancara, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNakhir, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(21, 21, 21)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmdTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdBatal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdCetak)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdKeluar)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(txtTertulis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(txtWawancara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtNakhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(cmbKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(txtUmur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIpk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cmdKeluar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdTambah)
                            .addComponent(cmdSimpan)
                            .addComponent(cmdBatal)
                            .addComponent(cmdCetak))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblNilaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNilaiMouseClicked

    }//GEN-LAST:event_tblNilaiMouseClicked

    private void txtWawancaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWawancaraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtWawancaraActionPerformed

    private void txtNakhirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNakhirActionPerformed
        try {
            hitung_nilai();
            simpan_ditabel();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txtNakhirActionPerformed

    private void cmdTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTambahActionPerformed
        kosong();
        ComboBoxModel c1=new DefaultComboBoxModel();
        cmbKode.setModel(c1);
        baca_peserta();
        aktif(true);
        setTombol(false);
    }//GEN-LAST:event_cmdTambahActionPerformed

    private void cmdSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSimpanActionPerformed
        try {
            simpan_database();
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        JOptionPane.showMessageDialog(null, "Data Berhasil disimpan");
    }//GEN-LAST:event_cmdSimpanActionPerformed

    private void cmdBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBatalActionPerformed
        aktif(false);
        setTombol(true);
    }//GEN-LAST:event_cmdBatalActionPerformed

    private void cmdCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCetakActionPerformed
        String ctk="Daftar Peserta Lolos Seleksi Penilaian";
        ctk=ctk+"\n"+"-----------------------------------------------------------------------------------------"+"\n";
        ctk=ctk+"\n"+"-----------------------------------------------------------------------------------------"+"";
        ctk=ctk+"\n"+"Kode Peserta | Nama | Umur | Jurusan | IPK | Tes Tertulis | Tes Wawancara | Nilai Akhir";
        ctk=ctk+"\n"+"-------------------------------------------------------------------------------------------------";
        for(int i=0;i<tblNilai.getRowCount();i++) {
            String xkd=(String)tblNilai.getValueAt(i,0);
            String xnama=(String)tblNilai.getValueAt(i,1);
            int xumur=(Integer)tblNilai.getValueAt(i,2);
            String xjurusan=(String)tblNilai.getValueAt(i,3);
            Float xipk=(Float)tblNilai.getValueAt(i,4);
            Float xtertulis=(Float)tblNilai.getValueAt(i,5);
            Float xwawancara=(Float)tblNilai.getValueAt(i,6);
            Float xakhir=(Float)tblNilai.getValueAt(i,7);
            ctk=ctk+"\n"+xkd+" | "+xnama+" | "+xumur+" | "+xjurusan+" | "+xipk+" | "+xtertulis+" | "+xwawancara+" | "+xakhir;
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

    private void cmdKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_cmdKeluarActionPerformed

    private void cmbKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKodeActionPerformed
        JComboBox cKd = (javax.swing.JComboBox)evt.getSource();
        //Membaca Item Yang Terpilih — > String
        kKd = (String)cKd.getSelectedItem();
        detail_peserta(kKd);
    }//GEN-LAST:event_cmbKodeActionPerformed

    private void txtNakhirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNakhirMouseClicked
        try {
            hitung_nilai();
            simpan_ditabel();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_txtNakhirMouseClicked

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
            java.util.logging.Logger.getLogger(FormPenilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPenilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPenilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPenilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPenilaian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbKode;
    private javax.swing.JButton cmdBatal;
    private javax.swing.JButton cmdCetak;
    private javax.swing.JButton cmdKeluar;
    private javax.swing.JButton cmdSimpan;
    private javax.swing.JButton cmdTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblNilai;
    private javax.swing.JTextArea text;
    private javax.swing.JTextField txtIpk;
    private javax.swing.JTextField txtJurusan;
    private javax.swing.JTextField txtNakhir;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtTertulis;
    private javax.swing.JTextField txtUmur;
    private javax.swing.JTextField txtWawancara;
    // End of variables declaration//GEN-END:variables
}
