package nl.tue.dbsu10.group6.gestures;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import nl.tue.dbsu10.group6.gestures.util.Util;
import nl.tue.id.oocsi.client.OOCSIClient;

/**
 * Main class for the Gestures-module.
 * <p>
 * This contains all general information as well as provides an information hook (like for example
 * passing app-specific information on to the module).
 *
 * @author Group 6: Ruben Schellekens, Tobi de Kok, Art Selbach
 */
public class Gestures {

    /**
     * The OOCSI-channel that is used by the Gestures module.
     */
    public static final String OOCSI_CHANNEL = "dbsu10-gestures";

    /**
     * The hostname of the OOCSI-server to broadcast to.
     * <p>
     * The port number must not be defined here, for that see {@link Gestures#OOCSI_DEFAULT_PORT}.
     */
    public static final String OOCSI_DEFAULT_HOST = "oocsi.id.tue.nl";

    /**
     * The port number on which the OOCSI-server is listening.
     */
    public static final int OOCSI_DEFAULT_PORT = 4444;

    /**
     * The MAC-address that gets passed around when a certain MAC-address could not be found.
     * <p>
     * See this as a 'null' value or error value.
     */
    public static final String MAC_NOT_FOUND = "02:00:00:00:00:00";

    /**
     * The key of the gesteure id in an {@link nl.tue.id.oocsi.OOCSIEvent} data pair.
     */
    public static final String KEY_GESTURE_ID = "gesture";

    /**
     * The key of the mac address in an {@link nl.tue.id.oocsi.OOCSIEvent} data pair.
     */
    public static final String KEY_MAC_ADDRESS = "mac";

    /**
     * The default client that is being used by the Gestures module to send messages over OOCSI.
     */
    private static OOCSIClient client;

    /**
     * The SensorManager that provides the device sensors to the program.
     */
    private static SensorManager sensorManager;

    /**
     * The android Vibrator that allows the module to send vibrations.
     */
    private static Vibrator vibrator;

    // Prevent instances of Gestures.
    private Gestures() {
    }

    /**
     * Connects to the OOCSI server with default credentials.
     * <p>
     * Server address: {@link Gestures#OOCSI_DEFAULT_HOST}:{@link Gestures#OOCSI_DEFAULT_PORT}
     */
    public static OOCSIClient newClient() {
        Util.permitAll();
        client = new OOCSIClient(Gestures.class.getName());
        client.connect(OOCSI_DEFAULT_HOST, OOCSI_DEFAULT_PORT);
        return client;
    }

    /**
     * Enables the Gestures module.
     * <p>
     * This connects the {@link SensorManager}, the {@link Vibrator} and the given client to the
     * Gestures module. Also the default gestures will be enabled.
     *
     * @throws ClassCastException
     *         When either the SensorManager or the Vibrator could not be correctly obtained from
     *         the Activity.
     * @see Gestures#sensorManager
     * @see Gestures#vibrator
     * @see Gestures#client
     * @see Gesture#enableDefaultGestures()
     */
    public static void enable(AppCompatActivity activity, OOCSIClient client) throws ClassCastException {
        Gestures.sensorManager = (SensorManager)activity.getSystemService(Context.SENSOR_SERVICE);
        Gestures.vibrator = (Vibrator)activity.getSystemService(Context.VIBRATOR_SERVICE);
        Gestures.client = client;
        Gesture.enableDefaultGestures();
    }

    /**
     * Get the client through which all messages must be sent.
     * <p>
     * {@link Gestures#enable(AppCompatActivity, OOCSIClient)} or {@link Gestures#newClient()} must
     * have been called prior.
     *
     * @throws IllegalStateException
     *         When {@link Gestures#enable(AppCompatActivity, OOCSIClient)} or {@link
     *         Gestures#newClient()} has not been called first.
     * @see Gestures#client
     */
    public static OOCSIClient getClient() throws IllegalStateException {
        if (client == null) {
            throw new IllegalStateException("enable must have been called first");
        }

        return client;
    }

    /**
     * Get the registered SensorManager.
     * <p>
     * {@link Gestures#enable(AppCompatActivity, OOCSIClient)} must have been called prior.
     *
     * @throws IllegalStateException
     *         When {@link Gestures#enable(AppCompatActivity, OOCSIClient)} has not been called
     *         first.
     * @see Gestures#sensorManager
     */
    public static SensorManager getSensorManager() throws IllegalStateException {
        if (sensorManager == null) {
            throw new IllegalStateException("enable must have been called first");
        }

        return sensorManager;
    }

    /**
     * Get the registered Vibrator
     * <p>
     * {@link Gestures#enable(AppCompatActivity, OOCSIClient)} must have been called prior.
     *
     * @throws IllegalStateException
     *         When {@link Gestures#enable(AppCompatActivity, OOCSIClient)} has not been called
     *         first.
     * @see Gestures#vibrator
     */
    public static Vibrator getVibrator() throws IllegalStateException {
        if (vibrator == null) {
            throw new IllegalStateException("enable must have been called first");
        }

        return vibrator;
    }
}
