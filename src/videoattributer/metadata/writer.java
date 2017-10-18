/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoattributer.metadata;

import static ExcelReading.ReadWriteExcelFile.videos;
import java.awt.Cursor;
import java.io.File;
import java.net.URL;
import javax.swing.JOptionPane;
import videoattributer.gui.main;
import static videoattributer.gui.main.TableVideoModel;
import static videoattributer.gui.main.VideoTable;
import static videoattributer.gui.main.jButton4;
import static videoattributer.gui.main.jDialog1;
import static videoattributer.gui.main.jPanel3;
import static videoattributer.gui.main.jPanel4;
import static videoattributer.gui.main.videoDirectorySourcePath;
import static videoattributer.gui.main.videosToEdit;
import videoattributer.object.Video;


/**
 *
 * @author Jesus Soto
 */
public class writer {
    
    private static volatile boolean stopRequested;
    private static Thread runThread;
    static String systemArch = "";
    static int VideoCounter = 0;
    static int TimePropertyController = 0;
    static String properties[] = {"System.Title", "System.Media.AuthorUrl",
                           "System.Media.PromotionUrl", "System.Media.SubTitle",
                           "System.Rating", "System.Keywords","System.Comment",
                           "System.ItemNameDisplay"};
    public void InsertNewMetadata(){
       jButton4.setEnabled(false);
       main.jMenuItem3.setEnabled(false);  
       
       if(System.getProperty("os.arch").equals("amd64")){
         systemArch = "amd64";
       }else{
         systemArch = "x86";  
       }                 
        
       //Start the process
       run();
    }
    
    public static boolean ExecuteModification(String path, Video video, int i){    
         
    try{
        switch(i){
            case 0:
                String commandTitle[] = {"C:/ProgramData/Metadata Editor/"+systemArch+"/shellproperty.exe" , 
                          "set", properties[i], "on", path, "to", "VT_LPWSTR",
                           video.getTitle()}; 
                Runtime.getRuntime().exec(commandTitle);                 
                break;
            case 1:                         
                String commandAuthor = "C:/ProgramData/Metadata Editor/"+systemArch+"/shellproperty.exe set System.Media.AuthorUrl "
                        + "on \""+path+"\" to VT_LPWSTR \""+video.getAuthor_url()+"\"";                                                

                Runtime.getRuntime().exec(commandAuthor);                
                break;
           case 2:
                String commandPomotion[] = {"C:/ProgramData/Metadata Editor/"+systemArch+"/shellproperty.exe" , 
                          "set", properties[i], "on", path, "to", "VT_LPWSTR",
                           video.getPromotion_url()}; 
                Runtime.getRuntime().exec(commandPomotion);                
                break;
            case 3:
                String commandSubtitle[] = {"C:/ProgramData/Metadata Editor/"+systemArch+"/shellproperty.exe" , 
                          "set", properties[i], "on", path, "to", "VT_LPWSTR",
                           video.getSubtitles()}; 
                Runtime.getRuntime().exec(commandSubtitle);                
                break;
            case 4:
                Double d = Double.parseDouble(video.getRating());
                int finalRating = d.intValue();     

                switch(finalRating){
                    case 1: finalRating  = 10;
                        break;
                    case 2: finalRating  = 35;
                        break;
                    case 3: finalRating  = 62;
                        break;
                    case 4: finalRating  = 87;
                        break;
                    case 5: finalRating  = 99;
                        break;
                }

                String commandRating = "C:/ProgramData/Metadata Editor/"+systemArch+"/shellproperty.exe set System.Rating "
                        + "on \""+path+"\" to VT_LPWSTR \""+finalRating+"\"";                                                

                Runtime.getRuntime().exec(commandRating);                
                break;
            case 5:
                String commandTags[] = {"C:/ProgramData/Metadata Editor/"+systemArch+"/shellproperty.exe" , 
                          "set", properties[i], "on", path, "to", "VT_LPWSTR",
                           video.getTags()}; 
                Runtime.getRuntime().exec(commandTags);                
                break;
            case 6:
                String commandComments[] = {"C:/ProgramData/Metadata Editor/"+systemArch+"/shellproperty.exe" , 
                          "set", properties[i], "on", path, "to", "VT_LPWSTR",
                           video.getComments()}; 
                Runtime.getRuntime().exec(commandComments);                
                break;
            case 7:
                String commandName[] = path.split("\\\\");
                String parentPath = path.replace(commandName[commandName.length-1], "");                
                new File(path).renameTo(new File(parentPath+"\\"+video.getName()));                             
                break;
        }

    }catch(Exception e){
        System.err.println(e);
         JOptionPane.showMessageDialog(null, "An error occurring while editing file "+
                 video.getName() + "\n" + e, "Unkonw Error", 0);
        return false;
    }
        
      return true;
    }
    
