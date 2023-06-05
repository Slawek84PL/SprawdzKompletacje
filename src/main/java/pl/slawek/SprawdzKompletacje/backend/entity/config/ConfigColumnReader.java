package pl.slawek.SprawdzKompletacje.backend.entity.config;

import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class ConfigColumnReader {

    private DataFormatter dataFormatter =  new DataFormatter();

    public List<ConfigHeaderHelper> readExcelFile(File file) throws IOException {

        List<ConfigHeaderHelper> configs = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            int firstRow = sheet.getFirstRowNum();
            Row hederRow = sheet.getRow(firstRow);

            for (Cell column : hederRow) {
                configs.add(new ConfigHeaderHelper(column.getColumnIndex(), dataFormatter.formatCellValue(column), false));
            }
        }
    return configs;
    }
}
