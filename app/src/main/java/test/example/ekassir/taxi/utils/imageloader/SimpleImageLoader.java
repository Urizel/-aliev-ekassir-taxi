package test.example.ekassir.taxi.utils.imageloader;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import test.example.ekassir.taxi.utils.imageloader.cache.BitmapDiskCache;
import test.example.ekassir.taxi.utils.imageloader.tasks.*;
import test.example.ekassir.taxi.utils.tasks.RuntimeArgs;
import test.example.ekassir.taxi.utils.tasks.TreeTask;
import test.example.ekassir.taxi.utils.tasks.TreeTasksRunner;

/**
 * Created on 24.07.2016.
 */
public class SimpleImageLoader {

    private static final String TAG = "SimpleImageLoader";

    private BitmapDiskCache cache;
    private Handler handler;
    private ExecutorService executorService;
    private Context context;

    public SimpleImageLoader(@NonNull Context context){
        this.context = context;

        handler = new Handler(Looper.myLooper());
        executorService = Executors.newFixedThreadPool(1);
        cache = new BitmapDiskCache.Configuration(context)
                .setMaxCacheTimeMillis(10*60*1000L)
                .createCache();
    }

    public void load(@NonNull String url,
                     @NonNull final ImageView imageView){
        load(url,imageView, -1, -1);
    }

    public void load(@NonNull String url,
                     @NonNull final ImageView imageView,
                     int resizeWidth,
                     int resizeHeight){

        final RuntimeArgs args = new RuntimeArgs();
        args.putValue(LoadImageTask.ARG_IMAGE_URL, url);
        args.putValue(ShowImageTask.ARG_IMAGE_VIEW, imageView);
        args.putValue(SaveToBitmapCacheTask.ARG_DISK_CACHE, cache);
        args.putValue(DecodeBitmapTask.ARG_WIDTH, resizeWidth);
        args.putValue(DecodeBitmapTask.ARG_HEIGHT, resizeHeight);

        // show image
        ShowImageTask showImageTask = new ShowImageTask();

        // save to bitmap cache
        SaveToBitmapCacheTask saveToBitmapCacheTask = new SaveToBitmapCacheTask();
        // success
        saveToBitmapCacheTask.addNextTask(showImageTask);


        // decode bitmap
        DecodeBitmapTask decodeBitmap = new DecodeBitmapTask();
        // success
        decodeBitmap.addNextTask(saveToBitmapCacheTask);


        // load image
        LoadImageTask loadImage = new LoadImageTask();
        // success
        loadImage.addNextTask(decodeBitmap);


        // load from bitmap cache
        LoadFromBitmapCacheTask loadFromBitmapCache = new LoadFromBitmapCacheTask();
        // success
        loadFromBitmapCache.addNextTask(showImageTask);
        // fail
        loadFromBitmapCache.addNextTask(loadImage);

        TreeTasksRunner tasksRunner = new TreeTasksRunner(loadFromBitmapCache);
        tasksRunner.setHandler(handler);
        tasksRunner.setExecutor(executorService);
        tasksRunner.setTaskRunnerListener(new TreeTasksRunner.TaskRunnerListener() {
            @Override
            public void onBeforeTaskStarted(@NonNull TreeTask task) {
                BaseTask baseTask = (BaseTask)task;
                baseTask.attachedToRunner(context, handler, args);
            }

            @Override
            public void onTaskFinished(@NonNull TreeTask task) {
                BaseTask baseTask = (BaseTask)task;
                baseTask.detachedFromRunner();
            }

            @Override
            public void onRunnerFinished() {

            }
        });
        tasksRunner.run();

    }
}

