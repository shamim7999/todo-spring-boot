package org.dsi.todospringboot.helper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dsi.todospringboot.models.Todo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    public static String FILE_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static String integerToString(Integer x) {
        return x.toString();
    }
    public static Boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType.equals(FILE_CONTENT_TYPE);
    }

    public static List<Todo> excelSheetToListOfTodos(InputStream inputStream) throws Exception {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet("Sheet1");
        Iterator<Row> rows = sheet.iterator();
        List<Todo> todos = new ArrayList<>();


        Iterator<Row> iterator = sheet.iterator();
        int rowNumber = 0;

        while(iterator.hasNext()) {
            Row row = iterator.next();
            if(rowNumber == 0) {
                rowNumber++;
                continue;
            }

            Iterator<Cell> cells = row.iterator();
            int cid = 0;

            Todo todo = new Todo();

            while(cells.hasNext()) {
                Cell cell = cells.next();
                switch (cid) {
                    case 0:
                        todo.setTitle(cell.getStringCellValue());
                        break;
                    case 1:
                        todo.setDescription(cell.getStringCellValue());
                        break;
                    case 2:
                        todo.setPriority(integerToString((int) cell.getNumericCellValue()));
                        break;
                    case 3:
                        todo.setStatus(cell.getStringCellValue());
                        break;
                    default:
                        break;
                }
                cid++;
            }
            todos.add(todo);
        }
        return todos;
    }

}
