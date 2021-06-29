package com.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对数据相关的工具包
 * @author 
 */
public class DataUtils {
    
    /**
     * 验证是否是电子邮箱
     * @param str
     * @return
     * @author 
     */
    public static boolean isEmail(String str) {
        if (null == str) return false;
        return str.matches("^[\\w&&[^_]]+([\\.\\-_][\\w&&[^_]]+)*@([\\w&&[^_]]+[\\.\\-_]?)+[\\w&&[^_]]+\\.[A-Za-z]{2,5}$");
    }
    
    /**
     * 验证是否正确的手机号
     * @param mobile
     * @return
     * @author 
     */
    public static boolean isMobile(String mobile) {
        if (mobile == null) return false;
        return mobile.matches("^1[34578]\\d{9}$");
    }
    
    /**
     * 判断字符串是否为正整数
     * @param code
     * @return 如果是正整数则返回true，否则返回false
     * @author 
     */
    public static boolean isUInteger(String code) {
        if (null == code) return false;
        return code.matches("^\\d+");
    }
    
    /**
     * 判断字符串是否为整数
     * @param code
     * @return 如果是整数则返回true，否则返回false
     * @author 
     */
    public static boolean isInteger(String code) {
        if (null == code) return false;
        return code.matches("^[-]?\\d+");
    }
    
    /**
     * 判断字符串是否为正浮点数
     * @param code
     * @return 如果是正浮点数则返回true，否则返回false
     * @author 
     */
    public static boolean isUFloat(String code) {
        if (null == code) return false;
        return code.matches("^\\d*(\\.\\d+)?");
    }
    
    /**
     * 判断字符串是否为浮点数
     * @param code
     * @return 如果是浮点数则返回true，否则返回false
     * @author 
     */
    public static boolean isFloat(String code) {
        if (null == code) return false;
        return code.matches("^[-]?\\d*(\\.\\d+)?");
    }
    
    /**
     * 判断字符串是否为空或空字符串
     * @param str 需要判断的字符串
     * @param trim 是否需要忽略两边空白后判断
     * @return 当字符串为空或为空字符串或在trim=true时str.trim()后为空字符串时，返回true，否则返回false
     * @author 
     */
    public static boolean isNullOrEmpty(String str, boolean trim) {
        if (str == null) return true;
        if (str.isEmpty() || "null".equals(str.trim()) || (trim && str.trim().isEmpty())) return true;
        return false;
    }
    
    /**
     * 	判断list是否为空
     */
    public static boolean isNullOrEmpty(Collection lis) {
    	if(lis == null) {
    		return true;
    	}
    	return lis.isEmpty();
    }
    
    /**
     * 	判断list是否为空
     */
    public static boolean isNullOrEmpty(Map map) {
    	if(map == null) {
    		return true;
    	}
    	return map.isEmpty();
    }
    /**
     * 判断字符串是否为空或空字符串（忽略两边空白）<br>
     * 同DataUtils.isNullOrEmpty(str, true)
     * 
     * <pre>
     * DataUtils.isNullOrEmpty(null) = true
     * DataUtils.isNullOrEmpty("") = true
     * DataUtils.isNullOrEmpty(" ") = true
     * DataUtils.isNullOrEmpty("12345") = false
     * DataUtils.isNullOrEmpty(" 12345 ") = false
     * </pre>
     * @param str 需要判断的字符串
     * @return 当字符串为空或在str.trim()后为空字符串时，返回true，否则返回false
     * @author 
     */
    public static boolean isNullOrEmpty(String str) {
        return isNullOrEmpty(str, true);
    }
    
    public static boolean isNullOrEmpty(Integer str) {
        return str == null;
    }
    
    /**
     * 获取非空字符串
     * @param value 需要处理判断的字符串
     * @param defaultValue 默认字符串
     * @param trim 是否需要忽略value的两边空白
     * @return 当value为null时，返回defaultValue，否则根据trim判断返回value
     * @throws IllegalArgumentException defaultValue为空时抛出
     * @author 
     */
    public static String defaultString(String value, String defaultValue, boolean trim) {
        if (defaultValue == null) throw new IllegalArgumentException("defaultValue不允许为空");
        if (value == null) return defaultValue;
        return trim ? value.trim() : value;
    }
    
