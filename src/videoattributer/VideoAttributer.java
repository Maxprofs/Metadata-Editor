/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoattributer;

import javax.swing.ImageIcon;
import videoattributer.gui.main;
/**
 *
 * @author Jesus Soto
 */
public class VideoAttributer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       main GUI = new main();
       GUI.setVisible(true);
       GUI.setLocation(500, 100);
       GUI.setTitle("Video Metadata Editor");
       GUI.setLayout(null);       
    }
    
}
