package pl.slawek.SprawdzKompletacje.file;

import lombok.Data;
import org.springframework.stereotype.Service;
import pl.slawek.SprawdzKompletacje.file.config.PathFileConfig;

import java.io.*;

@Data
@Service
public class CopyFileService {
    private final PathFileConfig pathFileConfig;
    public CopyFileService(PathFileConfig pathFileConfig) {
        this.pathFileConfig = pathFileConfig;
    }

    public void copyFile(String uploadingFile, InputStream in){
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(pathFileConfig.getPath() + uploadingFile))) {
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

     public void copyFile(final String uploadingFile, final InputStream in, final String finishedFilePath) {
         try (OutputStream out = new BufferedOutputStream(new FileOutputStream(pathFileConfig.getPath() + finishedFilePath + uploadingFile))) {
             byte[] buffer = new byte[1024];
             int lengthRead;
             while ((lengthRead = in.read(buffer)) > 0) {
                 out.write(buffer, 0, lengthRead);
                 out.flush();
             }
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
    }
}
