package pl.slawek.SprawdzKompletacje.file;

import lombok.Data;
import org.springframework.stereotype.Service;
import pl.slawek.SprawdzKompletacje.file.config.PathFileConfig;

import java.io.*;
import java.nio.file.Files;

@Data
@Service
public
class FinishFileService {

    private final CopyFileService copyFileService;
    private final PathFileConfig pathFileConfig;

    public void moveAndRenameFile(String fileName) {
        File file = new File(pathFileConfig.getPath() + fileName);
        File newFile = new File(pathFileConfig.getPath() + pathFileConfig.getFinishedFilePath() + file.getName());
        try {
            Files.move(file.toPath(), newFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
