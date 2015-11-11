package cat.tecnocampus.metricservicelocation.app.location;


public final class LocationUtils {

	public static final String APPTAG = "LocationSample";

	public static final String SHARED_PREFERENCES = "com.example.android.location.SHARED_PREFERENCES";

	public static final String KEY_UPDATES_REQUESTED = "com.example.android.location.KEY_UPDATES_REQUESTED";

	public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	public static final int MILLISECONDS_PER_SECOND = 1000;

	public static final int UPDATE_INTERVAL_IN_SECONDS = 5;

	public static final int FAST_CEILING_IN_SECONDS = 1;

	public static final long UPDATE_INTERVAL_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
			* UPDATE_INTERVAL_IN_SECONDS;

	public static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
			* FAST_CEILING_IN_SECONDS;

	public static final String EMPTY_STRING = new String();

//	public static String getLatLng(Context context, Location currentLocation) {
//		if (currentLocation != null) {
//			return context.getString(R.string.latitude_longitude,
//					currentLocation.getLatitude(),
//					currentLocation.getLongitude());
//		} else {
//			return EMPTY_STRING;
//		}
//	}
}