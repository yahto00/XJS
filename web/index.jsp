<%@page import="com.pipi.entity.admin.User"%>
<%@page import="com.pipi.common.constant.SystemConstant"%>
<%
	User user = (User)request.getSession(true).getAttribute(SystemConstant.CURRENT_USER);
	String path = request.getContextPath();
	if(user == null){
		response.sendRedirect(path+"/pages/login.do");
	}
	else if(user.getRoles().contains(1)
			|| user.getRoles().contains(5)
			|| user.getRoles().contains(6)){
		response.sendRedirect(path+"/pages/sub_station.do");
	}
	else if(user.getRoles().contains(2)
			|| user.getRoles().contains(7)
			|| user.getRoles().contains(8)){
		response.sendRedirect(path+"/pages/transfer_station.do");
	}
	else if(user.getRoles().contains(3)){
		response.sendRedirect(path+"/pages/master_station.do");
	}
	else if(user.getRoles().contains(4)){
		response.sendRedirect(path+"/pages/site_manage.do");
	}
	else if(user.getRoles().contains(9)){
		response.sendRedirect(path+"/pages/master_station_localcity.do");
	}
%>