package com.samrt.qq.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Title: HttpClientUtil.java
 * @Package: com.ailk.aip.util
 * @Description: HTTP请求实现类
 * @Comment:
 * @author: luocan
 * @CreateDate: 2014年8月28日 上午11:27:20
 */
@SuppressWarnings("deprecation")
public class HttpRequestUtil {
	private final static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

	public static final String RESULT_CONTENT_KEY = "respResult";
	public static final String RESULT_CODE_KEY = "StatusCode";
	public static final String RESULT_REASON_KEY = "ReasonPhrase";

	/**
	 * @Title: getResultData
	 * @Description:模拟post请求
	 * @author: luocan
	 * @Create: 2014年8月28日 下午2:24:36
	 * @Modify: 2014年8月28日 下午2:24:36
	 * @param:idCode:身份识别， 用来识别被请求方， url：请求url，  charset:请求字符集，respCharset读取响应结果使用的字符集, dataMap：请求参数
	 * @return:
	 * @throws IOException
	 */
	public static Map<String, String> getResultData(String idCode, String url, String charset, Map<String, String> dataMap) throws IOException {
		String contents =String.format("请求URL :%s，请求参数: %s。", url, dataMap);
		logger.info(contents);
		return getResultData(idCode,url,charset,null,dataMap,null,"post");
	}

	/**
	 * @Title: getResultData
	 * @Description:模拟post请求
	 * @author: luocan
	 * @Create: 2014年8月28日 下午2:24:36
	 * @Modify: 2014年8月28日 下午2:24:36
	 * @param:idCode:身份识别， 用来识别被请求方， url：请求url，  charset:请求字符集， dataMap：请求参数
	 * @return:
	 * @throws IOException
	 */
	public static Map<String, String> getResultData(String idCode, String url, String charset,String respCharset, Map<String, String> dataMap) throws IOException {
		String contents =String.format("请求URL :%s，请求参数: %s。", url, dataMap);
		logger.info(contents);
		return getResultData(idCode,url,charset,respCharset,dataMap,null,"post");
	}

	/**
	 * @Title: doGet
	 * @Description:模拟get请求
	 * @author: luocan
	 * @Create: 2014年11月29日 下午3:12:21
	 * @Modify: 2014年11月29日 下午3:12:21
	 * @param:
	 * @return:
	 */
	public static Map<String, String> doGet(String idCode, String url, String charset, Map<String, String> dataMap) throws Exception {
		return getResultData(idCode,url,charset,null,dataMap,null,"get");
	}

