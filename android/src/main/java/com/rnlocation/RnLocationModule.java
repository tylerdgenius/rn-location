package com.rnlocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.content.Context;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import org.json.JSONObject;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.rnlocation.utils.Constants;

@ReactModule(name = Constants.MODULE_NAME)
public class RnLocationModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private LocationManager locationManager;
  private LocationListener locationListener;

  public RnLocationModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.locationManager = (LocationManager) reactContext.getSystemService(Context.LOCATION_SERVICE);
    this.locationListener = new LocationListener() {
      @Override
      public void onLocationChanged(Location location) {
        sendLocationUpdate(location);
      }

      // @Override
      // public void onStatusChanged(String provider, int status, Bundle extras) {
      // }

      // @Override
      // public void onProviderEnabled(String provider) {
      // }

      // @Override
      // public void onProviderDisabled(String provider) {
      // }
    };
  }

  @Override
  @NonNull
  public String getName() {
    return Constants.MODULE_NAME;
  }

  @ReactMethod
  public void requestAndroidPermission(Promise promise) {
    try {
      if (!hasLocationPermission()) {
        ActivityCompat.requestPermissions(getCurrentActivity(),
            new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, Constants.PERMISSION_REQUEST_LOCATION);

        promise.resolve("Location request successfully prompted");
      } else {
        promise.reject(Constants.PERMISSION_PREVIOUSLY_GRANTED, "Location already granted");
      }
    } catch (Exception e) {
      promise.reject(Constants.UNKNOWN_ERROR, e.getMessage());
    }
  }

  @ReactMethod
  public void isLocationEnabled(Promise promise) {
    try {
      boolean isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
          || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
      promise.resolve(isLocationEnabled);
    } catch (Exception e) {
      promise.reject(Constants.PERMISSION_DENIED, "Location currently unavailable");
    }

  }

  @ReactMethod
  public void getCurrentPosition(final Promise promise) {
    try {
      if (!hasLocationPermission()) {
        promise.reject(Constants.PERMISSION_DENIED, "Location permission not granted");
        return;
      }

      locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);
      Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
      if (location != null) {
        JSONObject locationObj = new JSONObject();
        locationObj.put("latitude", location.getLatitude());
        locationObj.put("longitude", location.getLongitude());
        promise.resolve(locationObj.toString());
      } else {
        promise.reject(Constants.LOCATION_UNAVAILABLE, "Location unavailable");
      }
    } catch (SecurityException e) {
      promise.reject(Constants.PERMISSION_DENIED, e.getMessage());
    } catch (Exception e) {
      promise.reject(Constants.UNKNOWN_ERROR, e.getMessage());
    }
  }

  private void sendLocationUpdate(Location location) {
    if (location != null) {
      JSONObject locationObj = new JSONObject();
      try {
        locationObj.put("latitude", location.getLatitude());
        locationObj.put("longitude", location.getLongitude());
        // Emitting an event to JavaScript
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit("onLocationUpdate", locationObj.toString());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private boolean hasLocationPermission() {
    int permissionState = ContextCompat.checkSelfPermission(
        reactContext, Manifest.permission.ACCESS_FINE_LOCATION);

    return permissionState == PackageManager.PERMISSION_GRANTED;
  }
}