package pl.slawek.SprawdzKompletacje.entity.product;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import pl.slawek.SprawdzKompletacje.entity.order.OrderService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

@Service
public
class ProductService {

    private final ProductRepository productRepo;
    private final OrderService orderService;

    public ProductService(final ProductRepository productRepo, final OrderService orderService) {
        this.productRepo = productRepo;
        this.orderService = orderService;
    }

    public void readExcel(File uploadingFile, String fileName) {
        long orderId = orderService.addOrder(fileName);
        Workbook workbook;
        try (FileInputStream inputStream = new FileInputStream(uploadingFile)) {
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
                product.setOrderNumberId(orderId);
                productRepo.save(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
