package test.example.ekassir.taxi.utils.imageloader.tasks;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created on 24.07.2016.
 */
public class LoadImageTask extends BaseTask {

    public static final String ARG_IMAGE_URL = "arg_image_url";
    private static final String TAG = "LoadImageTask";

    @NonNull
    @Override
    public Enum getTaskId() {
        return TaskTypes.LOAD_IMAGE;
    }

    @Override
    public void run() {
        checkNotNullArg(LoadImageTask.ARG_IMAGE_URL);

        String url = getArg(ARG_IMAGE_URL, String.class);
        OkHttpClient client = new OkHttpClient
                .Builder()
                .build();

        Call call = client.newCall(new Request.Builder()
                .get()
                .url(url)
                .build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    putArg(DecodeBitmapTask.ARG_RESPONSE, response);
                    startTask(TaskTypes.DECODE_IMAGE);
                }
            }
        });
    }
}
