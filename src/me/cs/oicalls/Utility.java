package me.cs.oicalls;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class Utility {

	public static String getPhoneNumber(String phoneNumber){
		String phoneNum = phoneNumber.substring(phoneNumber.length()-10);
		phoneNum = "+91"+phoneNum;
		return phoneNum;
	}
	public static String getPhoneNumberBD(String phoneNumber){
		String phoneNum = phoneNumber.substring(phoneNumber.length()-10);
		phoneNum = "+880"+phoneNum;
		return phoneNum;
	}
	

	public static void updateBalance(long seconds,String username,String date){
		ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("username", username));
    	String response = null;
    	String res= null;
    	int minutes=0;
    	if(seconds>60){
    		minutes = (int) ((seconds/60)+1);
    	}/*else if(seconds<60 && seconds != 0 ){
    		minutes = 1;
    	}*/else{
    		minutes=0;
    	}
    	postParams.add(new BasicNameValuePair("minutes", String.valueOf(minutes)));
    	try{    		
    	    response = CustomHttpClient.executeHttpPost("http://104.238.74.138:80/android/index3.php", postParams);
    	    res=response.toString();
    	    System.out.println("res  :"+ res);
    	    res = res.trim();
    	    res= res.replaceAll("\\s+","");  
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		
	}
}
