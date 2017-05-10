package com.pipi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * xml解析工具类
 * @author liuyang
 */
public class XMLUtil {
	/**log4j 日志处理对象*/
	public final static Logger log = Logger.getLogger(XMLUtil.class);
	
	/** 得到指定路径下的xml的document */
	private static Document getDocument(String xmlPath){
		Document document = null;
		try{
			document =  new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlPath));
		}
		catch(DocumentException e){
			log.error("解析xml错误");
			e.printStackTrace();
		}
		return document;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getDictionaryTypeList(){
		List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
		Document document = getDocument("com\\pipi\\common\\xml\\dictionary.xml");
		List<Element> dictionaryItemList = document.selectNodes("/dictionary/dictionary-item");
		if(dictionaryItemList != null)
			for (Element dictionaryItem : dictionaryItemList) {
				Element nameElement = (Element) dictionaryItem.selectSingleNode("name");
				Element codeElement = (Element) dictionaryItem.selectSingleNode("code");
				
				Map<String,Object> dictionaryMap = new HashMap<String, Object>();
				dictionaryMap.put("text", nameElement.getText());
				dictionaryMap.put("code", codeElement.getText());
				dictionaryMap.put("leaf", true);
				ret.add(dictionaryMap);
			}
		
		return ret;
	}
}
