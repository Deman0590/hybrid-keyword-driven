package com.epam.utility;

import com.epam.config.Constants;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelUtils {

    private XSSFSheet excelSheet;
    private XSSFWorkbook excelBook;
    private XSSFCell excelCell;

    public void setExcelFile(String path) throws ExcelException {
        try {
            FileInputStream inputStream = new FileInputStream(path);
            excelBook = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            throw new ExcelException("File exception" + e.getMessage());
        }
    }

    public String getCellData(int row, int col, String sheetName) throws ExcelException {
        try {
            excelSheet = excelBook.getSheet(sheetName);
            excelCell = excelSheet.getRow(row).getCell(col);
            return (null != excelCell) ? excelCell.getStringCellValue() : null;
        } catch (Exception e) {
            throw new ExcelException(e.getMessage());
        }
    }

    public int getRowCount(String sheetName) throws ExcelException {
        try {
            excelSheet = excelBook.getSheet(sheetName);
            return excelSheet.getLastRowNum();
        } catch (Exception e) {
            throw new ExcelException(e.getMessage());
        }
    }

    public int getRowContains(String testCaseName, int colNum, String sheetName) throws ExcelException {
        try {
            int i;
            excelSheet = excelBook.getSheet(sheetName);
            int rowCount = getRowCount(sheetName);
            for (i = 0; i < rowCount; i++) {
                if (getCellData(i, colNum, sheetName).equalsIgnoreCase(testCaseName)) {
                    break;
                }
            }
            return i;
        } catch (Exception e) {
            throw new ExcelException(e.getMessage());
        }
    }

    public int getTestStepsCount(String sheetName, String testCaseID, int testCaseStart) throws ExcelException {
        try {
            for (int i = testCaseStart; i <= getRowCount(sheetName); i++) {
                if (!testCaseID.equals(getCellData(i, Constants.COL_TEST_CASE_ID, sheetName))) {
                    int number = i - 1;
                    return number;
                }
            }
            excelSheet = excelBook.getSheet(sheetName);
            int number = excelSheet.getLastRowNum();
            return number;
        } catch (Exception e) {
            throw new ExcelException(e.getMessage());
        }
    }

}
