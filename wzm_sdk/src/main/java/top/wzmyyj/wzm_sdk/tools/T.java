package top.wzmyyj.wzm_sdk.tools;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;


/**
 * Created by wzm on 2018/1/11 0011.
 */

public class T {

    private static Application app;

    private T() {
    }

    public static void init(Application app) {
        T.app = app;
    }

    public static void s(String msg) {
        s(app, msg);
    }

    public static void l(String msg) {
        l(app, msg);
    }

    public static void s(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public static void l(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
