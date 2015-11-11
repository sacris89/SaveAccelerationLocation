package cat.tecnocampus.metricservicelocation.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import cat.tecnocampus.metricservicelocation.app.acceleration.AccelerationHelper;
import cat.tecnocampus.metricservicelocation.app.location.LocationHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Timer;
import java.util.TimerTask;


public class GforceService extends Service {

	private FileOutputStream outputStream;
	private File file;
	
	private static int FOREGROUND_ID = 123;
	
	private Timer updateTimer;
	
	private WakeLock wakeLock;
	
	private LocationHelper locationHelper;
	private AccelerationHelper accelerationHelper;
	
	private final int mId = 7;
	


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void onCreate() {
		super.onCreate();
		// get the sensor manager

		Log.i("[Service]", "in onCreate the service ");	
	}
	
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
		        "MyWakelockTag");
		wakeLock.acquire();
		
		startForeground(FOREGROUND_ID, buildForegroundNotification());

		Log.i("[Service]", "in onStart  service ");			
		
		locationHelper = new LocationHelper(this);
		locationHelper.initLocationService();
		locationHelper.connect();
		//while (!locationHelper.connected()) {}
		
		
		//open file
		try {
			file = new File(Environment.getExternalStorageDirectory(), "data.csv");
			outputStream = new FileOutputStream(file);
			outputStream.write("timeStamp, x, y, z, gforce, lat, long, speed, accuracy, time\n".getBytes());
		}
		catch (Exception e) {
			Log.i("[Service]","file exception " + e);
		}

		accelerationHelper = new AccelerationHelper(this);
		accelerationHelper.start();
	
		updateTimer = new Timer("uiUpdate");
		updateTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				writeFile();
			}
		}, 0, 100);
		
				
		return Service.START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.i("service", "onDestroy Service ");
		
		wakeLock.release();
		updateTimer.cancel();
		accelerationHelper.stop();
		
		try {
			outputStream.close();
		}
		catch (Exception e) {
			
		}
		
		locationHelper.disconnect();
		stopForeground(true);
		super.onDestroy();
	}


		
	private void writeFile() {
		String values;
		
		
		values = accelerationHelper.getAcceleration() + ", " + locationHelper.getCurrentLocation() + "\n";
		
		Log.i("Service", values);
		
		try {
			outputStream.write(values.getBytes());
		}
		catch (Exception e) {
			Log.i("[Service]","write file exception " + e);
		}
		
	}
	
	private Notification buildForegroundNotification() {
		NotificationCompat.Builder b = new NotificationCompat.Builder(this);
		
		b.setOngoing(true);
		b.setContentTitle(getString(R.string.telematics))
			.setContentText("Saving")
			.setSmallIcon(android.R.drawable.stat_sys_upload)
			.setTicker(getString(R.string.telematics));
		
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MetricActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MetricActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		b.setContentIntent(resultPendingIntent);

		Notification n = b.build();

		return (n);
	}

}

