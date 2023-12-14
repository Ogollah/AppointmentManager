package com.example.appointmentmanager.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DateUtils {

    public static void showDatePickerDialog(Context context, EditText dateEditText) {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a date picker dialog and set the listener for date selection
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                (Context) context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Display the selected date in the EditText
                        String selectedDate = selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear;
                        dateEditText.setText(selectedDate);
                    }
                },
                year, month, dayOfMonth);

        // Show the date picker dialog
        datePickerDialog.show();
    }

    public static void showTimePickerDialog(Context context, EditText timeEditText) {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a time picker dialog and set the listener for time selection
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                (view, selectedHour, selectedMinute) -> {
                    // Display the selected time
                    String amPm;
                    if (selectedHour >= 12) {
                        amPm = "PM";
                        selectedHour = selectedHour - 12;
                    } else {
                        amPm = "AM";
                    }

                    String selectedTime = selectedHour + ":" + String.format("%02d", selectedMinute) + " " + amPm;
                    timeEditText.setText(selectedTime);
                },
                hour, minute, DateFormat.is24HourFormat(context));

        // Show the time picker dialog
        timePickerDialog.show();
    }
}
