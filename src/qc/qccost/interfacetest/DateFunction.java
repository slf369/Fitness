package qc.qccost.interfacetest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Jmeter时间相关的方法
 * @author user
 *
 */
public class DateFunction {
	
	/**
	 * 获取当前时间：格式yyyyMMddHHmmss
	 * @return
	 */
	public static String  getCurrentTime(){		
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	/**
	 * 获取当前日：格式yyyy-MM-dd
	 * @return
	 */
	public static String getCurrentDay(){
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	
	/**
	 * 获取当前月：格式yyyy-MM
	 * @return
	 */
	public static String getCurrentMonth(){
		return new SimpleDateFormat("yyyy-MM").format(new Date());
	}
	
	/**
	 * 获取当前日一天：格式yyyy-MM-dd
	 * @return
	 */
	public static String getOneDayAfter(){
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(new Date().getTime()+1*24*3600*1000));
	}
	
	/**
	 * @param format
	 * @param month
	 * @return
	 */
	public static String getMonthInterval(int month,String format){		
		SimpleDateFormat df=new SimpleDateFormat(format);
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MONTH,month);
		return df.format(cal.getTime());		
	}	
	
	/**
	 * @param format
	 * @param day
	 * @return
	 */
	public static String getDayInterval(int day,String format){		
		SimpleDateFormat df=new SimpleDateFormat(format);
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE,day);
		return df.format(cal.getTime());		
	}	

	
	/**
	 * @param format
	 * @param day
	 * @return
	 */
	public static String getYearInterval(int year,String format){		
		SimpleDateFormat df=new SimpleDateFormat(format);
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.YEAR,year);
		return df.format(cal.getTime());		
	}
	
}
