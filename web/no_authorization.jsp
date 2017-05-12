<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/in.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit" />
<title>没有访问权限</title>
<script type="text/javascript">
$(function(){
}); 
</script>
</head>
<body style="margin:0px">
	hello ${current_user.userName }<br/>
	您没有访问权限
	<a href="pages/login.html">登录</a>
</body>
</html>
