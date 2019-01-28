package sn.zhang.amfparser.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

/**
 * @author Not ZhangJun!
 * This is someone's code copy,because I'm too lazy to study how POI use.
 */

public class ExcelUtils {

    public static <T> HSSFWorkbook export(String[] excelHeader, ArrayList dataList) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle titleStyle = wb.createCellStyle();
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 15);
        titleFont.setFontName("黑体");
        titleStyle.setFont(titleFont);
        HSSFSheet sheet = wb.createSheet("Sheet");
        String[] titleArray = new String[excelHeader.length];
        String[] fieldArray = new String[excelHeader.length];
        for (int i = 0; i < excelHeader.length; i++) {
            String[] tempArray = excelHeader[i].split("#");
            titleArray[i] = tempArray[0];
            fieldArray[i] = tempArray[1];
        }

        HSSFRow row = sheet.createRow(0);
        sheet.autoSizeColumn(0);
        for (int i = 0; i < titleArray.length; i++) {
            HSSFCell titleCell = row.createCell(i);
            titleCell.setCellValue(titleArray[i]);
            titleCell.setCellStyle(titleStyle);
            sheet.autoSizeColumn(i);
        }
        HSSFCellStyle dataStyle = wb.createCellStyle();
        Font dataFont = wb.createFont();
        dataFont.setFontHeightInPoints((short) 12);
        dataFont.setFontName("宋体");
        dataStyle.setFont(dataFont);

        for (int index = 0; index < dataList.size(); index++) {
            row = sheet.createRow(index + 1);
            sheet.autoSizeColumn(0);
            for (int i = 0; i < fieldArray.length; i++) {
                HSSFCell dataCell = row.createCell(i);
                dataCell.setCellStyle(dataStyle);
                sheet.autoSizeColumn(i);
                String[] tmp = (String[]) dataList.get(index);
                dataCell.setCellValue(tmp[i]);
            }
        }

        OutputStream outputStream = new FileOutputStream(new File("id.xls"));
        wb.write(outputStream);
        wb.close();
        outputStream.flush();
        outputStream.close();
        return wb;
    }
}