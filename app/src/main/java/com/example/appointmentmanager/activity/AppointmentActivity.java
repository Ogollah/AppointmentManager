package com.example.appointmentmanager.activity;

import static com.example.appointmentmanager.utils.DateUtils.showDatePickerDialog;
import static com.example.appointmentmanager.utils.DateUtils.showTimePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.appointmentmanager.R;
import com.example.appointmentmanager.utils.Result;

/**
 * Activity for scheduling and saving appointments.
 */
public class AppointmentActivity extends AppCompatActivity {
    private EditText etAppointmentDate, etAppointmentTime;
    private Button btnSaveAppointment;
    private TextView tvError;
    private ProgressBar progressBar;
    private AppViewModel appointmentViewModel;
    private String patientId; // Assume patientId is set when starting the activity

  private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // Initialize UI components
        etAppointmentDate = findViewById(R.id.etAppointmentDate);
        etAppointmentTime = findViewById(R.id.etAppointmentTime);
        btnSaveAppointment = findViewById(R.id.btnSaveAppointment);
        tvError = findViewById(R.id.tvError);
        progressBar = findViewById(R.id.progressBar);


        // Set date and time pickers on click listeners
        etAppointmentDate.setOnClickListener(view -> showDatePickerDialog(AppointmentActivity.this, etAppointmentDate));
        etAppointmentTime.setOnClickListener(view -> showTimePickerDialog(AppointmentActivity.this, etAppointmentTime));

        // Get patientId from the intent
        patientId = getIntent().getStringExtra("patient_id");

        // Initialize ViewModel
        appointmentViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        // Set save button click listener
        btnSaveAppointment.setOnClickListener(view -> saveAppointment(Long.parseLong(patientId)));

        // Observe the result of the appointment operation
        appointmentViewModel.getAppointmentResultLiveData().observe(this, result -> {
            progressBar.setVisibility(View.GONE);
            if (result instanceof Result.Success) {
                // Appointment saved successfully
                Toast.makeText(this, "Appointment saved successfully", Toast.LENGTH_SHORT).show();
            } else if (result instanceof Result.Error) {
                // Error saving appointment
                Result.Error<Void> errorResult = (Result.Error<Void>) result;
                Toast.makeText(this, "Failed to save Appointment: " + errorResult.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Save the appointment with the provided details.
     *
     * @param patientId The ID of the patient for whom the appointment is scheduled.
     */
    private void saveAppointment(Long patientId) {
        // Get appointment details from UI components
        String appointmentDate = etAppointmentDate.getText().toString();
        String appointmentTime = etAppointmentTime.getText().toString();

        // Check if date and time are provided
        if (!appointmentDate.isEmpty() && !appointmentTime.isEmpty()) {
            try {
                // Trigger the ViewModel to save the appointment
                appointmentViewModel.saveAppointments(appointmentDate, appointmentTime, patientId);
                Log.i("---> AppointmentActivity", "Saving new appointment for patient: " + patientId);

            } catch (Exception e) {
                // Handle exception and display error message
                tvError.setText(getString(R.string.connect_exception_error));
                tvError.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        } else {
            // Show error message if date or time is missing
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(getString(R.string.date_time));
        }
    }
}
