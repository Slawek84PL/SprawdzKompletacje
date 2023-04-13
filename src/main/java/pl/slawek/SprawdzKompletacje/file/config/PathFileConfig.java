package pl.slawek.SprawdzKompletacje.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="file")
public
class PathFileConfig {
    private String path = "";
    private String finishedFilePath = "";
}
