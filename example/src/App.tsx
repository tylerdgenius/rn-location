import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import {
  requestLocationPermission,
  isLocationEnabled,
  getCurrentPosition,
} from 'rn-location';

export default function App() {
  const [result] = React.useState<number | undefined>();

  const requestingPermissions = async () => {
    try {
      const data = await requestLocationPermission();

      console.log({ data });
    } catch (error) {
      console.log({ error });
    }
  };

  const checkLocationPermission = async () => {
    try {
      const data = await isLocationEnabled();

      const location = await getCurrentPosition();
      console.log({ data, location });
    } catch (error) {
      console.log({ error });
    }
  };

  React.useEffect(() => {
    requestingPermissions();
    checkLocationPermission();
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
