package com.pipi.common.session;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/**
 * session容器，方便管理session
 * @author liuyang
 */
public class MySessionContext {
	private static HashMap<String, HttpSession> mymap = new HashMap<String, HttpSession>();

	public static synchronized void AddSession(HttpSession session) {
		if (session != null) {
			mymap.put(session.getId(), session);
		}
	}

	public static synchronized void DelSession(HttpSession session) {
		if (session != null) {
			mymap.remove(session.getId());
		}
	}

	public static synchronized HttpSession getSession(String session_id) {
		if (session_id == null)
			return null;
		return mymap.get(session_id);
	}
}