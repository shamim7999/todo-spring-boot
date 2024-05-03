package org.dsi.todospringboot.controllers.home;

import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.dsi.todospringboot.services.TodoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

@Controller
@AllArgsConstructor
public class PdfController {

    private final TodoService todoService;

    @GetMapping("/pdf")
    @ResponseBody
    public ResponseEntity<byte[]> generatePdf() throws FileNotFoundException, JRException {
        JRBeanCollectionDataSource jrBeanCollectionDataSource
                = new JRBeanCollectionDataSource(todoService.findAllTodos());
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/Blank_A4.jrxml"));
        HashMap<String, Object> map = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, jrBeanCollectionDataSource);

        //JasperExportManager.exportReportToPdfFile(jasperPrint, "Blank_A4.pdf");
        byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=todos.pdf");

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(data);
    }
}
