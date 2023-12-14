package com.example.appointmentmanager.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.appointmentmanager.repository.AppointmentRepository;
import com.example.appointmentmanager.repository.RepositoryProvider;
import com.example.appointmentmanager.utils.Result;

public class AppointmentViewModel extends ViewModel {

    private final AppointmentRepository appointmentRepository;

    public AppointmentViewModel() {
        appointmentRepository = RepositoryProvider.provideAppointmentRepository();
    }

    public LiveData<Result<Void>> getAppointmentResultLiveData() {
        return appointmentRepository.getAppointmentResultLiveData();
    }

    public void saveAppointments(String appointmentDate, String appointmentTime, Long patientId) {
        appointmentRepository.saveAppointments(appointmentDate, appointmentTime, patientId);
    }
}
