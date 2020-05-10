package com.example.sp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp.R;
import com.example.sp.TodayTaskListAdapter;
import com.example.sp.TaskViewModel;
import com.example.sp.utils.ItemClickSupport;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Today extends Fragment {

    final String TAG = "Today";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        View rootView = inflater.inflate(R.layout.fragment_today, null);

        setToolbar(rootView);
        setFloatingActionButton(rootView);
        checkData();
        setRecyclerView(rootView);


        return rootView;
    }


    void setToolbar(View view) {
        TextView title = view.findViewById(R.id.toolbar_title);
        title.setText(TAG);
    }

    void setFloatingActionButton(View view) {
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Edit();
                startFragment(fragment);
            }
        });
    }

    void setRecyclerView(View view) {


        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final TodayTaskListAdapter adapter = new TodayTaskListAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Fragment fragment = new Edit();
                Bundle bundle = new Bundle();


                TextView timeStart = v.findViewById(R.id.today_task_startTime);
                TextView title = v.findViewById(R.id.today_task_title);
                TextView description = v.findViewById(R.id.today_task_description);

                bundle.putString("TITLE", title.getText().toString());
                bundle.putString("DESCRIPTION", description.getText().toString());
                bundle.putString("TIME_START", timeStart.getText().toString());
                fragment.setArguments(bundle);
                startFragment(fragment);
            }
        });


        TaskViewModel mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        mTaskViewModel.getAllTodayTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });

    }

    private void startFragment(Fragment fragment) {
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        } else {
            Log.d(TAG, "startFragment: Fragment is null");
        }
    }


    void checkData() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            String action = bundle.getString("ACTION", "");
            String title = bundle.getString("TITLE", "");
            String description = bundle.getString("DESCRIPTION", "");
            String timeStart = bundle.getString("TIME_START", "");

            TaskViewModel mTaskViewModel = ViewModelProviders.of(this).
                    get(TaskViewModel.class);

            if (action == "ADDED_TASK") {
                mTaskViewModel.insert(new Task(title, description, 2, timeStart, -1, null));

                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        R.string.toast_task_added, Toast.LENGTH_SHORT);
                toast.show();


            } else if (action == "DELETED_TASK") {
                mTaskViewModel.deleteOne(new Task(title, description, 2, timeStart, -1, null));


                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        R.string.toast_task_deleted, Toast.LENGTH_SHORT);
                toast.show();

            } else if (action == "MODIFIED_TASK") {

                String oldTitle = bundle.getString("TITLE_OLD", "");
                String oldDescription = bundle.getString("DESCRIPTION_OLD", "");
                String oldTimeStart = bundle.getString("TIME_START_OLD", "");

                mTaskViewModel.update(new Task(oldTitle, oldDescription, 2, oldTimeStart, -1, null),
                        new Task(title, description, 2, timeStart, -1, null));


                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        R.string.toast_task_modified, Toast.LENGTH_SHORT);
                toast.show();

            }
        }
    }
}
