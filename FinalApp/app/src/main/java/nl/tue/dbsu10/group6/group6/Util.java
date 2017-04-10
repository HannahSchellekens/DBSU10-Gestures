package nl.tue.dbsu10.group6.group6;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Ruben Schellekens
 */
public class Util {

    public static void toast(Context context, CharSequence text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private Util() {
        throw new AssertionError("Noop");
    }
}
