package test.example.ekassir.taxi.utils.imageloader.tasks;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import test.example.ekassir.taxi.utils.imageloader.cache.BitmapDiskCache;

/**
 * Created on 25.07.2016.
 */
public class SaveToBitmapCacheTask extends BaseTask {

    private static final String TAG = "SaveToBitmapCacheTask";
    public static final String ARG_DISK_CACHE = "arg_disk_cache";

    @NonNull
    @Override
    public Enum getTaskId() {
        return TaskTypes.SAVE_TO_BITMAP_CACHE;
    }

    @Override
    public void run() {
        checkNotNullArg(LoadImageTask.ARG_IMAGE_URL);
        checkNotNullArg(ShowImageTask.ARG_BITMAP);
        checkNotNullArg(SaveToBitmapCacheTask.ARG_DISK_CACHE);

        String url = getArg(LoadImageTask.ARG_IMAGE_URL, String.class);
        Bitmap bitmap = getArg(ShowImageTask.ARG_BITMAP, Bitmap.class);
        BitmapDiskCache bitmapDiskCache = getArg(SaveToBitmapCacheTask.ARG_DISK_CACHE, BitmapDiskCache.class);

        bitmapDiskCache.put(url, bitmap);
        Log.d(TAG, "url " + url + " saved local.");
        startTask(TaskTypes.SHOW_IMAGE);
    }
}
