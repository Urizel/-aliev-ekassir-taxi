package test.example.ekassir.taxi.utils.tasks;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 *
 */
public abstract class Task implements Runnable {

    private TaskListener listener;
    private Handler handler;

    public void setHandler(@Nullable Handler handler) {
        this.handler = handler;
    }

    public interface TaskListener {
        void onComplete(@Nullable Task task);
        void onCancel(@Nullable Task task);
    }

    public Task(@NonNull TaskListener listener){
        setOnTaskListener(listener);
    }

    public Task(){}

    public abstract void run();

    public void setOnTaskListener(@Nullable TaskListener listener) {
        this.listener = listener;
    }

    public TaskListener getOnTaskListener(){
        return listener;
    }

    protected void completeTask(){
        if (handler != null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (listener != null){
                        listener.onComplete(Task.this);
                    }
                }
            });
        }
        else {
            if (listener != null) {
                listener.onComplete(this);
            }
        }
    }

    protected void cancelTask() {
        if (listener != null){
            listener.onCancel(this);
        }
    }

    @Override
    public String toString(){
        return "[" + getClass().getSimpleName() +"]";
    }

}
