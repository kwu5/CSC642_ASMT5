package com.example.sp.fragments;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.example.sp.R;
//
//import java.util.Date;
//import java.util.List;
//
//public class Task extends Fragment {
//
//
//    private final String TAG = "Task";
//
//    private Button lowP, midP, highP;
//    private String title, descripction;
//    private String time;
//    private String day;
//    private int priority = 0;    //default priority is 0
//
//    private List<Task> allTasks;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View rootView = inflater.inflate(R.layout.fragment_profile, null);
//
//
//
//        return rootView;
//    }
//
//    public List<Task> getAllTasks(){
//        return allTasks;
//    }
//
//    public void setTasks(Task tasks){
//        allTasks.add(tasks);
//    }
//
//
//    public String getTitle(){
//        return title;
//    }
//
//    public String getDescripction(){
//        return descripction;
//    }
//
//
//    public String getTime(){
//        return time;
//    }
//
//    public String getDay(){
//        return day;
//    }
//}


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @NonNull
    @ColumnInfo(name = "type")
    private int type;

    @ColumnInfo(name = "weekday")
    private int weekDay;

    @NonNull
    @ColumnInfo(name = "time_start")
    private String timeStart;

    @ColumnInfo(name = "date_start")
    private String dateStart;

    public Task(String title,
                String description,
                int type,
                String timeStart,
                int weekDay,
                String dateStart) {

        this.title = title;
        this.description = description;
        this.type = type;
        this.weekDay = weekDay;
        this.timeStart = timeStart;
        this.dateStart = dateStart;
    }

    public int getId() { return this.id; }

    @NonNull
    public String getTitle() { return this.title; }

    public String getDescription() { return this.description; }

    @NonNull
    public int getType() { return this.type; }

    @NonNull
    public String getTimeStart() { return this.timeStart; }

    public int getWeekDay() { return this.weekDay; }

    public String getDateStart() { return this.dateStart; }

    public void setId(int id) { this.id = id; }
    public void setType(@NonNull int type) { this.type = type; }
    public void setTitle(@NonNull String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setWeekDay(int weekDay) { this.weekDay = weekDay; }
    public void setTimeStart(@NonNull String timeStart) { this.timeStart = timeStart; }
    public void setDateStart(@NonNull String dateStart) { this.dateStart = dateStart; }
}
