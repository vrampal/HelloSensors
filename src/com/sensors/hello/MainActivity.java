package com.sensors.hello;

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

  // ----- Sensor business objects -----

  private SensorManager sensorManager;

  private List<Sensor> sensorList;

  // ----- UI Elements -----

  private Spinner spinner;

  private TextView value0, value1, value2, accuracy;

  private TextView sensorName, sensorMaximumRange, sensorPower, sensorResolution, sensorVendor, sensorVersion;

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
    value0 = (TextView) findViewById(R.id.value0);
    value1 = (TextView) findViewById(R.id.value1);
    value2 = (TextView) findViewById(R.id.value2);
    accuracy = (TextView) findViewById(R.id.accuracy);
    sensorName = (TextView) findViewById(R.id.name);
    sensorMaximumRange = (TextView) findViewById(R.id.rangeMax);
    sensorPower = (TextView) findViewById(R.id.power);
    sensorResolution = (TextView) findViewById(R.id.resolution);
    sensorVendor = (TextView) findViewById(R.id.vendor);
    sensorVersion = (TextView) findViewById(R.id.version);

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
    disableListener();
    super.onPause();
  }

  // ----- OnItemSelectedListener API -----

  @Override
  public void onItemSelected(AdapterView<?> adapter, View view, int index, long id) {
    disableListener();
    selectSensor(index);
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapter) {
    disableListener();
  }

  // ----- Business methods used by UI -----

  private void selectSensor(int index) {
    Sensor sensor = sensorList.get(index);

    sensorName.setText(sensor.getName());
    sensorMaximumRange.setText("Max range: " + sensor.getMaximumRange());
    sensorResolution.setText("Resolution: " + sensor.getResolution());
    sensorPower.setText("Power: " + sensor.getPower() + "mA");
    sensorVendor.setText("Vendor: " + sensor.getVendor());
    sensorVersion.setText("Version: " + sensor.getVersion());

    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
  }

  private void disableListener() {
    sensorManager.unregisterListener(this);
  }

  // ----- SensorEventListener API -----

  @Override
  public void onSensorChanged(SensorEvent event) {
    value0.setText(String.format("value[0]: %f", event.values[0]));
    value1.setText(String.format("value[1]: %f", event.values[1]));
    value2.setText(String.format("value[2]: %f", event.values[2]));
    accuracy.setText(String.format("Accuracy: %d", event.accuracy));
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracyVal) {
    accuracy.setText(String.format("Accuracy: %d", accuracyVal));
  }

}
