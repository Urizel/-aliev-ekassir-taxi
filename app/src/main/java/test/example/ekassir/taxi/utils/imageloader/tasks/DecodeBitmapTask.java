package test.example.ekassir.taxi.utils.imageloader.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import okhttp3.Response;

/**
 * Created on 24.07.2016.
 */
public class DecodeBitmapTask extends BaseTask {

    private static final String TAG = "DecodeBitmapTask";

    public static final String ARG_RESPONSE = "arg_response";
    public static final String ARG_WIDTH = "arg_width";
    public static final String ARG_HEIGHT = "arg_heigh";

    @NonNull
    @Override
    public Enum getTaskId() {
        return TaskTypes.DECODE_IMAGE;
    }

    @Override
    public void run() {
        checkNotNullArg(ARG_RESPONSE);
        checkNotNullArg(ARG_WIDTH);
        checkNotNullArg(ARG_HEIGHT);

        Response response = getArg(ARG_RESPONSE, Response.class);
        Integer reqWidth = getArg(ARG_WIDTH, Integer.class);
        Integer reqHeight = getArg(ARG_HEIGHT, Integer.class);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        byte[] stream = new byte[0];
        try {
            stream = response.body().bytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.decodeByteArray(stream, 0, stream.length, options);

        final int height = options.outHeight;
        final int width = options.outWidth;

        Log.d(TAG, "height=" + height + " width="+width);

        int inSampleSize = 1;

        if (reqHeight > 0 && height > reqHeight || reqWidth > 0 && width > reqWidth) {
            while ((height / inSampleSize) >= reqHeight && (width / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        options.inSampleSize = inSampleSize;

        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeByteArray(stream, 0, stream.length, options);
        Log.d(TAG, "decode finished. " + bitmap);

        putArg(ShowImageTask.ARG_BITMAP, bitmap);
        startTask(TaskTypes.SAVE_TO_BITMAP_CACHE);
    }
}
