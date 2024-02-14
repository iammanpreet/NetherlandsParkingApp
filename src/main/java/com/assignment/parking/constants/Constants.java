package com.assignment.parking.constants;

public interface Constants {
    /**
     * Default is reported value to fetch the unregistered parking which are not reported yet.
     * */
    String DEFAULT_REPORTING_VALUE = "N";
    /**
     * Error context if vehicle is already parked.
     * */
    String VEHICLE_ALREADY_PARKED_EXCEPTION = "Vehicle registration is already active in parking system. Please de-register and register again.";
    /**
     * Error context if vehicle parking entry is not found.
     * */
    String NO_VEHICLE_PARKED_EXCEPTION = "No active parking session found for license plate: ";

}
