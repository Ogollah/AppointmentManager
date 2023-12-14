package com.example.appointmentmanager.data;

import com.example.appointmentmanager.model.Appointment;
import com.example.appointmentmanager.model.Patient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiEndpoints {

    @POST("patient")
    Call<Void> postPatient(@Body Patient patient);
    @POST("api/v1/appointment")
    Call<Void> saveAppointments(@Body Appointment appointment);
}
