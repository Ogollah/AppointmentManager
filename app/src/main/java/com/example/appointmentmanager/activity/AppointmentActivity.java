package com.example.appointmentmanager.activity;

import static com.example.appointmentmanager.utils.DateUtils.showDatePickerDialog;
import static com.example.appointmentmanager.utils.DateUtils.showTimePickerDialog;

import android.content.Intent;
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
    private AppViewModel appointmentViewModel;
    private String patientId; // Assume patientId is set when starting the activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        etAppointmentDate = findViewById(R.id.etAppointmentDate);
        etAppointmentTime = findViewById(R.id.etAppointmentTime);
        btnSaveAppointment = findViewById(R.id.btnSaveAppointment);

        etAppointmentDate.setOnClickListener(view -> showDatePickerDialog(this, etAppointmentDate));

        etAppointmentTime.setOnClickListener(view -> showTimePickerDialog(this, etAppointmentTime));

        patientId = getIntent().getStringExtra("patient_id");

        appointmentViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        btnSaveAppointment.setOnClickListener(view -> saveAppointment(Long.parseLong(patientId)));

        // Observe the result of the appointment operation
        appointmentViewModel.getAppointmentResultLiveData().observe(this, result -> {
            if (result instanceof Result.Success) {
                // Appointment saved successfully
                Toast.makeText(this, "Appointment saved successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
            } else if (result instanceof Result.Error) {
                // Error saving appointment
                Result.Error<Void> errorResult = (Result.Error<Void>) result;
                Toast.makeText(this, "Failed to save Appointment: " + errorResult.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveAppointment(Long patientId) {
        String appointmentDate = etAppointmentDate.getText().toString();
        String appointmentTime = etAppointmentTime.getText().toString();

        if (!appointmentDate.isEmpty() && !appointmentTime.isEmpty()) {
            appointmentViewModel.saveAppointments(appointmentDate, appointmentTime, patientId);
        } else {
            Toast.makeText(this, "Please enter Appointment date and time", Toast.LENGTH_SHORT).show();
        }
    }
}
