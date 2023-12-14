package com.example.appointmentmanager.repository;

import com.example.appointmentmanager.data.ApiClient;
import com.example.appointmentmanager.data.ApiEndpoints;

public class RepositoryProvider {

    private static ApiEndpoints apiEndpoints;
    private static AppointmentRepository appointmentRepository;
    private static PatientRepository patientRepository;

    public static ApiEndpoints provideApiEndpoints() {
        if (apiEndpoints == null) {
            apiEndpoints = ApiClient.getClient().create(ApiEndpoints.class);
        }
        return apiEndpoints;
    }

    public static AppointmentRepository provideAppointmentRepository() {
        if (appointmentRepository == null) {
            appointmentRepository = new AppointmentRepository(provideApiEndpoints());
        }
        return appointmentRepository;
    }

    public static PatientRepository providePatientRepository(){
        if (patientRepository == null){
            patientRepository = new PatientRepository(provideApiEndpoints());
        }
        return patientRepository;
    }
}
