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

    public List<Config> readExcelFile(File file) throws IOException {

        List<Config> configs = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            int firstRow = sheet.getFirstRowNum();
            Row hederRow = sheet.getRow(firstRow);

            for (Cell column : hederRow) {
                Config config = new Config();
                config.setColumnNumber(column.getColumnIndex());
                config.setColumnNameHeader(dataFormatter.formatCellValue(column));
                configs.add(config);
            }
        }
    return configs;
    }
}
