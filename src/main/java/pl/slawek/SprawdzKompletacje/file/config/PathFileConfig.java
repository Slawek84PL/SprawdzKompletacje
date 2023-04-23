package pl.slawek.SprawdzKompletacje.file.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix="file")
public
class PathFileConfig {
    private String path = "";
    private String finishedFilePath = "";
}
