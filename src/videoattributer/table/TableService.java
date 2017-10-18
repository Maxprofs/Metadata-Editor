/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoattributer.table;

import videoattributer.table.renderers.ColorColumnRenderer;
import videoattributer.table.renderers.CheckBoxRenderer;
import static ExcelReading.ReadWriteExcelFile.videos;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.TableColumn;
import videoattributer.gui.main;
import static videoattributer.gui.main.TableVideoModel;
import static videoattributer.gui.main.VideoTable;
import static videoattributer.gui.main.jLabel1;
import static videoattributer.gui.main.jPanel3;
import static videoattributer.gui.main.jPanel4;
import static videoattributer.gui.main.jProgressBar2;
import static videoattributer.gui.main.videoDirectorySourcePath;
import videoattributer.object.Video;
import videoattributer.object.VideoMetadata;
import videoattributer.table.renderers.CustomRenderer;

/**
 *
 * @author Jesus Soto
 */
public class TableService {   
    static int counter = 0;    
    static int videoCounterInTimer = 0;
    static int sec = 0;
    static int progressBarValue = 0;
    static int TimeController = 0;
    volatile static boolean threadIsRunning;
    private static volatile boolean stopRequested;
    private static Thread runThread;
    static String systemArch;
    static List<Video> foundVideos = new ArrayList<Video>();
    
    public static boolean SetDefaultTableStyle(){

       if(System.getProperty("os.arch").equals("amd64")){
         systemArch = "amd64";
       }else{
         systemArch = "x86";  
       }             
        
        foundVideos.clear();        
        
        if(VideoTable.getColumnModel().getColumn(0).getHeaderValue().equals("Video Name")){                  
            //Look for videos inside directory       
            return SearchVideosInPathSpecified();               
        }else{                   
            TableVideoModel.removeRow(0);
            //Setting colors and size
            VideoTable.setRowHeight(25);        
            VideoTable.setFont(new Font("Dialog", Font.PLAIN, 15));   
            VideoTable.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 16));

            //Adding the rest of the columns

            TableColumn found = new TableColumn();
            TableColumn selected = new TableColumn();
            TableColumn edited = new TableColumn();


            TableVideoModel.addColumn(found);
            TableVideoModel.addColumn(selected);
            TableVideoModel.addColumn(edited);

            //Setting names to the headers
            VideoTable.getColumnModel().getColumn(0).setHeaderValue("Video Name");
            VideoTable.getColumnModel().getColumn(1).setHeaderValue("Found");
            VideoTable.getColumnModel().getColumn(2).setHeaderValue("Selected");        
            VideoTable.getColumnModel().getColumn(3).setHeaderValue("Edited");                

            TableColumn tm = VideoTable.getColumnModel().getColumn(0);
            tm.setCellRenderer(new ColorColumnRenderer(new Color(191,191,191), new Color(0, 38, 77)));
            
