package pl.slawek.SprawdzKompletacje.file;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;

@Data
@Component
public class FileList {

    private File[] fileList = new File[0];
    private File filePath = new File("C:\\Users\\slapy\\OneDrive\\Dokumenty\\Projekty\\SprawdzKompletacje\\");

    public FileList() {
        fileList = this.filePath.listFiles((dir, name) -> name.endsWith(".xlsx"));
    }

    public void printFileList(){
        for (File file : fileList) {
            System.out.println(file.getName());
        }
    }
}
