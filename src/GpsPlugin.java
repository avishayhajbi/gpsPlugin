package avishayhajbi.gpsplugin;
//notification
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaActivity;
import org.json.JSONArray;
import org.json.JSONException;
//json parse
import org.json.JSONObject;
//dialog
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import android.app.Activity;
import android.provider.Settings;

import android.annotation.TargetApi;
import android.util.Log;


public class GpsPlugin extends CordovaPlugin{

    static boolean dialogExist=false;
    static LocationManager locationManager=null;

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if(action.equals("gps")){
        	final String status = args.getString(0); 
          	cordova.getActivity().runOnUiThread(new Runnable() { //cordova.getThreadPool().execute(new Runnable() {
	        	public void run() {
                    locationManager = (LocationManager) cordova.getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                    Toast toast=null;
                   
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        toast = android.widget.Toast.makeText(webView.getContext(), "GPS is Enabled in your device", 0);
                        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 20);
                        toast.setDuration(android.widget.Toast.LENGTH_LONG);
                        //toast.show();
						
                        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                        	webView.reload();
                        }
                        else {
                            toast = android.widget.Toast.makeText(webView.getContext(), "NETWORK is disabled in your device. Enable it!", 0);
                            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 20);
                            toast.setDuration(android.widget.Toast.LENGTH_LONG);
                            toast.show();
                        }
                        
                    }else{
	                    if (!dialogExist){
	                    	dialogExist=true;
	                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cordova.getActivity());
	                        alertDialogBuilder
	                                .setMessage("GPS is disabled in your device. Enable it?")
	                                .setCancelable(false)
	                                .setPositiveButton("Enable GPS",
	                                        new DialogInterface.OnClickListener() {
	                                            public void onClick(DialogInterface dialog,int id) {
	                                                Intent callGPSSettingIntent = new Intent(
	                                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                                                ((CordovaActivity)cordova.getActivity()).startActivity(callGPSSettingIntent);
	                                            }
	                                        });
	                        alertDialogBuilder.setNegativeButton("Cancel",
	                                new DialogInterface.OnClickListener() {
	                                    public void onClick(DialogInterface dialog, int id) {
	                                        dialogExist=false;
	                                        dialog.cancel();
	                                    }
	                                });
	                        AlertDialog alert = alertDialogBuilder.create();
	                        alert.show();
	                    }
                        
                    }
                    callbackContext.success("gps info");
	            }
            });
            return true;
        }
      	callbackContext.error("unknown");
      	return false;
    }

}
