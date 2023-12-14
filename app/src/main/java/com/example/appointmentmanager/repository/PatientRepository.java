package com.example.appointmentmanager.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appointmentmanager.data.ApiEndpoints;
import com.example.appointmentmanager.model.Appointment;
import com.example.appointmentmanager.model.Patient;
import com.example.appointmentmanager.utils.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientRepository {

    private ApiEndpoints apiEndpoints;
    private MutableLiveData<Result<Patient>> patientResultLiveData = new MutableLiveData<>();

    public PatientRepository(ApiEndpoints apiEndpoints) {
        this.apiEndpoints = apiEndpoints;
    }

    public LiveData<Result<Patient>> getPatientResultLiveData() {
        return patientResultLiveData;
    }

    public void addPatient(String firstName, String surname, String otherName, String patientNumber,
                           String birthDate, String idNumber, String mobileNumber, String email,
                           String altContactPerson, String atlContactPersonPhone, Boolean disability, String county) {
        Patient patient = new Patient(firstName, surname, otherName,patientNumber,
                birthDate,idNumber,mobileNumber,email,
                altContactPerson,atlContactPersonPhone,disability,county);
        Call<Patient> call = apiEndpoints.postPatient(patient);
        if (call != null) {
            call.enqueue(new Callback<Patient>() {
                @Override
                public void onResponse(Call<Patient> call, Response<Patient> response) {
                    if (response.isSuccessful()) {
                        // Notify observers of successful patient registration
                        patientResultLiveData.setValue(new Result.Success<>(response.body()));
                    } else {
                        // Notify observers of patient registration failure
                        patientResultLiveData.setValue(new Result.Error<>(new Exception("Patient registration failed")));
                    }
                }

                @Override
                public void onFailure(Call<Patient> call, Throwable t) {
                    // Notify observers of network error
                    patientResultLiveData.setValue(new Result.Error<>(new Exception(t)));
                }
            });
        }
    }
}
