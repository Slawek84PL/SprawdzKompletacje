package pl.slawek.SprawdzKompletacje.file;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.slawek.SprawdzKompletacje.file.config.FileConfig;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Service
public class FileLister {

    private final FileConfig fileConfig;

    FileLister(final FileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }

    public List<String> getExcelFileNames() {
        File directory = new File(fileConfig.getPath());
        return Arrays.stream(Objects.requireNonNull(directory.listFiles()))
                .filter(file -> file.getName().endsWith(".xlsx"))
                .map(File::getName)
                .collect(Collectors.toList());
    }
}
