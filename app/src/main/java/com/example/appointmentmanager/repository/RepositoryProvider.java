package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.data.ApiClient;
import com.example.appointmentmanager.data.ApiEndpoints;

/**
 * Class responsible for providing instances of repositories and API endpoints.
 */
public class RepositoryProvider {
    // Static instances of API endpoints and repositories
    private static ApiEndpoints apiEndpoints;
    private static AppointmentRepository appointmentRepository;
    private static PatientRepository patientRepository;

    /**
     * Provides an instance of the Retrofit API endpoints.
     *
     * @return An instance of the Retrofit API endpoints.
     */
    public static ApiEndpoints provideApiEndpoints() {
        // Check if the API endpoints instance is null
        if (apiEndpoints == null) {
            // Create a new instance using the ApiClient
            apiEndpoints = ApiClient.getClient().create(ApiEndpoints.class);
        }
        // Return the instance of the API endpoints
        return apiEndpoints;
    }

    /**
     * Provides an instance of the AppointmentRepository.
     *
     * @return An instance of the AppointmentRepository.
     */
    public static AppointmentRepository provideAppointmentRepository() {
        // Check if the appointmentRepository instance is null
        if (appointmentRepository == null) {
            // Create a new instance of the AppointmentRepository with the API endpoints
            appointmentRepository = new AppointmentRepository(provideApiEndpoints());
        }
        // Return the instance of the AppointmentRepository
        return appointmentRepository;
    }

    /**
     * Provides an instance of the PatientRepository.
     *
     * @return An instance of the PatientRepository.
     */
    public static PatientRepository providePatientRepository() {
        // Check if the patientRepository instance is null
        if (patientRepository == null) {
            // Create a new instance of the PatientRepository with the API endpoints
            patientRepository = new PatientRepository(provideApiEndpoints());
        }
        // Return the instance of the PatientRepository
        return patientRepository;
    }
}
