package org.dsi.todospringboot.services;

import lombok.AllArgsConstructor;
import org.dsi.todospringboot.helper.DatabaseToExcelHelper;
import org.dsi.todospringboot.models.Todo;
import org.dsi.todospringboot.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
@AllArgsConstructor
public class ExcelService {
    private final TodoRepository todoRepository;

    public ByteArrayInputStream getActualData() throws Exception {
        List<Todo> todos = todoRepository.findAll();
        return DatabaseToExcelHelper.dataToExcel(todos);
    }
}
