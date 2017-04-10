package nl.tue.dbsu10.group6.gestures;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * A tracker that detects rolling of a device, i.e. turning your phone around a certain axis.
 *
 * @author Ruben Schellekens
 */
public class RollTracker extends GestureTracker implements SensorEventListener {

    private static final int THRESHOLD = 500;
    public static final int LEFT = 1;
    public static final int RIGHT = -1;
    public static final int UP = 1;
    public static final int DOWN = -1;
    public static final int X_AXIS = 0;
    public static final int Y_AXIS = 1;
    public static final int Z_AXIS = 2;

    private final int axis;
    private final int direction;

    private long lastUpdate = 0;
    private float[] last = new float[3];

    public RollTracker(int axis, int direction) {
        this.axis = axis;
        this.direction = direction;
    }

    @Override
    public void enable() {
        SensorManager sensorManager = Gestures.getSensorManager();
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        if (sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }

        float value = event.values[axis];
        float yModifier = axis == Y_AXIS ? 0.85f : 1f;

        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastUpdate) <= 180 * yModifier) {
            return;
        }

        long difference = currentTime - lastUpdate;
        lastUpdate = currentTime;

        float speed = (value - last[axis]) / difference * 10000;
        if (direction * speed > THRESHOLD) {
            activate();
        }

        last[axis] = value;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
