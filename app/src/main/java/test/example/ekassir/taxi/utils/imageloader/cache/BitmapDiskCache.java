package test.example.ekassir.taxi.utils.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import test.example.ekassir.taxi.utils.FileUtils;
import test.example.ekassir.taxi.utils.crypto.CryptoSerialization;
import test.example.ekassir.taxi.utils.disklrucache.DiskLruCache;
import java.io.*;

/**
 * Created on 25.07.2016.
 */
public class BitmapDiskCache {

    private static final String TAG = "DiskLruImageCache";

    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10;
    private static final int IO_BUFFER_SIZE = 8 * 1024;

    private static final int APP_VERSION = 1;
    private static final int VALUE_COUNT = 1;

    private DiskLruCache diskLruCache;
    private Configuration configuration;

    private BitmapDiskCache(Configuration configuration) {
        this.configuration = configuration;
        try {
            diskLruCache = DiskLruCache.open(
                    configuration.diskCacheDir,
                    APP_VERSION,
                    VALUE_COUNT,
                    DISK_CACHE_SIZE
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Configuration {

        private String cacheName;
        private int compressQuality;
        private Bitmap.CompressFormat compressFormat;
        private File diskCacheDir;
        private long maxCacheTimeMillis;

        public Configuration(@NonNull Context context){
            this.cacheName = "default";
            this.compressQuality = 70;
            this.compressFormat = Bitmap.CompressFormat.JPEG;
            this.maxCacheTimeMillis = 10 * 60 * 1000;
            this.diskCacheDir = FileUtils.getDiskCacheDir(context, cacheName);
        }

        public Configuration setCacheName(@NonNull String cacheName) {
            this.cacheName = cacheName;
            return this;
        }

        public Configuration setCompressFormat(@NonNull Bitmap.CompressFormat compressFormat) {
            this.compressFormat = compressFormat;
            return this;
        }

        public Configuration setDiskCacheDir(@NonNull File diskCacheDir) {
            this.diskCacheDir = diskCacheDir;
            return this;
        }

        public Configuration setCompressQuality(int compressQuality) {
            this.compressQuality = compressQuality;
            return this;
        }

        public Configuration setMaxCacheTimeMillis(long maxCacheTimeMillis) {
            this.maxCacheTimeMillis = maxCacheTimeMillis;
            return this;
        }

        public BitmapDiskCache createCache(){
            return new BitmapDiskCache(this);
        }
    }



    public void put(@NonNull String url, @NonNull Bitmap bitmap) {
        DiskLruCache.Editor editor = null;

        String internalKey = CryptoSerialization.toMD5(url);
        try {
            editor = diskLruCache.edit(internalKey);
            if (editor == null) {
                return;
            }
            if(writeBitmapToFile(bitmap, editor)) {
                diskLruCache.flush();
                editor.commit();
            }
            else {
                editor.abort();
            }
        } catch (IOException e) {
            try {
                if (editor != null) {
                    editor.abort();
                }
            }
            catch (IOException ignored) {}
        }
    }

    private Bitmap readBitmapFromFile(DiskLruCache.Snapshot snapshot) throws IOException {
        Bitmap bitmap = null;
        final InputStream is = snapshot.getInputStream(0);
        if (is != null) {
            final BufferedInputStream bis = new BufferedInputStream(is, IO_BUFFER_SIZE);
            DataInputStream dis = new DataInputStream(bis);
            long timestamp = dis.readLong();
            if (isTimeExpected(timestamp, configuration.maxCacheTimeMillis)){
                return null;
            }
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
        }
        return bitmap;
    }

    private boolean writeBitmapToFile(Bitmap bitmap, DiskLruCache.Editor editor) throws IOException {
        BufferedOutputStream bos = null;
        try {
            OutputStream os = editor.newOutputStream(0);
            bos = new BufferedOutputStream(os, IO_BUFFER_SIZE);
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeLong(SystemClock.uptimeMillis());
            return bitmap.compress(configuration.compressFormat, configuration.compressQuality, dos);
        } finally {
            if (bos != null) {
                bos.close();
            }
        }
    }

    private static boolean isTimeExpected(long timestamp, long maxCacheTimeMillis){
        return SystemClock.uptimeMillis() > timestamp + maxCacheTimeMillis;
    }

    public @Nullable Bitmap get(@NonNull String url) {
        Bitmap bitmap = null;
        DiskLruCache.Snapshot snapshot = null;
        String internalKey = CryptoSerialization.toMD5(url);
        try {
            snapshot = diskLruCache.get(internalKey);
            if (snapshot == null) {
                return null;
            }
            bitmap = readBitmapFromFile(snapshot);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }
        return bitmap;
    }

    public void clearCache() {
        try {
            diskLruCache.delete();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public File getCacheFolder() {
        return diskLruCache.getDirectory();
    }

}
