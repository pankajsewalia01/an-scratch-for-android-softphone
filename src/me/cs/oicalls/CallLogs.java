package me.cs.oicalls;

import java.util.Date;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CallLogs extends ActionBarActivity {


	 TextView textView = null;

	 public String username = null;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_call_logs);
       Intent intent = getIntent();
       username = intent.getStringExtra("username");
       textView = (TextView) findViewById(R.id.textview_call);
       getCallDetails();
   }
   @Override
   public void onBackPressed() {
   }
   private void getCallDetails() { 
   	StringBuffer sb = new StringBuffer(); 
   	Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null); 
   	int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER); 
   	int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE); 
   	int date = managedCursor.getColumnIndex(CallLog.Calls.DATE); 
   	int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION); 
   	sb.append("Call Log :");
   	while (managedCursor.moveToNext()) { 
   		String phNumber = managedCursor.getString(number);
   		String callType = managedCursor.getString(type); 
   		String callDate = managedCursor.getString(date); 
   		Date callDayTime = new Date(Long.valueOf(callDate));
   		String callDuration = managedCursor.getString(duration); 
   		String dir = null; 
   		int dircode = Integer.parseInt(callType); 
   		
   		switch (dircode) { 
   		case CallLog.Calls.OUTGOING_TYPE: dir = "OUTGOING";
   		break; 
   		case CallLog.Calls.INCOMING_TYPE: dir = "INCOMING"; 
   		break; 
   		case CallLog.Calls.MISSED_TYPE: dir = "MISSED"; 
   		break; 
   		} 
   		sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- " + dir + " \nCall Date:--- " + callDayTime + " \nCall duration in sec :--- " + callDuration); 
   		sb.append("\n----------------------------------"); } 
   	//managedCursor.close(); 
   		textView.setText(sb); 
   }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.call_logs, menu);
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
