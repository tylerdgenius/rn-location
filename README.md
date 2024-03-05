# rn-location

A package that helps to connect android and ios native location apis

## Installation

```sh
npm install rn-location
```

## Default Error Response Object

```ts
{
    success: true; //This is a boolean field that details if the request being processed is successful or not. It can only be true or false
    message: "" // This field carries details of the request that occurred whether it was successful or not
    payload: ?? // This field can be any type of payload. It follows the data needed to be returned from the query done. e.g. Getting current position gives out longitude and latitude
}
```

## Request Permission Usage

```js
import { requestLocationPermission } from 'rn-location';

const performingAction = async () => {
  try {
    const data = await requestLocationPermission();

    // Do something else with the data needed
  } catch (error) {
    // Error will follow the standard response format detailed above. Keep in mind that all errors will always be thrown to the parent to catch
  }
};
```

## Check if Location is Enabled

```js
import { isLocationEnabled } from 'rn-location';

const performingAction = async () => {
  try {
    const data = await isLocationEnabled();

    // Do something else with the data needed
  } catch (error) {
    // Error will follow the standard response format detailed above. Keep in mind that all errors will always be thrown to the parent to catch
  }
};
```

## Get current users position

```js
import { getCurrentPosition } from 'rn-location';

const performingAction = async () => {
  try {
    const data = await getCurrentPosition();

    // Do something else with the data needed
  } catch (error) {
    // Error will follow the standard response format detailed above. Keep in mind that all errors will always be thrown to the parent to catch
  }
};
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
