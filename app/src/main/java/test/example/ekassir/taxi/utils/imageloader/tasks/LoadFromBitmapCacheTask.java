package test.example.ekassir.taxi.utils.imageloader.tasks;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import test.example.ekassir.taxi.utils.imageloader.cache.BitmapDiskCache;

/**
 * Created on 25.07.2016.
 */
public class LoadFromBitmapCacheTask extends BaseTask {

    private static final String TAG = "LoadFromBitmapCacheTask";

    @NonNull
    @Override
    public Enum getTaskId() {
        return TaskTypes.LOAD_FROM_BITMAP_CACHE;
    }

    @Override
    public void run() {
        checkNotNullArg(LoadImageTask.ARG_IMAGE_URL);
        checkNotNullArg(SaveToBitmapCacheTask.ARG_DISK_CACHE);

        String  url = getArg(LoadImageTask.ARG_IMAGE_URL, String.class);
        BitmapDiskCache bitmapDiskCache = getArg(SaveToBitmapCacheTask.ARG_DISK_CACHE, BitmapDiskCache.class);

        Bitmap bitmap = bitmapDiskCache.get(url);
        if (bitmap == null){
            Log.d(TAG, "bitmap not found on disk cache. load image.");
            startTask(TaskTypes.LOAD_IMAGE);
        }
        else {
            Log.d(TAG, "bitmap found on disk cache.");
            putArg(ShowImageTask.ARG_BITMAP, bitmap);
            startTask(TaskTypes.SHOW_IMAGE);
        }
    }
}
