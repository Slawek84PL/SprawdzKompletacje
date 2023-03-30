package pl.slawek.SprawdzKompletacje.file;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Component
public class FileLister {

    public List<String> getExcelFileNames(String filePath) {
        File directory = new File(filePath);
        return Arrays.stream(Objects.requireNonNull(directory.listFiles()))
                .filter(file -> file.getName().endsWith(".xlsx"))
                .map(File::getName)
                .collect(Collectors.toList());
    }
}
