package com.example.appointmentmanager.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.appointmentmanager.data.ApiEndpoints;
import com.example.appointmentmanager.model.Appointment;
import com.example.appointmentmanager.utils.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentRepository {
    private ApiEndpoints apiEndpoints;
    private MutableLiveData<Result<Void>> appointmentResultLiveData = new MutableLiveData<>();

    public AppointmentRepository(ApiEndpoints apiEndpoints) {
        this.apiEndpoints = apiEndpoints;
    }

    public MutableLiveData<Result<Void>> getAppointmentResultLiveData() {
        return appointmentResultLiveData;
    }

    public void saveAppointments(String appointmentDate, String appointmentTime) {
        Appointment appointment = new Appointment(appointmentDate, appointmentTime);
        Call<Void> call = apiEndpoints.saveAppointments(appointment);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    appointmentResultLiveData.setValue(new Result.Success<>(null));
                } else {
                    appointmentResultLiveData.setValue(new Result.Error<>(new Exception("Failed to save appointment")));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                appointmentResultLiveData.setValue(new Result.Error<>(new Exception(t)));
            }
        });
    }
}
