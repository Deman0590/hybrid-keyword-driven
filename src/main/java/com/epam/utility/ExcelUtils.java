package com.epam.utility;

import com.epam.config.Constants;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    private XSSFSheet excelSheet;
    private XSSFWorkbook excelBook;
    private XSSFCell excelCell;

    public void setExcelFile(String path) throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        excelBook = new XSSFWorkbook(inputStream);
    }

    public String getCellData(int row, int col, String sheetName) {
        excelSheet = excelBook.getSheet(sheetName);
        excelCell = excelSheet.getRow(row).getCell(col);
        return (null != excelCell) ? excelCell.getStringCellValue() : null;
    }

    public int getRowCount(String sheetName) {
        excelSheet = excelBook.getSheet(sheetName);
        return excelSheet.getLastRowNum();
    }

    public int getRowContains(String testCaseName, int colNum, String sheetName) {
        int i;
        excelSheet = excelBook.getSheet(sheetName);
        int rowCount = getRowCount(sheetName);
        for (i = 0; i < rowCount; i++) {
            if (getCellData(i, colNum, sheetName).equalsIgnoreCase(testCaseName)){
                break;
            }
        }
        return i;
    }

    public int getTestStepsCount(String sheetName, String testCaseID, int testCaseStart){
        for(int i=testCaseStart;i<=getRowCount(sheetName);i++){
            if(!testCaseID.equals(getCellData(i, Constants.COL_TEST_CASE_ID, sheetName))){
                int number = i;
                return number;
            }
        }
        excelSheet = excelBook.getSheet(sheetName);
        int number=excelSheet.getLastRowNum();
        return number;
    }

}
