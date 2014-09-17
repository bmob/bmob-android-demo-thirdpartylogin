package com.bmob.thirdpartylogindemo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.tauth.IRequestListener;
import com.tencent.utils.HttpUtils.HttpStatusException;
import com.tencent.utils.HttpUtils.NetworkUnavailableException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class BaseApiListener implements IRequestListener {

	private String mScope = "all";
	private Boolean mNeedReAuth = false;
	private Activity mActivity;
	public BaseApiListener(String scope, boolean needReAuth,Activity activity) {
		mScope = scope;
		mNeedReAuth = needReAuth;
		mActivity = activity;
	}
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			Bundle params = msg.getData();
			String title = params.getString("title");
			String response = params.getString("response");
			Util.showResultDialog(mActivity, response, title);
			Log.d("bmob", "title "+title+"  response "+response);
			super.handleMessage(msg);
			Util.dismissDialog();
		}
		
	};
	
	@Override
	public void onComplete(final JSONObject response) {
		Message msg = new Message();
		Bundle params = new Bundle();
		params.putString("response", response.toString());
		params.putString("title", "onComplete");
		msg.setData(params);
		mHandler.sendMessage(msg);
	}

	@Override
	public void onIOException(final IOException e) {
		Message msg = new Message();
		Bundle params = new Bundle();
		params.putString("response", e.getMessage());
		params.putString("title", "onIOException");
		msg.setData(params);
		mHandler.sendMessage(msg);
	}

	@Override
	public void onMalformedURLException(final MalformedURLException e) {
		Message msg = new Message();
		Bundle params = new Bundle();
		params.putString("response", e.getMessage());
		params.putString("title", "onMalformedURLException");
		msg.setData(params);
		mHandler.sendMessage(msg);
	}

	@Override
	public void onJSONException(final JSONException e) {
		Message msg = new Message();
		Bundle params = new Bundle();
		params.putString("response", e.getMessage());
		params.putString("title", "onJSONException");
		msg.setData(params);
		mHandler.sendMessage(msg);
	}

	@Override
	public void onConnectTimeoutException(ConnectTimeoutException e) {
		Message msg = new Message();
		Bundle params = new Bundle();
		params.putString("response", e.getMessage());
		params.putString("title", "onConnectTimeoutException");
		msg.setData(params);
		mHandler.sendMessage(msg);
	}

	@Override
	public void onSocketTimeoutException(SocketTimeoutException e) {
		Message msg = new Message();
		Bundle params = new Bundle();
		params.putString("response", e.getMessage());
		params.putString("title", "onSocketTimeoutException");
		msg.setData(params);
		mHandler.sendMessage(msg);
	}

	@Override
	public void onUnknowException(Exception e) {
		Message msg = new Message();
		Bundle params = new Bundle();
		params.putString("response", e.getMessage());
		params.putString("title", "onUnknowException");
		msg.setData(params);
		mHandler.sendMessage(msg);
	}

	@Override
	public void onHttpStatusException(HttpStatusException e) {
		Message msg = new Message();
		Bundle params = new Bundle();
		params.putString("response", e.getMessage());
		params.putString("title", "onHttpStatusException");
		msg.setData(params);
		mHandler.sendMessage(msg);
	}

	@Override
	public void onNetworkUnavailableException(NetworkUnavailableException e) {
		Message msg = new Message();
		Bundle params = new Bundle();
		params.putString("response", e.getMessage());
		params.putString("title", "onNetworkUnavailableException");
		msg.setData(params);
		mHandler.sendMessage(msg);
	}

	public String getmScope() {
		return mScope;
	}

	public void setmScope(String mScope) {
		this.mScope = mScope;
	}

	public Boolean getmNeedReAuth() {
		return mNeedReAuth;
	}

	public void setmNeedReAuth(Boolean mNeedReAuth) {
		this.mNeedReAuth = mNeedReAuth;
	}

	public Activity getmActivity() {
		return mActivity;
	}

	public void setmActivity(Activity mActivity) {
		this.mActivity = mActivity;
	}

}
