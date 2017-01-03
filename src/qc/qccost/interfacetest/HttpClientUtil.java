package qc.qccost.interfacetest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.jayway.jsonpath.JsonPath;

public class HttpClientUtil {

	public HttpClientUtil() {
		
	}

	public  String responseStr;

	public  String getResponse() {
		return responseStr;
	}

	public  void postMethodWithFormData(String url, Map<String, String> headerMap, Map<String, String> bodyMap)
			throws ClientProtocolException, IOException {

		CloseableHttpClient httpClients = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		// 添加请求头
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpPost.addHeader(entry.getKey(), entry.getValue());
		}
		List<NameValuePair> pairsList = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry1 : bodyMap.entrySet()) {
			pairsList.add(new BasicNameValuePair(entry1.getKey(), entry1.getValue()));
		}
		UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(pairsList, "UTF-8");
		httpPost.setEntity(reqEntity);
		CloseableHttpResponse httpResponse = httpClients.execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// 获取返回实体
			HttpEntity entity = httpResponse.getEntity();
			// 设置返回为UTF-8,并对中文Unicode码处理
			responseStr = Unicode2String.convertUnicode(EntityUtils.toString(entity, "UTF-8"));
			System.out.println("Response content: " + responseStr);
		}
		httpResponse.close();
		httpClients.close();
	}

	public  void postMethodWihRawBody(String url, String jsonBody)
			throws Exception {
		//如果有"${",执行替换参数操作
		if(jsonBody.indexOf("$#")!=-1){
			jsonBody=dealParamReplace(jsonBody);
		}
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		// 添加Header
//		if (headerMap != null) {
//			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
//				httpPost.addHeader(entry.getKey(), entry.getValue());
//			}
//		}

		// 添加Body整串-Json字符串
		StringEntity strEntity = new StringEntity(jsonBody);
		strEntity.setContentEncoding("UTF-8");
		strEntity.setContentType("application/json");
		httpPost.setEntity(strEntity);

		System.out.println("Executing request: " + httpPost.getURI());
		// 发送HttpPost
		CloseableHttpResponse response = httpClient.execute(httpPost);	
		
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// 获取返回实体
			HttpEntity entity = response.getEntity();
			// 设置返回为UTF-8,并对中文Unicode码处理
			responseStr = Unicode2String.convertUnicode(EntityUtils.toString(entity, "UTF-8"));
			System.out.println("Response content: " + responseStr);
		}		
		response.close();		
		httpClient.close();
	}
	
	public Set<String> getReplaceParam(String jsonStr){
		Set<String> set = new HashSet<String>();
		int index = 0;
		int cur = 0;
		int begin = 0;
		String keyStr = "";

		while ((cur = jsonStr.indexOf("$#", index)) != -1) {
			index = cur + 2;// 跳过"${"两个字符
			if ((cur = jsonStr.indexOf("#", index)) != -1) {
				begin = index;
				keyStr = jsonStr.substring(begin, cur);
				set.add(keyStr.trim());
				index = cur + 1;
			}
		}		
		return set;
	}
	
	@SuppressWarnings("rawtypes")
	public String dealParamReplace(String jsonStr) throws Exception{
		Set<String> paramSet = getReplaceParam(jsonStr);
		Class clazz = Class.forName("qc.qccost.interfacetest.HttpClientUtil");
		Object obj = clazz.newInstance();
		for (String str2 : paramSet) {
			String methodName = "get" + str2.substring(0, 1).toUpperCase() + str2.substring(1);
			Method method = obj.getClass().getDeclaredMethod(methodName);
			jsonStr = jsonStr.replaceAll("\\$#" + str2 + "#", (String) method.invoke(obj));
		}
		return jsonStr;
	}
	
	

	/**
	 * 功能说明：对Json的响应返回串，根据searchKey搜索得到Value
	 * 
	 * @return String
	 * @param jsonStr
	 *            被搜索的Json字符串
	 * @param searchKey
	 *            要搜索的Key（JsonPath表达式） *
	 * @param index
	 *            多个匹配时，取第几个 （索引从1开始）
	 */
	public String parseJsonPath(String jsonStr, String searchKey, int index) {
		String result = "";
		try {
			List<Object> list = JsonPath.read(jsonStr, searchKey);
			List<String> allResult = new ArrayList<String>();
			System.out.println("找到" + list.size() + "个");
			if (list.size() == 1) {
				System.out.println("根据搜素Key " + "\"" + searchKey + "\"" + "搜索到值是：" + list.get(0));
				return String.valueOf(list.get(index - 1));
			} else if (list.size() > 1 && list.size() != 0) {
				System.out.println("根据搜素Key " + "\"" + searchKey + "\"" + "搜索到值如下：");
				for (Object str : list) {
					System.out.println("值为" + "\"" + str + "\"");
					if (str instanceof Integer || str instanceof Float || str instanceof Double
							|| str instanceof Boolean) {
						allResult.add(String.valueOf(str));
					}
				}
				return allResult.get(index - 1);
			}
		} catch (Exception e) {
			System.out.println("未搜索到关键字匹配的内容，请确认关键字表达式是否正确");
		}
		return result;
	}

	/**
	 * 获取当前时间：格式yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public  String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	/**
	 * 获取当前日：格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public  String getCurrentDay() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 获取当前月：格式yyyy-MM
	 * 
	 * @return
	 */
	public  String getCurrentMonth() {
		return new SimpleDateFormat("yyyy-MM").format(new Date());
	}

	public String getTimeStamp(){
		return String.valueOf(new Date().getTime());
	}
	
	/**
	 * 获取当前日一天：格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public  String getOneDayAfter() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(new Date().getTime() + 1 * 24 * 3600 * 1000));
	}

	/**
	 * @param format
	 * @param month
	 * @return
	 */
	public  String getMonthInterval(int month, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, month);
		return df.format(cal.getTime());
	}

	/**
	 * @param format
	 * @param day
	 * @return
	 */
	public  String getDayInterval(int day, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, day);
		return df.format(cal.getTime());
	}

	/**
	 * @param format
	 * @param day
	 * @return
	 */
	public  String getYearInterval(int year, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, year);
		return df.format(cal.getTime());
	}
	


	public static void main(String[] args) throws ClientProtocolException, IOException, Exception {

		 HttpClientUtil httpCli=new HttpClientUtil();
		
		// String url = "http://api.fk1.qa/calendar/checkin/create";
		//
		// Map<String, String> headerMap = new HashMap<String, String>();
		// headerMap.put("Encryption", "CLB_NONE");
		// headerMap.put("VersionCode", "3.6.0");
		// headerMap.put("token", "fd5c0bff9d4bc8e67eb0607e552e4fb1");
		// headerMap.put("Accept", "application/vnd.columbus.v1+json");
		//
		// Map<String, String> bodyMap = new HashMap<String, String>();
		// bodyMap.put("content", "施凌峰07_20160902签到");
		// bodyMap.put("city_name", "上海");
		// bodyMap.put("address", "上海市杨浦区周家嘴路3586号");
		// bodyMap.put("lng", "121.545056");
		// bodyMap.put("lat", "31.282956");
		//
		// httpCli.postMethod(url, headerMap, bodyMap);

		//String dest="{\"productType\": {\"name\": \"AppleiPhone2 Plus\",\"code\": \"3133863\",\"status\": \"enable\",\"sort\": \"0\"}}";
		String dest = "{\"start_time\":\"$#currentDay# 00:00\",\"end_time\":\"$#oneDayAfter# 18:00\",\"schedule_list\":[{\"startCityStatus\":true,\"startTimeStatus\":true,\"endCityStatus\":true,\"vehicleStatus\":true,\"end_city\":{\"city_id\":52,\"city_name\":\"北京\"},\"start_city\":{\"city_id\":321,\"city_name\":\"上海\"},\"start_time\":\"$#currentDay# 00:00\",\"end_time\":\"$#oneDayAfter# 18:00\",\"transport\":\"飞机\"},{\"startCityStatus\":true,\"startTimeStatus\":true,\"endCityStatus\":true,\"vehicleStatus\":true,\"end_city\":{\"city_id\":321,\"city_name\":\"上海\"},\"start_city\":{\"city_id\":52,\"city_name\":\"北京\"},\"start_time\":\"$#currentDay# 18:00\",\"end_time\":\"$#oneDayAfter# 18:00\",\"transport\":\"飞机\"}]}";
		System.out.println(httpCli.dealParamReplace(dest));			
		
		String uri = "http://qa004.qc.com:7822/product/producttype/add";
		String body = "{\"productType\": {\"name\": \"AppleiPhone1 Plus\",\"code\": \"3133863\",\"status\": \"enable\",\"sort\": \"0\"}}";
		httpCli.postMethodWihRawBody(uri, body);		
	}
}
