package nl.tue.dbsu10.group6.gestures;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * A Tracker that detects a strong shake movement.
 *
 * @author Ruben Schellekens
 */
public class ShakeTracker extends GestureTracker implements SensorEventListener {

    private static final int SHAKE_THRESHOLD = 2000;
    private long lastUpdate = 0;
    private float lastX;
    private float lastY;
    private float lastZ;

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

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastUpdate) <= 180) {
            return;
        }

        long difference = currentTime - lastUpdate;
        lastUpdate = currentTime;

        float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / difference * 10000;

        if (speed > SHAKE_THRESHOLD) {
            activate();
        }

        lastX = x;
        lastY = y;
        lastZ = z;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
