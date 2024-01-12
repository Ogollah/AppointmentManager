package com.example.appointmentmanager.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.appointmentmanager.data.ApiEndpoints;
import com.example.appointmentmanager.model.Appointment;
import com.example.appointmentmanager.utils.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository class responsible for handling appointment-related data operations.
 */
public class AppointmentRepository {
    // Retrofit API endpoints for appointment operations
    private ApiEndpoints apiEndpoints;

    // MutableLiveData to observe and update the result of appointment operations
    private MutableLiveData<Result<Void>> appointmentResultLiveData = new MutableLiveData<>();

    /**
     * Constructor for the AppointmentRepository.
     *
     * @param apiEndpoints Retrofit API endpoints for appointment operations.
     */
    public AppointmentRepository(ApiEndpoints apiEndpoints) {
        this.apiEndpoints = apiEndpoints;
    }

    /**
     * Get the MutableLiveData for observing appointment operation results.
     *
     * @return MutableLiveData containing the result of appointment operations.
     */
    public MutableLiveData<Result<Void>> getAppointmentResultLiveData() {
        return appointmentResultLiveData;
    }

    /**
     * Save a new appointment using the provided appointment details.
     *
     * @param appointmentDate The date of the appointment.
     * @param appointmentTime The time of the appointment.
     * @param patientId       The ID of the patient associated with the appointment.
     */
    public void saveAppointments(String appointmentDate, String appointmentTime, Long patientId) {
        // Create an Appointment object with the provided details
        Appointment appointment = new Appointment(appointmentDate, appointmentTime);

        // Call the saveAppointments API endpoint using Retrofit
        Call<Void> call = apiEndpoints.saveAppointments(appointment, patientId);

        // Asynchronously enqueue the API call
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Check if the API call is successful
                if (response.isSuccessful()) {
                    // Notify observers with a success result
                    appointmentResultLiveData.setValue(new Result.Success<>(null));
                } else {
                    // Notify observers with an error result if the API call is not successful
                    appointmentResultLiveData.setValue(new Result.Error<>(new Exception("Failed to save appointment")));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Notify observers with an error result in case of API call failure
                appointmentResultLiveData.setValue(new Result.Error<>(new Exception(t)));
            }
        });
    }
}
