package pl.slawek.SprawdzKompletacje.file;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Service
public class FileLister {

    public List<String> getExcelFileNames(String filePath) {
        File directory = new File(filePath);
        return Arrays.stream(Objects.requireNonNull(directory.listFiles()))
                .filter(file -> file.getName().endsWith(".xlsx"))
                .map(File::getName)
                .collect(Collectors.toList());
    }
}
