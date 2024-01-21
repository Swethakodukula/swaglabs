package Helpers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelHelpers {
	
	 Workbook workbook;
	 Sheet sheet;
	 
	 public void xl_book(String filePath,String sheetName, String data) {
		 try {	            // Check if the Excel file exists
		            if (new File(filePath).exists()) {
		                // If it exists, read the existing workbook
		                FileInputStream fileInputStream = new FileInputStream(new File(filePath));
		                workbook = new XSSFWorkbook(fileInputStream);
		                fileInputStream.close();
		            } else {
		                // If it doesn't exist, create a new workbook
		                workbook = new XSSFWorkbook();
		            }

		            // Get or create a sheet in the workbook
		            Sheet sheet = workbook.getSheet(sheetName);
		            if (sheet == null) {
		                sheet = workbook.createSheet(sheetName);
		            }

		            // Write data to the sheet
		            writeData(sheet, data);

		            // Write the workbook to an Excel file
		            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
		                workbook.write(fileOut);
		                System.out.println("Excel file created/updated successfully!");
		            }

		            // Close the workbook to release resources
		            workbook.close();

		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		 
		    }

		 private static void writeData(Sheet sheet, String data) {
		        // Write data to the sheet
		        int rowNum = sheet.getLastRowNum() + 1;
		        Row row = sheet.createRow(rowNum);
		        
		        Cell cellName = row.createCell(0);
		        cellName.setCellValue(data);

		    }
	 }