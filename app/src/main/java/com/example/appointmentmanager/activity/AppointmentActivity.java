package com.example.appointmentmanager.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.appointmentmanager.R;
import com.example.appointmentmanager.utils.Result;

public class AppointmentActivity extends AppCompatActivity{

    private EditText etAppointmentDate, etAppointmentTime;
    private Button btnSaveAppointment;
    private AppointmentViewModel appointmentViewModel;

    private String patientId; // Assume patientId is set when starting the activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        etAppointmentDate = findViewById(R.id.etAppointmentDate);
        etAppointmentDate = findViewById(R.id.etAppointmentDate);
        btnSaveAppointment = findViewById(R.id.btnSaveAppointment);

        patientId = getIntent().getStringExtra("patient_id");

        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);

        btnSaveAppointment.setOnClickListener(view -> saveAppointment(Long.parseLong(patientId)));

        // Observe the result of the visit operation
        appointmentViewModel.getAppointmentResultLiveData().observe(this, result -> {
            if (result instanceof Result.Success) {
                // Visit saved successfully
                Toast.makeText(this, "Appointment saved successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity
            } else if (result instanceof Result.Error) {
                // Error saving visit
                Result.Error<Void> errorResult = (Result.Error<Void>) result;
                Toast.makeText(this, "Failed to save Appointment: " + errorResult.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveAppointment(Long PatientId) {
        String appointmentDate = etAppointmentDate.getText().toString();
        String appointmentTime = etAppointmentTime.getText().toString();

             if (!appointmentDate.isEmpty() && !appointmentTime.isEmpty()) {
            appointmentViewModel.saveAppointments(appointmentDate, appointmentTime, PatientId);
        } else {
            Toast.makeText(this, "Please enter Appointment date", Toast.LENGTH_SHORT).show();
        }
    }
}
