package test.example.ekassir.taxi.utils.tasks;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public final class RuntimeArgs {

    private final Map<String, Object> runtimeStorage = new HashMap<>();

    public <T> void putValue(String key, T value){
        synchronized (runtimeStorage) {
            runtimeStorage.put(key, value);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String key, Class<T> typeTag){
        synchronized (runtimeStorage) {
            Object val = runtimeStorage.get(key);
            return (T)val;
        }
    }

    public void clear() {
        runtimeStorage.clear();
    }

    public boolean contains(String key) {
        return runtimeStorage.containsKey(key) && runtimeStorage.get(key) != null;
    }
}
