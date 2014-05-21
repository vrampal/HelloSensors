package vrampal.hellosensors;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemSelectedListener, SensorEventListener {

  private static final int SENSOR_DELAY = SensorManager.SENSOR_DELAY_UI;

  // ----- Sensor business objects -----

  private SensorManager sensorManager;

  private List<Sensor> sensorList;

  private Sensor sensor;

  private SensorType type;

  private long prevTimestamp;

  // ----- UI Elements -----

  private Spinner spinner;

  private TextView sensorName, sensorType, sensorVendor, sensorVersion;

  private TextView sensorMinDelay, sensorResolution, sensorMaxRange, sensorPower;

  private TextView timestamp, deltaT, valueX, valueY, valueZ, value3, value4;

  // ----- Activity UI API -----

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Get business objects
    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

    // Get UI elements
    spinner = (Spinner) findViewById(R.id.spinner);

    sensorName = (TextView) findViewById(R.id.name);
    sensorType = (TextView) findViewById(R.id.type);
    sensorVendor = (TextView) findViewById(R.id.vendor);
    sensorVersion = (TextView) findViewById(R.id.version);

    sensorMinDelay = (TextView) findViewById(R.id.minDelay);
    sensorResolution = (TextView) findViewById(R.id.resolution);
    sensorMaxRange = (TextView) findViewById(R.id.maxRange);
    sensorPower = (TextView) findViewById(R.id.power);

    timestamp = (TextView) findViewById(R.id.timestamp);
    deltaT = (TextView) findViewById(R.id.deltaT);
    valueX = (TextView) findViewById(R.id.valueX);
    valueY = (TextView) findViewById(R.id.valueY);
    valueZ = (TextView) findViewById(R.id.valueZ);
    value3 = (TextView) findViewById(R.id.value3);
    value4 = (TextView) findViewById(R.id.value4);

    // Build the spinner
    List<String> sensorNames = new ArrayList<String>(sensorList.size());
    for (Sensor sensor : sensorList) {
      sensorNames.add(sensor.getName());
    }

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_dropdown_item,
        sensorNames);
    spinner.setAdapter(adapter);

    spinner.setOnItemSelectedListener(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    disableListener();
  }

  @Override
  protected void onResume() {
    super.onResume();
    enableListener();
  }

  // ----- OnItemSelectedListener API -----

  @Override
  public void onItemSelected(AdapterView<?> adapter, View view, int index, long id) {
    disableListener();
    selectSensor(index);
    enableListener();
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapter) {
    disableListener();
  }

  // ----- Business methods used by UI -----

  private void selectSensor(int index) {
    sensor = sensorList.get(index);

    int typeInt = sensor.getType();
    type = SensorType.valueOf(typeInt);
    String unit = type.getUnit();

    sensorName.setText(sensor.getName());
    sensorType.setText("Type: " + type.name());
    sensorVendor.setText("Vendor: " + sensor.getVendor());
    sensorVersion.setText("Version: " + sensor.getVersion());

    sensorMinDelay.setText(String.format("MinDelay: %d us", sensor.getMinDelay()));
    sensorResolution.setText(String.format("Resolution: %7.5f %s", sensor.getResolution(), unit));
    sensorMaxRange.setText(String.format("MaxRange: %7.5f %s", sensor.getMaximumRange(), unit));
    sensorPower.setText(String.format("Power: %f mA", sensor.getPower()));
  }

  private void enableListener() {
    if (sensor != null) {
      sensorManager.registerListener(this, sensor, SENSOR_DELAY);
    }
  }

  private void disableListener() {
    sensorManager.unregisterListener(this);
  }

  // ----- SensorEventListener API -----

  @Override
  public void onSensorChanged(SensorEvent event) {
    timestamp.setText(String.format("timestamp: %d us", event.timestamp / 1000));

    long dT = event.timestamp - prevTimestamp;
    prevTimestamp = event.timestamp;
    deltaT.setText(String.format("deltaT: %d us", dT / 1000));

    String unit = type.getUnit();
    String tempText;

    tempText = String.format("valueX: %+7.5f %s", event.values[0], unit);
    valueX.setText(tempText);

    if (event.values.length >= 2) {
      tempText = String.format("valueY: %+7.5f %s", event.values[1], unit);
    } else {
      tempText = "";
    }
    valueY.setText(tempText);

    if (event.values.length >= 3) {
      tempText = String.format("valueZ: %+7.5f %s", event.values[2], unit);
    } else {
      tempText = "";
    }
    valueZ.setText(tempText);

    if (event.values.length >= 4) {
      tempText = String.format("value3: %+7.5f %s", event.values[3], unit);
    } else {
      tempText = "";
    }
    value3.setText(tempText);

    if (event.values.length >= 5) {
      tempText = String.format("value4: %+7.5f %s", event.values[4], unit);
    } else {
      tempText = "";
    }
    value4.setText(tempText);
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracyVal) {
    // Nothing to do.
  }

}
