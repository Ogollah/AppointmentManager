package com.example.appointmentmanager.activity;

import static com.example.appointmentmanager.utils.DateUtils.showDatePickerDialog;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.appointmentmanager.R;
import com.example.appointmentmanager.data.ApiClient;
import com.example.appointmentmanager.data.ApiEndpoints;
import com.example.appointmentmanager.model.Patient;
import com.example.appointmentmanager.repository.PatientRepository;
import com.example.appointmentmanager.utils.Result;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegistrationActivity extends AppCompatActivity {
    private EditText etNationId, etSurname, etFirstName, etCounty, etBirthDate, etPatientNo,
            etMobile, etEmail, etAltContactPerson,etAlContactPersonPhone;
    private RadioGroup rbDisability;
    private RadioButton rbYes, rbNo;

    private ProgressBar progressBar;
    private PatientRepository patientRepository;

    private AppViewModel appointmentViewModel;

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
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etAltContactPerson = findViewById(R.id.etAltPerson);
        etAlContactPersonPhone = findViewById(R.id.etAltPersonPhone);

        rbDisability = findViewById(R.id.rbDisability);
        rbYes = findViewById(R.id.rbYes);
        rbNo = findViewById(R.id.rbNo);

        progressBar = findViewById(R.id.progressBar);

        appointmentViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        etBirthDate.setOnClickListener(view -> showDatePickerDialog(RegistrationActivity.this, etBirthDate));

        AtomicBoolean isDisability = new AtomicBoolean(false);
        rbDisability.setOnCheckedChangeListener((group, checked)->{
            if (checked == rbYes.getId())
            {
                isDisability.set(true);
            } else if (checked == rbNo.getId()) {
                isDisability.set(false);
            }
        });

        ApiEndpoints apiEndpoints = ApiClient.getClient().create(ApiEndpoints.class);
        patientRepository = new PatientRepository(apiEndpoints);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view -> savePatient(isDisability.get()));
    }
    private void savePatient(boolean isDisability) {
        String nationId = etNationId.getText().toString();
        String firstName = etFirstName.getText().toString();
        String birthDate = etBirthDate.getText().toString();
        String surname = etSurname.getText().toString();
        String county = etCounty.getText().toString();
        String patientNumber = etPatientNo.getText().toString();
        String  mobile = etMobile.getText().toString();
        String email = etEmail.getText().toString();
        String altContactPerson = etAltContactPerson.getText().toString();
        String atlContactPersonPhone = etAlContactPersonPhone.getText().toString();


        if (!surname.isEmpty() && !firstName.isEmpty() && !birthDate.isEmpty() && !county.isEmpty()
                && !mobile.isEmpty() && !patientNumber.isEmpty()) {

            // Show loading indicator
            progressBar.setVisibility(View.VISIBLE);

            appointmentViewModel.savePatients(firstName, surname, surname,patientNumber,
                    birthDate,nationId,mobile,email,
                    altContactPerson,atlContactPersonPhone,isDisability,county);

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
}
