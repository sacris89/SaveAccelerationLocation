package cat.tecnocampus.metricservicelocation.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MetricActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_metric);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_metric, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void startService(View view) {
		Log.i("[Activity]", "going to START service");	
		Intent intent = new Intent(this, GforceService.class);
		
		//bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		startService(intent);
	}
	
	public void stopService(View view) {
		Log.i("[Activity]", "going to STOP service");	
	      // Unbind from the service
		Intent intent = new Intent(this, GforceService.class);
		
		//bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		stopService(intent);
	}
	
}
