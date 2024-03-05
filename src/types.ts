type DefaultRNLocationResponse<T = any> = {
  success: boolean;
  message: string;
  payload: T;
};

export type RequestPermission = () => Promise<DefaultRNLocationResponse<null>>;

type LocationProps = {
  latitude: number;
  longitude: number;
};

export type GetCurrentPermission = () => Promise<
  DefaultRNLocationResponse<LocationProps | null>
>;

export type IsLocationEnabled = () => Promise<
  DefaultRNLocationResponse<boolean | null>
>;
