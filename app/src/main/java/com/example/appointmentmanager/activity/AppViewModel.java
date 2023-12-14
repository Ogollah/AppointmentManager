package com.example.appointmentmanager.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.appointmentmanager.model.Patient;
import com.example.appointmentmanager.repository.AppointmentRepository;
import com.example.appointmentmanager.repository.PatientRepository;
import com.example.appointmentmanager.repository.RepositoryProvider;
import com.example.appointmentmanager.utils.Result;

public class AppViewModel extends ViewModel {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public AppViewModel() {
        appointmentRepository = RepositoryProvider.provideAppointmentRepository();
        patientRepository = RepositoryProvider.providePatientRepository();
    }

    public LiveData<Result<Void>> getAppointmentResultLiveData() {
        return appointmentRepository.getAppointmentResultLiveData();
    }

    public LiveData<Result<Patient>> getPatientResultLiveDate(){
        return patientRepository.getPatientResultLiveData();
    }

    public void saveAppointments(String appointmentDate, String appointmentTime, Long patientId) {
        appointmentRepository.saveAppointments(appointmentDate, appointmentTime, patientId);
    }

    public void savePatients(String firstName, String surname, String otherName, String patientNumber,
                             String birthDate, String idNumber, String mobileNumber, String email,
                             String altContactPerson, String atlContactPersonPhone, Boolean disability, String county)
    {
        patientRepository.addPatient(firstName, surname, otherName,patientNumber,
                birthDate,idNumber,mobileNumber,email,
                altContactPerson,atlContactPersonPhone,disability,county);
    }
}
