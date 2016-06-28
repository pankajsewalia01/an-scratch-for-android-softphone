package me.cs.oicalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class OutgoingCallListener extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
		//if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            setResultData(null);
            // start other activities
         //}
	}
}
