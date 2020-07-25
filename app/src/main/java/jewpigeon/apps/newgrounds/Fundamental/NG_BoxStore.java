package jewpigeon.apps.newgrounds.Fundamental;

import android.content.Context;

import io.objectbox.BoxStore;

public class NG_BoxStore {
    private static BoxStore boxStore;

    public static void init(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
    }

    public static BoxStore get() { return boxStore; }
}
