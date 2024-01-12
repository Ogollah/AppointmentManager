package com.example.appointmentmanager.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.appointmentmanager.model.Patient;
import com.example.appointmentmanager.repository.AppointmentRepository;
import com.example.appointmentmanager.repository.PatientRepository;
import com.example.appointmentmanager.repository.RepositoryProvider;
import com.example.appointmentmanager.utils.Result;

/**
 * ViewModel for managing interactions between the UI and data repositories related to appointments and patients.
 */
public class AppViewModel extends ViewModel {

    // Repositories for handling appointment and patient data
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    /**
     * Constructor for creating an instance of AppViewModel.
     */
    public AppViewModel() {
        // Initialize appointment and patient repositories
        appointmentRepository = RepositoryProvider.provideAppointmentRepository();
        patientRepository = RepositoryProvider.providePatientRepository();
    }

    /**
     * Get the LiveData representing the result of appointment operations.
     *
     * @return LiveData containing the result of appointment operations.
     */
    public LiveData<Result<Void>> getAppointmentResultLiveData() {
        return appointmentRepository.getAppointmentResultLiveData();
    }

    /**
     * Get the LiveData representing the result of patient operations.
     *
     * @return LiveData containing the result of patient operations.
     */
    public LiveData<Result<Patient>> getPatientResultLiveDate() {
        return patientRepository.getPatientResultLiveData();
    }

    /**
     * Save a new appointment with the provided details.
     *
     * @param appointmentDate The date of the appointment.
     * @param appointmentTime The time of the appointment.
     * @param patientId       The ID of the patient for whom the appointment is scheduled.
     */
    public void saveAppointments(String appointmentDate, String appointmentTime, Long patientId) {
        appointmentRepository.saveAppointments(appointmentDate, appointmentTime, patientId);
    }

    /**
     * Save a new patient with the provided details.
     *
     * @param firstName              The first name of the patient.
     * @param surname                The surname of the patient.
     * @param otherName              The other name of the patient.
     * @param patientNumber          The patient number.
     * @param birthDate              The birth date of the patient.
     * @param idNumber               The ID number of the patient.
     * @param mobileNumber           The mobile number of the patient.
     * @param email                  The email address of the patient.
     * @param altContactPerson       The alternative contact person for the patient.
     * @param atlContactPersonPhone  The phone number of the alternative contact person.
     * @param disability             The disability status of the patient.
     * @param county                 The county where the patient is located.
     */
    public void savePatients(String firstName, String surname, String otherName, String patientNumber,
                             String birthDate, String idNumber, String mobileNumber, String email,
                             String altContactPerson, String atlContactPersonPhone, Boolean disability, String county) {
        patientRepository.addPatient(firstName, surname, otherName, patientNumber,
                birthDate, idNumber, mobileNumber, email,
                altContactPerson, atlContactPersonPhone, disability, county);
    }
}
