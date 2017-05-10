package com.pipi.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author liuyang
 */
public class Ufn extends Properties {
	
	private static final long serialVersionUID = -1329441935505279007L;
	private static final DecimalFormat NUMBER_DOT1 = new DecimalFormat("0.#");	// 如果最后一位小数为0就不显示
	private static final DecimalFormat NUMBER_DOT2 = new DecimalFormat("0.00");
	private static final DecimalFormat NUMBER_DOT3 = new DecimalFormat("0.000");
	private static final DecimalFormat NUMBER_DOT4 = new DecimalFormat("0.0000");
	private static final Map<String, SimpleDateFormat> dateFormatMap = new HashMap<String, SimpleDateFormat>();
	
	public static Date parseDate(String str, String format) {
		if (str == null || "".equals(str)){
			return null;
		}
		SimpleDateFormat sdf = dateFormatMap.get(format);
		if (null == sdf) {
			sdf	= new SimpleDateFormat(format, Locale.ENGLISH);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			dateFormatMap.put(format, sdf);
		}
		try {
			synchronized(sdf){
				// SimpleDateFormat is not thread safe
				return sdf.parse(str);
			}
		} catch	(ParseException	pe) {
		}
		return null;
	}
	public static Date parseDate(Object value){
		if (value == null)
			return null;
		if (value instanceof Date)
			return (Date)value;
		if (value instanceof String)
			return parseDate(value);
		if (value instanceof Integer)
			return new Date((Integer)value);
		if (value instanceof Long)
			return new Date((Long)value);
		return null;
	}
	// 取得距离现在n天以前的时间
	public static Date beforeDate(int days){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -days);
		return calendar.getTime();
	}
	
	// 比较两个日期是否相等
	public static boolean equal(Date date1, Date date2){
		if (date1 == null || date2 == null)
			return false;
		GregorianCalendar d1 = new GregorianCalendar();
		d1.setTime(date1);
		GregorianCalendar d2 = new GregorianCalendar();
		d2.setTime(date2);
		return d1.get(Calendar.DAY_OF_YEAR) == d2.get(Calendar.DAY_OF_YEAR);
	}
	// 字符串比较，空字符串等于null
	public static boolean equal(String str1, String str2){
		if (str1 == null || str1.length() == 0)
			return str2 == null || str2.length() == 0;
		if (str2 == null || str2.length() == 0)
			return false;
		return str1.equals(str2);
	}
	
	// 输出小数格式
	public static String format(Double db, Integer rate){
		if (db == null) return null;
		if (rate == null) rate = 2;	// 默认输入2位小数
		switch(rate){
		case 0: return Integer.toString(db.intValue());
		case 1: return NUMBER_DOT1.format(db);
		case 2: return NUMBER_DOT2.format(db);
		case 3: return NUMBER_DOT3.format(db);
		case 4: return NUMBER_DOT4.format(db);
		}
		return db.toString();
	}
	
	// 替换source中包含${var}变量的字符串
	public static String replace(String source, Map<String, String> vars){
		if (source == null || vars == null)
			return source;
		Pattern pattern = Pattern.compile("\\$\\{([\\w]+)}");
		Matcher matcher = pattern.matcher(source);
		// 开始替换
		int p0 = 0;
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()){
			buffer.append(source.substring(p0, matcher.start()));
			// 取得第1个括号里边的匹配项, 0表示整个匹配项
			String value = vars.get(matcher.group(1));
			if (value != null)
				buffer.append(value);
			p0 = matcher.end();
		}
		// 检查末尾
		if (p0 < source.length())
			buffer.append(source.substring(p0));
		return buffer.toString();
	}
	// 把字符串转为数字
	public static Integer parseInt(String str){
		if (str == null || str.equals(""))
			return null;
		str = str.trim();
		int nLen = str.length();
		if (nLen == 0)
			return null;
		Integer value = null;
		try {
			value = Integer.parseInt(str, 10);
		}catch (NumberFormatException e){
		}
		return value;
	}
	public static int parseInt(String str, int nullValue){
		Integer value = Ufn.parseInt(str);
		return value == null ? nullValue : value;
	}
	// 把对象转为整数
	public static Integer parseInt(Object value){
		if (value == null)
			return null;
		if (value instanceof Integer)
			return (Integer)value;
		if (value instanceof Long)
			return ((Long)value).intValue();
		if (value instanceof Byte)
			return ((Byte)value).intValue();
		if (value instanceof Float)
			return ((Float)value).intValue();
		if (value instanceof Double)
			return ((Double)value).intValue();
		if (value instanceof java.math.BigDecimal)
			return ((java.math.BigDecimal)value).intValue();
		if (value instanceof String)
			return Ufn.parseInt((String)value);
		if (value.getClass().isArray())
			return Ufn.parseInt(((Object[])value)[0]);
		return Ufn.parseInt(value.toString());	// 转为字符串后再转
	}
	// 把对象转换为浮点型
	public static Float parseFloat(Object value){
		if (value == null)
			return null;
		if (value instanceof Float)
			return (Float)value;
		if (value instanceof Integer)
			return ((Integer)value).floatValue();
		if (value instanceof Double){
			return ((Double)value).floatValue();
		}
		try {
			return Float.parseFloat(value.toString());
		} catch (NumberFormatException e){}
		return null;
	}
	public static Float parseFloat(Object value, float nullValue){
		Float fValue = parseFloat(value);
		return fValue == null ? nullValue : fValue;
	}
	// 把对象转为boolean
	public static boolean parseBoolean(Object value){
		if (value == null)
			return false;
		if (value instanceof Boolean)
			return (Boolean)value;
		if (value instanceof Integer)
			return ((Integer)value) > 0;
		if (value instanceof String){
			String temp = ((String)value).trim();
			if (temp.equals("")) return false;
			if (Character.isDigit(temp.charAt(0)))
				return Ufn.parseInt(temp, 0) > 0;
			return Boolean.parseBoolean(temp);
		}
		return false;
	}
	
	// 判断字符串是否为数字
	public static boolean isNumeric(String text){
		if (text == null || text.equals(""))
			return false;
		int length = text.length();
		for (int i=0; i<length; i++)
			if (!Character.isDigit(text.charAt(i)))
				return false;
		return true;
	}
	// 转为utf-8字符串
	public static String iso8859toUTF8(String text){
		if (text == null || text.equals(""))
			return text;
		try {
			//if (GBK_Encoder.canEncode(text)){
				byte[] buf = text.getBytes("ISO8859-1");
				text = new String(buf, "UTF-8");
			//}
		} catch (UnsupportedEncodingException e) {
		}
		return text;
	}

	// 把List的字符串值连接成字符串
	public static String join(Collection<?> list){
		if (list == null)
			return null;
		StringBuffer buf = new StringBuffer();
		Iterator<?> iter = list.iterator();
		boolean comma = false;
		while (iter.hasNext()) {
			if (comma)
				buf.append(',');
			else
				comma = true;
			buf.append(iter.next());
		}
		return buf.toString();
	}
	// 把数组连接成字符串
	public static String join(Object array){
		if (array == null)
			return null;
		if (array.getClass().isArray()){
			StringBuffer buf = new StringBuffer();
			int length = Array.getLength(array);
			for (int i=0; i<length; i++) {
            	Object value = Array.get(array, i);
    			if (i != 0)
    				buf.append(',');
    			buf.append(value);
			}
			return buf.toString();
		}
		return array.toString();
	}
	// 在数组中查找字符串
	public static int inArray(String elem, String[] array){
		if (elem == null || array == null || array.length == 0)
			return -1;
		for (int i=0; i<array.length; i++){
			if (elem.equals(array[i]))
				return i;
		}
		return -1;
	}

	// 转为驼峰命名法
	public static String toCamelCase(String name){
		if (name == null || name.equals("") || name.indexOf('_') == -1)
			return name;
		String[] res = name.split("_");
		if (res.length == 1)
			return res[0];
		if (res.length == 2)
			return res[0] + Character.toUpperCase(res[1].charAt(0)) + res[1].substring(1);
		
		StringBuffer buffer = new StringBuffer(res[0]);
		for (int i=1; i<res.length; i++){
			buffer.append(Character.toUpperCase(res[i].charAt(0)));
			buffer.append(res[i].substring(1));
		}
		return buffer.toString();
	}
	// 替换 html 符号
	public static String replaceHTML(String content){
		if (content == null || content.length() < 3)
			return content;
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll(">", "&gt;");
		return addHerfTag(content);
	}

	//找出url并加上html标签，不能处理有www.baidu.com和http://www.baidu.com并存的情况，还有若是www.baidu.com很有可能是相对路径，解决不了问题
	/*public static String addHerfTag(String content){
		Pattern p = Pattern.compile("(((ht|f)tp(s?))\\://)?(www.|[a-zA-Z].)[a-zA-Z0-9\\-\\.]+\\.(com|edu|gov|mil|net|org|biz|info|name|museum|us|ca|uk)(\\:[0-9]+)*(/($|[a-zA-Z0-9\\.\\,\\;\\?\\'\\+&amp;%\\$#\\=~_\\-]+))*");
        Matcher m = p.matcher(content);
        Set<String> strs = new HashSet<String>();
        while (m.find()) {
        	String str = m.group();
        	boolean flag = false;
        	for(String s : strs){
        		if(s.contains(str) || str.contains(s)){
        			flag = true;
        			break;
        		}
        	}
        	if(flag)
        		continue;
            strs.add(str); 
        } 
        for (String s : strs){
            content = content.replaceAll(s, "<a href=\""+s+"\" >"+s+"</a>");
        }
        return content;
	}*/

	//找出url并加上html标签
	public static String addHerfTag(String content){
		Pattern p = Pattern.compile("(((ht|f)tp(s?))\\://)?(www.|[a-zA-Z].)[a-zA-Z0-9\\-\\.]+\\.(com|cn|edu|gov|mil|net|org|biz|info|name|museum|us|ca|uk)(\\:[0-9]+)*(/($|[a-zA-Z0-9\\.\\,\\;\\?\\'\\+&amp;%\\$#\\=~_\\-]+))*");
        Matcher m = p.matcher(content);
        //匹配到的url地址
        String matchStr = null;
        //content剩下未处理的子串
        String temCon = content;

        //新生成的加了链接的待return的字符串
        StringBuffer sb = new StringBuffer();
    	String append = "";
    	
    	int index = 0;
        while (m.find()) {
        	matchStr = m.group();
        	index = temCon.indexOf(matchStr) + matchStr.length();
        	
        	String temSubstring = temCon.substring(0, index);
        	if(matchStr.startsWith("http") || matchStr.startsWith("ftp"))
        		append = temSubstring.replace(matchStr, "<a href=\""+matchStr+"\" >"+matchStr+"</a>");
        		//append = temSubstring.replaceAll(matchStr, "<a href=\""+matchStr+"\" >"+matchStr+"</a>");
        	else
        		append = temSubstring.replace(matchStr, "<a href=\"http://"+matchStr+"\" >"+matchStr+"</a>");
        		//append = temSubstring.replaceAll(matchStr, "<a href=\"http://"+matchStr+"\" >"+matchStr+"</a>");
        	sb.append(append);
        	temCon = temCon.substring(index);
        }
        return sb.append(temCon).toString();
        
	}

	// 分割queryString，取得Map格式存储的参数列表
	public static Map<String, String> getParameterMap(String source) {
		if (source == null || source.equals(""))
			return null;
		Map<String, String> buf = new HashMap<String, String>();
		int p0 = source.indexOf('?');
		if (p0 == -1)
			p0 = 0;
		else
			p0 = p0 + 1;
		
		int length = source.length();
		while (p0 < length){
			int p = source.indexOf('=', p0);
			if (p == -1)
				break;		// 遇到无效的变量
			String name = source.substring(p0, p);
			//System.out.println(name);
			p0 = p + 1;
			if (p0 == length){	// 到达末尾
				buf.put(name, null);
				break;
			}
			// 查找变量值的结束位置
			p = source.indexOf('&', p0);
			if (p == -1){
				buf.put(name, source.substring(p0));
				break;
			}
			// 变量值为空
			if (p == p0)
				buf.put(name, null);
			else
				buf.put(name, source.substring(p0, p));
			p0 = p + 1;
		}
		return buf;
	}
	// 解析json对象
	public static JsonNode readTree(String content){
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = null;
		try {
			rootNode = mapper.readTree(content);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return rootNode;
	}
	// 创建分区
	public static Map<String, String> createSection(String content){
		Pattern pattern = Pattern.compile("^<p>\\[([^]]+)]</p>$", Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(content);
		// 开始替换
		int p0 = 0;
		String name = null;
		Map<String, String> section = new HashMap<String, String>();
		while (matcher.find()){
			if (name != null)
				section.put(name, content.substring(p0, matcher.start()).trim());
			// 取得第1个括号里边的匹配项, 0表示整个匹配项
			name = matcher.group(1);
			p0 = matcher.end();
		}
		// 检查末尾
		if (p0 < content.length() && name != null)
			section.put(name, content.substring(p0).trim());
		return section;
	}
	// 创建属性表，delimiter 是属性之间的分隔符，symbol 是属性名和属性值的分隔符
	public static Map<String, String> createItemMap(String content, String delimiter, char symbol){
		Map<String, String> itemMap = new HashMap<String, String>();
		String[] lines = content.split(delimiter);
		for (int i=0; i<lines.length; i++){
			String line = lines[i];
			int p = line.indexOf(symbol);
			if (p != -1){
				String value = line.substring(p + 1).trim();
				if (value.equals(""))
					value = null;
				itemMap.put(line.substring(0, p).trim(), value);
			}
		}
		return itemMap;		
	}
	// 使用 symbol 字符串中的一个字符来分割
	public static Map<String, String> createItemMap(String content, String delimiter, String symbol){
		if (content == null || content.equals(""))
			return null;
		// 去掉开头的  html 标记
		if (content.charAt(0) == '<'){
			int p0 = content.indexOf('>');
			if (p0 != -1){
				p0 += 1;
				int p = content.indexOf('<', p0);
				content = p == -1 ? content.substring(p0) : content.substring(p0, p);
			}
		}
		Map<String, String> itemMap = new HashMap<String, String>();
		String[] lines = content.split(delimiter);
		int length = delimiter.length();
		for (int i=0; i<lines.length; i++){
			String line = lines[i];
			int p = -1;
			for (int j=0; j<length; j++){
				p = line.indexOf(symbol.charAt(j));
				if (p != -1)
					break;
			}
			if (p != -1)
				itemMap.put(line.substring(0, p).trim(), line.substring(p + 1).trim());
		}
		return itemMap;
	}
	// 加密字符串算法
	public static String encodeMd5(String source){
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(source.getBytes());
			//BASE64Encoder encoder = new BASE64Encoder();
			byte[] keyBytes = md5.digest();
			StringBuffer buf = new StringBuffer();
			for (int i=0; i<keyBytes.length; i++)
				buf.append(Integer.toString(keyBytes[i] & 0xff, 16));	// java的byte类型是有符号的
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
