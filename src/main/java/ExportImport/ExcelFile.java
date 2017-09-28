package ExportImport;

import functionality.DataHolder;
import functionality.MainWindow;
import model.ReferencedPaper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import javax.swing.*;
import java.io.FileOutputStream;
import java.util.List;


public class ExcelFile { 
 
  

  public  ExcelFile(String filename) { 
	  
	   
	  List<ReferencedPaper> list = DataHolder.listReferencedPaperAll;

      ExportOptionWindow exportOptionWindow = new ExportOptionWindow();
      boolean allReferences = exportOptionWindow.getOption();
    try {
    	
      HSSFWorkbook workbook = new HSSFWorkbook(); 
      HSSFSheet sheet = workbook.createSheet("backward-search"); 
    
      HSSFRow rowHead = sheet.createRow((short) 0); 
      rowHead.createCell(0).setCellValue("id"); 
      rowHead.createCell(1).setCellValue("title"); 
      rowHead.createCell(2).setCellValue("authors"); 
      rowHead.createCell(3).setCellValue("year"); 
      rowHead.createCell(4).setCellValue("journal"); 
      rowHead.createCell(5).setCellValue("volume"); 
      rowHead.createCell(6).setCellValue("issue"); 
      rowHead.createCell(7).setCellValue("page"); 
      rowHead.createCell(8).setCellValue("score"); 
       
      int id = 1;  
         
      for(ReferencedPaper paper : list) 
      {

        if((paper.getMergeID() == null && !allReferences && !paper.isInLiteratureSet())
                || paper.getMergeID() == null && allReferences) {
        HSSFRow row = sheet.createRow((short) id); 
        row.createCell(0).setCellValue(id); 
        row.createCell(1).setCellValue(paper.getTitle()); 
        row.createCell(2).setCellValue(paper.getAuthorsString());
        row.createCell(3).setCellValue(paper.getYear()); 
        row.createCell(4).setCellValue(paper.getJournal());
        row.createCell(5).setCellValue(paper.getVolume()); 
        row.createCell(6).setCellValue(paper.getIssue()); 
        row.createCell(7).setCellValue(paper.getPage()); 
        row.createCell(8).setCellValue(paper.getScore()); 
         
      id += 1; 
 
      }

      }
      FileOutputStream fileOut = new FileOutputStream(filename + ".xls"); 
      workbook.write(fileOut); 
      fileOut.close(); 
 
	    JOptionPane.showMessageDialog(MainWindow.frame, "successfully saved to: " +filename +".xls");
       
    } 
    catch(Exception e) 
    { 
	    JOptionPane.showMessageDialog(MainWindow.frame, "error: " +filename +".xls");

    }
  }
}
