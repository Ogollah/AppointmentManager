package com.example.appointmentmanager.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appointmentmanager.data.ApiEndpoints;
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

    public void addPatient(Patient patient) {
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
