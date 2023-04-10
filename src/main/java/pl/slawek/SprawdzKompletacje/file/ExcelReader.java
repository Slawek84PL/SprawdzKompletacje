package pl.slawek.SprawdzKompletacje.file;

import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import pl.slawek.SprawdzKompletacje.file.config.FileConfig;
import pl.slawek.SprawdzKompletacje.skan.Product;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Service
public class ExcelReader {

    private final FileConfig fileConfig;

    public ExcelReader(final FileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }

    public List<Product> readProductsFromExcel(String selectedFile) {
        List<Product> productList = new ArrayList<>();

        Workbook workbook;
        try (FileInputStream inputStream = new FileInputStream(fileConfig.getPath() + selectedFile)) {
            workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                String code = "";
                String name = "";
                int quantity = 0;
                int scannedQuantity = 0;

                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    int columnIndex = currentCell.getColumnIndex();

                    switch (columnIndex) {
                        case 0:
                            if(currentCell.getCellType() == CellType.NUMERIC){
                                code = String.valueOf((int) currentCell.getNumericCellValue());
                            } else {
                                code = currentCell.getStringCellValue();
                            }
                            break;
                        case 1:
                            name = currentCell.getStringCellValue();
                            break;
                        case 2:
                            quantity = (int) currentCell.getNumericCellValue();
                            break;
                        case 3:
                            scannedQuantity = (int) currentCell.getNumericCellValue();
                            break;
                        default:
                            break;
                    }
                }
                Product product = new Product(code, name, quantity, scannedQuantity);
                productList.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productList;
    }
}
