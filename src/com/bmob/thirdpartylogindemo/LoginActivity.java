package com.bmob.thirdpartylogindemo;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends Activity implements OnClickListener{
	
	private String APPID = "";//--外网


	EditText et_account,et_pwd;
	TextView tv_weibo,tv_qq;
	
	Button btn_login,btn_register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//请自行初始化Bmob的ApplicationId
		Bmob.initialize(this, APPID);
		toast("请自行初始化Bmob的ApplicationId");
		initView();
		BmobUser user = BmobUser.getCurrentUser(this);
		if(user!=null){
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
		}
	}

	public void initView(){
		et_account = (EditText)findViewById(R.id.et_account);
		et_pwd = (EditText)findViewById(R.id.et_pwd);
		tv_weibo = (TextView)findViewById(R.id.tv_weibo);
		tv_qq = (TextView)findViewById(R.id.tv_qq);
		btn_login = (Button)findViewById(R.id.btn_login);
		btn_register = (Button)findViewById(R.id.btn_register);
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		tv_weibo.setOnClickListener(this);
		tv_qq.setOnClickListener(this);
	}

//	JSONObject weiboObj = new JSONObject();
	
	String account,pwd;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login://登陆
			account = et_account.getText().toString().trim();
			pwd = et_pwd.getText().toString().trim();
			if(account.equals("")){
				toast("填写你的用户名");
				return;
			}
			
			if(pwd.equals("")){
				toast("填写你的密码");
				return;
			}
			BmobUser user = new BmobUser();
			user.setUsername(account);
			user.setPassword(pwd);
			user.login(this, new SaveListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					toast("登陆失败："+arg1);
				}
			});
			break;
			
		case R.id.btn_register://注册
			account= et_account.getText().toString().trim();
			pwd = et_pwd.getText().toString().trim();
			if(account.equals("")){
				toast("填写你的用户名");
				return;
			}
			if(pwd.equals("")){
				toast("填写你的密码");
				return;
			}
			BmobUser u = new BmobUser();
			u.setUsername(account);
			u.setPassword(pwd);
			u.setEmail("123456@qq.com"); 
			u.signUp(this, new SaveListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					toast("注册失败："+arg1);
				}
			});
			break;
			
		case R.id.tv_weibo:
			BmobUser.weiboLogin(this, "1485273395", "http://www.bmob.cn", new OtherLoginListener() {
				
				@Override
				public void onSuccess(JSONObject userAuth) {
					// TODO Auto-generated method stub
					toast("weibo登陆成功返回:"+userAuth);
					Log.i("login", "weibo登陆成功返回:"+userAuth.toString());
//							{
//								  "weibo": {
//								    "uid": "2696876973", 
//								    "access_token": "2.00htoVwCV9DWcB02e14b7fa50vUwjg", 
//								    "expires_in": 1410461999162
//								  }
//							}
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					intent.putExtra("json", userAuth.toString());
					intent.putExtra("from", "weibo");
					startActivity(intent);
				}
				
				@Override
				public void onFailure(int code, String msg) {
					// TODO Auto-generated method stub
					//若出现授权失败(authData error)，可清除该应用缓存，之后在授权新浪登陆
					toast("第三方登陆失败："+msg);
				}
			});
			
			break;
		case R.id.tv_qq:
			//222222--appid,此为腾讯官方提供的AppID,个人开发者需要去QQ互联官网为自己的应用申请对应的AppId
			BmobUser.qqLogin(this, "222222", new OtherLoginListener() {
				
				@Override
				public void onSuccess(JSONObject userAuth) {
					// TODO Auto-generated method stub
					toast("QQ登陆成功返回:"+userAuth.toString());
					Log.i("login", "QQ登陆成功返回:"+userAuth.toString());
					//下面则是返回的json字符
//					{
//						  "qq": {
//						    "openid": "B4F5ABAD717CCC93ABF3BF28D4BCB03A", 
//						    "access_token": "05636ED97BAB7F173CB237BA143AF7C9", 
//						    "expires_in": 7776000
//						  }
//					}
					//如果你想在登陆成功之后关联当前用户
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					intent.putExtra("json", userAuth.toString());
					intent.putExtra("from", "qq");
					startActivity(intent);
				}
				
				@Override
				public void onFailure(int code, String msg) {
					// TODO Auto-generated method stub
					toast("第三方登陆失败："+msg);
				}
			});
			break;

		default:
			break;
		}
	}
	
	private void toast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}
