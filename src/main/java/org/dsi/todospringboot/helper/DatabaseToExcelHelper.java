package org.dsi.todospringboot.helper;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dsi.todospringboot.models.Todo;

import java.io.ByteArrayInputStream;
import java.util.List;

public class DatabaseToExcelHelper {
    public static String [] HEADERs = {
            "id",
            "title",
            "description",
            "priority",
            "status",
            "isEnabled",
            "updatedTime"
    };

    public static String SHEET_NAME = "todo_data";

    public static ByteArrayInputStream dataToExcel(List<Todo> todos) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet(SHEET_NAME);

        Row row = sheet.createRow(0);
        for(int i=0; i< HEADERs.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(HEADERs[i]);
        }

        int rowNumber = 1;
        for(Todo todo : todos) {
            Row dataRow = sheet.createRow(rowNumber);
            rowNumber++;
            dataRow.createCell(0).setCellValue(todo.getId());
            dataRow.createCell(1).setCellValue(todo.getTitle());
            dataRow.createCell(2).setCellValue(todo.getDescription());
            dataRow.createCell(3).setCellValue(todo.getPriority());
            dataRow.createCell(4).setCellValue(todo.getStatus());
            dataRow.createCell(5).setCellValue(todo.getIsEnabled());
            dataRow.createCell(6).setCellValue(todo.getUpdatedTime().toString());
        }
        workbook.write(byteArrayOutputStream);
        workbook.close();
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
