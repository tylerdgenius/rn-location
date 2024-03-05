import { NativeModules, Platform } from 'react-native';
import type {
  GetCurrentPermission,
  IsLocationEnabled,
  RequestPermission,
} from './types';

const LINKING_ERROR =
  `The package 'rn-location' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const RnLocation = NativeModules.RnLocation
  ? NativeModules.RnLocation
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export const requestLocationPermission: RequestPermission = async () => {
  try {
    const locationPermission = await RnLocation.requestAndroidPermission();

    return {
      success: true,
      message: locationPermission,
      payload: null,
    };
  } catch (error) {
    const errorMessage = error as Error;
    return {
      success: false,
      message: errorMessage.message,
      payload: null,
    };
  }
};

export const isLocationEnabled: IsLocationEnabled = async () => {
  try {
    const locationStatus = await RnLocation.isLocationEnabled();

    return {
      success: true,
      message: 'Successfully gotten location enablement status',
      payload: locationStatus,
    };
  } catch (error) {
    const errorMessage = error as Error;
    return {
      success: false,
      message: errorMessage.message,
      payload: null,
    };
  }
};

export const getCurrentPosition: GetCurrentPermission = async () => {
  try {
    const position = await RnLocation.getCurrentPosition();

    return {
      success: true,
      message: 'Successfully gotten current position',
      payload: JSON.parse(position),
    };
  } catch (error) {
    const errorMessage = error as Error;
    return {
      success: false,
      message: errorMessage.message,
      payload: null,
    };
  }
};