	/**
	 * @Title: getResultData
	 * @Description:模拟post请求
	 * @author: liangdl5
	 * @Create: 2015年6月28日 下午2:24:36
	 * @Modify: 2015年6月28日 下午2:24:36
	 * @param:idCode:身份识别， 用来识别被请求方， url：请求url，  charset:请求字符集，
	 * respCharse读取响应结果使用的字符集, dataMap：请求参数
	 * features 超时设置信息,method请求方式get或post默认get
	 * @param:features 其他特性参数
	 * @return:
	 * @throws IOException
	 */
	public static Map<String, String> getResultData(String idCode, String url, String charset,String respCharset,
													Map<String, String> dataMap, Map<String, String> features,String method) throws IOException {
		String contents =String.format("请求URL :%s，请求参数: %s。", url, dataMap);
		logger.info(contents);
		Map<String, String> resultMap = new HashMap<String, String>();
		String result = "";
		HttpClient httpclient = getHttpClient(url);
		int CONNECTION_TIMEOUT = 60000; // 请求超时1分钟
		int SO_TIMEOUT = 60000; // 读取超时1分钟
		if (features != null && features.get("CONNECTION_TIMEOUT") != null) {
			CONNECTION_TIMEOUT = Integer.parseInt(features.get("CONNECTION_TIMEOUT").toString());
			logger.info("HTTP请求超时时间CONNECTION_TIMEOUT被设置为：" + CONNECTION_TIMEOUT);
		}
		if (features != null && features.get("SO_TIMEOUT") != null) {
			SO_TIMEOUT = Integer.parseInt(features.get("SO_TIMEOUT").toString());
			logger.info("HTTP读取超时时间SO_TIMEOUT被设置为：" + SO_TIMEOUT);
		}
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
		url = url.replaceAll(" ", "%20");
		HttpPost httpPost = null;
		HttpGet gets = null;
		//组织参数
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if (dataMap != null && !dataMap.isEmpty()) {
				for (String key : dataMap.keySet()) {
					logger.info(key + ":" + dataMap.get(key));
					params.add(new BasicNameValuePair(key, dataMap.get(key)));
				}
			}
			HttpResponse response = null;
			if("post".equals(method)){
				httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params, charset));
				response = httpclient.execute(httpPost);
			}else{
				gets=new HttpGet(url);
				String str = EntityUtils.toString(new UrlEncodedFormEntity(params, charset));
				gets.setURI(new URI(gets.getURI().toString() + "?" + str));
				// 发送请求
				response = httpclient.execute(gets);
			}

			resultMap = getResult(response,respCharset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw e;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			if("post".equals(method)){
				httpPost.releaseConnection();
			}else{
				gets.releaseConnection();
			}
		}
		return resultMap;
	}

	private static Map<String,String> getResult(HttpResponse response,String charset) throws IOException {
		Map<String, String> resultMap = new HashMap<String, String>();
		String result = "";
		// 获取返回的StatusLine
		StatusLine StatusLine = response.getStatusLine();
		logger.info("响应状态：" + StatusLine);
		//resultMap.put("ProtocolVersion", response.getProtocolVersion().toString());// 协议版本
		resultMap.put(RESULT_CODE_KEY,String.valueOf(StatusLine.getStatusCode()));// 返回状态码
		resultMap.put(RESULT_REASON_KEY, StatusLine.getReasonPhrase());// 返回原因短语
		HttpEntity responseEntity = response.getEntity();
		result = parseEntityContent(responseEntity,charset==null?null:Charset.forName(charset));
		resultMap.put(RESULT_CONTENT_KEY, result);
		logger.info("响应结果： " + result);
		EntityUtils.consume(responseEntity);
		return resultMap;
	}

	/**
	 * 获取httpClient，如果是https请求，增加相应设置
	 * @param url
	 * @return
	 */
	public static HttpClient getHttpClient(String url){
		HttpClient httpclient = null;
		//http请求和https请求判断
		String header=url.substring(0, 5);
		if (header.equalsIgnoreCase("https")) {
			httpclient = getHttpsClient();
		}else{
			httpclient = new DefaultHttpClient();
		}
		return httpclient;
	}

	/**
	 * @Title: getHttpsClient
	 * @Description:https请求配置（一）
	 * @author: luocan
	 * @Create: 2014年9月3日 上午11:16:08
	 * @Modify: 2014年9月3日 上午11:16:08
	 * @param:
	 * @return:
	 */
	public static HttpClient getHttpsClient() {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
				public X509Certificate[] getAcceptedIssuers() { return null; }
			};
			DefaultHttpClient client = new DefaultHttpClient();
			ctx.init(null, new TrustManager[] { tm }, null);
			//SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER为必须，否则报错
			SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = client.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			// 设置要使用的端口，默认是443
			sr.register(new Scheme("https", 443, ssf));
			return client;
		} catch (Exception ex) {
			return null;
		}
	}
	/**
	 * @Title: getHttpsClient
	 * @Description:https请求配置（二），留待备用
	 * @author: luocan
	 * @Create: 2014年9月2日 下午9:56:53
	 * @Modify: 2014年9月2日 下午9:56:53
	 * @param:
	 * @return:
	 */
	public static HttpClient wrapClient(HttpClient base) {
		try {
			//TLS是SSL的继承者
			
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("https", 443, ssf));
			ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
			
			
			return new DefaultHttpClient(mgr,base.getParams());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	private static void ignoreSsl() throws Exception{
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        };
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }
    private static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
    static class miTM implements TrustManager,X509TrustManager {

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }
        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }
        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
        
    }
	/**
	 * 读取响应结果
	 * @param entity
	 * @param charset
	 * @return
	 */
	private static String parseEntityContent(HttpEntity entity,Charset charset){
		InputStream instream = null;
		try {
			instream = entity.getContent();
			if (instream == null) {
				return null;
			}
			if (entity.getContentLength() > Integer.MAX_VALUE) {
				throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
			}
			int i = (int)entity.getContentLength();
			if (i < 0) {
				i = 4096;
			}
			ContentType contentType = ContentType.getOrDefault(entity);
			if(charset == null && contentType != null){
				charset = contentType.getCharset();
			}
			if (charset == null) {
				charset = HTTP.DEF_CONTENT_CHARSET;
			}
			Reader reader = new InputStreamReader(instream, charset);
			CharArrayBuffer buffer = new CharArrayBuffer(i);
			char[] tmp = new char[1024];
			int l;
			while((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "read response data error:"+e.getMessage();
		} finally {
			try {
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static String inputStreamToStrFromByte(InputStream input) {  
        String result = "";  
        int len = 0;  
        byte[] array = new byte[1024];  
        StringBuffer buffer = new StringBuffer();  
        if (input != null) {  
            try {  
                while ((len = input.read(array)) != -1) {  
                    buffer.append(new String(array, 0, len, "utf-8"));  
                }  
                result = buffer.toString();  
            } catch (IOException e) {  
                buffer = null;  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
	/** 
     * 根据url的协议选择对应的请求方式  
     * @param method 请求的方法 
     * @return 
     * @throws IOException 
     */  
    private static HttpURLConnection getConnection(String method, URL url) throws IOException {  
        HttpURLConnection conn = null;  
            conn = (HttpURLConnection) url.openConnection();  
        conn.setRequestMethod(method);  
        conn.setUseCaches(false);  
        conn.setDoInput(true);  
        conn.setDoOutput(true);  
        return conn;  
    }  
}
