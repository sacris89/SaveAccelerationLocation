package cat.tecnocampus.metricservicelocation.app.acceleration;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerationHelper {
	private float xAxis_lateral, yAxis_longitudinal, zAxis_vertical, acceleration;
	private long timeStamp; 

	private SensorManager sensorManager; 
	private Sensor accelerometer;

	private Context context;
	
	//sensor event listener
	private final SensorEventListener mySensorEventListener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				xAxis_lateral = event.values[0];
				yAxis_longitudinal = event.values[1];
				zAxis_vertical = event.values[2];
				timeStamp = event.timestamp;
			
				acceleration = Math.round(Math.sqrt(Math.pow(xAxis_lateral, 2) + Math.pow(yAxis_longitudinal,2) + Math.pow(zAxis_vertical, 2))) - SensorManager.STANDARD_GRAVITY;
				
			}
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};
	

	public AccelerationHelper(Context ctx) {
		context = ctx;
	}

	public void start() {
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);	

		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(mySensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

	}
	
	public void stop() {
		sensorManager.unregisterListener(mySensorEventListener);
	}
	
	public String getAcceleration() {
		return Long.toString(timeStamp) + ", " + Double.toString(xAxis_lateral) + ", " + Double.toString(yAxis_longitudinal) + ", " +
				Double.toString(zAxis_vertical) + ", " + Double.toString(acceleration);
	}

}
