package com.example.appointmentmanager.activity;

import static com.example.appointmentmanager.utils.DateUtils.showDatePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.appointmentmanager.R;
import com.example.appointmentmanager.model.Patient;
import com.example.appointmentmanager.utils.Result;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegistrationActivity extends AppCompatActivity {
    private EditText etNationId, etSurname, etFirstName, etCounty, etBirthDate, etPatientNo,
            etMobile, etEmail, etAltContactPerson,etAlContactPersonPhone;
    private RadioGroup rbDisability;
    private RadioButton rbYes, rbNo;
    private TextView tvError;
    private ProgressBar progressBar;

    private AppViewModel appViewModel;

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
        tvError = findViewById(R.id.tvError);

        rbDisability = findViewById(R.id.rbDisability);
        rbYes = findViewById(R.id.rbYes);
        rbNo = findViewById(R.id.rbNo);

        progressBar = findViewById(R.id.progressBar);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

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

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view -> savePatient(isDisability.get()));

        // Observe the result
        appViewModel.getPatientResultLiveDate().observe(this, result -> {
            progressBar.setVisibility(View.GONE);
            if (result instanceof Result.Success) {
                // Patient registration success
                Patient patient = ((Result.Success<Patient>) result).getData();
                Toast.makeText(this, "Patient registered successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, AppointmentActivity.class);
                intent.putExtra("patient_id", String.valueOf(patient.getId()));
                startActivity(intent);
            } else if (result instanceof Result.Error) {
                // Patient registration failure
                Result.Error<Patient> errorResult = (Result.Error<Patient>) result;
                Log.e("--> RegistrationActivity", "Patient registration: " + errorResult.getError().getMessage());
                Toast.makeText(this, "Patient registration failed", Toast.LENGTH_SHORT).show();

                if (errorResult.getError().getCause() instanceof IOException) {
                    // Network error
                    tvError.setText(getString(R.string.network_error));
                } else {
                    // Other errors
                    tvError.setText(getString(R.string.reg_failed));
                }
                tvError.setVisibility(View.VISIBLE);
            }
        });
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

            try {
                appViewModel.savePatients(firstName, surname, surname,patientNumber,
                        birthDate,nationId,mobile,email,
                        altContactPerson,atlContactPersonPhone,isDisability,county);
                Log.i("---> RegistrationActivity", "Registering New Patient");

                clearFields();
            } catch (Exception e) {
                    tvError.setText(getString(R.string.connect_exception_error));

                tvError.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        } else {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(getString(R.string.required_fields_error));
        }
    }

    private void clearFields(){
        etNationId.getText().clear();
        etFirstName.getText().clear();
        etBirthDate.getText().clear();
        etSurname.getText().clear();
        etCounty.getText().clear();
        etPatientNo.getText().clear();
        etMobile.getText().clear();
        etEmail.getText().clear();
        etAltContactPerson.getText().clear();
        etAlContactPersonPhone.getText().clear();

        tvError.setVisibility(View.GONE);
    }
}
