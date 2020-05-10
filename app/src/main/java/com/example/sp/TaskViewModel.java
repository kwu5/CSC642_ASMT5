package com.example.sp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sp.fragments.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository mRepository;
    private LiveData<List<Task>> mAllTasks;
    private List<LiveData<List<Task>>> mAllPlannedTasksForWeekDay = new ArrayList<>();;
    private LiveData<List<Task>> mAllSpecialTasks;
    private LiveData<List<Task>> mAllTodayTasks;

    public TaskViewModel(Application application) {
        super(application);
        mRepository = new TaskRepository(application);
        mAllTasks = mRepository.getAllTasks();
        mAllSpecialTasks = mRepository.getAllSpecialTasks();
        mAllTodayTasks = mRepository.getAllTodayTasks();

        for (int i = 0; i < 7; i++) {
            mAllPlannedTasksForWeekDay.add(mRepository.getAllTasksForWeekDay(i));
        }
    }

    public LiveData<List<Task>> getAllTasks() {
        return this.mAllTasks;
    }
    public LiveData<List<Task>> getAllTasksForWeekDay(int weekDay) { return this.mAllPlannedTasksForWeekDay.get(weekDay); }
    public LiveData<List<Task>> getAllSpecialTasks() { return this.mAllSpecialTasks; }
    public LiveData<List<Task>> getAllTodayTasks() { return this.mAllTodayTasks; }
    public LiveData<List<Task>> getNextTaskForToday(String currentTime) { return mRepository.getNextTaskForToday(currentTime); }

    public void insert(Task task) { mRepository.insert(task); }
    public void deleteOne(Task task) { mRepository.deleteOne(task); }
    public void update(Task oldTask, Task newTask) { mRepository.update(oldTask, newTask); }
    public void deleteToday() { mRepository.deleteToday(); }
}
