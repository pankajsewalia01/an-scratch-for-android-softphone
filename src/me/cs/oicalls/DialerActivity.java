package me.cs.oicalls;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.mobicents.restcomm.android.sipua.impl.SipManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DialerActivity extends ActionBarActivity {

	private static final int PICK_CONTACT = 0;
	public String pretext = null;
	private long endTime = 0;
	//long startTime = 0;
	long call_duration =0;
	boolean durationFlag = true;
	SipManager mSipManager = null;
	public static String message = null;
	public static String Status = "calling to ";
	public static int Zero = 0;
	public static long startTime= 0;
	//CallLogs logs = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialer);
		//DeviceImpl.GetInstance().Call("sip:2600@"+MainActivity.ipString);
		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		Intent intent = getIntent();
		message = intent.getStringExtra(MainActivity.LOGIN_USERNAME);
		System.out.println("message  : "+message);
		/*TextView textView = new TextView(this);
		textView.setTextSize(20);
		textView.setText(message);
		setContentView(textView);	*/
		TextView textView = (TextView) findViewById(R.id.check_bal);
		textView.setTextSize(15);
		textView.setText(balanceCheck()+" min.");
		//DeviceImpl.GetInstance().GetSipManager().getSipManagerState();
		//logs = new CallLogs();
		//logs.updateBal();
	}

	public void logout(View view){
		//ignoreCall();
		//exitCleanly();
    	ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
    	postParams.add(new BasicNameValuePair("username", message));
    	String response = null;
    	String res= null;
    	try{
    	    response = CustomHttpClient.executeHttpPost("http://104.238.74.138:80/android/index1.php", postParams);
    	    res=response.toString();
    	  //  System.out.println("res  :"+ res);
    	    res = res.trim();
    	    res= res.replaceAll("\\s+","");  
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		finish();
	}
	public void CallIdClick(View view){
		/*Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);  
		startActivityForResult(intent, 1); */ 
		//Intent it= new Intent(Intent.ACTION_PICK,  Contacts.CONTENT_URI);

        //startActivityForResult(it, PICK_CONTACT);
	}
	@Override
	public void onBackPressed() {
	}
	public void ClearNum(View view){
		((TextView)(findViewById(R.id.call_number))).setText("");		
		//DeviceImpl.GetInstance().GetSipManager().RejectCall();

    	//ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
    	//postParams.add(new BasicNameValuePair("username", message));
    	//String response = null;
    	//String res= null;
		//long minutes=0;
		//long seconds=0;
		/*if(DeviceImpl.GetInstance().GetSipManager().getSipEvent().equals("BYE") ||
				DeviceImpl.GetInstance().GetSipManager().getSipEvent().equals("DECLINE")){*/
				/*endTime =  System.currentTimeMillis();	
				call_duration = endTime - startTime;
				if(durationFlag){
					seconds = call_duration/1000;
					System.out.println("seconds :  "+seconds);
					if(seconds>60)
						minutes = (seconds/60)+1;
					else
						minutes=1;			
					durationFlag = false;*/
		//}	
		//float costOfTheCall = (float) (minutes*12.35);
		//String strCost = String.valueOf(costOfTheCall);
		//System.out.println("strCost:  "+strCost+" message:  "+message);
		/*String minutesCall = String.valueOf(minutes);
    	postParams.add(new BasicNameValuePair("minutes", minutesCall));
    	try{    		
    	    response = CustomHttpClient.executeHttpPost("http://50.62.133.54:80/android/index3.php", postParams);
    	    res=response.toString();
    	    System.out.println("res  :"+ res);
    	    res = res.trim();
    	    res= res.replaceAll("\\s+","");  
    	}catch(Exception e){
    		e.printStackTrace();
    	}*/
    //	minutes = DeviceImpl.GetInstance().GetSipManager().minutesCalled();
    	//System.out.println("minutes    :"+minutes);
	}
	public String balanceCheck(){

    	ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
    	postParams.add(new BasicNameValuePair("username", message));
    	String response = null;
    	String res= null;
		try{    		
    	    response = CustomHttpClient.executeHttpPost("http://104.238.74.138:80/android/index2.php", postParams);
    	    res=response.toString();
    	    
    	   // System.out.println("res111 :"+ res);
    	    res = res.trim();
    	    res= res.replaceAll("\\s+","");  
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return res;
	}
	public void CallNum(View view){
		/*Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null); 
		int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
		int date = managedCursor.getColumnIndex(CallLog.Calls.DATE); 
     	int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE); 
		String callDuration =null;   	
		if(managedCursor.moveToLast()){
	   		String callType = managedCursor.getString(type); 
	   		int dircode = Integer.parseInt(callType); 
	   		String dir = null;    		
	   		switch (dircode) { 
	   		case CallLog.Calls.OUTGOING_TYPE: dir = "OUTGOING";
	   		break; 
	   		case CallLog.Calls.INCOMING_TYPE: dir = "INCOMING"; 
	   		break; 
	   		case CallLog.Calls.MISSED_TYPE: dir = "MISSED"; 
	   		break; 
	   		} 
	if( dir.equalsIgnoreCase("OUTGOING")){
				callDuration = managedCursor.getString(duration); 
				String callDate = managedCursor.getString(date); 
				Utility.updateBalance(Long.valueOf(callDuration),message,callDate);				
			}
		}*/
		int balance = Integer.parseInt(balanceCheck());
		if(balance <= Zero){
			finish();
		}
		TextView textView = (TextView) findViewById(R.id.check_bal);
		textView.setTextSize(15);
		textView.setText(balanceCheck()+" min.");
		
		
		startTime = (long) System.currentTimeMillis();
		String phonenum =(String) ((TextView)(findViewById(R.id.call_number))).getText();		
		if(phonenum != null || phonenum != ""){
			Intent intent = new Intent(this,CallOut.class);
			intent.putExtra("phoneNumber", phonenum);
			intent.putExtra("startTime", startTime);
			intent.putExtra("username",this.message);
			startActivity(intent);
		}else return;
		
	}
	public void DialButton(View view){
		Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		switch(view.getId()){
			case R.id.one_button:
				vibe.vibrate(100);
				if(findViewById(R.id.call_number).equals(null))
					((TextView)(findViewById(R.id.call_number))).setText("1");
				else{
					pretext = (String) ((TextView)(findViewById(R.id.call_number))).getText();
					((TextView)(findViewById(R.id.call_number))).setText(pretext.concat("1"));
				}
			break;
			case R.id.two_button:
				vibe.vibrate(100);
				if(findViewById(R.id.call_number).equals(null))
					((TextView)(findViewById(R.id.call_number))).setText("2");
				else{
					pretext = (String) ((TextView)(findViewById(R.id.call_number))).getText();
					((TextView)(findViewById(R.id.call_number))).setText(pretext.concat("2"));
				}
			break;
			case R.id.three_button:
				vibe.vibrate(100);
				if(findViewById(R.id.call_number).equals(null))
					((TextView)(findViewById(R.id.call_number))).setText("3");
				else{
					pretext = (String) ((TextView)(findViewById(R.id.call_number))).getText();
					((TextView)(findViewById(R.id.call_number))).setText(pretext.concat("3"));
				}
			break;
			case R.id.four_button:
				vibe.vibrate(100);
				if(findViewById(R.id.call_number).equals(null))
					((TextView)(findViewById(R.id.call_number))).setText("4");
				else{
					pretext = (String) ((TextView)(findViewById(R.id.call_number))).getText();
					((TextView)(findViewById(R.id.call_number))).setText(pretext.concat("4"));
				}
			break;
			
			case R.id.five_button:
				vibe.vibrate(100);
				if(findViewById(R.id.call_number).equals(null))
					((TextView)(findViewById(R.id.call_number))).setText("5");
				else{
					pretext = (String) ((TextView)(findViewById(R.id.call_number))).getText();
					((TextView)(findViewById(R.id.call_number))).setText(pretext.concat("5"));
				}
			break;
			
			case R.id.six_button:
				vibe.vibrate(100);
				if(findViewById(R.id.call_number).equals(null))
					((TextView)(findViewById(R.id.call_number))).setText("6");
				else{
					pretext = (String) ((TextView)(findViewById(R.id.call_number))).getText();
					((TextView)(findViewById(R.id.call_number))).setText(pretext.concat("6"));
				}
			break;
			
			case R.id.seven_button:
				vibe.vibrate(100);
				if(findViewById(R.id.call_number).equals(null))
					((TextView)(findViewById(R.id.call_number))).setText("7");
				else{
					pretext = (String) ((TextView)(findViewById(R.id.call_number))).getText();
					((TextView)(findViewById(R.id.call_number))).setText(pretext.concat("7"));
				}
			break;
			
			case R.id.eight_button:
				vibe.vibrate(100);
				if(findViewById(R.id.call_number).equals(null))
					((TextView)(findViewById(R.id.call_number))).setText("8");
				else{
					pretext = (String) ((TextView)(findViewById(R.id.call_number))).getText();
					((TextView)(findViewById(R.id.call_number))).setText(pretext.concat("8"));
				}
			break;
			
			case R.id.nine_button:
				vibe.vibrate(100);
				if(findViewById(R.id.call_number).equals(null))
					((TextView)(findViewById(R.id.call_number))).setText("9");
				else{
					pretext = (String) ((TextView)(findViewById(R.id.call_number))).getText();
					((TextView)(findViewById(R.id.call_number))).setText(pretext.concat("9"));
				}
			break;
			
			case R.id.zero_button:
				vibe.vibrate(100);
				if(findViewById(R.id.call_number).equals(null))
					((TextView)(findViewById(R.id.call_number))).setText("0");
				else{
					pretext = (String) ((TextView)(findViewById(R.id.call_number))).getText();
					((TextView)(findViewById(R.id.call_number))).setText(pretext.concat("0"));
				}
			break;
			
			case R.id.asterisk_button:
				vibe.vibrate(100);
				if(findViewById(R.id.call_number).equals(null))
					((TextView)(findViewById(R.id.call_number))).setText("*");
				else{
					pretext = (String) ((TextView)(findViewById(R.id.call_number))).getText();
					((TextView)(findViewById(R.id.call_number))).setText(pretext.concat("*"));
				}
			break;
			
			case R.id.hash_button:
				vibe.vibrate(100);
				if(findViewById(R.id.call_number).equals(null))
					((TextView)(findViewById(R.id.call_number))).setText("#");
				else{
					pretext = (String) ((TextView)(findViewById(R.id.call_number))).getText();
					((TextView)(findViewById(R.id.call_number))).setText(pretext.concat("#"));
				}
			break;
		}
	}
	

    public void pickContacts(View v){
         Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
         startActivityForResult(intent, PICK_CONTACT);
     }

    @Override 
    public void onActivityResult(int reqCode, int resultCode, Intent data){
    	super.onActivityResult(reqCode, resultCode, data);
    	switch(reqCode){
    		case (PICK_CONTACT):
    			if (resultCode == Activity.RESULT_OK){
    				Uri contactData = data.getData();
    				Cursor c = managedQuery(contactData, null, null, null, null);
    				if (c.moveToFirst()){
    					String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
    					String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

    					if (hasPhone.equalsIgnoreCase("1")){
    						Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, 
    						ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
    						phones.moveToFirst();
    						String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
           // Toast.makeText(getApplicationContext(), cNumber, Toast.LENGTH_SHORT).show();
            //setCn(cNumber);
            //System.out.println("cNumber: " + cNumber);
    						((TextView)(findViewById(R.id.call_number))).setText(cNumber);
			//DeviceImpl.GetInstance().Call("sip:"+Utility.getPhoneNumer(cNumber)+"@50.62.133.54");
          }
         }
       }
    }
   }
    
    public void callLogs(View view){
    	/*Intent intent = new Intent(this,CallLogs.class);
    	intent.putExtra("username", message);
    	startActivity(intent);*/
    }
    

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
        if (id == R.id.action_settings) {
        	Logout();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}

	private void Logout() {
		// TODO Auto-generated method stub
		finish();
		
	}
}
