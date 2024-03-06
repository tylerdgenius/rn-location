import CoreLocation;

@objc(RnLocation)
class RnLocation: NSObject {

  @objc(multiply:withB:withResolver:withRejecter:)
  func multiply(a: Float, b: Float, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
    resolve(a*b)
  }

  //Function to prompt users on ios to get location permission
  @objc(getLocationPermission:withResolver:withRejecter:)
  func getLocationPermission(resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) -> Void {
    do {
      if !hasLocationPermission() {
        let locationManager = try CLLocationManager()
        locationManager.requestWhenInUseAuthorization()

        resolve("Location permission prompted")
      } else {
        reject("ERROR_CODE", "Location already granted", nil)
      }
    } catch error {
      reject("ERROR_CODE", "Error message", error)
    }
  }

  @objc public static func hasLocationPermission() -> Bool {
    return CLLocationManager.authorizationStatus() == .authorizedWhenInUse || CLLocationManager.authorizationStatus() == .authorizedAlways
  }
}
