package com.example.appointmentmanager.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
                        String selectedDate = selectedYear + "-" + formatDate((selectedMonth + 1)) + "-" + formatDate(selectedDay);
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
                    String selectedTime = formatDate(selectedHour) + ":" + formatDate(selectedMinute);
                    timeEditText.setText(selectedTime);
                },
                hour, minute, true); // The last parameter (true) enables 24-hour format

        // Show the time picker dialog
        timePickerDialog.show();
    }

    private static  String formatDate(int value){
        return String.format("%02d", value);
    }
}
