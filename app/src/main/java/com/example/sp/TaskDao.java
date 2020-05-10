package com.example.sp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sp.fragments.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Query("DELETE FROM task_table " +
            "WHERE title = :title AND " +
            "description = :description AND " +
            "weekday = :weekDay AND " +
            "type = :type AND " +
            "time_start = :timeStart")
    void deleteOne(String title, String description, int weekDay, int type, String timeStart);

    @Query("UPDATE task_table " +
            "SET title = :newTitle, " +
            "description = :newDescription, " +
            "weekday = :newWeekDay, " +
            "type = :newType, " +
            "time_start = :newTimeStart " +
            "WHERE title = :oldTitle AND " +
            "description = :oldDescription AND " +
            "weekday = :oldWeekDay AND " +
            "type = :oldType AND " +
            "time_start = :oldTimeStart")
    void update(String oldTitle,
                String newTitle,
                String oldDescription,
                String newDescription,
                int oldWeekDay,
                int newWeekDay,
                String oldTimeStart,
                String newTimeStart,
                int oldType,
                int newType);

    @Query("SELECT * from task_table ORDER BY time_start ASC;")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT " +
            "* " +
            "from task_table " +
            "WHERE weekday = :weekDay AND " +
            "type = 0 " +
            "ORDER BY time_start ASC")
    LiveData<List<Task>> getAllTasksForWeekDay(int weekDay);

    @Query("SELECT " +
            "* " +
            "from task_table " +
            "WHERE type = 1 AND " +
            "time_start >= :currentTime AND " +
            "date_start >= :currentDate " +
            "ORDER BY date_start ASC, " +
            "time_start ASC")
    LiveData<List<Task>> getAllSpecialTasks(String currentTime, String currentDate);

    @Query("SELECT " +
            "* " +
            "from task_table " +
            "WHERE type = 2 AND " +
            "time_start >= :currentTime " +
            "ORDER BY time_start ASC")
    LiveData<List<Task>> getAllTodayTasks(String currentTime);

    @Query("SELECT " +
            "* " +
            "from task_table " +
            "WHERE type = 0 AND " +
            "weekday = :weekDay " +
            "ORDER BY time_start ASC")
    LiveData<List<Task>> getTasksForTodayFromDayPlan(int weekDay);

    @Query("SELECT " +
            "* " +
            "from task_table " +
            "WHERE type = 1 AND " +
            "date_start = :today " +
            "ORDER BY time_start ASC")
    LiveData<List<Task>> getTasksForTodayFromSpecialTasks(String today);

    @Query("DELETE " +
            "from task_table " +
            "WHERE type = 2")
    void deleteTasksForToday();

    @Query("SELECT " +
            "* " +
            "FROM task_table " +
            "WHERE type = 2 AND " +
            "time_start >= :currentTime " +
            "ORDER BY time_start ASC " +
            "LIMIT 1")
    LiveData<List<Task>> getNextTaskForToday(String currentTime);
}