     public static void run() {              
        runThread = Thread.currentThread();        
        
        while(!stopRequested){                                                    
             
             TimePropertyController++;            
             
             if(TimePropertyController >= 1 && TimePropertyController < 3){
                 for(Video video: videos){                                          
                     if(videosToEdit.get(VideoCounter).equals(video.getName())){
                        ExecuteModification(videoDirectorySourcePath+"\\"+video.getVideoMetadata().getName(),
                                        video, 0);                          
                     }
                 }     
            }
             
            if(TimePropertyController >= 3 && TimePropertyController < 5){                  
              for(Video video: videos){
                 if(videosToEdit.get(VideoCounter).equals(video.getName())){
                    ExecuteModification(videoDirectorySourcePath+"\\"+video.getVideoMetadata().getName(),
                                    video, 1); 
                 }
              }   
            }

            if(TimePropertyController >= 5 && TimePropertyController < 7){                  
              for(Video video: videos){
                 if(videosToEdit.get(VideoCounter).equals(video.getName())){
                    ExecuteModification(videoDirectorySourcePath+"\\"+video.getVideoMetadata().getName(),
                                    video, 2); 
                 }
              } 
            }

            if(TimePropertyController >= 7 && TimePropertyController < 9){                  
              for(Video video: videos){
                 if(videosToEdit.get(VideoCounter).equals(video.getName())){
                    ExecuteModification(videoDirectorySourcePath+"\\"+video.getVideoMetadata().getName(),
                                    video, 3); 
                 }
              }   
            }

            if(TimePropertyController >= 9 && TimePropertyController < 11){                  
              for(Video video: videos){
                 if(videosToEdit.get(VideoCounter).equals(video.getName())){
                    ExecuteModification(videoDirectorySourcePath+"\\"+video.getVideoMetadata().getName(),
                                    video, 4); 
                 }
              }   
            }

            if(TimePropertyController >= 11 && TimePropertyController < 13){                  
              for(Video video: videos){
                 if(videosToEdit.get(VideoCounter).equals(video.getName())){
                    ExecuteModification(videoDirectorySourcePath+"\\"+video.getVideoMetadata().getName(),
                                    video, 5); 
                 }
              } 
            }

            if(TimePropertyController >= 13 && TimePropertyController < 15){                  
              for(Video video: videos){
                 if(videosToEdit.get(VideoCounter).equals(video.getName())){
                    ExecuteModification(videoDirectorySourcePath+"\\"+video.getVideoMetadata().getName(),
                                    video, 6); 
                 }
              }  
            }

            if(TimePropertyController >= 15 && TimePropertyController < 17){                   
              for(Video video: videos){
                 if(videosToEdit.get(VideoCounter).equals(video.getName())){
                    ExecuteModification(videoDirectorySourcePath+"\\"+video.getVideoMetadata().getName(),
                                    video, 7); 
                 }
              }  
            }
         
         if(TimePropertyController > 24){
             System.out.println("File Edited #"+VideoCounter);
             System.err.println("_________________________________");                                       
             VideoCounter++;
             TimePropertyController = 0;
         }
         
         if(VideoCounter >= videosToEdit.size()){                          
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
        System.out.println("All files edited, exiting process...");  
        jDialog1.setVisible(true);
        VideoTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        jPanel3.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        jPanel4.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        stopRequested=true;
        if(runThread!=null){
            runThread.interrupt();
        }
        
         for(int i=0; i<TableVideoModel.getRowCount(); i++){  
            for(int j = 0; j<videosToEdit.size(); j++) {
                if(videosToEdit.get(j).equals(VideoTable.getValueAt(i, 0))){
                    VideoTable.setValueAt("Edited", i, 3);  
                }
            }                        
          }
         
         for(Video video: videos){             
             for(int i=0; i<videosToEdit.size(); i++){
                 if(video.getName().equals(videosToEdit.get(i))){                     
                    video.getVideoMetadata().setAuthor_url(video.getAuthor_url());             
                    video.getVideoMetadata().setComments(video.getComments());
                    video.getVideoMetadata().setName(video.getName());
                    video.getVideoMetadata().setPromotion_url(video.getPromotion_url());
                    video.getVideoMetadata().setSubtitles(video.getSubtitles());
                    video.getVideoMetadata().setTags(video.getTags());
                    video.getVideoMetadata().setTitle(video.getTitle());
                    video.getVideoMetadata().setRating(video.getRating());
                 }
             }          
         }
         
         jDialog1.setVisible(false);
    }
}
