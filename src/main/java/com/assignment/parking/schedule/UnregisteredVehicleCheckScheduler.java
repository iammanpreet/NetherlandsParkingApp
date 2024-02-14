package com.assignment.parking.schedule;

import com.assignment.parking.config.CsvConfig;
import com.assignment.parking.service.UnregisteredLicensePlateService;
import com.assignment.parking.model.response.UnregisteredLicencePlateResponse;

import com.assignment.parking.util.CsvUtility;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Component
public class UnregisteredVehicleCheckScheduler {
    private final UnregisteredLicensePlateService unregisteredLicensePlateService;
    private final CsvConfig csvConfig;
    private static final Logger logger = LoggerFactory.getLogger(UnregisteredVehicleCheckScheduler.class);

    @Autowired
    public UnregisteredVehicleCheckScheduler(UnregisteredLicensePlateService unregisteredLicensePlateService, CsvConfig csvConfig) {
        this.unregisteredLicensePlateService = unregisteredLicensePlateService;
        this.csvConfig = csvConfig;
    }

    /**
     * Cron Job running at 10 min intervals.
     * This job is basically used to fetch the unreported unregistered license plates
     * and generates the report for the same in csv, which is stored in the project itself.
     */
    @Scheduled(cron = "0 */10 * * * *")
    public void checkUnregisteredParkings() {
        logger.info("Scheduled task run started successfully.");
        Optional<List<UnregisteredLicencePlateResponse>> unregisteredPlatesOptional = unregisteredLicensePlateService.findByIsReported("N");
        if (unregisteredPlatesOptional.isPresent()) {
            List<UnregisteredLicencePlateResponse> unregisteredLicensePlateList = unregisteredPlatesOptional.get();
            List<Long> unregisteredPlatesReportIdsList = unregisteredLicensePlateList.stream().map(UnregisteredLicencePlateResponse::getUnregisteredId).collect(Collectors.toList());
            generateReport(unregisteredLicensePlateList);
            unregisteredLicensePlateService.updateIsReportedByIds(unregisteredPlatesReportIdsList);
        } else {
            logger.info("No Unregistered Parkings found since last run.");
        }
        logger.info("Scheduled task completed successfully.");
    }

    /**
     * This method is used to generate the csv report based on the collected unreported data for unregistered license plates
     */
    private void generateReport(List<UnregisteredLicencePlateResponse> unregisteredLicencePlateResponseList) {

        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));
        String csvFilePath = csvConfig.getCsvFilePath() + "unregistered_plates_report_" + currentDateTime + ".csv";

        try {
            CSVWriter csvWriter = CsvUtility.getCsvWriter(unregisteredLicencePlateResponseList, csvFilePath);
            csvWriter.flush();
            logger.info("CSV report generated successfully. File path: {}", csvFilePath);
        } catch (IOException e) {
            logger.error("Error occurred while generating CSV report.", e);
        }
    }
}
