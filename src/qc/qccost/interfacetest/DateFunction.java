package qc.qccost.interfacetest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Jmeterʱ����صķ���
 * @author user
 *
 */
public class DateFunction {
	
	/**
	 * ��ȡ��ǰʱ�䣺��ʽyyyyMMddHHmmss
	 * @return
	 */
	public static String  getCurrentTime(){		
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	/**
	 * ��ȡ��ǰ�գ���ʽyyyy-MM-dd
	 * @return
	 */
	public static String getCurrentDay(){
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	
	/**
	 * ��ȡ��ǰ�£���ʽyyyy-MM
	 * @return
	 */
	public static String getCurrentMonth(){
		return new SimpleDateFormat("yyyy-MM").format(new Date());
	}
	
	/**
	 * ��ȡ��ǰ��һ�죺��ʽyyyy-MM-dd
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
