package test.example.ekassir.taxi.utils.imageloader.tasks;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created on 24.07.2016.
 */
public class ShowImageTask extends BaseTask {

    private static final String TAG = "ShowImageTask";
    public static final String ARG_BITMAP = "arg_bitmap";
    public static final String ARG_IMAGE_VIEW = "arg_image_view";

    @NonNull
    @Override
    public Enum getTaskId() {
        return TaskTypes.SHOW_IMAGE;
    }

    @Override
    public void run() {
        checkNotNullArg(ARG_IMAGE_VIEW);
        checkNotNullArg(ARG_BITMAP);

        final ImageView imageView = getArg(ARG_IMAGE_VIEW, ImageView.class);
        final Bitmap bitmap = getArg(ARG_BITMAP, Bitmap.class);

        getHandler().post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
        startTask(null);
    }
}
