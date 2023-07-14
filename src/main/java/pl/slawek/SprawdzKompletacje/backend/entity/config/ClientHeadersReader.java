package pl.slawek.SprawdzKompletacje.backend.entity.config;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientHeadersReader {

    private DataFormatter dataFormatter =  new DataFormatter();

    public List<Header> readExcelFile(File file) throws IOException {

        List<Header> clientHeadersList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            int firstRow = sheet.getFirstRowNum();
            Row headerRow = sheet.getRow(firstRow);

            for (Cell column : headerRow) {
                clientHeadersList.add(new Header(column.getColumnIndex(), dataFormatter.formatCellValue(column)));
            }
        }
    return clientHeadersList;
    }
}
