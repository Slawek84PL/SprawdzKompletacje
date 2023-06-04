package pl.slawek.SprawdzKompletacje.backend.entity.config;

import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class ConfigColumnReader {

    private DataFormatter dataFormatter =  new DataFormatter();

    public Map readExcelFile(File file) throws IOException {

        Map<Integer, String> configs = new HashMap<>();

        try (Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            int firstRow = sheet.getFirstRowNum();
            Row hederRow = sheet.getRow(firstRow);

            for (Cell column : hederRow) {
                configs.put(column.getColumnIndex(), dataFormatter.formatCellValue(column));
            }
        }
    return configs;
    }
}
