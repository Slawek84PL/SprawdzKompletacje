package pl.slawek.SprawdzKompletacje.front.upload;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.slawek.SprawdzKompletacje.backend.entity.config.Config;
import pl.slawek.SprawdzKompletacje.backend.entity.config.ConfigColumnReader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

class ImportConfigView extends VerticalLayout {

    private final ConfigColumnReader columnReader = new ConfigColumnReader();
    private Grid<Config> configGrid = new Grid<>(Config.class);
    private List<Config> headers;
    private File file;

    ImportConfigView(File file) throws IOException {
        this.file = file;
        importReders();
        createGrid();
    }

    private void importReders() throws IOException {
        headers = columnReader.readExcelFile(file);
    }

    private void createGrid() {
        configGrid.setItems(headers);
        configGrid.addColumn(Config::getColumnNameHeader).setHeader("Kolumna w pliku klienta");
        configGrid.addComponentColumn().setHeader("Nazwa w twoim imporcie");
        add(configGrid);
    }
}
