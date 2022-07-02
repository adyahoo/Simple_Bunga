package id.ac.uasbungaa.helper;

import android.content.Context;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public final class ToastHelper {
    @NotNull
    public static final ToastHelper INSTANCE;

    public final void showToast(@NotNull String msg, @NotNull Context context) {
        Toast.makeText(context, (CharSequence) msg, Toast.LENGTH_SHORT).show();
    }

    private ToastHelper() {
    }

    static {
        INSTANCE = new ToastHelper();
    }
}