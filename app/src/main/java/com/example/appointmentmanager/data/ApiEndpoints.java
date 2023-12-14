package com.example.appointmentmanager.data;

import com.example.appointmentmanager.model.Appointment;
import com.example.appointmentmanager.model.Patient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiEndpoints {

    @POST("patient")
    Call<Patient> postPatient(@Body Patient patient);
    @POST("appointment/{id}")
    Call<Void> saveAppointments(@Body Appointment appointment, @Path("id") Long id);
}
