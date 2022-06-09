package com.example.lastproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    public interface DatePickerListener {
        void onDateSet(DatePicker datePicker, int i, int i1, int i2);
    }

    DatePickerFragment.DatePickerListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DatePickerListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString() + " must implements DatePickerListener");
        }
    }

    @NonNull
    @Override
    /**
     * מחלקה שמציגה את הדיאלוג עם DatePicker
     */
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar mCalendar = Calendar.getInstance();

        int year = mCalendar.get(Calendar.YEAR);

        int month = mCalendar.get(Calendar.MONTH);

        int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);

        Dialog dialog = new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);

        return dialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        mListener.onDateSet(datePicker, i, i1, i2);
    }
}
