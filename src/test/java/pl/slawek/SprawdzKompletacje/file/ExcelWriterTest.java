package pl.slawek.SprawdzKompletacje.file;

import org.junit.jupiter.api.Test;
import pl.slawek.SprawdzKompletacje.skan.Product;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ExcelWriterTest {
    ExcelWriter excelWriter = new ExcelWriter();
    @Test
    void shouldWriteProductToExcelFile() throws IOException {
        String file = "C:\\Users\\slapy\\OneDrive\\Dokumenty\\Projekty\\SprawdzKompletacje\\pliki\\zlecenie 12.xlsx";
        Product product = new Product("123", "nazwa", 10, 11);

        excelWriter.updateProduct(file,product);
    }
}