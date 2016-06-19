package ttaiit.blogspot.com;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.PrintWriter;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MessageClient extends Activity implements OnClickListener , SensorEventListener , OnSeekBarChangeListener {
	Sensor accelerometer;
	SensorManager sm;
	TextView acceleration;
	EditText etMessage;
	Button bSend;
	Socket s = null;
	DataOutputStream dos = null;
	PrintWriter output = null;
	private SeekBar seekBar;
	private SeekBar seekBar2;
    int value;
	boolean vstart = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messageclient);
		sm = (SensorManager)getSystemService(SENSOR_SERVICE);
		accelerometer=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		acceleration=(TextView)findViewById(R.id.acceleration);
		etMessage=(EditText)findViewById(R.id.etMessage);
		bSend=(Button)findViewById(R.id.bSend);
		bSend.setOnClickListener(this);
		setupStopbutton();

        //set change listener
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar2.setOnSeekBarChangeListener(this);
	}
	private void setupStopbutton() {
		Button bStop = (Button)findViewById(R.id.bStop);
		bStop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				vstart = false;
				try {
					dos.flush();
					dos.close();
					s.close();
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
								
				
			}
		});
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Thread t= new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					s=new Socket(etMessage.getText().toString(),7000);
					dos=new DataOutputStream(s.getOutputStream());
					vstart = true;	
					
					output = new PrintWriter(dos);         
     
					//output.println("Hello from Android");    
								      

					
					//dos.writeUTF(etMessage.getText().toString());
					//dos.flush();
					//dos.close();
					//s.close();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
		};
		t.start();
		Toast.makeText(this, "This Message Sent!", Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub		
		if	(vstart)
		{
			
		
		
			output.println("pos,"+event.values[0]+","+event.values[2]);
			output.flush();
				
		//acceleration.setText("X: "+event.values[0]+	"\nY: "+event.values[1]+"\nZ: "+event.values[2]);
		
	}
		
	}
	
	//public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		
	//}
	//@Override
	//public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	//}
	//@Override
	//public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		//seekBar1.setProgress(50);
		//output.println("Z,50");
		//output.flush();
		
	//}
	
	//declare variables
           //The SeekBar value output
    
	@Override
    public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
		
		switch (seekBar.getId()) {

	    case R.id.seekBar1:
	    	value = progresValue;
	        output.println("R,"+value);
	  	  	output.flush();
	        break;

	    case R.id.seekBar2:
	    	value = progresValue;
	        output.println("Z,"+value);
	  	  	output.flush();
	        break;
	    }
    }
	
	@Override
    public void onStopTrackingTouch(SeekBar seekBar) {
      switch (seekBar.getId()) {
      case R.id.seekBar1:
    	seekBar.setProgress(50);
  		output.println("R,50");
  		output.flush();
  		break; 		
      
      }
		
    }
    //@Override
    //public void onProgressChanged(SeekBar seekBar, int progress, boolean arg2) {
        //value = progress;
        //output.println("Z,"+value);
		//output.flush();
    //}
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
    
    

}
