/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectakhir;

import java.io.File;
import java.io.FileWriter;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author Nantodd
 */
public class ExportToExcel {
    
    public ExportToExcel(JTable table, File file) {
        try {
            TableModel tableModel = table.getModel();
            FileWriter fOut = new FileWriter(file);
            for(int i = 0; i < tableModel.getColumnCount(); i++){
                fOut.write(tableModel.getColumnName(i)+"\t");
            }
            fOut.write("\n");
            for(int i = 0; i < tableModel.getRowCount(); i++){
                for(int j = 0; j < tableModel.getColumnCount(); j++){
                    fOut.write(tableModel.getValueAt(i, j).toString()+"\t");
                }
                fOut.write("\n");
            }
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // TODO code application logic here
    }
}
