package pl.slawek.SprawdzKompletacje.backend.entity.config;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigColumnReader {

    private DataFormatter dataFormatter =  new DataFormatter();

    public List<ConfigHeader> readExcelFile(File file) throws IOException {

        List<ConfigHeader> configs = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            int firstRow = sheet.getFirstRowNum();
            Row headerRow = sheet.getRow(firstRow);

            for (Cell column : headerRow) {
                ConfigHeader config = new ConfigHeader();
                config.setColumnIndex(column.getColumnIndex());
                config.setColumnName(dataFormatter.formatCellValue(column));
                configs.add(config);
            }
        }
    return configs;
    }
}
