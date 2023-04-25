package pl.slawek.SprawdzKompletacje.entity;

import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.slawek.SprawdzKompletacje.entity.order.OrderNumber;
import pl.slawek.SprawdzKompletacje.entity.order.OrderService;
import pl.slawek.SprawdzKompletacje.entity.product.Product;
import pl.slawek.SprawdzKompletacje.entity.product.ProductService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class ExcelReaderToDB {

    private final OrderService orderService;
    private final ProductService productService;

    @Transactional
    public void readExcelFile(File file, String fileName) throws IOException {
        List<Product> products = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            OrderNumber orderNumber = new OrderNumber();
            orderNumber.setFileName(fileName.replaceAll("\\.xlsx?$", ""));
            orderNumber.setDate(LocalDate.now());

            for (Row row : sheet) {

                Product product = new Product();
                if (row.getCell(0).getCellType() == CellType.NUMERIC) {
                    product.setBarcode(String.valueOf((int) row.getCell(0).getNumericCellValue()));
                } else {
                    product.setBarcode(row.getCell(0).getStringCellValue());
                }
                product.setName(row.getCell(1).getStringCellValue());
                product.setQuantity((int) row.getCell(2).getNumericCellValue());
                product.setOrderNumber(orderNumber);
                products.add(product);
            }
            orderService.addProductToOrder(orderNumber, products);
        }
    }
}
