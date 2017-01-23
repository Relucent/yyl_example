package yyl.example.basic.http;

import java.nio.charset.Charset;

/**
 * HTTP响应结果
 */
public class HttpResponse {
	// ==============================Fields========================================
	/** HTTP响应状态码 */
	protected int code;
	/** HTTP响应的内容 */
	protected byte[] content;
	/** UTF-8 字符集 */
	private static final Charset UTF_8 = Charset.forName("UTF-8");

	// ==============================Constructors=====================================
	/** 构造函数 */
	protected HttpResponse() {
	}

	/**
	 * 构造函数
	 * @param code 响应状态码
	 * @param content 响应的内容
	 */
	public HttpResponse(int code, byte[] content) {
		this.code = code;
		this.content = content;
	}

	// ==============================PropertyAccessors================================
	/**
	 * 获得响应状态码
	 * @return 响应状态码
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 获得响应的内容
	 * @return 响应的内容
	 */
	public byte[] getContent() {
		return content;
	}

	// ==============================Methods==========================================
	/**
	 * 获得响应的字符串内容(使用UTF-8字符集)
	 * @return 响应的内容(字符串形式)
	 */
	public String getContentAsString() {
		return new String(content, UTF_8);
	}

	// ==============================OverrideMethods==================================
	/**
	 * 对象的字符串实行
	 */
	@Override
	public String toString() {
		return "HttpResponse [code=" + code + ", content=" + content + "]";
	}
}