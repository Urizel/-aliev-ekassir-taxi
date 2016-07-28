package test.example.ekassir.taxi.utils.imageloader.tasks;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import test.example.ekassir.taxi.utils.tasks.RuntimeArgs;
import test.example.ekassir.taxi.utils.tasks.TreeTask;

/**
 * Created on 24.07.2016.
 */
public abstract class BaseTask extends TreeTask {

    private RuntimeArgs runtimeArgs;
    private Context context;
    private Handler handler;

    public void attachedToRunner(@NonNull Context context,
                                 @NonNull Handler handler,
                                 @NonNull RuntimeArgs runtimeArgs){
        this.runtimeArgs = runtimeArgs;
        this.context = context;
        this.handler = handler;
    }

    public void detachedFromRunner(){
        this.runtimeArgs = null;
        this.context = null;
        this.handler = null;
    }

    public void checkNotNullArg(@NonNull String key){
        if (!runtimeArgs.contains(key)){
            throw new NullPointerException("nullable argument " + key + "!");
        }
    }

    public <T> T getArg(String key, Class<T> tag){
        return runtimeArgs.getValue(key, tag);
    }

    public <T> void putArg(String key, T value){
        runtimeArgs.putValue(key, value);
    }

    @NonNull
    public Context getContext() {
        return context;
    }

    @NonNull
    public Handler getHandler() {
        return handler;
    }
}