    /**
     * 获取非空字符串
     * @param value 需要处理判断的字符串
     * @param defaultValue 默认字符串
     * @return 当value为null时，返回defaultValue，否则返回value.trim()的返回值
     * @throws IllegalArgumentException defaultValue为空时抛出
     * @author 
     */
    public static String defaultString(String value, String defaultValue) {
        return defaultString(value, defaultValue, true);
    }
    
    /**
     * 获取非空字符串
     * @param value 需要处理判断的字符串
     * @return 当value为null时，返回""，否则返回value.trim()的返回值
     * @author 
     */
    public static String defaultString(String value) {
        return defaultString(value, "", true);
    }
    
    /**
     * 获取非空Double
     * @param value 需要处理判断的Double
     * @param defaultValue 默认Double值
     * @return 当value为null时，返回defaultValue，否则返回value
     * @throws IllegalArgumentException defaultValue为空时抛出
     * @author 
     */
    public static Double defaultDouble(Double value, Double defaultValue) {
        if (defaultValue == null) throw new IllegalArgumentException("defaultValue不允许为空");
        if (value == null) return defaultValue;
        return value;
    }
    
    /**
     * 获取非空Double
     * @param value 需要处理判断的Double
     * @return 当value为null时，返回0.0D，否则返回value
     * @author 
     */
    public static Double defaultDouble(Double value) {
        return defaultDouble(value, 0.0D);
    }
    
    /**
     * 获取非空Float
     * @param value 需要处理判断的Float
     * @param defaultValue 默认Float值
     * @return 当value为null时，返回defaultValue，否则返回value
     * @throws IllegalArgumentException defaultValue为空时抛出
     * @author 
     */
    public static Float defaultFloat(Float value, Float defaultValue) {
        if (defaultValue == null) throw new IllegalArgumentException("defaultValue不允许为空");
        if (value == null) return defaultValue;
        return value;
    }
    
    /**
     * 获取非空Float
     * @param value 需要处理判断的Float
     * @return 当value为null时，返回0.0F，否则返回value
     * @author 
     */
    public static Float defaultFloat(Float value) {
        return defaultFloat(value, 0.0F);
    }
    
    /**
     * 获取非空Integer
     * @param value 需要处理判断的Integer
     * @param defaultValue 默认Integer值
     * @return 当value为null时，返回defaultValue，否则返回value
     * @throws IllegalArgumentException defaultValue为空时抛出
     * @author 
     */
    public static Integer defaultInteger(Integer value, Integer defaultValue) {
        if (defaultValue == null) throw new IllegalArgumentException("defaultValue不允许为空");
        if (value == null) return defaultValue;
        return value;
    }
    
    /**
     * 获取非空Integer
     * @param value 需要处理判断的Integer
     * @return 当value为null时，返回0，否则返回value
     * @author 
     */
    public static Integer defaultInteger(Integer value) {
        return defaultInteger(value, 0);
    }
    
    /**
     * 获取非空Long
     * @param value 需要处理判断的Long
     * @param defaultValue 默认Long值
     * @return 当value为null时，返回defaultValue，否则返回value
     * @throws IllegalArgumentException defaultValue为空时抛出
     * @author 
     */
    public static Long defaultLong(Long value, Long defaultValue) {
        if (defaultValue == null) throw new IllegalArgumentException("defaultValue不允许为空");
        if (value == null) return defaultValue;
        return value;
    }
    
    /**
     * 获取非空Long
     * @param value 需要处理判断的Long
     * @return 当value为null时，返回0L，否则返回value
     * @author 
     */
    public static Long defaultLong(Long value) {
        return defaultLong(value, 0L);
    }
    
    /**
     * 将指定字符串转换为Double对象
     * @param value 需要转换的字符串
     * @return 如果value为null，或格式不是Double类型，返回0.0D，否则返回value内容的Double值
     * @author 
     */
    public static Double toDouble(String value) {
        if (isFloat(value)) {
            return Double.valueOf(value);
        } else {
            return 0.0D;
        }
    }
    
