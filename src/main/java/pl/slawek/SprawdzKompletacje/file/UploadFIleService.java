package pl.slawek.SprawdzKompletacje.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
class UploadFIleService {

    @Value("${file.path}")
    private String destinationPath;

    public void copyFile(String uploadingFile){
        File copied = new File(uploadingFile);
        try (InputStream in = new BufferedInputStream(new FileInputStream(uploadingFile));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(destinationPath + copied.getName()))) {
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
