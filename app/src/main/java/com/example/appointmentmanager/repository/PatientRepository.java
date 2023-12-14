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
    private MutableLiveData<Result<Void>> patientResultLiveData = new MutableLiveData<>();

    public PatientRepository(ApiEndpoints apiEndpoints) {
        this.apiEndpoints = apiEndpoints;
    }

    public LiveData<Result<Void>> getPatientResultLiveData() {
        return patientResultLiveData;
    }

    public void addPatient(Patient patient) {
        Call<Void> call = apiEndpoints.postPatient(patient);
        if (call != null) {
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Notify observers of successful patient registration
                        patientResultLiveData.setValue(new Result.Success<>(null));
                    } else {
                        // Notify observers of patient registration failure
                        patientResultLiveData.setValue(new Result.Error<>(new Exception("Patient registration failed")));
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Notify observers of network error
                    patientResultLiveData.setValue(new Result.Error<>(new Exception(t)));
                }
            });
        }
    }
}
