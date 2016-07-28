package test.example.ekassir.taxi.utils.tasks;

import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 */
public class TaskQueue extends Task {

    private static final String TAG = TaskQueue.class.getSimpleName();
    private Queue<Task> queue;

    public TaskQueue(){
        super();
        queue = new LinkedList<>();
    }

    @Override
    public void run(){
        nextTask();
    }

    public void addTask(Task task){
        queue.add(task);
    }

    private void nextTask(){
        Task task = queue.poll();
        Log.d(TAG, "start task " + task);
        if (task != null){
            task.setOnTaskListener(new Task.TaskListener() {
                @Override
                public void onComplete(Task task) {
                    Log.d(TAG, "task " + task + " completed");
                    nextTask();
                }
                @Override
                public void onCancel(Task task) {
                    Log.d(TAG, "task " + task + " canceled");
                    queue.clear();
                    TaskListener listener = getOnTaskListener();
                    if (listener != null) {
                        listener.onCancel(TaskQueue.this);
                    }
                }
            });
            task.run();
        }
        else {
            TaskListener listener = getOnTaskListener();
            Log.d(TAG, "queue completed");
            if (listener != null) {
                listener.onComplete(TaskQueue.this);
            }
        }
    }
}
