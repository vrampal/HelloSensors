package vrampal.hellosensors;

import android.hardware.Sensor;

public enum SensorType {

  ACCELEROMETER(Sensor.TYPE_ACCELEROMETER, "m/s^2", 3),
  MAGNETIC_FIELD(Sensor.TYPE_MAGNETIC_FIELD, "uT", 3),
  ORIENTATION(Sensor.TYPE_ORIENTATION, "deg", 3),
  GYROSCOPE(Sensor.TYPE_GYROSCOPE, "rad/s", 3),
  LIGHT(Sensor.TYPE_LIGHT, "lux", 1),
  PRESSURE(Sensor.TYPE_PRESSURE, "hPa", 1),
  PROXIMITY(Sensor.TYPE_PROXIMITY, "cm", 1),
  GRAVITY(Sensor.TYPE_GRAVITY, "m/s^2", 3),
  LINEAR_ACCELERATION(Sensor.TYPE_LINEAR_ACCELERATION, "m/s^2", 3),
  RELATIVE_HUMIDITY(Sensor.TYPE_RELATIVE_HUMIDITY, "%", 1),
  AMBIENT_TEMPERATURE(Sensor.TYPE_AMBIENT_TEMPERATURE, "°C", 1),
  ROTATION_VECTOR(Sensor.TYPE_ROTATION_VECTOR, "", 5),
  UNKNOWN(0, "", 0);

  public static SensorType valueOf(int type) {
    for (SensorType st : SensorType.values()) {
      if (st.type == type) {
        return st;
      }
    }
    return UNKNOWN;
  }

  private final int type;

  private final String unit;

  private final int nbFields;

  private SensorType(int type, String unit, int nbFields) {
    this.type = type;
    this.unit = unit;
    this.nbFields = nbFields;
  }

  public int getType() {
    return type;
  }

  public String getUnit() {
    return unit;
  }

  public int getNbFields() {
    return nbFields;
  }

}
