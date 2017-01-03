package qc.qccost.interfacetest;

import java.util.ArrayList;
import java.util.List;
import com.jayway.jsonpath.JsonPath;

public class JsonPathUtil {

	public JsonPathUtil() {

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
				return allResult.get(index-1);
			}
		} catch (Exception e) {
			System.out.println("未搜索到关键字匹配的内容，请确认关键字表达式是否正确");
		}
		return result;
	}

	public static void main(String[] args) {
		String myStr = "{\"status\":0,\"message\":\"签到成功\",\"data\":{\"item_type\":27,\"checkin_id\":1273,\"user\":{\"user_id\":214,\"fullname\":\"shilingfeng07\",\"telephone\":\"13713713709\",\"telephone\":\"13713713707\",\"email\":\"shilingfneg07@quancheng-ec.com\",\"deleted\":0,\"cost_center\":{\"cost_center_id\":2981,\"title\":\"技术部\",\"user_id\":209,\"serial_no\":\"SLB251610949\",\"principal\":{\"user_id\":209,\"fullname\":\"shilingfeng02\",\"telephone\":\"13713713702\",\"email\":\"shilingfeng02@quancheng-ec.com\",\"deleted\":0,\"status\":1,\"company_id\":6},\"pid\":2979}},\"lat\":\"31.276801930974\",\"lng\":\"121.53861166765\",\"address\":\"上海市杨浦区周家嘴路3586号\",\"photos\":[],\"content\":\"施凌峰07_20160902签到\",\"superior\":{\"user_id\":209,\"fullname\":\"shilingfeng02\",\"telephone\":\"13713713702\",\"email\":\"shilingfeng02@quancheng-ec.com\",\"deleted\":0,\"cost_center\":{\"cost_center_id\":2981,\"title\":\"技术部\",\"user_id\":209,\"serial_no\":\"SLB251610949\",\"principal\":{\"user_id\":209,\"fullname\":\"shilingfeng02\",\"telephone\":\"13713713702\",\"email\":\"shilingfeng02@quancheng-ec.com\",\"deleted\":0,\"status\":3,\"company_id\":6},\"pid\":2979}},\"created_at\":\"2016-09-05 19:06\",\"updated_at\":\"2016-09-05 19:06\"}}";
		System.out.println(new JsonPathUtil().parseJsonPath(myStr, "$..status", 3));
		
	}
}
