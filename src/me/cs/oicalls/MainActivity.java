package me.cs.oicalls;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import me.cs.oicalls.TableData.TableInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.mobicents.restcomm.android.sipua.SipProfile;
import org.mobicents.restcomm.android.sipua.impl.DeviceImpl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import de.mindpipe.android.logging.log4j.LogConfigurator;

public class MainActivity extends ActionBarActivity implements OnClickListener,OnSharedPreferenceChangeListener{

	private static final String TAG = "MainActivity";
	public final static String LOGIN_USERNAME = "me.cs.oicalls.username";
	public final static String ipString="*.*.*.*";
	private String eUsername = null;
	private String ePassword = null;
	SharedPreferences prefs;
	SipProfile sipProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);     
        try{
            LogConfigurator logConfigurator = new LogConfigurator();
            logConfigurator.setFileName(Environment.getExternalStorageDirectory()
                            + File.separator + "MyApp" + File.separator + "logs"
                            + File.separator + "log4j.txt");
            System.out.println("log file path"+Environment.getExternalStorageDirectory()
                    + File.separator + "MyApp" + File.separator + "logs"
                    + File.separator + "log4j.txt");
            logConfigurator.setRootLevel(Level.DEBUG);
            logConfigurator.setLevel("org.apache", Level.ERROR);
            logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
            logConfigurator.setMaxFileSize(1024 * 1024 * 5);
            logConfigurator.setImmediateFlush(true);
            logConfigurator.configure();
            Logger log = Logger.getLogger(MainActivity.class);
            log.info("My Application Created");
            log.info(Environment.getExternalStorageDirectory()
                            + File.separator + "MyApp" + File.separator + "logs"
                            + File.separator + "log4j.txt");
            

            HashMap<String, String> customHeaders = new HashMap<String, String>();
            customHeaders.put("customHeader1","customValue1");
            customHeaders.put("customHeader2","customValue2");
            
            sipProfile = new SipProfile();
    		DeviceImpl.GetInstance().Initialize(getApplicationContext(), sipProfile,customHeaders);
    		prefs = PreferenceManager.getDefaultSharedPreferences(this);
    		prefs.registerOnSharedPreferenceChangeListener(this);
    		initializeSipFromPreferences();
        	
        }catch(Exception e){
    		Toast.makeText(getBaseContext(), "Error ...please try again!", Toast.LENGTH_LONG).show();        	
        }
    }    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private int IntializeDBRequest(){
    	Log.d(TAG, "Inside IntializeDBRequest ");
    	ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
    	postParams.add(new BasicNameValuePair("username", eUsername));
    	postParams.add(new BasicNameValuePair("password", ePassword));
    	String response = null;
    	String res= null;
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
    	try{
    	    response = CustomHttpClient.executeHttpPost("http://104.238.74.138:80/android/index.php", postParams);
    	    res=response.toString();
    	    //Log.d(TAG, "response: "+res);
    	    res = res.trim();
    	    res= res.replaceAll("\\s+","");  
    	}catch(Exception e){
       	 Log.d(TAG, "Exception in IntializeDBRequest:  ",e);    		
    	}
    	 return Integer.parseInt(res);
    } 
    
    /** Called when the user clicks the SignIn Or Login button 
     * @param username */
    public void login(View view){
		try{
			eUsername = ((EditText) findViewById(R.id.username)).getText().toString();
			ePassword = ((EditText) findViewById(R.id.password)).getText().toString();
			if(checkCredential()){
    		//Log.d(TAG, "inside login successful....");

    			DatabaseOperation DB = new DatabaseOperation(this);
    			
    			//Clear SQLite Database Tablename
    			SQLiteDatabase sdb = DB.getWritableDatabase();
    			sdb.execSQL("DELETE FROM "+TableInfo.TABLE_NAME); //delete all rows in a table
    			sdb.close();
    			
    			DB.putInformation(DB, eUsername, ePassword);
    			DB.close();
    			Toast.makeText(getBaseContext(), "registration successful", Toast.LENGTH_LONG).show();

        		//DeviceImpl.GetInstance().Register();
        		//DeviceImpl.GetInstance().Call("sip:2600@"+ipString);
        		//Intent intent = new Intent(this, DialerActivity.class);
        		Intent intent = new Intent(this,DialerActivity.class);
        		intent.putExtra(LOGIN_USERNAME, eUsername);
            	startActivity(intent);
        	}else
        		Toast.makeText(getBaseContext(), "registration failed... wrong Credential or balance is less than 0!", Toast.LENGTH_LONG).show();
        	
    	}catch(Exception e){
    		Toast.makeText(getBaseContext(), "Error ...please try again!", Toast.LENGTH_LONG).show();	
    	}
    }     
    public void saveCreds(View view){
    	DatabaseOperation db = new DatabaseOperation(getBaseContext());
		Cursor cr = db.getInformation(db);	
		cr.moveToFirst();
		String username= cr.getString(cr.getColumnIndex(TableInfo.USER_NAME));
		String password = cr.getString(cr.getColumnIndex(TableInfo.USER_PASS));
		((EditText) findViewById(R.id.username)).setText(username);
		((EditText) findViewById(R.id.password)).setText(password);
    }
    private boolean checkCredential(){
    	if(eUsername != null && ePassword != null && IntializeDBRequest() == 1){
    		return true;
    	}else{
    		return false;
    	}
    } 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub		
	}

	@SuppressWarnings("static-access")
	private void initializeSipFromPreferences() {
		sipProfile.setRemoteIp((prefs.getString("pref_proxy_ip", "")));
		sipProfile.setRemotePort(Integer.parseInt(prefs.getString(
				"pref_proxy_port", "5060")));
		sipProfile.setSipUserName(prefs.getString("pref_sip_user", ""));
		sipProfile.setSipPassword(prefs
				.getString("pref_sip_password", ""));

	}
}
