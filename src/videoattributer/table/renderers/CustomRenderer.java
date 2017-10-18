package videoattributer.table.renderers;


import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jesús Soto
 */
  public class CustomRenderer extends DefaultTableCellRenderer 
{
     
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {      
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if(table.getValueAt(row, column).equals("Found")){
            cellComponent.setBackground(new Color(128, 212, 255));
            cellComponent.setForeground(new Color (112, 108, 108));
        } else if(table.getValueAt(row, column).equals("Not Found")){
            cellComponent.setBackground(new Color(244,104,66));
            cellComponent.setForeground(Color.WHITE);
        }else if(table.getValueAt(row, column).equals("Not Edited")){
            cellComponent.setBackground(new Color(226,229,185));
            cellComponent.setForeground(new Color (112, 108, 108));
        }else if(table.getValueAt(row, column).equals("Edited")){
            cellComponent.setBackground(new Color(65,181,84));
            cellComponent.setForeground(Color.WHITE);
        }
        return cellComponent;
    }
}

