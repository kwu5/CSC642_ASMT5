package com.example.sp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sp.fragments.Task;

import java.util.List;

public class TodayTaskListAdapter extends RecyclerView.Adapter<TodayTaskListAdapter.ViewHolder> {

    private final LayoutInflater Inflater;
//    private List<Task> tasks = new Task().getAllTasks();
    private List<Task> tasks;
    private final String TAG = "TodayTaskListAdapter";

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskTitle;
        private final TextView taskDescription;
        private final TextView taskTimeStart;

        private ViewHolder(View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.today_task_title);
            taskDescription = itemView.findViewById(R.id.today_task_description);
            taskTimeStart = itemView.findViewById(R.id.today_task_startTime);
        }
    }

    public TodayTaskListAdapter(Context context) { Inflater = LayoutInflater.from(context); }

    @Override
    public TodayTaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = Inflater.inflate(R.layout.today_recyclerview_item, parent, false);
        return new TodayTaskListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TodayTaskListAdapter.ViewHolder holder, int position) {
        if (tasks != null) {
            Task t = tasks.get(position);
            holder.taskTitle.setText(t.getTitle());
//            holder.taskDescription.setText(t.getDescription());
            holder.taskTimeStart.setText(t.getTimeStart());
        } else {
            holder.taskTitle.setText("No title");
//            holder.taskDescription.setText("No description");
            holder.taskTimeStart.setText("No time");
        }
    }

    @Override
    public int getItemCount() {
        if (tasks != null) {
            return tasks.size();
        } else {
            return 0;
        }

    }



    public void setTasks(List<Task> t) {
        tasks = t;
        notifyDataSetChanged();
    }
}
