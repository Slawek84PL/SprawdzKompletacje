package pl.slawek.SprawdzKompletacje.file;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import pl.slawek.SprawdzKompletacje.skan.Product;

import java.io.*;
import java.util.Iterator;

@Component
public class ExcelWriter {
    public static void updateProduct(File file, Product product) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Cell barcodeCell = currentRow.getCell(0);
                if (barcodeCell.getStringCellValue().equals(product.getBarcode())) {
                    Cell scannedQuantity = currentRow.getCell(3);
                    scannedQuantity.setCellValue(product.getScannedQuantity());
                    break;
                }
            }

            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

