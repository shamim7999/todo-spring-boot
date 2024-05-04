package org.dsi.todospringboot.controllers.home;

import lombok.AllArgsConstructor;
import org.dsi.todospringboot.helper.ExcelToDatabaseHelper;
import org.dsi.todospringboot.services.ExcelService;
import org.dsi.todospringboot.services.TodoService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Map;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class ExcelSheetController {

    private final ExcelService excelService;
    private final TodoService todoService;

    @PostMapping("/todos/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty() && ExcelToDatabaseHelper.checkExcelFormat(file)) {
            this.todoService.save(file);
            return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to db"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
    }

    @GetMapping("/todos/create-excel")
    public ResponseEntity<?> createExcel() throws Exception {
        String fileName = "todos.xlsx";
        ByteArrayInputStream byteArrayInputStream = excelService.getActualData();
        InputStreamResource file = new InputStreamResource(byteArrayInputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
