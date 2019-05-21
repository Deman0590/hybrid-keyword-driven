package com.epam.executionEngine;

import com.epam.config.ActionKeywords;
import com.epam.config.Constants;
import com.epam.utility.ActionExecutionException;
import com.epam.utility.ExcelException;
import com.epam.utility.ExcelUtils;
import com.epam.utility.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DriverScript {

    public static ActionKeywords actionKeywords;
    public static Method method[];
    private static ExcelUtils excelUtils;

    public static void main(String[] args) {
        try {
            actionKeywords = new ActionKeywords();
            method = actionKeywords.getClass().getMethods();

            String dataFilePath = DriverScript.class.getClassLoader().getResource(Constants.FILE_TEST_DATA).getPath();
            excelUtils = new ExcelUtils();
            excelUtils.setExcelFile(dataFilePath);

            new DriverScript().executeTestCase();
        } catch (Exception e) {
            Log.info("************************************************************************");
            Log.error(e.getMessage());
            Log.info("************************************************************************");
        }
    }

    private void executeTestCase() {

        try {
            int totalTestCases = excelUtils.getRowCount(Constants.SHEET_TEST_CASES);
            System.out.println(totalTestCases);
            for (int i = 1; i <= totalTestCases; i++) {
                String testCaseId = excelUtils.getCellData(i, Constants.COL_TEST_CASE_ID, Constants.SHEET_TEST_CASES);

                String run = excelUtils.getCellData(i, Constants.COL_RUN_MODE, Constants.SHEET_TEST_CASES);

                if (run.equals("Yes")) {
                    int testStep = excelUtils.getRowContains(testCaseId, Constants.COL_TEST_CASE_ID, Constants.SHEET_TEST_STEPS);
                    int testLastStep = excelUtils.getTestStepsCount(Constants.SHEET_TEST_STEPS, testCaseId, testStep);
                    Log.startTestCase(testCaseId);
                    for (; testStep <= testLastStep; testStep++) {
                        String keyword = excelUtils.getCellData(testStep, Constants.COL_ACTION_KEYWORD, Constants.SHEET_TEST_STEPS);
                        String pageObject = excelUtils.getCellData(testStep, Constants.COL_PAGE_OBJECT, Constants.SHEET_TEST_STEPS);

                        System.out.println(keyword + " - " + pageObject);

                        try {
                            executeActions(keyword, pageObject);
                        } catch (Exception e) {
                            break;
                        }
                    }
                    Log.endTestCase(testCaseId);
                }
            }
        } catch (Exception e) {
            Log.info("************************************************************************");
            Log.error(e.getMessage());
            Log.info("************************************************************************");
        }
    }

    private void executeActions(String keyword, String pageObject) throws Exception {

        for (int i = 0; i < method.length; i++) {
            if (method[i].getName().equals(keyword)) {
                method[i].invoke(actionKeywords, pageObject);
                break;
            }
        }
    }
}
