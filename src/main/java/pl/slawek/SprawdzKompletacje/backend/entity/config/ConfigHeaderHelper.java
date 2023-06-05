package pl.slawek.SprawdzKompletacje.backend.entity.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfigHeaderHelper {

    private int columnIndex;
    private String columnHeader;
    private boolean use;

}