package com.crud.person.project.csv;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "csv.output")
@Component
public class CSVConfig {

    private String directory;
    private String filename;

    public String getDirectory() {
        return directory;
    }

    public String getFilename() {
        return filename;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
