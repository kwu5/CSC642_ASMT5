package com.example.sp;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.sp.fragments.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TaskRepository {

    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTasks;
    private List<LiveData<List<Task>>> mAllPlannedTasksForWeekDay = new ArrayList<>();
    private LiveData<List<Task>> mAllSpecialTasks;
    private LiveData<List<Task>> mAllTodayTasks;
    private LiveData<List<Task>> mTasksForTodayFromDayPlan;
    private LiveData<List<Task>> mTasksForTodayFromSpecialTasks;

    TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mTaskDao = db.taskDao();

        Calendar c = Calendar.getInstance();
        String currentTime = String.format("%02d", c.get(Calendar.HOUR_OF_DAY))+ ":" +
                String.format("%02d", c.get(Calendar.MINUTE));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy.MM.dd", Locale.getDefault());
        String currentDate = dateFormat.format(c.getTime());
        int currentWeekDay = c.get(Calendar.DAY_OF_WEEK) - 2;
        if (currentWeekDay == -1) currentWeekDay = 6; // Sunday

        mAllTasks = mTaskDao.getAllTasks();
        mAllSpecialTasks = mTaskDao.getAllSpecialTasks(currentTime, currentDate);
        mAllTodayTasks = mTaskDao.getAllTodayTasks(currentTime);
        mTasksForTodayFromDayPlan = mTaskDao.getTasksForTodayFromDayPlan(currentWeekDay);
        mTasksForTodayFromSpecialTasks = mTaskDao.getTasksForTodayFromSpecialTasks(currentDate);

        for (int i = 0; i < 7; i++) {
            mAllPlannedTasksForWeekDay.add(mTaskDao.getAllTasksForWeekDay(i));
        }
    }

    public LiveData<List<Task>> getAllTasks() { return this.mAllTasks; }
    public LiveData<List<Task>> getAllTasksForWeekDay(int weekDay) { return this.mAllPlannedTasksForWeekDay.get(weekDay); };
    public LiveData<List<Task>> getAllSpecialTasks() { return this.mAllSpecialTasks; }
    public LiveData<List<Task>> getAllTodayTasks() { return this.mAllTodayTasks; }

    public LiveData<List<Task>> getTasksForTodayFromDayPlan() { return this.mTasksForTodayFromDayPlan; }
    public LiveData<List<Task>> getTasksForTodayFromSpecialTasks() { return this.mTasksForTodayFromSpecialTasks; }

    public LiveData<List<Task>> getNextTaskForToday(String currentTime) { return  mTaskDao.getNextTaskForToday(currentTime); }

    public void insert(Task task) { new insertAsyncTask(mTaskDao).execute(task); }
    public void deleteOne(Task task) { new deleteOneAsyncTask(mTaskDao).execute(task); }
    public void update(Task oldTask, Task newTask) { new updateAsyncTask(mTaskDao).execute(oldTask, newTask); }
    public void deleteToday() { new deleteTodayAsyncTask(mTaskDao).execute(); }

    private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao mAsyncTaskDao;

        insertAsyncTask(TaskDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteOneAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncTaskDao;

        deleteOneAsyncTask(TaskDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.deleteOne(params[0].getTitle(),
                                    params[0].getDescription(),
                                    params[0].getWeekDay(),
                                    params[0].getType(),
                                    params[0].getTimeStart());
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao mAsyncTaskDao;

        updateAsyncTask(TaskDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.update(   params[0].getTitle(),
                                    params[1].getTitle(),
                                    params[0].getDescription(),
                                    params[1].getDescription(),
                                    params[0].getWeekDay(),
                                    params[1].getWeekDay(),
                                    params[0].getTimeStart(),
                                    params[1].getTimeStart(),
                                    params[0].getType(),
                                    params[1].getType());
            return null;
        }
    }

    private static class deleteTodayAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao mAsyncTaskDao;

        deleteTodayAsyncTask(TaskDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(Void... params) {
            mAsyncTaskDao.deleteTasksForToday();
            Log.d("NIGGER", "tyson");
            return null;
        }
    }
}