            //Look for videos inside directory       
            return SearchVideosInPathSpecified();  
        }
    }
    
    public static boolean SearchVideosInPathSpecified(){
        
        //Clean table before adding new rows          
        int rows = TableVideoModel.getRowCount(); 
        for(int i = rows - 1; i >=0; i--){
           TableVideoModel.removeRow(i); 
        }
        
        for(Video video: videos){
            File file = new File(videoDirectorySourcePath + "\\" + video.getName());
            try (FileInputStream fis = new FileInputStream(file)) {   
                foundVideos.add(video);
                try{  
                    TableVideoModel.addRow(new Object[]{
                        video.getName(),
                        "Found",
                        Boolean.TRUE,
                        "Not Edited"
                    });                                                                       
                 }catch(Exception e){
                     System.err.println(e);
                     return false;
                } 
            } catch (IOException e) {                    
                String causeFilter[] = e.toString().split(":");
                String cause = causeFilter[0];                    
                if(cause.equals("java.io.FileNotFoundException")){
                    try{  
                        TableVideoModel.addRow(new Object[]{
                            video.getName(),
                            "Not Found",
                            Boolean.FALSE,
                            "Not Edited"
                        });            
                    }catch(Exception ex){
                        System.err.println("Look into line 88, inside TableService class." + ex);
                        return false;
                   }
                }
            }
        }         
        
        VideoTable.getColumn("Found").setCellRenderer(new CustomRenderer());
        VideoTable.getColumn("Edited").setCellRenderer(new CustomRenderer());        
        VideoTable.getColumn("Selected").setCellRenderer(new CheckBoxRenderer());
        threadIsRunning = true;                                            
        
        jProgressBar2.setVisible(true);
        jLabel1.setText("Looking for files specified");
        jLabel1.setVisible(true);
        
        runProgressBar();
        run();
        return true;
    }
    
    public static void runProgressBar(){
      Thread clock = new Thread(){        
          public void run(){                          
              for(;;){    
                  if(threadIsRunning){
                    sec++;                      
                    jProgressBar2.setValue(progressBarValue+=10);                    
                    jLabel1.setText("Loading found videos...");        
                    if(jProgressBar2.getValue() == 100){                    
                        sec = 0;
                        jProgressBar2.setVisible(false);       
                        jLabel1.setVisible(false);
                        threadIsRunning = false;
                        VideoTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        jPanel3.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        jPanel4.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        main.jButton4.setEnabled(true);
                        main.jMenuItem3.setEnabled(true);
                    }
                    
               //the code below controls the time on the thread
                  try {
                      sleep(1000);
                  } catch (InterruptedException ex) {
                      System.err.println(ex);
                  }// end try-catch
              }//rare for   
            }
          }//run          
       };//trhead
      clock.start();
    }//currentDate
    
    public static void retrieveMetadata(File file, Video video){
        VideoMetadata metadata = new VideoMetadata();
        String properties[] = {"System.Title", "System.Media.AuthorUrl",
                               "System.Media.PromotionUrl", "System.Media.SubTitle",
                               "System.Rating", "System.Keywords","System.Comment"};
        metadata.setName(video.getName());
        for(int i=0; i<properties.length; i++){
            try{
                String command[] = {"C:/ProgramData/Metadata Editor/"+systemArch+"/shellproperty.exe" , 
                                  "read", properties[i], "from", file.toString()};            

                ProcessBuilder processBuilder = new ProcessBuilder(command);

                processBuilder.redirectErrorStream(true);

                Process process = processBuilder.start();
                StringBuilder processOutput = new StringBuilder();

                try (BufferedReader processOutputReader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));){
                    String readLine;
                    while ((readLine = processOutputReader.readLine()) != null){
                        processOutput.append(readLine + System.lineSeparator());
                    }
                    process.waitFor();
                }

                String queueResult = processOutput.toString().trim();
                String plainResult[] = queueResult.split(":");                                
                
                switch(i){
                    case 0:                        
                        if(plainResult.length > 1){                                                                                                  
                            metadata.setTitle(plainResult[1]);                            
                        } else {
                            metadata.setTitle("N/A");
                        }                        
                        break;
                    case 1:                        
                        if(plainResult.length > 1){
                            metadata.setAuthor_url(plainResult[1]); 
                        } else {
                            metadata.setAuthor_url("N/A"); 
                        }
                        break;                        
                    case 2:                        
                       if(plainResult.length > 1){
                            metadata.setPromotion_url(plainResult[1]); 
                        } else {
                            metadata.setPromotion_url("N/A"); 
                        }
                        
                        break;
                    case 3:                        
                       if(plainResult.length > 1){
                            metadata.setSubtitles(plainResult[1]); 
                        } else {
                            metadata.setSubtitles("N/A"); 
                        }
                        
                        break;
                    case 4:                        
                        metadata.setRating(plainResult[1]);                        
                        break;
                    case 5:                        
                        if(plainResult.length > 1){
                            metadata.setTags(plainResult[1]); 
                        } else {
                            metadata.setTags("N/A"); 
                        }
                        
                        break;
                    case 6:                        
                        if(plainResult.length > 1){
                            metadata.setComments(plainResult[1]); 
                        } else {
                            metadata.setComments("No Comments..."); 
                        }
                        
                        break;
                }                                               
            }catch(Exception e){
                System.out.println(e);
            }
        }
        video.setVideoMetadata(metadata);
    }
    
    public static void run() {  
        runThread = Thread.currentThread();
        stopRequested=false;
        
        while(!stopRequested){     
         TimeController++;
         if(TimeController == 1){
             System.out.println("Retrieving file number: "+counter);
             System.err.println("_________________________________");
             File file = new File(videoDirectorySourcePath + "\\" + foundVideos.get(counter).getName());
             retrieveMetadata(file, foundVideos.get(counter));
             counter++;             
         }
         
         if(TimeController > 2){
             TimeController = 0;
         }
         
         if(counter >= foundVideos.size()){
             System.out.println("All files retrieved, exiting process...");
             stopRequest();
         }
            try{
                Thread.sleep(1000);
            }catch(InterruptedException x){
                Thread.currentThread().interrupt();
            }
        }
    }
    
    //Method to stop the thread
    public static void stopRequest(){
        //System.out.println("entro en STOP");
        stopRequested=true;
        if(runThread!=null){
            runThread.interrupt();
        }
    }
}