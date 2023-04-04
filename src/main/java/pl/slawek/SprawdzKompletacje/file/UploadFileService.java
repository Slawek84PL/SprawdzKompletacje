package pl.slawek.SprawdzKompletacje.file;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class UploadFileService {



    public UploadFileService() {
    }

    public void copyFile(String uploadingFile, InputStream in, String destinationPath){
//        File copied = new File(uploadingFile);
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(destinationPath + uploadingFile))) {
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
