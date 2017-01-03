package qc.qccost.interfacetest;

import java.io.IOException;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientDemo {

	public static void postMethodWtihRawBody(String url, Map<String, String> headerMap, String jsonBody)
			throws ClientProtocolException, IOException {

		// 创建默认的httClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 创建httpPost
		HttpPost httpPost = new HttpPost(url);
		
		// 添加Header	
		if(headerMap!=null){
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
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
			String responseStr = Unicode2String.convertUnicode(EntityUtils.toString(entity, "UTF-8"));
			System.out.println("Response content: " + responseStr);
		}
		// 关闭Response
		response.close();
		// 关闭HttpClient
		httpClient.close();
	}

	public static void main(String[] args) throws IOException {
		String uri = "http://qa004.qc.com:7822/product/producttype/add";
		String body = "{\"productType\": {\"name\": \"AppleiPhone1 Plus\",\"code\": \"3133863\",\"status\": \"enable\",\"sort\": \"0\"}}";
		postMethodWtihRawBody(uri, null, body);
	}
}
