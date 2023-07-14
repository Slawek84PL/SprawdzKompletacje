package pl.slawek.SprawdzKompletacje.backend.entity.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Header {

    private int columnIndex;
    private String columnName;
}