    /**
     * 将指定字符串转换为Integer对象
     * @param value 需要转换的字符串
     * @return 如果value为null，或格式不是Integer类型，返回0，否则返回value内容的Integer值
     * @author 
     */
    public static Integer toInteger(String value) {
        if (isInteger(value)) {
            return Integer.valueOf(value);
        } else {
            return 0;
        }
    }
    
    /**
     * 将指定日期的时间单位清零后返回
     * @param date 日期对象
     * @return 返回时间单位被清零后的date，即日期信息为 <b>yyyy-MM-dd 00:00:00</b>
     * @throws IllegalArgumentException date为空时抛出
     * @author 
     */
    public static Date clearDateTime(Date date) {
        if (date == null) throw new IllegalArgumentException("date不允许为空");
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * 将当前日期的时间清零后返回
     * @return 返回时间单位被清零后的当前日期，即日期信息为 <b>yyyy-MM-dd 00:00:00</b>
     * @author 
     */
    public static Date clearDateTime() {
        return clearDateTime(new Date());
    }
    
    /**
     * 计算日期天数差，该计算忽略时间差
     * @param cal1 第一个日期
     * @param cal2 第二个日期
     * @return 相差天数，若返回0，则表示同一天
     * @throws IllegalArgumentException cal1或cal2为空时抛出
     * @author 
     */
    public static int dayDiff(Calendar cal1, Calendar cal2) {
        if (cal1 == null) throw new IllegalArgumentException("cal1不允许为空");
        if (cal2 == null) throw new IllegalArgumentException("cal2不允许为空");
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DAY_OF_MONTH));
        long millis1 = c.getTimeInMillis();
        c.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH));
        long millis2 = c.getTimeInMillis();
        return Long.valueOf(Math.abs(millis1 - millis2) / 86400000).intValue();
    }
    
    /**
     * 计算日期天数差，该计算忽略时间差
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return 相差天数，若返回0，则表示同一天
     * @throws IllegalArgumentException date1或date2为空时抛出
     * @author 
     */
    public static int dayDiff(Date date1, Date date2) {
        if (date1 == null) throw new IllegalArgumentException("date1不允许为空");
        if (date2 == null) throw new IllegalArgumentException("date2不允许为空");
        Calendar c1 = Calendar.getInstance(Locale.CHINA);
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance(Locale.CHINA);
        c2.setTime(date2);
        return dayDiff(c1, c2);
    }
    
    /**
     * 计算日期天数差，该计算忽略时间差
     * @param cal 第一个日期
     * @param date 第二个日期
     * @return 相差天数，若返回0，则表示同一天
     * @throws IllegalArgumentException cal或date为空时抛出
     * @author 
     */
    public static int dayDiff(Calendar cal, Date date) {
        if (cal == null) throw new IllegalArgumentException("cal不允许为空");
        if (date == null) throw new IllegalArgumentException("date不允许为空");
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(date);
        return dayDiff(cal, c);
    }
    
    /**
     * 计算给出的日期相关sum天的日期
     * @param date 指定日期
     * @param sum 相差天数，负数就是前几天
     * @return 相差sum天的日期
     * @throws IllegalArgumentException date为空时抛出
     * @author 
     */
    public static Date dayDiff(Date date, int sum) {
        if (date == null) throw new IllegalArgumentException("date不允许为空");
        return new Date(date.getTime() + (sum * 86400000l));
    }
    
    /**
     * 计算给出的日期相关sum天的日期字符串
     * @param date 指定日期 <b>yyyy-MM-dd</b> 或 <b>yyyy/MM/dd</b>
     * @param sum 相差天数，负数就是前几天
     * @return 相差sum天的日期 <b>yyyy-MM-dd</b>
     * @throws IllegalArgumentException date格式错误
     * @author 
     */
    public static String dayDiff(String date, int sum) {
        Date d = FormatUtils.toDate(date);
        if (d == null) throw new IllegalArgumentException("date格式错误");
        return FormatUtils.dateString(dayDiff(d, sum));
    }
    
    /**
     * 根据给出星期规则从指定日期范围中获得所有符合规则的日期集合<br>
     * 如指定周一，周二，则从指定日期范围中，挑出所有周一和周二的日期
     * @param week 日期规则 一个包含1-7的字符串，1-7分别对应周一至周日，如：1347或1,3,4,7
     * @param beginDate 开始日期
     * @param endDate 结束日期（含）
     * @return 符合week指定的日期集合
     * @author 
     */
    public static List<Date> weekDate(String week, Date beginDate, Date endDate) {
        List<Date> datelist = new ArrayList<Date>();
        Calendar begdate = Calendar.getInstance(Locale.CHINA);
        Calendar enddate = Calendar.getInstance(Locale.CHINA);
        begdate.setTime(beginDate);
        enddate.setTime(endDate);
        int weekday;
        while (begdate.before(enddate) || begdate.equals(enddate)) {
            weekday = begdate.get(Calendar.DAY_OF_WEEK) - 1;
            weekday = weekday == 0 ? 7 : weekday;
            if (week.contains(String.valueOf(weekday))) {
                datelist.add(begdate.getTime());
            }
            begdate.add(Calendar.DAY_OF_MONTH, 1);
        }
        return datelist;
    }
    
    /**
     * 根据给出星期规则从指定日期范围中获得所有符合规则的日期集合<br>
     * 如指定周一，周二，则从指定日期范围中，挑出所有周一和周二的日期
     * @param week 日期规则 一个包含1-7的字符串，1-7分别对应周一至周日，如：1347或1,3,4,7
     * @param beginDate 开始日期 <b>yyyy-MM-dd</b>
     * @param endDate 结束日期（含） <b>yyyy-MM-dd</b>
     * @return 符合week指定的日期集合
     * @throws IllegalArgumentException beginDate或endDate为空或不符合指定格式时抛出
     * @author 
     */
    public static List<Date> weekDate(String week, String beginDate, String endDate) {
        Date begdate = FormatUtils.toDate(beginDate);
        Date enddate = FormatUtils.toDate(endDate);
        if (begdate == null) throw new IllegalArgumentException("begdate格式错误");
        if (enddate == null) throw new IllegalArgumentException("enddate格式错误");
        return weekDate(week, begdate, enddate);
    }
    
    /**
     * 获取字符串字节长度，针对数据库保存时准确的判断字符长度
     * @param value 要判断的字符(半角字母、数字、英文符号算1个字符，其他算2个字符)
     * @return 字符串的字节总长度
     * @author 
     */
    public static int bytesLength(String value) {
        return bytesLength(value, 2);
    }
    
    /**
     * 获取字符串字节长度，针对数据库保存时准确的判断字符长度
     * @param value 要判断的字符(半角字母、数字、英文符号算1个字符，其他算repLen指定的长度字符)
     * @param repLen 非半角字母数字等符号换算为多少长度
     * @return 字符串的字节总长度
     * @author 
     */
    public static int bytesLength(String value, int repLen) {
        if (value == null) return 0;
        StringBuilder len = new StringBuilder();
        for (int i = 0; i < repLen; i++) {
            len.append("*");
        }
        return value.replaceAll("[^\\x00-\\xff]", len.toString()).length();
    }
    
    /**
     * 判断是否UUID字符串方法
     * @param uid
     * @return 如果uid为UUID字符串则返回true，否则返回false
     * @author 
     */
    public static boolean isUid(String uid) {
        if (null == uid) return false;
        return uid.matches("^[\\w&&[^_]]{4}([\\w&&[^_]]{4}-){4}[\\w&&[^_]]{12}");
    }
    
    /**
     * 生成随机字符串（仅含数字及大小写字母）
     * @param length 随机字符串长度
     * @return 随机字符串
     * @author 
     */
    public static String randomString(int length) {
        return randomString("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", length);
    }
    
    /**
     * 生成随机字符串（仅含数字）
     * @param length 随机字符串长度
     * @return 随机字符串
     * @author 
     */
    public static String randomNumber(int length) {
        return randomString("0123456789", length);
    }
    
    /**
     * 生成随机字符串方法
     * 
     * <pre>
     * 示例
     * randomString("abc123ABC", 3) = "2Ba" 
     * randomString("#$%UZefop", 5) = "%Zfe#"
     * </pre>
     * @param range 随机字符串候选值
     * @param length 随机字符串长度
     * @return 随机字符串
     * @author 
     */
    public static String randomString(String range, int length) {
        Random rd = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(range.charAt(rd.nextInt(range.length())));
        }
        return result.toString();
    }
    
    
    /**
	 * 获取指定日期的后 d 天
	 * @param specifiedDay
	 *  * @param pattern  时间格式  yy-MM-dd    yyyy-MM-dd HH:mm:ss
	 * @param d
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay,Integer d ,String pattern) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat(pattern).parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + d);

		String dayAfter = new SimpleDateFormat(pattern).format(c.getTime());
		return dayAfter;
	}
	
	/***
	 * 获取某个时间段内的所有天数
	 * @param dBegin
	 * @param dEnd
	 * @return
	 */
	public static List<Date> findDates(Date dBegin, Date dEnd)  
	 {  
	  List lDate = new ArrayList();  
	  lDate.add(dBegin);  
	  Calendar calBegin = Calendar.getInstance();  
	  // 使用给定的 Date 设置此 Calendar 的时间  
	  calBegin.setTime(dBegin);  
	  Calendar calEnd = Calendar.getInstance();  
	  // 使用给定的 Date 设置此 Calendar 的时间  
	  calEnd.setTime(dEnd);  
	  // 测试此日期是否在指定日期之后  
	  while (dEnd.after(calBegin.getTime()))  
	  {  
	   // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
	   calBegin.add(Calendar.DAY_OF_MONTH, 1);  
	   lDate.add(calBegin.getTime());  
	  }  
	  return lDate;  
	 }  
	
	
	 /** 
     * 根据日期获得星期 
     * @param date 
     * @return 
     */ 
	public static String getWeekOfDate(Date date) { 
		String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" }; 
//	  String[] weekDaysName = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }; 
	  String[] weekDaysCode ={ "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" }; 
//	  String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" }; 
	  Calendar calendar = Calendar.getInstance(); 
	  calendar.setTime(date); 
	  int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; 
	  return weekDaysCode[intWeek]; 
	} 
	
	/**
	 * 星期数
	 * 
	 * @return
	 */
	public static String getNumberToWeek(int inNumber) {
		String strWeek = "天";
		switch (inNumber) {
		case 6:
			strWeek = "六";
			break;
		case 5:
			strWeek = "五";
			break;
		case 4:
			strWeek = "四";
			break;
		case 3:
			strWeek = "三";
			break;
		case 2:
			strWeek = "二";
			break;
		case 1:
			strWeek = "一";
			break;
		default:
			strWeek = "日";
			break;
		}
		return strWeek;
	}
	
	/**
	 * 获取唯一性id
	 * 
	 * @return String 唯一uuid
	 */
	public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString(); 
        String uuidStr=str.replace("-", "");
        return uuidStr;
      }
	/**
	 * 
	 * 字符串转时间戳(格式yyyy-MM-dd HH:mm:ss)
	 * @return long 时间戳
	 */
	public static long getTimeStamp(String user_time) {  
//		String re_time = null;  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		long l = 0;  
//		try {  
		  
		  
		try {
			l = sdf.parse(user_time).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
//		long l = d.getTime();  
//		re_time = l+"";  
////		re_time = str.substring(0, 10);  
//		  
//		  
//		} catch (ParseException e) {  
//		// TODO Auto-generated catch block  
//		e.printStackTrace();  
//		}  
		return l;  
	} 
	/**
	 * 
	 * date转字符串(格式yyyy-MM-dd HH:mm:ss)
	 * @return String 中文日期
	 */
	public static String getDateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		return sdf.format(date);
	}
	
	/**
	 * 根据项目id加密生成token(项目id+时间戳)
	 */
	public static String getToken(String id) {
		String key = ConstantUtils.getConstant("lanlyc_key");
		String token = EncryptionUtils.encryptAESBase64(id + DateUtils.getCurrenTimeStamp(), key);
		return token;
	}
	
	/**
	 * 解密token
	 */
	public static String parseToken(String token) {
		String key = ConstantUtils.getConstant("lanlyc_key");
		String parsetoken = EncryptionUtils.decryptAESBase64(token, key);
		return parsetoken;
	}
	/**
	 * 类转Map<String,Object>
	 * @return Map<String,Object>
	 */
	public static Map<String,Object> ClassToMapObject(Object obj) {
	  Map<String,Object> reMap = new HashMap<String,Object>();
	  if (obj == null) 
	   return null;
	  Field[] fields = obj.getClass().getDeclaredFields();
	  try {
	   for(int i=0;i<fields.length;i++){
	    try {
	     Field f = obj.getClass().getDeclaredField(fields[i].getName());
	     f.setAccessible(true);
	           Object o = f.get(obj);
	           reMap.put(fields[i].getName(), o);
	    } catch (NoSuchFieldException e) {
	     // TODO Auto-generated catch block
	     e.printStackTrace();
	    } catch (IllegalArgumentException e) {
	     // TODO Auto-generated catch block
	     e.printStackTrace();
	    } catch (IllegalAccessException e) {
	     // TODO Auto-generated catch block
	     e.printStackTrace();
	    }
	   }
	  } catch (SecurityException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  } 
	  return reMap;
	}
	/**
	 * 类转Map<String,String>
	 * @return Map<String,String>
	 */
	public static Map<String,String> ClassToMapString(Object obj) {
		  Map<String,String> reMap = new HashMap<String,String>();
		  if (obj == null) 
		   return null;
		  Field[] fields = obj.getClass().getDeclaredFields();
		  try {
		   for(int i=0;i<fields.length;i++){
		    try {
		     Field f = obj.getClass().getDeclaredField(fields[i].getName());
		     f.setAccessible(true);
		           String o = f.get(obj).toString();
		           reMap.put(fields[i].getName(), o);
		    } catch (NoSuchFieldException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    } catch (IllegalArgumentException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    } catch (IllegalAccessException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    }
		   }
		  } catch (SecurityException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  } 
		  return reMap;
		}

	
	/***
	 * @author:huzhihao
	 * @version:v1.0
	 * @Package cn.com.gongdi.base.util
	 * @Description: 字符串转map
	 * @param:
	 * @return:
	 * @date:2019年5月8日 下午5:35:39
	 */
	public static Map<String,String> getStringToMap(String str){
		//感谢bojueyou指出的问题
		//判断str是否有值
		if(null == str || "".equals(str)){
			return null;
		}
		//根据,截取
		String[] strings = str.replace("\"", "")
				.replace("{", "")
				.replace("}", "")
				.split(",");
		//设置HashMap长度
		int mapLength = strings.length;
		//判断hashMap的长度是否是2的幂。
		if((strings.length % 2) != 0){
			mapLength = mapLength+1;
		}

		Map<String,String> map = new HashMap<>(mapLength);
		try {
			//循环加入map集合
			for (int i = 0; i < strings.length; i++) {
				//截取一组字符串
				String[] strArray = strings[i].split(":");
				//strArray[0]为KEY  strArray[1]为值
				map.put(strArray[0],strArray[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}
	
	
	/***
	 * @author:huzhihao
	 * @version:v1.0
	 * @Package cn.com.gongdi.base.util
	 * @Description: Map转 Object
	 * @param:
	 * @return:
	 * @date:2019年12月18日 上午10:47:53
	 */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)  
            return null;    
  
        Object obj = beanClass.newInstance();  
  
        Field[] fields = obj.getClass().getDeclaredFields();   
        for (Field field : fields) {    
            int mod = field.getModifiers();    
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
                continue;    
            }    
  
            field.setAccessible(true);    
            field.set(obj, map.get(field.getName()));   
        }   
  
        return obj;    
    }   


    
	/**
	 * 
	 * 构建Cron表达式
	 * 
	 * @Description: Cron表达式工具类 目前支持三种常用的cron表达式 1.每天的某个时间点执行 例:12 12 12 * *
	 *               ?表示每天12时12分12秒执行 2.每周的哪几天执行 例:12 12 12 ? * 1,2,3表示每周的周1周2周3
	 *               ,12时12分12秒执行 3.每月的哪几天执行 例:12 12 12 1,21,13 * ?表示每月的1号21号13号
	 *               12时12分12秒执行
	 * @param rate
	 *            频率 0秒；1分；2小时；3日；4月
	 * @param cycle
	 *            周期
	 * @return String
	 */
	public static String createLoopCronExpression(int rate, String cycle) {
		String cron = "";
		switch (rate) {
		case 0:// 每cycle秒执行一次
			cron = "0/" + cycle + " * * * * ?";
			break;
		case 1:// 每cycle分钟执行一次
			cron = "0 0/" + cycle + " * * * ?";
			break;
		case 2:// 每cycle小时执行一次
			cron = "0 0 0/" + cycle + " * * ?";
			break;
		case 3:// 每cycle天的0点执行一次
			cron = "0 0 0 1/" + cycle + " * ?";
			break;
		case 4:// 每cycle月的1号0点执行一次
			cron = "0 0 0 1 1/" + cycle + " ? ";
			break;
		case 5:// 每天cycle点执行一次
			cron = "0 " + cycle + " * * ?";
			break;
		}
		return cron;
	}
	
	
	
	/***
	 * 判断两个List<Map> 中的数据是否一致
	 */
	private static final Integer INTEGER_ONE = 1;
	public static boolean isEqualCollection(Collection a, Collection b) {
		if (a.size() != b.size()) { // size是最简单的相等条件
			return false;
		}
		Map mapa = getCardinalityMap(a);
		System.out.println(mapa);
		Map mapb = getCardinalityMap(b);
		System.out.println(mapb);

		// 转换map后，能去掉重复的，这时候size就是非重复项，也是先决条件
		if (mapa.size() != mapb.size()) {
			System.out.println("存储的map数据不一致！");
			return false;
		} else {
			System.out.println("存储的map数据一致！");
		}
		Iterator it = mapa.keySet().iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			// 查询同一个obj，首先两边都要有，而且还要校验重复个数，就是map.value
			if (getFreq(obj, mapa) != getFreq(obj, mapb)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 以obj为key，可以防止重复，如果重复就value++ 这样实际上记录了元素以及出现的次数
	 */
	public static Map getCardinalityMap(Collection coll) {
		Map count = new HashMap();
		for (Iterator it = coll.iterator(); it.hasNext();) {
			Object obj = it.next();
			Integer c = (Integer) count.get(obj);
			if (c == null)
				count.put(obj, INTEGER_ONE);
			else {
				count.put(obj, new Integer(c.intValue() + 1));
			}
		}
		return count;
	}

	private static final int getFreq(Object obj, Map freqMap) {
		Integer count = (Integer) freqMap.get(obj);
		if (count != null) {
			return count.intValue();
		}
		return 0;
	}

	
	
	

	/**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
    
 // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔), 
    // 字符串在编译时会被转码一次,所以是 "\\b" 
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔) 
    static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
      +"|windows (phone|ce)|blackberry"
      +"|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
      +"|laystation portable)|nokia|fennec|htc[-_]"
      +"|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b"; 
    static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
      +"|[1-4][0-9]{2}x[1-4][0-9]{2})\\b"; 
    //移动设备正则匹配：手机端、平板 
    static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE); 
    static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE); 
    
    /** 
     * 检测是否是移动设备访问 
     * 
     * @Title: check 
     * @Date : 
     * @param userAgent 浏览器标识 
     * @return 2:移动设备接入，1:pc端接入 
     */
	public static Integer check(String userAgent) {
		if (null == userAgent) {
			userAgent = "";
		}
		// 匹配
		Matcher matcherPhone = phonePat.matcher(userAgent);
		Matcher matcherTable = tablePat.matcher(userAgent);
		if (matcherPhone.find() || matcherTable.find()) {
			return 2;
		} else {
			return 1;
		}
	}
}
