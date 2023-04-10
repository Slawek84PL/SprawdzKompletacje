package pl.slawek.SprawdzKompletacje.file;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.*;

@Data
@Service
class DeleteFileService {

    private final CopyFileService copyFileService;

    private String finishedFilePath = "ukończone\\";

    public void finishFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        try (InputStream inputStream = new FileInputStream(file)) {
            copyFileService.copyFile(file.getName(), inputStream, finishedFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // todo dodać metody i inne do zmiany nazwy pliku na zakończony,
        //  oraz przenieść plik do nowej lokalizacji na zakończone pliki
    }

}
