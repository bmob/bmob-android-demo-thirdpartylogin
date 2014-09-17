package com.bmob.thirdpartylogindemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetUtils {
	/**
	 * 当前是否有网络
	 * 
	 * @Title: isOpenNetwork
	 * @Description: TODO
	 * @param @param context
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isOpenNetwork(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}

	/**
	 * GET请求方式访问api接口
	 * 
	 * @param urlString
	 * @param params
	 * @return
	 */
	public static String getRequest(String urlString, Map<String, String> params) {
		try {
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append(urlString);
			if (null != params) {
				urlBuilder.append("?");
				Iterator<Entry<String, String>> iterator = params.entrySet()
						.iterator();
				while (iterator.hasNext()) {
					Entry<String, String> param = iterator.next();
					urlBuilder
							.append(URLEncoder.encode(param.getKey(), "UTF-8"))
							.append('=')
							.append(URLEncoder.encode(param.getValue(), "UTF-8"));
					if (iterator.hasNext()) {
						urlBuilder.append('&');
					}
				}
			}
			HttpClient client = getNewHttpClient();
			HttpGet getMethod = new HttpGet(urlBuilder.toString());
			HttpResponse response = client.execute(getMethod);
			int res = response.getStatusLine().getStatusCode();
			if (res == 200) {
				StringBuilder builder = new StringBuilder();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));
				for (String s = reader.readLine(); s != null; s = reader
						.readLine()) {
					builder.append(s);
				}
				return builder.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * POST请求方式访问api
	 * 
	 * @param urlString
	 * @param params
	 * @return
	 */
	public static String postRequest(String urlString,
			List<BasicNameValuePair> params) {
		try {
			HttpClient client = getNewHttpClient();
			HttpPost postMethod = new HttpPost(urlString);
			postMethod.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = client.execute(postMethod);
			int statueCode = response.getStatusLine().getStatusCode();
			if (statueCode == 200) {
				System.out.println(statueCode);
				return EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
		}
		return null;
	}

	public static long expires(String second) {
		Long l = Long.valueOf(second);
		return l * 1000L + System.currentTimeMillis();
	}

	private static HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);
			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}
}
