package me.cs.oicalls;

import java.util.HashMap;

import org.mobicents.restcomm.android.sipua.SipProfile;
import org.mobicents.restcomm.android.sipua.impl.DeviceImpl;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CallOut extends ActionBarActivity {

	public long startTime = 0;
	String username = null;
	SipProfile sipProfile = null;
	long endTime = 0;
	TextView countDown;
	private static final String FORMAT = "%02d:%02d:%02d";
	public static boolean flag = false;
	Intent intent;
	DeviceImpl device = DeviceImpl.GetInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_out);
		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		
		/*countDown = (TextView)findViewById(R.id.countDown);		
		new CountDownTimer(16069000, 1000) {			
		     public void onTick(long millisUntilFinished) {
		    	// if(((16069000-millisUntilFinished)/1000) >45){
			    	 long seconds= ((16069000-millisUntilFinished)/1000);
				     countDown.setText("seconds: " + seconds);		    		 
		    	// }
		     }

		     public void onFinish() {
		    	 countDown.setText("done!");
		     }
		  }.start();  */		
		
		intent = getIntent();
		String callPhoneNum = intent.getStringExtra("phoneNumber");		
		Log.i("tag", "callPhoneNum:  "+callPhoneNum);
		
		username = intent.getStringExtra("username");
		startTime = intent.getLongExtra("startTime", 0);
		device.Register();
		/*if(callPhoneNum.startsWith("+880") || callPhoneNum.startsWith("880") ||callPhoneNum.startsWith("00880")){
			DeviceImpl.GetInstance().Call("sip:"+Utility.getPhoneNumberBD(callPhoneNum)+username+"@104.238.74.138");
			((TextView)(findViewById(R.id.callingPhoneNumberButton))).setText(Utility.getPhoneNumberBD(callPhoneNum));
			
			
		}else */
		HashMap<String,String> hm = new HashMap<String,String>();
		if(callPhoneNum.startsWith("+91") ||callPhoneNum.startsWith("91")||callPhoneNum.startsWith("0091")){
			//Log.i("tag", "sip:"+Utility.getPhoneNumber(callPhoneNum)+"@50.62.133.54");
			device.Call("sip:"+Utility.getPhoneNumber(callPhoneNum)+"@sip.flowroute.com",hm);
			((TextView)(findViewById(R.id.callingPhoneNumberButton))).setText(Utility.getPhoneNumber(callPhoneNum));
		}else{
			Toast.makeText(getBaseContext(), "please correct number in international format", Toast.LENGTH_LONG).show();
		}
	}
	
	public void endCall(View view){
		endTime = System.currentTimeMillis();
		int seconds = (int) ((endTime-startTime)/1000);
		String date= null;
		Utility.updateBalance(seconds,this.username,date);
		try{
			//DeviceImpl.GetInstance().GetSipManager().RejectCall();
			device.GetSipManager().Hangup();	
			//DeviceImpl.GetInstance().GetSipManager().Cancel();
		}catch(Exception e){
			e.printStackTrace();
		}
		finish();
	}
	@Override
	public void onBackPressed() {
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.call_out, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
