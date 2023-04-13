package pl.slawek.SprawdzKompletacje.file;

import lombok.Data;
import org.springframework.stereotype.Service;
import pl.slawek.SprawdzKompletacje.file.config.PathFileConfig;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Service
public class FileLister { private final PathFileConfig pathFileConfig;

    FileLister(final PathFileConfig pathFileConfig) {
        this.pathFileConfig = pathFileConfig;
    }

    public List<String> getExcelFileNames() {
        File directory = new File(pathFileConfig.getPath());
        return Arrays.stream(Objects.requireNonNull(directory.listFiles()))
                .filter(file -> file.getName().endsWith(".xlsx"))
                .map(File::getName)
                .collect(Collectors.toList());
    }
}
