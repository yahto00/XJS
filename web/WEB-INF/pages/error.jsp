<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>异常处理页面</title>
</head>
<body>
	<% Exception ex = (Exception) request.getAttribute("Exception");%>
	<H2>
		Exception:<%=ex.getMessage()%>
	</H2>
</body>
</html>