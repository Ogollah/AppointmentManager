package com.example.appointmentmanager.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appointmentmanager.R;
import com.example.appointmentmanager.data.ApiClient;
import com.example.appointmentmanager.data.ApiEndpoints;
import com.example.appointmentmanager.model.Patient;
import com.example.appointmentmanager.repository.PatientRepository;
import com.example.appointmentmanager.utils.Result;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {
    private EditText etNationId, etSurname, etFirstName, etCounty, etBirthDate, etPatientNo;

    private ProgressBar progressBar;
    private PatientRepository patientRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etNationId = findViewById(R.id.etNationalId);
        etFirstName = findViewById(R.id.etFirstName);
        etBirthDate = findViewById(R.id.etBirthDate);
        etSurname = findViewById(R.id.etSurName);
        etCounty = findViewById(R.id.etCounty);
        etPatientNo = findViewById(R.id.etPatientNumber);

        progressBar = findViewById(R.id.progressBar);

        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        ApiEndpoints apiEndpoints = ApiClient.getClient().create(ApiEndpoints.class);
        patientRepository = new PatientRepository(apiEndpoints);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view -> savePatient());
    }


    private void savePatient() {
        String nationId = etNationId.getText().toString();
        String firstName = etFirstName.getText().toString();
        String birthDate = etBirthDate.getText().toString();
        String surname = etSurname.getText().toString();
        String county = etCounty.getText().toString();
        String patientNumber = etPatientNo.getText().toString();


        if (!surname.isEmpty() && !firstName.isEmpty() && !birthDate.isEmpty()) {

            // Show loading indicator
            progressBar.setVisibility(View.VISIBLE);

            // Create a Patient object
            Patient patient = new Patient();
            patient.setFirstName(firstName);
            patient.setSurname(surname);
            patient.setBirthDate(birthDate);
            patient.setIdNumber(nationId);
            patient.setCounty(county);
            patient.setPatientNumber(patientNumber);

            // Call the repository to add the patient
            patientRepository.addPatient(patient);

            // Observe the result
            patientRepository.getPatientResultLiveData().observe(this, result -> {
                progressBar.setVisibility(View.GONE);
                if (result instanceof Result.SuccessWithId) {
                    // Patient registration success
                    Result.SuccessWithId<Patient> successResult = (Result.SuccessWithId<Patient>) result;
                    long createdPatientId = successResult.getId();
                    Toast.makeText(this, "Patient registered successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, AppointmentActivity.class);
                    intent.putExtra("patient_id", createdPatientId);
                    startActivity(intent);
                } else if (result instanceof Result.Error) {
                    // Patient registration failure
                    Toast.makeText(this, "Patient registration failed", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "Please fill All fields marked *", Toast.LENGTH_SHORT).show();
        }
    }

    public void showDatePickerDialog() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a date picker dialog and set the listener for date selection
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Display the selected date in the EditText
                        String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        etBirthDate.setText(selectedDate);
                    }
                },
                year, month, dayOfMonth);

        // Show the date picker dialog
        datePickerDialog.show();
    }
}
