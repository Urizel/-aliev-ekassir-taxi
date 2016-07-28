package test.example.ekassir.taxi.utils.tasks;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created on 08.07.2016.
 */
public abstract class TreeTask extends Task {

    private static final String TAG = "TreeTask";
    private TreeTask nextTask;
    private List<TreeTask> tasks;

    public TreeTask(){
        tasks = new LinkedList<>();
    }

    public @Nullable
    TreeTask getNextTask(){
        return nextTask;
    }

    public void startTask(@Nullable Enum taskId){
        // finish processing
        if (taskId == null){
            nextTask = null;
            completeTask();
            return;
        }

        this.nextTask = findTask(taskId);
        if (nextTask == null){
            Log.e(TAG, "task with id " + taskId + " not found! Need to add current task.");
            return;
        }
        completeTask();
    }

    public abstract @NonNull Enum getTaskId();

    public void addNextTask(@NonNull TreeTask treeTask){
        tasks.add(treeTask);
    }


    private @Nullable
    TreeTask findTask(@NonNull Enum taskId){
        for (TreeTask task : tasks) {
            if (task.getTaskId() == taskId){
                return task;
            }
        }
        return null;
    }
}
