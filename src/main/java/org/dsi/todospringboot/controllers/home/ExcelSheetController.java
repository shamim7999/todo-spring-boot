package org.dsi.todospringboot.controllers.home;

import lombok.AllArgsConstructor;
import org.dsi.todospringboot.helper.ExcelHelper;
import org.dsi.todospringboot.services.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class ExcelSheetController {

    private final TodoService todoService;

    @PostMapping("/todos/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws Exception {
        System.out.println("IN UPLOAD CONTROLLER\n"+file.getOriginalFilename()+"\n-------------------");
        if (!file.isEmpty() && ExcelHelper.checkExcelFormat(file)) {
            this.todoService.save(file);
            return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to db"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
    }

}
