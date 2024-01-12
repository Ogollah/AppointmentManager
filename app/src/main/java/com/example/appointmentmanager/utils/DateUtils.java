package com.example.appointmentmanager.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Utility class for handling date and time picker dialogs.
 */
public class DateUtils {

    /**
     * Displays a date picker dialog and sets the selected date in the provided EditText.
     *
     * @param context      The context in which the date picker dialog is displayed.
     * @param dateEditText The EditText where the selected date is displayed.
     */
    public static void showDatePickerDialog(Context context, EditText dateEditText) {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a date picker dialog and set the listener for date selection
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Display the selected date in the EditText
                    String selectedDate = selectedYear + "-" + formatDate((selectedMonth + 1)) + "-" + formatDate(selectedDay);
                    dateEditText.setText(selectedDate);
                },
                year, month, dayOfMonth);

        // Show the date picker dialog
        datePickerDialog.show();
    }

    /**
     * Displays a time picker dialog and sets the selected time in the provided EditText.
     *
     * @param context      The context in which the time picker dialog is displayed.
     * @param timeEditText The EditText where the selected time is displayed.
     */
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

    /**
     * Formats the given value to ensure it has two digits.
     *
     * @param value The value to be formatted.
     * @return A formatted string with two digits.
     */
    private static String formatDate(int value) {
        return String.format("%02d", value);
    }
}
