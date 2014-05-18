package vrampal.hellosensors;

import android.hardware.Sensor;

public enum SensorType {

  ACCELEROMETER(Sensor.TYPE_ACCELEROMETER, "m/s^2"),
  MAGNETIC_FIELD(Sensor.TYPE_MAGNETIC_FIELD, "uT"),
  ORIENTATION(Sensor.TYPE_ORIENTATION, "deg"),
  GYROSCOPE(Sensor.TYPE_GYROSCOPE, "rad/s"),
  LIGHT(Sensor.TYPE_LIGHT, "lux"),
  PRESSURE(Sensor.TYPE_PRESSURE, "hPa"),
  PROXIMITY(Sensor.TYPE_PROXIMITY, "cm"),
  GRAVITY(Sensor.TYPE_GRAVITY, "m/s^2"),
  LINEAR_ACCELERATION(Sensor.TYPE_LINEAR_ACCELERATION, "m/s^2"),
  RELATIVE_HUMIDITY(Sensor.TYPE_RELATIVE_HUMIDITY, "%"),
  AMBIENT_TEMPERATURE(Sensor.TYPE_AMBIENT_TEMPERATURE, "°C"),
  UNKNOWN(0, "");

  public static SensorType getByType(int type) {
    for (SensorType st : SensorType.values()) {
      if (st.type == type) {
        return st;
      }
    }
    return UNKNOWN;
  }

  private final int type;

  private final String unit;

  private SensorType(int type, String unit) {
    this.type = type;
    this.unit = unit;
  }

  public int getType() {
    return type;
  }

  public String getUnit() {
    return unit;
  }

}
