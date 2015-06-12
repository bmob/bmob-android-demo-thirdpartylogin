package com.bmob.demo.third;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.BmobUser.BmobThirdUserAuth;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.demo.third.net.NetUtils;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity implements OnClickListener {

	String json = "";
	String from = "";
	TextView tv_info;

	Button btn_relation_qq, btn_relation_weibo,btn_relation_weixin, btn_logout;
	
	Button btn_remove_qq, btn_remove_weibo,btn_remove_weixin;
	
	ImageView iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		json = getIntent().getStringExtra("json");
		from = getIntent().getStringExtra("from");
		iv_back = (ImageView) findViewById(R.id.iv_back);
		tv_info = (TextView) findViewById(R.id.tv_info);

		btn_relation_qq = (Button) findViewById(R.id.btn_relation_qq);
		btn_relation_qq.setOnClickListener(this);
		btn_relation_weibo = (Button) findViewById(R.id.btn_relation_weibo);
		btn_relation_weibo.setOnClickListener(this);
		btn_relation_weixin = (Button) findViewById(R.id.btn_relation_weixin);
		btn_relation_weixin.setOnClickListener(this);

		btn_logout = (Button) findViewById(R.id.btn_logout);
		btn_logout.setOnClickListener(this);
		// 取消关联
		btn_remove_weibo = (Button) findViewById(R.id.btn_remove_weibo);
		btn_remove_weibo.setOnClickListener(this);
		btn_remove_qq = (Button) findViewById(R.id.btn_remove_qq);
		btn_remove_qq.setOnClickListener(this);
		btn_remove_weixin = (Button) findViewById(R.id.btn_remove_weixin);
		btn_remove_weixin.setOnClickListener(this);

		iv_back.setOnClickListener(this);
		if (json != null && !json.equals("")) {
			btn_relation_qq.setVisibility(View.GONE);
			btn_relation_weibo.setVisibility(View.GONE);
			btn_relation_weixin.setVisibility(View.GONE);
			tv_info.setVisibility(View.VISIBLE);
		} else {
			btn_relation_qq.setVisibility(View.VISIBLE);
			btn_relation_weibo.setVisibility(View.VISIBLE);
			btn_relation_weixin.setVisibility(View.VISIBLE);
			tv_info.setVisibility(View.GONE);
		}
		// 请求个人信息
		if (from != null) {
			if (from.equals("weibo")) {
				btn_remove_qq.setVisibility(View.GONE);
				btn_remove_weixin.setVisibility(View.GONE);
				getWeiboInfo();
			} else if (from.equals("qq")) {
				btn_remove_weixin.setVisibility(View.GONE);
				btn_remove_weibo.setVisibility(View.GONE);
				getQQInfo();
			}else if(from.equals("weixin")){
				btn_remove_qq.setVisibility(View.GONE);
				btn_remove_weibo.setVisibility(View.GONE);
			}
		}
		BmobUser user = BmobUser.getCurrentUser(this);
		Log.i("life", ""+user.getUsername()+","+user.getSessionToken());
	}

	// 依次类推，想要获取QQ或者新浪微博其他的信息，开发者可自行根据官方提供的API文档，传入对应的参数即可
	// QQ的API文档地址：http://wiki.open.qq.com/wiki/website/API%E5%88%97%E8%A1%A8
	// 微博的API文档地址：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
	JSONObject obj;

	/**
	 * 获取微博的资料
	 * 
	 * @Title: getWeiboInfo
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	public void getWeiboInfo() {
		// 根据http://open.weibo.com/wiki/2/users/show提供的API文档
		new Thread() {
			@Override
			public void run() {
				try {
					obj = new JSONObject(json);
					Map<String, String> params = new HashMap<String, String>();
					if (obj != null) {
						params.put("access_token", obj.getJSONObject("weibo").getString("access_token"));// 此为微博登陆成功之后返回的access_token
						params.put("uid",obj.getJSONObject("weibo").getString("uid"));// 此为微博登陆成功之后返回的uid
					}
					String result = NetUtils.getRequest("https://api.weibo.com/2/users/show.json", params);
					Log.d("login", "微博的个人信息：" + result);
					Message msg = new Message();
					msg.obj = result;
					handler.sendMessage(msg);

				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		}.start();
	}

	/**
	 * 获取QQ的信息:具体可查看QQ的官方Demo，最新的QQ登录sdk已提供获取用户资料的接口并提供相应示例，可忽略此方式
	 * @Title: getQQInfo
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	public void getQQInfo() {
		// 若更换为自己的APPID后，仍然获取不到自己的用户信息，则需要
		// 根据(http://wiki.open.qq.com/wiki/website/get_user_info)提供的API文档，想要获取QQ用户的信息，则需要自己调用接口，传入对应的参数
		new Thread() {
			@Override
			public void run() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("access_token", "05636ED97BAB7F173CB237BA143AF7C9");// 此为QQ登陆成功之后返回access_token
				params.put("openid", "B4F5ABAD717CCC93ABF3BF28D4BCB03A");
				params.put("oauth_consumer_key", "222222");// oauth_consumer_key为申请QQ登录成功后，分配给应用的appid
				params.put("format", "json");// 格式--非必填项
				String result = NetUtils.getRequest("https://graph.qq.com/user/get_user_info", params);
				Log.d("login", "QQ的个人信息：" + result);
				Message msg = new Message();
				msg.obj = result;
				handler.sendMessage(msg);
			}

		}.start();
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result = (String) msg.obj;
			if (result != null) {
				tv_info.setText((String) msg.obj);
			} else {
				tv_info.setText("暂无个人信息");
			}
		};
	};

	/**和第三方账号进行关联  
	 * @method associateThirdParty 
	 * @param  snsType
	 * @param  accessToken
	 * @param  expiresIn
	 * @param  userId   
	 * @return void  
	 * @exception   
	 */
	private void associateThirdParty(String snsType,String accessToken, String expiresIn,String userId){
		BmobThirdUserAuth authInfo = new BmobThirdUserAuth(snsType,accessToken, expiresIn, userId);
		BmobUser.associateWithAuthData(this, authInfo, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("关联成功");
			}
			
			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("关联失败：code =" + code + ",msg = " + msg);
			}
		});
	}

	/**取消关联  
	 * @method dissociateAuth 
	 * @param  type：只能是三种值：qq、weibo、weixin   
	 * @return void  
	 * @exception   
	 */
	public void dissociateAuth(final String type){
		BmobUser.dissociateAuthData(this,type,new UpdateListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("取消"+type+"关联成功");
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				if (code == 208) {// 208错误指的是没有绑定相应账户的授权信息
					toast("你没有关联该账号");
				} else {
					toast("取消"+type+"关联失败：code =" + code + ",msg = " + msg);
				}
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == btn_relation_weibo) {
			associateThirdParty(BmobThirdUserAuth.SNS_TYPE_WEIBO, "2.00htoVwCV9DWcB02e14b7fa50vUwjg", "1410548398554", "2696876973");
		} else if (arg0 == btn_relation_qq) {
			associateThirdParty(BmobThirdUserAuth.SNS_TYPE_QQ, "A5DAEFCA1946AFE9FE35CD16B76F0FD1", "7776000", "2F848CC297DCD3C0494E99DC71CECB16");
		} else if (arg0 == btn_relation_weixin) {
			String accessToken = "OezXcEiiBSKSxW0eoylIePpsffIY64CyrRvrHWMWj_KdKD1OBKwfw-WsAlYVRjYCdhwIO8SfvzG24Id5owKp3LNJAzxgb_8vtZcQ4cVPJH_V9-W-j8bk5EcC93nIBMOJhqcOGYFlBfEUErQRGwvmKw";
			String openId = "oyXO2uEQP44TM-P0mNwwnzV6atZo";
			String expiresIn ="7200";
			associateThirdParty(BmobThirdUserAuth.SNS_TYPE_WEIXIN, accessToken, expiresIn, openId);
		} else if (arg0 == btn_remove_qq) {
			dissociateAuth("qq");
		} else if (arg0 == btn_remove_weibo) {
			dissociateAuth("weibo");
		} else if (arg0 == btn_remove_weixin) {
			dissociateAuth("weixin");
		}else if (arg0 == iv_back) {
			finish();
		} else if (arg0 == btn_logout) {//退出登陆，根据不同的第三方平台调用不同的退出API，此处省略
			//...
			BmobUser.logOut(this);
			finish();
		}
	}

	private void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

}
