package cn.util;

/*封装服务器JSON返回结果,统一格式*/
public class ServerResponse {
	private String code;
	private String msg;
	private Integer count;
	private Object data;

	public ServerResponse() {

	}
	
	public ServerResponse(String code) {
		this.code = code;
	}

	public ServerResponse(String code,String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ServerResponse(String code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}

	public ServerResponse(String code, Integer count, Object data) {
		super();
		this.code = code;
		this.count = count;
		this.data = data;
	}

	public ServerResponse(String code, String msg, Integer count, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
