/**
 */
package com.syntonize;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.onspota.sdk.model.Event;
import com.onspota.sdk.model.PlacesListItem;
import com.onspota.sdk.model.SearchResponse;
import com.onspota.sdk.model.SpotSearchResponse;
import com.onspota.sdk.OnspotaApi;

import java.util.ArrayList;
import java.util.List;


public class OnSpota extends CordovaPlugin {
  private static final String TAG = "OnSpota";

  //declare each plugin method
  private static final String INIT_TRACKER = "initTracker";
  private static final String SUSPEND_TRACKER = "suspendTracker";
  private static final String SET_USERID = "setUserId";
  private static final String SET_APIHOST = "setApiHost";
  private static final String RESUME_TRACKER = "resumeTracker";

  private OnspotaApi OnSpotaSDK;

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    Log.d(TAG, "Starting OnSpota plugin");

    OnSpotaSDK = new OnspotaApi(this.cordova.getContext());

  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

    if(INIT_TRACKER.equals(action)) {
        this.initTracker(callbackContext, args.getString(0));

    }else if (SUSPEND_TRACKER.equals(action)){
        this.suspendTracker(callbackContext);

    }else if (SET_USERID.equals(action)){
        this.setUser(callbackContext, args.getString(0));

    }else if (SET_APIHOST.equals(action)){
        this.setHost(callbackContext, args.getString(0));

    }else if (RESUME_TRACKER.equals(action)){
        this.resumeTracker(callbackContext);
    }
    return true;
  }


  private void initTracker(final CallbackContext callbackContext, final String appID){

      OnspotaApi.SdkResult sdkResult = OnSpotaSDK.start(appID);

      String msg = "";

      if (sdkResult == OnspotaApi.SdkResult.Ok) {
          // SDK was started.
          msg = "OnSpota SDK started with ID:" + appID;
      }
      else if (sdkResult == OnspotaApi.SdkResult.AndroidVersionNotSupported) {
          Log.w(TAG,"OnSpota SDK doesn't support current android os");

          msg = "OnSpota SDK doesn't support current android os";
      }
      else if (sdkResult == OnspotaApi.SdkResult.FailedToFind3rdPartyLib) {
          Log.e(TAG,"Critical error: OnSpota SDK Failed to find at least one of its required dependencies.");

          msg = "Critical error: OnSpota SDK Failed to find at least one of its required dependencies.";
      }
      else if (sdkResult == OnspotaApi.SdkResult.Failed) {
          Log.e(TAG,"OnSpota SDK Failed to start.");

          msg = "OnSpota SDK Failed to start.";
      }



      JSONObject jsonOut = new JSONObject();
      try{
          jsonOut.put("msg",msg);
      }catch (JSONException e){
          //
      }

    if (sdkResult == OnspotaApi.SdkResult.Ok){
        this.callbackSuccess(callbackContext, jsonOut);
    }else{
        this.callbackError(callbackContext, msg);
    }
  }

    private void suspendTracker(final CallbackContext callbackContext){

        OnSpotaSDK.stop();

        JSONObject jsonOut = new JSONObject();
        try{
            jsonOut.put("msg","OK");
        }catch (JSONException e){
            //
        }

        this.callbackSuccess(callbackContext, jsonOut);
    }
    private void setUser(final CallbackContext callbackContext, String userID){

        OnSpotaSDK.updateUserId(this.cordova.getContext(), userID);

        String msg = "User updated to:" + userID;
        JSONObject jsonOut = new JSONObject();
        try{
            jsonOut.put("msg",msg);
        }catch (JSONException e){
            //
        }

        this.callbackSuccess(callbackContext, jsonOut);
    }

    private void setHost(final CallbackContext callbackContext, String host){

        JSONObject jsonOut = new JSONObject();
        try{
            jsonOut.put("msg","NOT_IMPLEMENTED");
        }catch (JSONException e){
            //
        }

        this.callbackError(callbackContext, jsonOut);
    }

    private void resumeTracker(final CallbackContext callbackContext){

        JSONObject jsonOut = new JSONObject();
        try{
            jsonOut.put("msg","NOT_IMPLEMENTED");
        }catch (JSONException e){
            //
        }

        this.callbackError(callbackContext, jsonOut);
    }

    // @Override
    public void onResume(Boolean multitask) {
        super.onResume(multitask);

        cordova.getContext().registerReceiver(mEventReceiver, new IntentFilter(cordova.getContext().getString(com.onspota.sdk.R.string.intent_search)));
    }
    // @Override
    public void onPause(Boolean multitask) {
        super.onPause(multitask);
        cordova.getContext().unregisterReceiver(mEventReceiver);
    }

    private BroadcastReceiver mEventReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SearchResponse searchResponse = (SearchResponse) intent.getSerializableExtra(cordova.getContext().getString(com.onspota.sdk.R.string.intent_search_response));
            String searchReason = intent.getStringExtra(cordova.getContext().getString(com.onspota.sdk.R.string.intent_search_reason));
            Log.i(TAG, ".onReceive: searchResponse = " + searchResponse + ", searchReason = " + searchReason);

        }
    };


    private static void callbackSuccess(CallbackContext callbackContext, JSONObject jsonObject) {
        if (jsonObject == null) // in case there are no data
            jsonObject = new JSONObject();

        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, jsonObject);
        pluginResult.setKeepCallback(true);
        callbackContext.sendPluginResult(pluginResult);
    }

    private static void callbackError(CallbackContext callbackContext, JSONObject jsonObject) {
        if (jsonObject == null) // in case there are no data
            jsonObject = new JSONObject();

        PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, jsonObject);
        pluginResult.setKeepCallback(true);
        callbackContext.sendPluginResult(pluginResult);
    }

    private static void callbackError(CallbackContext callbackContext, String str) {
        PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, str);
        pluginResult.setKeepCallback(true);
        callbackContext.sendPluginResult(pluginResult);
    }

    @Override
    public void onDestroy() {
        //
    }


}
