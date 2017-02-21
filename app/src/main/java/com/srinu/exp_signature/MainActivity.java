package com.srinu.exp_signature;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener{

	private TextView tv_timeStamp = null;
	private TextView tv_acoustic = null;
	private TextView tv_pressure = null;
	private TextView tv_acceleration = null;

	private SensorManager sensorManager;

	//private ArrayList<String> al_SensorValues = null;

	private String filename = null;

	private FileWriter fw = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_timeStamp = (TextView)findViewById(R.id.textView_timeStamp);
		///tv_acoustic = (TextView)findViewById(R.id.textView_acoustic);
		//tv_pressure = (TextView)findViewById(R.id.textView_pressure);
		tv_acceleration = (TextView)findViewById(R.id.textView_acceleration);

		//create instance of sensor manager and get system service to interact with Sensor
		sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);

		//al_SensorValues = new ArrayList<String>();



		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/Acc_Sensor_Files");

		boolean var = false;
		if (!folder.exists())
			var = folder.mkdir();

		System.out.println("" + var);


		filename = folder.toString() + "/" + "Acc_Data_"+ System.currentTimeMillis() + ".csv";
		try {
			fw = new FileWriter(filename);
			fw.append("TimeStamp" +
					//"," + "SensorType" +
					"," + "X_Magnitude" +
					"," + "Y_Magnitude" +
					//"," + "Data3" +
					"," + "Z_Magnitude");
			fw.append("\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{

		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		// register this class as a listener for the Pressure Sensor
		//sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("onDestroy", "Destroy method Called0000000000000000000000");
		commitFile();
	}

	public void getInfoClickied(View v){
		commitFile();
	}

	public void commitFile(){
		//tv_timeStamp.setText(System.currentTimeMillis()+"");
		Log.i("commitedFile", "FileDoneeeeeeeeee1111111111111");
		sensorManager.unregisterListener(this);
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        android.os.Process.killProcess(android.os.Process.myPid());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		//int SensorType = event.sensor.getType();

		//Log.i("onSensorChanged", SensorType +"");
		// TODO Auto-generated method stub
		if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
			float[] values = event.values;
			tv_pressure.setText("" + values[0]);
			//al_SensorValues.add(System.currentTimeMillis() + "," + event.sensor.getType() + "," + values[0]);
			try {
				fw.append(System.currentTimeMillis() + "," +
						event.sensor.getType() + "," +
						values[0]);
				fw.append("\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            tv_timeStamp.setText(""+System.currentTimeMillis());
			float[] values = event.values;
			// Movement
			float x = values[0];
			float y = values[1];
			float z = values[2];
			//get acceleration 
			float accelationSquareRoot = (x * x + y * y + z * z)
					/ (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
			//if(accelationSquareRoot >=2){
				tv_acceleration.setText(""+accelationSquareRoot);
						//x + ":" + y + ":" + z + ":" +

			//}
			/*al_SensorValues.add(System.currentTimeMillis() + "," 
					+ event.sensor.getType() + "," 
					+ x + "," 
					+ y + "," 
					+ z + "," 
					+ accelationSquareRoot);*/
			try {
				fw.append(System.currentTimeMillis() + "," 
						//+ event.sensor.getType() + ","
						+ x + "," 
						+ y + "," 
						+ z
						//+ "," + accelationSquareRoot
				);
				fw.append("\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void writeToFile(String s) throws IOException {

	}


	/*public void exportEmailInCSV() throws IOException {
		{

			File folder = new File(Environment.getExternalStorageDirectory()
					+ "/Folder");

			boolean var = false;
			if (!folder.exists())
				var = folder.mkdir();

			System.out.println("" + var);


			final String filename = folder.toString() + "/" + "Test.csv";

			// show waiting screen
			CharSequence contentTitle = getString(R.string.app_name);
			final ProgressDialog progDailog = ProgressDialog.show(this, contentTitle, "even geduld aub...",true);//please wait
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {




				}
			};

			new Thread() {
				public void run() {
					try {

						FileWriter fw = new FileWriter(filename);

						// Cursor cursor = db.selectAll();

						fw.append("No");
						fw.append(',');

						fw.append("code");
						fw.append(',');

						fw.append("nr");
						fw.append(',');

						fw.append("Orde");
						fw.append(',');

						fw.append("Da");
						fw.append(',');

						fw.append("Date");
						fw.append(',');

						fw.append("Leverancier");
						fw.append(',');

						fw.append("Baaln");
						fw.append(',');

						fw.append("asd");
						fw.append(',');

						fw.append("Kwaliteit");
						fw.append(',');

						fw.append("asd");
						fw.append(',');

						fw.append('\n');

						if (cursor.moveToFirst()) {
							do {
								fw.append(cursor.getString(0));
								fw.append(',');

								fw.append(cursor.getString(1));
								fw.append(',');

								fw.append(cursor.getString(2));
								fw.append(',');

								fw.append(cursor.getString(3));
								fw.append(',');

								fw.append(cursor.getString(4));
								fw.append(',');

								fw.append(cursor.getString(5));
								fw.append(',');

								fw.append(cursor.getString(6));
								fw.append(',');

								fw.append(cursor.getString(7));
								fw.append(',');

								fw.append(cursor.getString(8));
								fw.append(',');

								fw.append(cursor.getString(9));
								fw.append(',');

								fw.append(cursor.getString(10));
								fw.append(',');

								fw.append('\n');

							} while (cursor.moveToNext());
						}
						if (cursor != null && !cursor.isClosed()) {
							cursor.close();
						}

						// fw.flush();
						fw.close();

					} catch (Exception e) {
					}
					handler.sendEmptyMessage(0);
					progDailog.dismiss();
				}
			}.start();

		}

	}*/

}
