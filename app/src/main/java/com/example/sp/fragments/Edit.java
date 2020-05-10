package com.example.sp.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sp.R;

import java.util.Calendar;
import java.util.Locale;

public class Edit extends Fragment {


    private final String TAG = "Edit";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        setUpToolbar(rootView);
        setUpTimeStartPicker(rootView);
        setUpDatePicker(rootView);
        setUpContent(rootView);

        return rootView;
    }



    private void setUpToolbar(View rootView) {
        // toolbar title
        TextView toolbarTitle = rootView.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(TAG);
//        if (action == "ADD_TASK") {
//            toolbarTitle.setText(R.string.add_today_task);
//        } else if (action == "MODIFY_TASK") {
//            toolbarTitle.setText(R.string.modify_today_task);
//        }

        // back button
        ImageButton buttonBack = rootView.findViewById(R.id.back_button);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Fragment fragment = new Today();
                startFragment(fragment);
            }
        });

    }


    private void setUpTimeStartPicker(final View rootView) {


        ImageButton timePickerButton = rootView.findViewById(R.id.button_timepicker);
        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                TextView selectedTimeStart = rootView
                                        .findViewById(R.id.Time);
                                selectedTimeStart.setText(String.format(Locale.getDefault(), "%02d", hourOfDay) + ":" +
                                        String.format(Locale.getDefault(), "%02d", minute));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });
    }

    private void setUpDatePicker(final View rootView) {


        ImageButton timePickerButton = rootView.findViewById(R.id.button_Datepicker);
        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
                                TextView selectedDate = rootView
                                        .findViewById(R.id.Date);
                                selectedDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

    }


    private void setUpContent(final View rootView) {

        final EditText editTextTitle = rootView.findViewById(R.id.Title_edit);
        final EditText editTextDescription = rootView.findViewById(R.id.Description_edit);
        final TextView timeStartTextView = rootView.findViewById(R.id.Time);
        final TextView DateTextView = rootView.findViewById(R.id.Date);



        // Set up editTexts
        //check if ADD_ACTION


        Bundle bundle = this.getArguments();

        if (bundle == null) {

            Button buttonAdd = rootView.findViewById(R.id.AddButton);
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    if (TextUtils.isEmpty(editTextTitle.getText().toString()) ||
                            TextUtils.isEmpty(timeStartTextView.getText().toString()) ||
                            TextUtils.isEmpty(DateTextView.getText().toString())) {

                        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                R.string.toast_fill_all, Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Fragment fragment = new Today();
                        Bundle bundle = new Bundle();
                        bundle.putString("ACTION", "ADDED_TASK");

                        String title = editTextTitle.getText().toString();
                        bundle.putString("TITLE", title);

                        String description = editTextDescription.getText().toString();
                        bundle.putString("DESCRIPTION", description);

                        String timeStart = timeStartTextView.getText().toString();
                        bundle.putString("TIME_START", timeStart);


                        fragment.setArguments(bundle);
                        startFragment(fragment);
                    }
                }
            });

        } else {


            final String textTitle = bundle.getString("TITLE", "");
            final String textDescription = bundle.getString("DESCRIPTION", "");
            final String textTimeStart = bundle.getString("TIME_START", "");

            editTextTitle.setText(textTitle);
            editTextDescription.setText(textDescription);
            timeStartTextView.setText(textTimeStart);





            Button buttonDelete = rootView.findViewById(R.id.DeleteButton);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new Today();
                    Bundle bundle = new Bundle();
                    bundle.putString("ACTION", "DELETED_TASK");
                    bundle.putString("TITLE", textTitle);
                    bundle.putString("DESCRIPTION", textDescription);
                    bundle.putString("TIME_START", textTimeStart);

                    fragment.setArguments(bundle);
                    startFragment(fragment);

                }
            });


            // Modify Button
            Button buttonEdit = rootView.findViewById(R.id.EditButton);
            buttonEdit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    if (TextUtils.isEmpty(editTextTitle.getText().toString()) ||
                            TextUtils.isEmpty(timeStartTextView.getText().toString())) {

                        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                R.string.toast_fill_all, Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Fragment fragment = new Today();
                        Bundle bundle = new Bundle();
                        bundle.putString("ACTION", "MODIFIED_TASK");

                        String title = editTextTitle.getText().toString();
                        bundle.putString("TITLE", title);

                        String description = editTextDescription.getText().toString();
                        bundle.putString("DESCRIPTION", description);

                        String timeStart = timeStartTextView.getText().toString();
                        bundle.putString("TIME_START", timeStart);

                        bundle.putString("TITLE_OLD", textTitle);
                        bundle.putString("DESCRIPTION_OLD", textDescription);
                        bundle.putString("TIME_START_OLD", textTimeStart);

                        fragment.setArguments(bundle);
                        startFragment(fragment);
                    }
                }
            });

            Calendar c = Calendar.getInstance();
            int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            timeStartTextView.setText(String.format(Locale.getDefault(), "%02d", hourOfDay) + ":" +
                    String.format(Locale.getDefault(), "%02d", minute));


        }
    }


    private void startFragment(Fragment fragment) {
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
