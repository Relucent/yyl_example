package yyl.example.basic.http;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * HTTP工具类.
 * @author YaoYiLang
 * @version 2014-12-06 09:30
 */
public class HttpUtil {

	// =================================Fields=================================================
	// =================================Constants==============================================
	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String CHAR_SET = "UTF-8";
	public static final int CONNECT_TIMEOUT = 15000;// 连接超时(单位毫秒)
	public static final int READ_TIMEOUT = 30000;// 读取超时(单位毫秒)

	private static final SSLSocketFactory sslSocketFactory = initSSLSocketFactory();
	private static final TrustAnyHostnameVerifier trustAnyHostnameVerifier = new HttpUtil().new TrustAnyHostnameVerifier();

	// =================================Methods================================================
	/**
	 * 发送GET请求
	 * 
	 * @param url
	 * @return 请求的结果
	 */
	public static HttpResponse get(String url) {
		return get(url, null, null);
	}

	/**
	 * 发送GET请求
	 * 
	 * @param url
	 * @param queryParams URL参数
	 * @return 请求的结果
	 */
	public static HttpResponse get(String url, Map<String, String> queryParams) {
		return get(url, queryParams, null);
	}

	/**
	 * 发送GET请求
	 * 
	 * @param url
	 * @param queryParams URL参数
	 * @param headers HTTP头数据
	 * @return 请求的结果
	 */
	public static HttpResponse get(String url, Map<String, String> queryParams, Map<String, String> headers) {
		HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(buildUrlWithQueryString(url, queryParams), GET, headers);
			conn.connect();
			return getHttpResponse(conn);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeQuietly(conn);
		}
	}

	/**
	 * 发送POST请求
	 * 
	 * @param url
	 * @param data 提交数据
	 * @return 请求的结果
	 */
	public static HttpResponse post(String url, byte[] data) {
		return post(url, null, data, null);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param url
	 * @param queryParams URL参数
	 * @param data 提交数据
	 * @return 请求的结果
	 */
	public static HttpResponse post(String url, Map<String, String> queryParams, byte[] data) {
		return post(url, queryParams, data, null);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param url
	 * @param data 提交数据
	 * @param headers HTTP头数据
	 * @return 请求的结果
	 */
	public static HttpResponse post(String url, byte[] data, Map<String, String> headers) {
		return post(url, null, data, headers);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param url
	 * @param queryParams URL参数
	 * @param data 提交数据
	 * @param headers HTTP头数据
	 * @return 请求的结果
	 */
	public static HttpResponse post(String url, Map<String, String> queryParams, byte[] data, Map<String, String> headers) {
		HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(buildUrlWithQueryString(url, queryParams), POST, headers);
			conn.connect();
			OutputStream out = null;
			try {
				out = conn.getOutputStream();
				out.write(data == null ? new byte[0] : data);
				out.flush();
			} finally {
				if (out != null) {
					out.close();
				}
			}
			return getHttpResponse(conn);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			closeQuietly(conn);
		}
	}

	/**
	 * 获得一个HTTP连接
	 * 
	 * @param url URL
	 * @param method 请求的方法
	 * @param headers HTTP头数据
	 */
	public static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers)
			throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		URL _url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
		if (conn instanceof HttpsURLConnection) {
			((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
			((HttpsURLConnection) conn).setHostnameVerifier(trustAnyHostnameVerifier);
		}
		conn.setRequestMethod(method);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setConnectTimeout(CONNECT_TIMEOUT);
		conn.setReadTimeout(READ_TIMEOUT);

		if (headers == null || !headers.containsKey("Content-Type")) {
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		}
		if (headers == null || !headers.containsKey("User-Agent")) {
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
		}

		if (headers != null && !headers.isEmpty()) {
			for (Entry<String, String> entry : headers.entrySet()) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}
		return conn;
	}

	/**
	 * 构建带条件的URL字符串
	 * 
	 * @param url URL地址
	 * @param query 查询条件
	 */
	private static String buildUrlWithQueryString(String url, Map<String, String> query) {
		if (query == null || query.isEmpty()) {
			return url;
		}
		StringBuilder sb = new StringBuilder(url);
		boolean first;
		if (url.indexOf("?") == -1) {
			first = true;
			sb.append("?");
		} else {
			first = false;
		}
		for (Entry<String, String> entry : query.entrySet()) {
			if (first) {
				first = false;
			} else {
				sb.append("&");
			}
			String key = entry.getKey();
			String value = entry.getValue();
			if (value != null && !value.isEmpty())
				try {
					value = URLEncoder.encode(value, CHAR_SET);
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
			sb.append(key).append("=").append(value);
		}
		return sb.toString();
	}

	/**
	 * 关闭连接
	 * 
	 * @param conn HTTP连接
	 */
	public static void closeQuietly(HttpURLConnection conn) {
		if (conn != null) {
			try {
				conn.disconnect();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 静默方式关闭连接(忽略异常)
	 * 
	 * @param closeable 可以关闭的对象
	 */
	private static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ioe) {
		}
	}

	/**
	 * 读取HTTP响应数据
	 * 
	 * @param conn HTTP连接
	 * @return HTTP响应数据
	 */
	public static HttpResponse getHttpResponse(HttpURLConnection conn) {
		int code;
		byte[] content = null;
		try {
			code = conn.getResponseCode();
			InputStream input = null;
			try {
				content = toByteArray(input = conn.getInputStream());
			} finally {
				closeQuietly(input);
			}
		} catch (Exception e) {
			try {
				code = conn.getResponseCode();
				InputStream input = null;
				try {
					content = toByteArray(input = conn.getErrorStream());
				} finally {
					closeQuietly(input);
				}
			} catch (Exception t) {
				throw new RuntimeException(t);
			}
		}
		return new HttpResponse(code, content);
	}

	/**
	 * 转换输入流为字节数组
	 * 
	 * @param input 输入流
	 * @return 输入流的内容(字节数组)
	 * @throws IOException
	 */
	private static byte[] toByteArray(InputStream input) throws IOException {
		if (input == null) {
			return null;
		} else {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int n = 0;
			while (-1 != (n = input.read(buffer))) {
				output.write(buffer, 0, n);
			}
			return output.toByteArray();
		}
	}

	/**
	 * 初始化SSLSocketFactory.
	 * 
	 * @return SSLSocketFactory
	 */
	private static SSLSocketFactory initSSLSocketFactory() {
		try {
			TrustManager[] tm = { new HttpUtil().new TrustAnyTrustManager() };
			SSLContext sslContext = SSLContext.getInstance("TLS", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			return sslContext.getSocketFactory();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// =================================InnerClasses===========================================
	/**
	 * HTTPS域名校验
	 */
	private class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	/**
	 * HTTPS证书管理
	 */
	private class TrustAnyTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
	}
}
