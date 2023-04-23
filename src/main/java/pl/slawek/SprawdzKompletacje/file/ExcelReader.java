package pl.slawek.SprawdzKompletacje.file;

import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import pl.slawek.SprawdzKompletacje.file.config.PathFileConfig;
import pl.slawek.SprawdzKompletacje.skan.Product;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Service
public class ExcelReader {

    private final PathFileConfig pathFileConfig;

    public ExcelReader(final PathFileConfig pathFileConfig) {
        this.pathFileConfig = pathFileConfig;
    }

    public List<Product> readProductsFromExcel(String selectedFile) {
        List<Product> productList = new ArrayList<>();

        Workbook workbook;
        try (FileInputStream inputStream = new FileInputStream(pathFileConfig.getPath() + selectedFile)) {
            workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                Product product = new Product();

                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    int columnIndex = currentCell.getColumnIndex();

                    switch (columnIndex) {
                        case 0:
                            if(currentCell.getCellType() == CellType.NUMERIC){
                                product.setBarcode(String.valueOf((int) currentCell.getNumericCellValue()));
                            } else {
                                product.setBarcode(currentCell.getStringCellValue());
                            }
                            break;
                        case 1:
                            product.setName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            product.setQuantity((int) currentCell.getNumericCellValue());
                            break;
                        case 3:
                            product.setScannedQuantity((int) currentCell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                }
                productList.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productList;
    }
}
