package com.assignment.parking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsvConfig {
    @Value("${csv.report.file-path}")
    private String csvFilePath;

    public String getCsvFilePath() {
        return csvFilePath;
    }
}
