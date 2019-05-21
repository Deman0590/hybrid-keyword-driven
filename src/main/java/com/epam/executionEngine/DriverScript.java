package com.epam.executionEngine;

import com.epam.config.ActionKeywords;
import com.epam.config.Constants;
import com.epam.utility.ExcelUtils;
import com.epam.utility.Log;

import java.lang.reflect.Method;

public class DriverScript {

    public static ActionKeywords actionKeywords;
    public static Method method[];
    private static ExcelUtils excelUtils;

    public static void main(String[] args) {
        try {
            actionKeywords = new ActionKeywords();
            method = actionKeywords.getClass().getMethods();

            excelUtils = new ExcelUtils();
            excelUtils.setExcelFile();

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
                    boolean testCasePass = true;
                    int testStep = excelUtils.getRowContains(testCaseId, Constants.COL_TEST_CASE_ID, Constants.SHEET_TEST_STEPS);
                    int testLastStep = excelUtils.getTestStepsCount(Constants.SHEET_TEST_STEPS, testCaseId, testStep);
                    Log.startTestCase(testCaseId);
                    for (; testStep <= testLastStep; testStep++) {
                        String keyword = excelUtils.getCellData(testStep, Constants.COL_ACTION_KEYWORD, Constants.SHEET_TEST_STEPS);
                        String pageObject = excelUtils.getCellData(testStep, Constants.COL_PAGE_OBJECT, Constants.SHEET_TEST_STEPS);
                        String data = excelUtils.getCellData(testStep, Constants.COL_DATA_SET, Constants.SHEET_TEST_STEPS);

                        System.out.println(keyword + " - " + pageObject);

                        try {
                            executeActions(keyword, pageObject, data);
                            excelUtils.setCellData(Constants.KEYWORD_PASS, testStep, Constants.COL_TEST_STEP_RESULT, Constants.SHEET_TEST_STEPS);
                        } catch (Exception e) {
                            excelUtils.setCellData(Constants.KEYWORD_FAIL, testStep, Constants.COL_TEST_STEP_RESULT, Constants.SHEET_TEST_STEPS);
                            testCasePass = false;
                            break;
                        }
                    }
                    excelUtils.setCellData((testCasePass) ? Constants.KEYWORD_PASS : Constants.KEYWORD_FAIL, i, Constants.COL_RESULT, Constants.SHEET_TEST_CASES);
                    Log.endTestCase(testCaseId);
                }
            }
        } catch (Exception e) {
            Log.info("************************************************************************");
            Log.error(e.getMessage());
            Log.info("************************************************************************");
        }
    }

    private void executeActions(String keyword, String pageObject, String data) throws Exception {

        for (int i = 0; i < method.length; i++) {
            if (method[i].getName().equals(keyword)) {
                method[i].invoke(actionKeywords, pageObject, data);
                break;
            }
        }
    }
}
