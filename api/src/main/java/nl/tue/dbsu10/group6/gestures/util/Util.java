package nl.tue.dbsu10.group6.gestures.util;

import android.os.StrictMode;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

/**
 * Several utility functions that can be handy.
 *
 * @author Ruben Schellekens
 */
public class Util {

    /**
     * Cheaty method of allowing networking on the main thread.
     * <p>
     * It's hacky, but it works.
     */
    public static void permitAll() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    /**
     * Get the MAC address of the current device.
     */
    public static Optional<String> getMAC() {
        try {
            List<NetworkInterface> interfaces =
                    Collections.list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface nif : interfaces) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) {
                    continue;
                }

                byte[] bytes = nif.getHardwareAddress();
                String macString = toMACString(bytes);

                return Optional.ofNullable(macString);
            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Turns the given byte array into a nicely formatted MAC-string:
     * <p>
     * Pairs of two hexadecimal digits seperated by colons.
     */
    private static String toMACString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(Integer.toHexString(b & 0xFF)).append(":");
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    // Prevent Util-instances.
    private Util() {
    }
}
