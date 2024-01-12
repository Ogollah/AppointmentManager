package com.example.appointmentmanager.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appointmentmanager.data.ApiEndpoints;
import com.example.appointmentmanager.model.Patient;
import com.example.appointmentmanager.utils.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository class responsible for handling patient-related data operations.
 */
public class PatientRepository {
    // Retrofit API endpoints for patient operations
    private ApiEndpoints apiEndpoints;

    // MutableLiveData to observe and update the result of patient operations
    private MutableLiveData<Result<Patient>> patientResultLiveData = new MutableLiveData<>();

    /**
     * Constructor for the PatientRepository.
     *
     * @param apiEndpoints Retrofit API endpoints for patient operations.
     */
    public PatientRepository(ApiEndpoints apiEndpoints) {
        this.apiEndpoints = apiEndpoints;
    }

    /**
     * Get the MutableLiveData for observing patient operation results.
     *
     * @return MutableLiveData containing the result of patient operations.
     */
    public LiveData<Result<Patient>> getPatientResultLiveData() {
        return patientResultLiveData;
    }

    /**
     * Add a new patient using the provided patient details.
     */
    public void addPatient(String firstName, String surname, String otherName, String patientNumber,
                           String birthDate, String idNumber, String mobileNumber, String email,
                           String altContactPerson, String atlContactPersonPhone, Boolean disability, String county) {
        // Create a Patient object with the provided details
        Patient patient = new Patient(firstName, surname, otherName, patientNumber,
                birthDate, idNumber, mobileNumber, email,
                altContactPerson, atlContactPersonPhone, disability, county);

        // Call the postPatient API endpoint using Retrofit
        Call<Patient> call = apiEndpoints.postPatient(patient);

        // Asynchronously enqueue the API call
        if (call != null) {
            call.enqueue(new Callback<Patient>() {
                @Override
                public void onResponse(Call<Patient> call, Response<Patient> response) {
                    // Check if the API call is successful
                    if (response.isSuccessful()) {
                        // Notify observers with a success result and the registered patient
                        if (response.body() != null) {
                            Patient patientReg = response.body();
                            patientResultLiveData.setValue(new Result.Success<>(patientReg));
                        } else {
                            // Notify observers of an error if the response body is null
                            patientResultLiveData.setValue(new Result.Error<>(new Exception("Registration - Null response body")));
                        }
                    } else {
                        // Notify observers with an error result based on the response code
                        if (response.code() == 500) {
                            patientResultLiveData.setValue(new Result.Error<>(new Exception("Patient registration failed Server Error")));
                        } else {
                            patientResultLiveData.setValue(new Result.Error<>(new Exception("Patient registration failed")));
                        }
                    }
                }

                @Override
                public void onFailure(Call<Patient> call, Throwable t) {
                    // Notify observers of a network error
                    patientResultLiveData.setValue(new Result.Error<>(new Exception(t)));
                }
            });
        }
    }
}
