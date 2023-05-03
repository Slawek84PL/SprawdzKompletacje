package pl.slawek.SprawdzKompletacje.front;

import com.vaadin.flow.component.upload.UploadI18N;

import java.util.Arrays;

public class UploadFilesI18N extends UploadI18N {
    public UploadFilesI18N() {
        setDropFiles(new DropFiles().setOne("Upuść plik tutaj")
                .setMany("Drop files here"));
        setAddFiles(new AddFiles().setOne("Załaduj plik")
                .setMany("Upload Files..."));
        setError(new Error().setTooManyFiles("Too Many Files.")
                .setFileIsTooBig("Za duży plik")
                .setIncorrectFileType("Niewłaściwy format pliku"));
        setUploading(new Uploading()
                .setStatus(new Uploading.Status().setConnecting("Connecting...")
                        .setStalled("Stalled")
                        .setProcessing("Ładowanie pliku...").setHeld("Queued"))
                .setRemainingTime(new Uploading.RemainingTime()
                        .setPrefix("remaining time: ")
                        .setUnknown("unknown remaining time"))
                .setError(new Uploading.Error()
                        .setServerUnavailable("Plik został już załadowany")
                        .setUnexpectedServerError("Plik został już załadowany")
                        .setForbidden("Przesyłąnie zabronione")));
        setUnits(new Units().setSize(Arrays.asList("B", "kB", "MB", "GB", "TB",
                "PB", "EB", "ZB", "YB")));
    }
}