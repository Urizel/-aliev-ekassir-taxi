package test.example.ekassir.taxi.utils.tasks;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 11.07.2016.
 */
public class TreeTasksRunner extends Task implements Task.TaskListener {

    private static final String TAG = "TreeTasksRunner";
    private ExecutorService executor;
    private Handler handler;

    public void setTaskRunnerListener(@Nullable TaskRunnerListener taskRunnerListener) {
        this.taskRunnerListener = taskRunnerListener;
    }

    public void setExecutor(@Nullable ExecutorService executor) {
        this.executor = executor;
    }

    public void setHandler(@Nullable Handler handler) {
        this.handler = handler;
    }

    public interface TaskRunnerListener {
        void onBeforeTaskStarted(@NonNull TreeTask task);
        void onTaskFinished(@NonNull TreeTask task);
        void onRunnerFinished();
    }

    private TreeTask initialTask;
    private TaskRunnerListener taskRunnerListener;

    public TreeTasksRunner(@NonNull TreeTask initialTask){
        this.initialTask = initialTask;
    }

    @Override
    public void run() {
        runTask(initialTask);
        initialTask = null;
    }

    private void runTask(TreeTask task){
        task.setOnTaskListener(this);
        if (handler != null){
            task.setHandler(handler);
        }
        if (taskRunnerListener != null){
            taskRunnerListener.onBeforeTaskStarted(task);
        }
        if (executor != null) {
            Log.d(TAG, "start next task with id "+task.getTaskId() + " from executor.");
            executor.execute(task);
        }
        else {
            Log.d(TAG, "start next task with id "+task.getTaskId() + " from current thread.");
            task.run();
        }
    }

    @Override
    public void onComplete(@Nullable Task task) {
        if (task != null && task instanceof TreeTask){
            Log.d(TAG, "completed.");
            TreeTask treeTask = (TreeTask)task;
            treeTask.setOnTaskListener(null);

            if (taskRunnerListener != null){
                taskRunnerListener.onTaskFinished(treeTask);
            }
            TreeTask nextTask = treeTask.getNextTask();
            if (nextTask != null){
                runTask(nextTask);
            }
            else {
                completeTask();
            }
        }
        else {
            completeTask();
        }
    }

    @Override
    protected void completeTask() {
        Log.d(TAG, "runner finished.");
        if (taskRunnerListener != null){
            taskRunnerListener.onRunnerFinished();
        }
        taskRunnerListener = null;
        super.completeTask();
    }

    @Override
    public void onCancel(@Nullable Task task) {

    }
}
