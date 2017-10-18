package ExcelReading;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static videoattributer.gui.main.showChooserForVideosRoute;
import videoattributer.object.Video;

public class ReadWriteExcelFile {

        public static List<Video> videos = new ArrayList<Video>();
        static String videoArray [][] = new String[100][8];
        static int rowCounter = 0;
        static int columnCounter = 0;
        static int numberOfColumns = 0; 
        static String headers [] = {"VIDEO NAME", "TITLE", "SUBTITLES", 
                                    "RATING", "TAGS", "COMMENTS", "AUTHOR URL",
                                    "PROMOTION URL"};
        
	public static void readXLSFile(String filePath) throws IOException{
            //Clear parameters before loading info
            clear_info();                                  
            int matches = 0;
            
            InputStream ExcelFileToRead = new FileInputStream(filePath);
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            HSSFSheet sheet=wb.getSheetAt(0);
            HSSFRow row; 
            HSSFCell cell;

            Iterator rows = sheet.rowIterator();

            while (rows.hasNext()){
                row=(HSSFRow) rows.next();
                Iterator cells = row.cellIterator();

                while (cells.hasNext()){
                    cell=(HSSFCell) cells.next();
                    if(rowCounter > 0){
                      videoArray[rowCounter][columnCounter] = cell.toString();   
                    }else{          
                        for(int i=0; i<headers.length; i++){                            
                            if(headers[i].equals(cell.toString())){
                                matches++;
                            }
                        }                        
                    }                                                                                                    
                    columnCounter++;  	
                }                             
                
                numberOfColumns = columnCounter;
                columnCounter = 0;
                rowCounter++;
            }    
            
            if(matches < 8){
                 String message = " Error importing this file. " +
                                  "The format is not correct. \n"+
                                  "Please use this format in the table's header to get "+
                                  "the file imported correclty: \n"+
             "VIDEO NAME | TITLE | SUBTITLES | RATING | TAGS | COMMENTS | AUTHOR URL | PROMOTION URL";
                 JOptionPane.showMessageDialog(null, message, "Error" , 0);
                 return;
            }
            
            for(int i=0; i<100; i++){                    
               if(videoArray[i][0]!=null){
                   Video ExcelDataVideo = new Video();
                    ExcelDataVideo.setName(videoArray[i][0]);
                    ExcelDataVideo.setTitle(videoArray[i][1]);
                    ExcelDataVideo.setSubtitles(videoArray[i][2]);
                    ExcelDataVideo.setRating(videoArray[i][3]);
                    ExcelDataVideo.setTags(videoArray[i][4]);
                    ExcelDataVideo.setComments(videoArray[i][5]);
                    ExcelDataVideo.setAuthor_url(videoArray[i][6]);
                    ExcelDataVideo.setPromotion_url(videoArray[i][7]);  
                    videos.add(ExcelDataVideo);
                }                                                      
            }
            JOptionPane.showMessageDialog(null, "Now choose a path to look for the videos"
                    + " specified.", "Info", 1);
            showChooserForVideosRoute();
	}
	
	public static void readXLSXFile(String filePath){
            
            //Clear parameters before loading info
            clear_info();            
            int matches = 0;
            
            try{                              
		InputStream ExcelFileToRead = new FileInputStream(filePath);
		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);				
		
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();              
                
		while (rows.hasNext()){
                    row=(XSSFRow) rows.next();
                    Iterator cells = row.cellIterator();  
                    
                    while (cells.hasNext()){
                        cell=(XSSFCell) cells.next();              
                        if(rowCounter > 0){
                          videoArray[rowCounter][columnCounter] = cell.toString();   
                        }else{          
                            for(int i=0; i<headers.length; i++){                            
                                if(headers[i].equals(cell.toString())){
                                    matches++;
                                }
                            }                        
                        }                         
                        columnCounter++;                                                        				
                    }
                    
                    numberOfColumns = columnCounter;
                    columnCounter = 0;
                    rowCounter++;
		}  
                
                 if(matches < 8){
                    String message = " Error importing this file. " +
                                        "The format is not correct. \n"+
                                        "Please use this format in the table's header to get "+
                                        "the file imported correclty: \n"+
                   "VIDEO NAME | TITLE | SUBTITLES | RATING | TAGS | COMMENTS | AUTHOR URL | PROMOTION URL";
                       JOptionPane.showMessageDialog(null, message, "Error" , 0);
                       return;
                  }
                
                for(int i=0; i<100; i++){                                          
                   if(videoArray[i][0]!=null){
                       Video ExcelDataVideo = new Video();
                        ExcelDataVideo.setName(videoArray[i][0]);
                        ExcelDataVideo.setTitle(videoArray[i][1]);
                        ExcelDataVideo.setSubtitles(videoArray[i][2]);
                        
                        try{
                            Double v = Double.parseDouble(videoArray[i][3]);  
                        }catch(Exception e){
                           JOptionPane.showMessageDialog(null, "Please, use an integer value on the rating field", "Error" , 0);
                            return; 
                        }
                        
                        ExcelDataVideo.setRating(videoArray[i][3]);
                        ExcelDataVideo.setTags(videoArray[i][4]);
                        ExcelDataVideo.setComments(videoArray[i][5]);
                        ExcelDataVideo.setAuthor_url(videoArray[i][6]);
                        ExcelDataVideo.setPromotion_url(videoArray[i][7]);  
                        videos.add(ExcelDataVideo);
                    }                                                      
                }
                JOptionPane.showMessageDialog(null, "Now choose a path to look for the videos"
                    + " specified.", "Info", 1);
                showChooserForVideosRoute();
            }catch(Exception e){
                e.printStackTrace();
            }
	}	
        
        public static void clear_info(){
            rowCounter = 0;
            columnCounter = 0;
            numberOfColumns = 0; 
            videos.removeAll(videos);                        
            
            for(int i=0; i<100; i++){
                for(int j=0; j<8; j++){
                    if(videoArray[i][j]!=null){
                        videoArray[i][j] = null;
                    }
                }
            }   
        }
    }