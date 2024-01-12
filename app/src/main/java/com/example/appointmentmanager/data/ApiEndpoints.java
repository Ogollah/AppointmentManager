package com.example.appointmentmanager.data;

import com.example.appointmentmanager.model.Appointment;
import com.example.appointmentmanager.model.Patient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Retrofit interface defining API endpoints for patient and appointment operations.
 */
public interface ApiEndpoints {

    /**
     * POST request to create a new patient.
     *
     * @param patient The patient object to be created.
     * @return A Retrofit Call representing the asynchronous operation with a Patient response.
     */
    @POST("/api/v1/patient")
    Call<Patient> postPatient(@Body Patient patient);

    /**
     * POST request to schedule a new appointment for a patient.
     *
     * @param appointment The appointment object to be scheduled.
     * @param id          The patient ID to associate the appointment with.
     * @return A Retrofit Call representing the asynchronous operation with a Void response.
     */
    @POST("/api/v1/appointments/{id}/schedule")
    Call<Void> saveAppointments(@Body Appointment appointment, @Path("id") Long id);
}
