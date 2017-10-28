package com.pipi.common.session;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/**
 * session容器
 * Created by yahto on 07/05/2017.
 */
public class MySessionContext {
    public static HashMap<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();

    public static synchronized void AddSession(HttpSession session) {
        if (session != null) {
            session.setMaxInactiveInterval(60*180);//设置session三小时失效
            sessionMap.put(session.getId(), session);
        }
    }

    public static synchronized void DelSession(HttpSession session) {
        if (session != null) {
            sessionMap.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String session_id) {
        if (session_id == null)
            return null;
        return sessionMap.get(session_id);
    }
}