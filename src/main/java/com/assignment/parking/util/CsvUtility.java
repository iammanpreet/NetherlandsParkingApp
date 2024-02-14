package com.assignment.parking.util;

import com.assignment.parking.model.response.UnregisteredLicencePlateResponse;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvUtility {
    /**
     * This is a utility method in order to generate the csv.
     * */
    public static CSVWriter getCsvWriter(List<UnregisteredLicencePlateResponse> unregisteredLicencePlateResponseList, String csvFilePath) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(new File(csvFilePath)))) {
            String[] header = {"License Plate", "Observation Date", "Street Name"};
            csvWriter.writeNext(header);

            for (UnregisteredLicencePlateResponse response : unregisteredLicencePlateResponseList) {
                String[] rowData = {
                        response.getLicensePlateNumber(),
                        response.getObservationDate().toString(),
                        response.getStreetName()
                };
                csvWriter.writeNext(rowData);
            }
            return csvWriter;
        }
    }
}
