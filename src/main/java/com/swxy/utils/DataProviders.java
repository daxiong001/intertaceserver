package com.swxy.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataProviders implements Iterator<Object> {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private int rowNum = 0;
    private int cellNum = 0;
    private int currentRowNo = 0;
    List<String> cellTitleName = new ArrayList<>();
    private static final String TEST_DATE_PATH = "src/test/java/testdata/OrderTest.xlsx";


    public DataProviders(){}

    public DataProviders(String sheetName) throws Exception {

        File file = new File(TEST_DATE_PATH);
        if (file.exists() && file.isFile()){
            try {
                workbook = new XSSFWorkbook(file);
                sheet = workbook.getSheet(sheetName);
                rowNum = sheet.getLastRowNum()-sheet.getFirstRowNum() +1;
                Row row = sheet.getRow(0);
                cellNum = row.getLastCellNum() - row.getFirstCellNum();
                for (Cell c:row
                ) {
                    cellTitleName.add(c.toString());
                }
                currentRowNo++;
            } catch (Exception e) {
                e.printStackTrace();

            }
        }else {
            throw new Exception("文件不存在");
        }
    }

    public DataProviders(String dataName,String sheetName) throws Exception {
        File file = new File(dataName);
        if (file.exists() && file.isFile()){
            try {
                workbook = new XSSFWorkbook(file);
                sheet = workbook.getSheet(sheetName);
                rowNum = sheet.getLastRowNum() - sheet.getFirstRowNum() + 1;
                Row row = sheet.getRow(0);
                cellNum = row.getLastCellNum() - row.getFirstCellNum();
                for (Cell c:row
                ) {
                    cellTitleName.add(c.toString());
                }
                currentRowNo++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            throw new Exception("文件不存在");
        }
    }

    @Override
    public boolean hasNext() {
        if (this.rowNum == 0 || this.currentRowNo > rowNum){
            try {
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }else {
            if (sheet.getRow(this.currentRowNo).getCell(0) == null ||
                sheet.getRow(this.currentRowNo).getCell(0).toString().equals("")){
                return false;
            }
            return true;
        }
    }

    @Override
    public Map<String,String> next() {
        Map<String,String> map = new HashMap<>();
        Row row = sheet.getRow(currentRowNo);
        for (int i = 0; i < cellNum; i++) {
            map.put(this.cellTitleName.get(i),row.getCell(i) == null ? "" : row.getCell(i).toString());
        }
        this.currentRowNo++;
        return map;
    }

    public void remove() {

    }

    public static Map<String, Object> inputJsonToMap(String data) {
        Map<String, Object> paramsMap = new HashMap<>();
        String value = "";
        String input = "";
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("inputJson=([\\s\\S]*)[\\}\\]]+");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            value = matcher.group(1);
        }
        if (!StringUtils.isEmpty(value)) {
            if (value.startsWith("\\{")) {
                input = sb.append("{").append(value).append("}").toString();
            } else if (value.startsWith("\\[")) {
                input = sb.append("[").append(value).append("]").toString();
            }
            paramsMap.put("inputJson", input);
        }
        System.out.println(sb);
        return paramsMap;
    }

    public static void main(String[] args) throws Exception {
//        DataProviders orderTest = new DataProviders("OrderTest");
//        while (orderTest.hasNext()){
//            System.out.println(orderTest.next().toString());
//        }
        String s = "\"inputJson=[\n" +
                "  {\n" +
                "    \"\"waybillId\"\": \"\"1ZT18032100000001\"\"\n" +
                "  }\n" +
                "]\"";
        System.out.println(DataProviders.inputJsonToMap(s));;
    }
}
