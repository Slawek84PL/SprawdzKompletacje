package pl.slawek.SprawdzKompletacje.file;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Component
public class FileLister {

    private File directory = new File("C:\\Users\\slapy\\OneDrive\\Dokumenty\\Projekty\\SprawdzKompletacje\\");

    public List<String> getExcelFileNames() {
        return Arrays.stream(Objects.requireNonNull(directory.listFiles()))
                .filter(file -> file.getName().endsWith(".xlsx"))
                .map(File::getName)
                .collect(Collectors.toList());
    }
}
