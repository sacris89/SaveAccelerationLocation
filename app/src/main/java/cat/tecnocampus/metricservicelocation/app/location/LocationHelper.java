package cat.tecnocampus.metricservicelocation.app.location;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import cat.tecnocampus.metricservicelocation.app.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationHelper implements LocationListener,
	GoogleApiClient.ConnectionCallbacks,
	GoogleApiClient.OnConnectionFailedListener {
	
	private LocationRequest mLocationRequest;
	private GoogleApiClient mLocationClient;
	
	private Context context;

	private Location currentLocation = null;
	
	
	
	public LocationHelper (Context ctx) {
		context = ctx;
	}
	
	public void initLocationService() {
		// builds the LocationRequest
		mLocationRequest = new LocationRequest();

		mLocationRequest.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

		//builds the API client
		mLocationClient = new GoogleApiClient.Builder(context)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();

	}
	
	public void disconnect() {
		if (mLocationClient.isConnected()) {
			LocationServices.FusedLocationApi.removeLocationUpdates(mLocationClient, this);
		}
		mLocationClient.disconnect();
	}
	
	public void connect() {
		mLocationClient.connect();
	}
	
	
	public String getCurrentLocation () {
		if (currentLocation != null) {
		
			return Double.toString(currentLocation.getLatitude()) + ", " + Double.toString(currentLocation.getLongitude())
					+ ", " + Float.toString((float) (currentLocation.getSpeed()* 3.6)) + ", " + 
					Float.toString(currentLocation.getAccuracy()) + ", " + Long.toString(currentLocation.getTime());
		}
		else {
			return "";
		}
	}
	

	private boolean servicesConnected() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(context);
		if (ConnectionResult.SUCCESS == resultCode) {
			Log.d(LocationUtils.APPTAG,
					context.getString(R.string.play_services_available));
			return true;
		} else {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,
					(Activity) context, 0);
			return false;
		}
	}

	@Override
	public void onLocationChanged(Location arg0) {
		currentLocation = arg0;
	}


	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onConnected(Bundle arg0) {
		if (servicesConnected()) {
			LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, this);
		}
	}

	@Override
	public void onConnectionSuspended(int i) {

	}


}
