package com.bmob.demo.third;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Application;

/**
 * @class BmobApplication
 * @author smile
 * @date 2015-6-2 上午10:13:35
 * 
 */
public class BmobApplication extends Application {

	// IWXAPI 是第三方app和微信通信的openapi接口
	public static IWXAPI api;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		register2WX();
	}

	// 注册微信，相当于微信初始化操作
	public void register2WX() {
		api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID, true);
		api.registerApp(Constants.WEIXIN_APP_ID);
	}
}
